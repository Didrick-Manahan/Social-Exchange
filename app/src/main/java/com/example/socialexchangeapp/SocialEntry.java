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

public class SocialEntry extends AppCompatActivity {

    private static final String FILE_NAME = "socials1.txt";

    EditText twitterText;
    EditText instagramText;
    EditText redditText;
    EditText interestsText;

    private Button next_Activity_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_entry);

        twitterText = findViewById(R.id.twitterID);
        instagramText = findViewById(R.id.instagramID);
        redditText = findViewById(R.id.redditID);
        interestsText = findViewById(R.id.interestsID);


    }
    public void save(View v){
        String text = twitterText.getText().toString();
        String text2 = instagramText.getText().toString();
        String text3 = redditText.getText().toString();
        String text4 = interestsText.getText().toString();


        Contact contact = Utils.getContact("PERSONAL",getApplicationContext());
        if(!text.equals("")) {
            contact.social.put("Twitter",text);
        }
        if(!text2.equals("")) {
            contact.social.put("Instagram",text2);
        }
        if(!text3.equals("")) {
            contact.social.put("Reddit",text3);
        }
        if(!text4.equals("")) {
            contact.interests = text4.replace(" ", "").split(",");
        }

        Utils.savePersonal(contact, getApplicationContext());

        Intent nexter = new Intent(this, ProfessionalEntry.class);
        startActivity(nexter);



    }

}