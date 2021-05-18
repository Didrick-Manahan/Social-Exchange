package com.example.socialexchangeapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class AddContact extends AppCompatActivity {
    private Activity act = this;
    private String urlString;
    private static final int QR_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        Button qr = findViewById(R.id.scan_qr_button);
        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent qrScan = new Intent(act, QRScanner.class);
                startActivityForResult(qrScan, QR_REQUEST);

            }
        });
        EditText linkEnter = findViewById(R.id.link_text);
        linkEnter.setOnEditorActionListener(new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                System.out.println(actionId);
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String urlString = linkEnter.getText().toString();
                    new GetJSONTask().execute(urlString);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == QR_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                urlString = data.getStringExtra("URL");
                new GetJSONTask().execute(urlString);
                // The user picked a contact.
            }
        }
    }


    private class GetJSONTask extends AsyncTask<String, Float, String> {
        String result;

        @Override
        protected String doInBackground(String... strings) {
            String urlString;
            if (strings.length == 0) {
                return "";
            } else {
                urlString = strings[strings.length - 1];
            }

            URL url = null;
            try {
                url = new URL(urlString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }
            String jsonString = "";
            try {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(url.openStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null)
                    jsonString += inputLine;
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

            return jsonString;//.substring(34);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                System.out.println(s);
                Utils.addContact(s, getApplicationContext());
                Intent intent = new Intent();
                setResult(1, intent);
                finish();
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "Error fetching information.", Toast.LENGTH_LONG);
                toast.show();
            }
        }


    }


}