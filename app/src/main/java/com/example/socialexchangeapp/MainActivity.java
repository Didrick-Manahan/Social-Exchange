package com.example.socialexchangeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private Button newUser;
    private Button returnUser;
    private Button eraseContacts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Utils.userExists(getApplicationContext())) {
            returnHome();
        } else {
            newHome();
        }
    }

    public void returnHome(){


        //Intent intent = new Intent(this, ProfileFragment.class);
        //startActivity(intent);

        //THIS WORKS (BRINGS BACK TO HOME PAGE SUCCESSFULLY!)
        Intent homePage = new Intent(this, NavigationBar.class);
        homePage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homePage);
        finish();
    }

    public void newHome(){


        //Intent intent = new Intent(this, ProfileFragment.class);
        //startActivity(intent);

        //THIS WORKS (BRINGS BACK TO HOME PAGE SUCCESSFULLY!)
        Intent newPage = new Intent(this, NameEntry.class);
        newPage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(newPage);
        finish();
    }


}