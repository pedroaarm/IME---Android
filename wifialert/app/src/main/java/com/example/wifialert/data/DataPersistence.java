package com.example.wifialert.data;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataPersistence {
    private static final String wifiPreference = "WifiName";
    private static final String datePreference = "dataPreference";

    public String getDate(){
        Date data = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        return  format.format(data);
    }
    public void saveDate(Context context){

        String dataActual = getDate();
        SharedPreferences.Editor editor = context.getSharedPreferences(datePreference, Context.MODE_PRIVATE).edit();
        editor.putString(dataActual, dataActual);
        editor.apply();
    }

    public boolean sendSmsToday(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(datePreference, Context.MODE_PRIVATE);
        String dataActual = preferences.getString(getDate(), null);
        if(dataActual == null){
            return false;
        }
        return true;
    }

    public void saveWifiName(String wifiName, Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences(wifiPreference, Context.MODE_PRIVATE).edit();
        editor.putString(wifiPreference, wifiName);
        editor.apply();
    }

    public String getWifiName(Context context){
        SharedPreferences preferences = context.getSharedPreferences(wifiPreference, Context.MODE_PRIVATE);
        return preferences.getString(wifiPreference, null);

    }
}
