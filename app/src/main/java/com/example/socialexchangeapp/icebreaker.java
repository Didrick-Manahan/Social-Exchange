package com.example.socialexchangeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class icebreaker extends AppCompatActivity {

    private static final String FILE_NAME = "IceBreaker.txt";

    Button page_next;
    RadioGroup radioGroup;
    RadioButton radioButton;
    TextView textView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icebreaker);

        radioGroup = findViewById(R.id.radioGroup);
        textView = findViewById(R.id.CustomIceBreaker);
        radioButton=findViewById(R.id.radio_one);

        // WHAT DOES THIS DO???
        /*Button buttonApply = findViewById(R.id.button_apply);
        buttonApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);
                textView.setText("Your choice: " + radioButton.getText());

            }
        });*/


    }

    public void checkButton(View v) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);

    }


    public void save(View v){
        int idTest = radioGroup.getCheckedRadioButtonId();
        String text;
        if(idTest == 4){
            text = textView.getText().toString();
        }

        else{
            text = radioButton.getText().toString();
        }
        //String text = textView.getText().toString();
        //String text = radioButton.getText().toString();
        //String text2 = numberText.getText().toString();
        //String text3 = emailText.getText().toString();

        Contact contact = Utils.getContact("PERSONAL",getApplicationContext());
        contact.icebreaker = text;
        Utils.savePersonal(contact,getApplicationContext());


        Intent navB = new Intent(this, NavigationBar.class);
        navB.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(navB);
        finish();


    }
}
