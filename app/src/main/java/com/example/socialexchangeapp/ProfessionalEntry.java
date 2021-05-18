package com.example.socialexchangeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ProfessionalEntry extends AppCompatActivity {

    private static final String FILE_NAME = "socials2.txt";

    EditText linkedinText;
    EditText websiteText;
    EditText githubText;
    EditText discordText;

    private Button toIB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professional_entry);

        linkedinText = findViewById(R.id.linkedinID);
        websiteText = findViewById(R.id.websiteID);
        githubText = findViewById(R.id.githubID);
        discordText = findViewById(R.id.discordID);
    }

    public void save(View v){
        String text = linkedinText.getText().toString();
        //String space = "\n";
        String text2 = websiteText.getText().toString();
        String text3 = githubText.getText().toString();
        String text4 = discordText.getText().toString();

        Contact contact = Utils.getContact("PERSONAL",getApplicationContext());
        if(!text.equals("")) {
            contact.professional.put("LinkedIn",text);
        }
        if(!text2.equals("")) {
            contact.professional.put("Website",text2);
        }
        if(!text3.equals("")) {
            contact.professional.put("GitHub",text3);
        }
        if(!text4.equals("")) {
            contact.professional.put("Discord",text4);
        }

        Utils.savePersonal(contact, getApplicationContext());

        Intent ice = new Intent(this, icebreaker.class);
        startActivity(ice);


    }
}