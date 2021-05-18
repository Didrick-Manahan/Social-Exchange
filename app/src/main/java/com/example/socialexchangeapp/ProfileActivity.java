package com.example.socialexchangeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    private ImageButton editBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.profile_container, new ProfileFragment(), null)
                .commit();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println(requestCode);
        super.onActivityResult(requestCode, resultCode, data);
        ViewGroup vg = findViewById (R.id.profile_container);
        vg.invalidate();
        getSupportFragmentManager().beginTransaction().replace(R.id.profile_container, new ProfileFragment()).commit();
    }
}