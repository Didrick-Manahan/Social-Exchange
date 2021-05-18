package com.example.socialexchangeapp;

import android.content.Context;
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

import androidx.cardview.widget.CardView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class InterestAdaptor extends BaseAdapter {
    private ArrayList<View> interests;

    public InterestAdaptor(Context appContext, String[] elements) throws JSONException {
        interests = new ArrayList<View>();
        LayoutInflater inflater = (LayoutInflater) appContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for(int i = 0; i < elements.length; i++) {
            View v = inflater.inflate(R.layout.interest_card, null);
            TextView view = v.findViewById(R.id.interest_item);
            view.setText(elements[i]);
            interests.add(v);
        }
    }

    @Override
    public int getCount() {
        return interests.size();
    }

    @Override
    public Object getItem(int position) {
        View view = interests.get(position);
        return view;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return interests.get(position);
    }
}
