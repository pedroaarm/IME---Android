package com.example.imc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String imcString = "imcString";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void imc(View view){
        Double altura = Double.parseDouble(((EditText) findViewById(R.id.altura)).getText().toString());
        Integer peso = Integer.parseInt(((EditText) findViewById(R.id.peso)).getText().toString());

        altura = altura/100;
        Double imc = Double.valueOf(peso / (altura*altura));
       // Integer imc2 = peso / (altura*altura);
        Intent intent = new Intent(this, resultado.class);
        intent.putExtra(imcString, imc);
        startActivity(intent);
        finish();

    }
}