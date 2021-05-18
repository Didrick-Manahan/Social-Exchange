package com.example.socialexchangeapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ContactListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Contact> localElements;
    private ArrayList<View> widgets;
    LayoutInflater inflater;

    public ContactListAdapter(Context appContext, ArrayList<Contact> elements) {
        context = appContext;
        localElements = elements;
        widgets = new ArrayList<View>();
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (Contact i : elements) {
            if(i == null) {
                continue;
            }
            View v = inflater.inflate(R.layout.contact_template, null);

            TextView view = v.findViewById(R.id.peer);
            view.setText(i.name);
            view.setTextSize(TypedValue.COMPLEX_UNIT_PT, 12);
            widgets.add(v);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent contactProfileScreen = new Intent(appContext, ProfileActivity.class);
                    contactProfileScreen.putExtra("contact", i);
                    appContext.startActivity(contactProfileScreen);
                }
            });
        }
    }

    @Override
    public int getCount() {
        return localElements.size();
    }

    @Override
    public Object getItem(int i) {
        return widgets.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View v, ViewGroup vg) {
        return widgets.get(i);
    }
}
