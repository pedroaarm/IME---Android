package com.example.wifialert;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.wifialert.data.DataPersistence;
import com.example.wifialert.service.MainService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_CODE = 1;
    private static DataPersistence dataPersitence = new DataPersistence();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!checkPermissions()) {
            return;
        }
        setWifiNameView();
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        scheduler.scheduleAtFixedRate
                (new Runnable() {
                    public void run() {
                        MainService mainService = new MainService();
                        if (!dataPersitence.sendSmsToday(getApplicationContext()) && dataPersitence.getWifiName(getApplicationContext()) != null) {
                            boolean result = mainService.scanAndSendSms(getApplicationContext());
                            if(result)
                                dataPersitence.saveDate(getApplicationContext());
                        }
                    }
                }, 0, 1, TimeUnit.MINUTES);
    }

    public void saveWifiName(View view) {
        String wifiName = (((EditText) findViewById(R.id.wifiName)).getText().toString());
        dataPersitence.saveWifiName(wifiName, this);
        changeTextView();
    }

    public void setWifiNameView() {
        String wifiName = dataPersitence.getWifiName(this);
        if (wifiName != null) {
            TextView meuTextView = findViewById(R.id.wifiName);
            meuTextView.setText(wifiName);
        }
    }

    public void changeTextView() {
        TextView meuTextView = findViewById(R.id.wifiName);
        meuTextView.setText(dataPersitence.getWifiName(getApplicationContext()));
        exibirMensagem(this, "Nome da Rede alterada com sucesso");
    }

    public void exibirMensagem(Context context, String mensagem) {
        Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show();
    }

    private boolean checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.CHANGE_WIFI_STATE") != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_WIFI_STATE") != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, "android.permission.READ_CONTACTS") != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, "android.permission.SEND_SMS") != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_WIFI_STATE", "android.permission.CHANGE_WIFI_STATE", "android.permission.ACCESS_FINE_LOCATION", "android.permission.SEND_SMS", "android.permission.READ_CONTACTS"}, PERMISSIONS_REQUEST_CODE);
            return false;
        }
        return true;
    }
}
