package com.example.socialexchangeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class EditProfile extends AppCompatActivity {

    private static final String FILE_NAME = "EditProfilePage.txt";

    EditText nameText;
    EditText phoneText;
    EditText emailText;
    EditText redditText;
    EditText twitterText;
    EditText instagramText;
    EditText linkedinText;
    EditText websiteText;
    EditText githubText;
    EditText interestsText;
    EditText icebreakerText;

    private Button returnButton;
    private Contact personal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Bundle extras = getIntent().getExtras();
        if(extras!=null && extras.containsKey("contact")) {
            personal = (Contact) extras.get("contact");
        } else {
            personal = Utils.getContact("PERSONAL", getApplicationContext());
        }

        nameText = findViewById(R.id.nametextentry);
        nameText.setText(personal.name == null ? "" : personal.name);
        phoneText = findViewById(R.id.phonetextentry);
        phoneText.setText(personal.basics.containsKey("Phone") ? personal.basics.get("Phone") : "");
        emailText = findViewById(R.id.emailtextentry);
        emailText.setText(personal.basics.containsKey("Email") ? personal.basics.get("Email") : "");
        redditText = findViewById(R.id.reddittextentry);
        redditText.setText(personal.social.containsKey("Reddit") ? personal.social.get("Reddit") : "");
        twitterText = findViewById(R.id.twittertextentry);
        twitterText.setText(personal.social.containsKey("Twitter") ? personal.social.get("Twitter") : "");
        instagramText = findViewById(R.id.instagramtextentry);
        instagramText.setText(personal.social.containsKey("Instagram") ? personal.social.get("Instagram") : "");
        linkedinText = findViewById(R.id.linkedintextentry);
        linkedinText.setText(personal.professional.containsKey("LinkedIn") ? personal.professional.get("LinkedIn") : "");
        websiteText = findViewById(R.id.websitetextentry);
        websiteText.setText(personal.professional.containsKey("Website") ? personal.professional.get("Website") : "");
        githubText = findViewById(R.id.githubtextentry);
        githubText.setText(personal.professional.containsKey("GitHub") ? personal.professional.get("GitHub") : "");
        interestsText = findViewById(R.id.intereststextentry);
        String interests = "";
        if (personal.interests != null && personal.interests.length > 0) {
            for (String s : personal.interests) {
                interests = interests + s + ",";
            }
            interests = interests.substring(0, interests.length() - 1);
        }

        interestsText.setText(interests);
        icebreakerText = findViewById(R.id.icebreakertextentry);
        icebreakerText.setText(personal.icebreaker);


        //ProfileFragment fragment = new ProfileFragment();
        //FragmentManager manager = getSupportFragmentManager();
        //manager.beginTransaction().add(R.id.EditProfileLayout,fragment).commit();


    }


    public void saveProfileEdits(View v) {
        String text1 = nameText.getText().toString();
        String text2 = phoneText.getText().toString();
        String text3 = emailText.getText().toString();
        String text4 = redditText.getText().toString();
        String text5 = twitterText.getText().toString();
        String text6 = instagramText.getText().toString();
        String text7 = linkedinText.getText().toString();
        String text8 = websiteText.getText().toString();
        String text9 = githubText.getText().toString();
        String text10 = interestsText.getText().toString();
        String text11 = icebreakerText.getText().toString();

        personal.name = text1;
        if (!text2.equals("")) {
            personal.basics.put("Phone", text2);
        } else {
            personal.basics.remove("Phone");
        }
        if (!text3.equals("")) {
            personal.basics.put("Email", text3);
        } else {
            personal.basics.remove("Email");
        }
        if (!text4.equals("")) {
            personal.social.put("Reddit", text4);
        } else {
            personal.social.remove("Reddit");
        }
        if (!text5.equals("")) {
            personal.social.put("Twitter", text5);
        } else {
            personal.social.remove("Twitter");
        }
        if (!text6.equals("")) {
            personal.social.put("Instagram", text6);
        } else {
            personal.social.remove("Instagram");
        }
        if (!text7.equals("")) {
            personal.professional.put("LinkedIn", text7);
        } else {
            personal.professional.remove("LinkedIn");
        }
        if (!text8.equals("")) {
            personal.professional.put("Website", text8);
        } else {
            personal.professional.remove("Website");
        }
        if (!text9.equals("")) {
            personal.professional.put("GitHub", text9);
        } else {
            personal.professional.remove("GitHub");
        }
        if (!text10.equals("")) {
            String[] interests = text10.split(",");
            for (int i = 0; i < interests.length; i++) {
                interests[i] = interests[i].trim();
            }
            personal.interests = interests;
        } else {
            personal.interests = null;
        }
        if (!text11.equals("")) {
            personal.icebreaker = text11;
        }

        Bundle extras = getIntent().getExtras();
        if(extras!=null && extras.containsKey("contact")) {
            Utils.saveContact(personal, getApplicationContext());
        } else {
            Utils.savePersonal(personal, getApplicationContext());
        }
        finish();

    }
}