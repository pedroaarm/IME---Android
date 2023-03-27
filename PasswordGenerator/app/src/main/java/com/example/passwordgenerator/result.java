package com.example.passwordgenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        ArrayList<String> passwords = intent.getStringArrayListExtra("FILES_TO_SEND");
        String senhaConcat = generateString(passwords);
        popule(senhaConcat);
    }

    private String generateString(ArrayList<String> passwords){

        String senha = "";
        for(int i=0; i<passwords.size(); i++){
            senha += "Senha "+ (i+1) + ": "+ passwords.get(i)+'\n';
        }
        return senha;
    }

    private void popule(String senha){
        final TextView mTextView = (TextView) findViewById(R.id.result);
        mTextView.setText(senha);
    }
}