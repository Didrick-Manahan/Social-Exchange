package com.example.socialexchangeapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

public class DataEntryAdapter extends BaseAdapter {
    private ArrayList<View> data_items;
    private Dictionary<String, Integer> left_icon_map;
    private Dictionary<String, Integer> right_icon_map;
    private Context context;

    public DataEntryAdapter(Context appContext, Contact profile_data) throws JSONException {
        context = appContext;
        data_items = new ArrayList<>();

        left_icon_map = new Hashtable<>();
        right_icon_map = new Hashtable<>();

        InitializeDictionary();

        LayoutInflater inflater = (LayoutInflater) appContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        AddFromSet(profile_data.basics, inflater);
        AddFromSet(profile_data.social, inflater);
        AddFromSet(profile_data.professional, inflater);
    }

    public void AddFromSet(Map<String, String> dataSet, LayoutInflater inflater) {
        for (String key : dataSet.keySet()) {
            if(Utils.getIcon(key,context) == null){
                continue;
            }

            View v = inflater.inflate(R.layout.data_entry_card, null);

            TextView tview_l = v.findViewById(R.id.label_text);
            TextView tview_r = v.findViewById(R.id.data_text);
            tview_l.setText(key);
            tview_r.setText(dataSet.get(key));

            ImageView iview_l = v.findViewById(R.id.data_icon);
            Integer resource = left_icon_map.get(key);
            Drawable icon = Utils.getIcon(key,context);
            if(icon != null) {
                iview_l.setImageDrawable(icon);
            } else {
                iview_l.setImageResource(resource);
            }

            ImageView ivew_r = v.findViewById(R.id.data_action);
            resource = right_icon_map.get(key);
            if(resource == null) {
                resource = right_icon_map.get("Twitter");
            }
            ivew_r.setImageResource(resource);

            data_items.add(v);
        }
    }

    public void InitializeDictionary() {
        left_icon_map.put("Phone", R.drawable.phone_icon);
        right_icon_map.put("Phone",R.drawable.copy_icon);

        left_icon_map.put("Email", R.drawable.email_icon);
        right_icon_map.put("Email", R.drawable.copy_icon);

        left_icon_map.put("Facebook", R.drawable.facebook_icon);
        right_icon_map.put("Facebook", R.drawable.link_icon);

        left_icon_map.put("Twitter", R.drawable.twitter_icon);
        right_icon_map.put("Twitter", R.drawable.link_icon);

        left_icon_map.put("Website", R.drawable.website_icon);
        right_icon_map.put("Website", R.drawable.link_icon);

        left_icon_map.put("Instagram", R.drawable.instagram_icon);
        right_icon_map.put("Instagram", R.drawable.link_icon);
    }

    @Override
    public int getCount() {
        return data_items.size();
    }

    @Override
    public Object getItem(int position) {
        View view = data_items.get(position);
        return view;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return data_items.get(position);
    }
}

