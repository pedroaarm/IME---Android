package com.example.passwordgenerator;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import org.passay.AllowedRegexRule;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

import java.util.ArrayList;
import java.util.List;

public class PasswordGeneratorService extends Service {

    private final IBinder mBinder = new MyBinder();

    public PasswordGeneratorService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    public class MyBinder extends Binder {
        PasswordGeneratorService getService() {
            return PasswordGeneratorService.this;
        }
    }

    public ArrayList<String> returnListPasswords(int letrasMaiusculas, int letrasMinusculas, int caractaeresEspeciais){
        ArrayList<String> passwords = new ArrayList<>();
        for (int i=0;i<10;i++){
            passwords.add(generatePassword(letrasMaiusculas, letrasMinusculas, caractaeresEspeciais));
        }
        return passwords;
    }
    private String generatePassword(int letrasMaiusculas, int letrasMinusculas, int caractaeresEspeciais){
        PasswordGenerator gen = new PasswordGenerator();
        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(letrasMinusculas);

        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(letrasMaiusculas);


        CharacterData specialChars = new CharacterData() {
            public String getErrorCode() {
                return AllowedRegexRule.ERROR_CODE;
            }

            public String getCharacters() {
                return "!@#$%^&*()_+";
            }
        };
        CharacterRule splCharRule = new CharacterRule(specialChars);
        splCharRule.setNumberOfCharacters(caractaeresEspeciais);

        return gen.generatePassword(letrasMaiusculas+letrasMinusculas+caractaeresEspeciais, splCharRule, lowerCaseRule,
                upperCaseRule);
    }
}