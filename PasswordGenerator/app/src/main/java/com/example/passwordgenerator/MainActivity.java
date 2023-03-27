package com.example.passwordgenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        ServiceConnection {

    private PasswordGeneratorService passwordGeneratorService;
    Intent intent;
   final static String arrayResult = "result";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    protected void onResume() {
        super.onResume();
        intent= new Intent(this, PasswordGeneratorService.class);
        bindService(intent, this , Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(this);
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder
            iBinder) {
        PasswordGeneratorService.MyBinder b = (PasswordGeneratorService.MyBinder) iBinder;
        passwordGeneratorService = b.getService();
        Toast.makeText(MainActivity.this, "Connected",

                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        passwordGeneratorService=null;
    }

    public void genenatePasswordActivity(View view){
        int letrasMaiusculas = Integer.parseInt(((EditText)findViewById(R.id.letraMaiuscula)).getText().toString());
        int letrasMinusculas = Integer.parseInt(((EditText)findViewById(R.id.letrasMinusculas)).getText().toString());
        int letrasEspeciais = Integer.parseInt(((EditText)findViewById(R.id.letrasEspeciais)).getText().toString());
        int numeros = Integer.parseInt(((EditText)findViewById(R.id.numeros)).getText().toString());
        ArrayList<String> result = passwordGeneratorService.returnListPasswords(letrasMaiusculas, letrasMinusculas, letrasEspeciais, numeros);

        Intent intent = new Intent(this, result.class);
        intent.putExtra("FILES_TO_SEND", result);
        startActivity(intent);
        finish();


    }
}