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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class NavigationBar extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_nav);
        try {
            File directory = getFilesDir();
            File file = new File(directory, "contacts");
            if(file.createNewFile()) {
                Log.d("File", "File created");
            } else {
                Log.d("File", "File already exists");
            }
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                Log.d("Data", data);
            }
            myReader.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
//        Button next = (Button) findViewById(R.id.sharebutton);
//        next.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                Intent myIntent = new Intent(MainActivity.this, ShareInfo.class);
//                startActivityForResult(myIntent, 0);
//            }
//
//        });

        bottomNavigationView=findViewById(R.id.bottomNav);

        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();

        TextView all = findViewById(R.id.all);
        TextView class1 = findViewById(R.id.class1);
        TextView class2 = findViewById(R.id.class2);
        TextView more = findViewById(R.id.more);

//        all.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "ALL CLASSMATES", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        class1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "CS 465 CLASSMATES", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        class2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "MATH 415 CLASSMATES", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        more.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "MORE CLASSMATES", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod=new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment fragment=null;

                    switch (item.getItemId())
                    {
                        case R.id.home:
                            fragment = new HomeFragment().newInstance(getApplicationContext());
                            break;
                        case R.id.share:
                            fragment = ShareFragment.newInstance(getApplicationContext());
                            break;
                        case R.id.profile:
                            fragment = new ProfileFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

                    return true;
                }
            };
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            ArrayList<Contact> contacts = new ArrayList<Contact>(Utils.getContactMap(getApplicationContext()).values());
            ContactListAdapter s_adapter = new ContactListAdapter(this, contacts);
            ListView listView = findViewById(R.id.contactlist);
            listView.setAdapter(s_adapter);
        }
        else if (requestCode == 2) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new ProfileFragment()).commit();
        }
    }
}