package com.example.socialexchangeapp;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.icu.text.IDNA;
import android.media.Image;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static androidx.core.graphics.drawable.DrawableCompat.setTint;

public class CheckboxAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<InfoType> localElements;
    private ArrayList<View> widgets;
    LayoutInflater inflater;
    private int numSelected = 0;
    private boolean[] checked;
    private Button shareButton;

    public CheckboxAdapter(Context appContext, ArrayList<InfoType> elements, Button s) {
        shareButton = s;
        context = appContext;
        localElements = elements;
        widgets = new ArrayList<View>();
        checked = new boolean[localElements.size()];
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (InfoType i : elements) {
            View v = inflater.inflate(R.layout.check_card, null);
            CheckedTextView view = v.findViewById(R.id.checklist_text);
            ImageView image = v.findViewById(R.id.checklist_icon);
            image.setImageDrawable(Utils.getIcon(i.name,appContext));
            view.setText(i.name);
            view.setCheckMarkDrawable(R.drawable.ic_baseline_check_24);
            widgets.add(v);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getCount() {
        return localElements.size();
    }

    @Override
    public Object getItem(int i) {
        return widgets.get(i);
    }

    public boolean isChecked(int i) {
        return checked[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public ArrayList<InfoType> getSelected() {
        ArrayList<InfoType> list = new ArrayList<InfoType>();
        for (int i = 0; i < localElements.size(); i++) {
            if (checked[i] && localElements.get(i).classification != Classification.CATEGORY) {
                list.add(localElements.get(i));
            }
        }
        return list;
    }

    public void setSelected(int i, boolean s) {
        CheckedTextView view = widgets.get(i).findViewById(R.id.checklist_text);
        InfoType info = localElements.get(i);
        boolean t = view.isChecked();
        view.setChecked(s);
        if (s && !checked[i]) {
            numSelected++;
        } else if (!s && checked[i]) {
            numSelected--;
        }
        shareButton.setEnabled(numSelected != 0);
        Drawable img = context.getResources().getDrawable( R.drawable.right_arrow );
        if(shareButton.isEnabled()) {
            shareButton.setCompoundDrawablesRelativeWithIntrinsicBounds( null, null, img, null);
            //setTint(img,c);
        } else {
            shareButton.setCompoundDrawablesRelativeWithIntrinsicBounds( null, null, null, null);
        }
        checked[i] = s;
        if (s) {
            view.setBackgroundColor(context.getResources().getColor(R.color.highlight));
            view.setCheckMarkTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.darkest_color)));
        } else {
            view.setBackgroundColor(context.getResources().getColor(R.color.lighter_grey));
            view.setCheckMarkTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.darker_grey)));
        }

    }

    public void selectCategory(Classification c) {
        boolean s = false;
        for (int j = 0; j < localElements.size(); j++) {
            if (localElements.get(j).classification == c && !checked[j]) {
                s = true;
                break;
            }
        }
        for (int j = 0; j < localElements.size(); j++) {
            if (localElements.get(j).classification == c) {
                setSelected(j, s);
            }
        }
    }

    @Override
    public View getView(int i, View v, ViewGroup vg) {
        return widgets.get(i);
    }

    private float dpToPix(Context context, int dp) {
        return (dp * context.getResources().getDisplayMetrics().density) / 160;
    }
}

