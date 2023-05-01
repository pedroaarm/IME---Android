package com.example.wifialert.service;


import android.annotation.SuppressLint;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;

import android.database.Cursor;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.telephony.SmsManager;

import androidx.annotation.Nullable;


import com.example.wifialert.data.DataPersistence;


import java.util.ArrayList;
import java.util.List;

public class MainService extends Service {

    DataPersistence dataPersitence = new DataPersistence();
    private final String smsMessage = "Olá, cheguei no trabalho";
    public boolean scanAndSendSms(Context context) {
        boolean send = false;
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.startScan();
        List<ScanResult> scanResults = wifiManager.getScanResults();
        for (ScanResult result : scanResults) {
            String ssid = result.SSID;
            if (ssid.equals(dataPersitence.getWifiName(context))) {
                List<String> contacts = getContactsStartingWithLetter("#F#", context);
                for (String phone : contacts
                ) {
                    sendSMS(phone);
                    send = true;
                }
            }
        }
        return send;
    }

    @SuppressLint("Range")
    private List<String> getContactsStartingWithLetter(String letter, Context context) {
        List<String> contacts = new ArrayList<>();

        String selection = ContactsContract.Contacts.DISPLAY_NAME + " LIKE ?";
        String[] selectionArgs = new String[]{letter + "%"};

        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                selection, selectionArgs, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                 String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);
                    while (phones.moveToNext()) {
                        String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        contacts.add(phoneNumber);
                    }
                    phones.close();
                }

            }
        }

        return contacts;
    }

    private void sendSMS(String phoneNumber) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, smsMessage , null, null);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
