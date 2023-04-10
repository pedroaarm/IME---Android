package com.example.agendacontatos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS},
                PackageManager.PERMISSION_GRANTED);

    }

    public void saveNumber(View view){

        String nome = ((EditText) findViewById(R.id.editTextTextPersonName)).getText().toString();
        String telefone = ((EditText) findViewById(R.id.editTextPhone)).getText().toString();
        ContentValues values = new ContentValues();
        values.put(ContactsContract.RawContacts.ACCOUNT_TYPE, "");
        values.put(ContactsContract.RawContacts.ACCOUNT_NAME, nome);

        Uri rawContactUri = getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, values);
        long rawContactId = ContentUris.parseId(rawContactUri);

        values.clear();
        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        values.put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, nome);
        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, telefone);

        Uri uri = getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);

    }
}