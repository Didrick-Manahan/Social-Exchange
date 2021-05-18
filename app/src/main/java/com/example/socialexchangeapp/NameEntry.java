package com.example.socialexchangeapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class NameEntry extends AppCompatActivity {

    private static final String FILE_NAME = "example.txt";

    EditText mEditText;
    EditText numberText;
    EditText emailText;

    private Button next_button;
    SharedPreferences myPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_entry);
        next_button = findViewById(R.id.continue1);
        mEditText = findViewById(R.id.nameID);
        numberText = findViewById(R.id.numID);
        emailText = findViewById(R.id.emailID);


    }


    public void save(View v) {
        String text = mEditText.getText().toString();
        if(text.equals("")) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("You must enter your name.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
            return;
        }
        //String space = "\n";
        String text2 = numberText.getText().toString();
        String text3 = emailText.getText().toString();

        Contact contact = new Contact(text);
        if (!text2.equals("")) {
            contact.basics.put("Phone", text2);
        }
        if (!text3.equals("")) {
            contact.basics.put("Email", text3);
        }

        Utils.savePersonal(contact, getApplicationContext());

        Intent nexter = new Intent(this, SocialEntry.class);
        startActivity(nexter);
    }

}