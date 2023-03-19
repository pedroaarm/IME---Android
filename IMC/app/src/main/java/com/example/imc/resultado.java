package com.example.imc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class resultado extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);
        Intent intent = getIntent();
        Double imc = intent.getDoubleExtra(MainActivity.imcString, 0.0);
        selectImage(imc);

    }

    private void selectImage(double imc){
        ImageView img= (ImageView) findViewById(R.id.resultadoImg);

        if(imc < 18.5) {
            img.setImageResource(R.drawable.abaixo);
        }if(imc>= 18.5 && imc <=24.9){
            img.setImageResource(R.drawable.normal);
        }if(imc > 25 && imc <= 29.9){
            img.setImageResource(R.drawable.sobrepeso);
        }if(imc >= 30 && imc <= 34.9){
            img.setImageResource(R.drawable.obesidade1);
        }if(imc >= 35 && imc <= 39.9){
            img.setImageResource(R.drawable.obesidade2);
        }if(imc >= 40){
            img.setImageResource(R.drawable.obesidade3);
        }
    }
}