package com.example.socialexchangeapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.zxing.WriterException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.example.socialexchangeapp.Classification.BASICS;
import static com.example.socialexchangeapp.Classification.CATEGORY;
import static com.example.socialexchangeapp.Classification.PROFESSIONAL;
import static com.example.socialexchangeapp.Classification.SOCIAL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShareFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShareFragment extends Fragment {
    private static ArrayList<InfoType> infoTypes;
    private static Context context;
    private static String link;
    private static Contact personal;

    public ShareFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ShareFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShareFragment newInstance(Context c) {
        context = c;
        ShareFragment fragment = new ShareFragment();
        personal = Utils.getPersonal(c);
        infoTypes = new ArrayList<>();
        Iterator<Map.Entry<String, String>> itr = personal.basics.entrySet().iterator();
        while(itr.hasNext()) {
            Map.Entry<String,String> entry = itr.next();
            infoTypes.add(new InfoType(entry.getKey(),entry.getValue(),BASICS));
        }
        itr = personal.social.entrySet().iterator();
        while(itr.hasNext()) {
            Map.Entry<String,String> entry = itr.next();
            infoTypes.add(new InfoType(entry.getKey(),entry.getValue(),SOCIAL));
        }
        itr = personal.professional.entrySet().iterator();
        while(itr.hasNext()) {
            Map.Entry<String,String> entry = itr.next();
            infoTypes.add(new InfoType(entry.getKey(),entry.getValue(),PROFESSIONAL));
        }
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View v, Bundle savedInstanceState) {
        super.onViewCreated(v,savedInstanceState);
        ListView socialBoxes = (ListView) v.findViewById(R.id.sharelist);
        socialBoxes.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        Button b = v.findViewById(R.id.sharelinkbutton);
        b.setEnabled(false);
        CheckboxAdapter s_adapter = new CheckboxAdapter(getActivity(), infoTypes,b);
        socialBoxes.setAdapter(s_adapter);
        socialBoxes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView v = (CheckedTextView) view.findViewById(R.id.checklist_text);
                CheckboxAdapter c = (CheckboxAdapter)parent.getAdapter();
                c.setSelected(position, !c.isChecked(position));
            }
        });


        b.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<InfoType> info = s_adapter.getSelected();
                new PostJSONTask().execute(info);
                LayoutInflater inflater = (LayoutInflater)
                        context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.fragment_window, null);
            }
        });

        Button b_s = v.findViewById(R.id.allsocbutton);
        b_s.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s_adapter.selectCategory(SOCIAL);
            }
        });
        Button b_p = v.findViewById(R.id.allprofbutton);
        b_p.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s_adapter.selectCategory(PROFESSIONAL);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_share, container, false);
    }


    private class PostJSONTask extends AsyncTask<ArrayList<InfoType>,Float,String> {
        String result;
        @Override
        protected String doInBackground(ArrayList<InfoType>... arrayLists) {
            ArrayList<InfoType> data = arrayLists[0];
            Contact contact = new Contact(personal.name);
            contact.interests = personal.interests;
            contact.icebreaker = personal.icebreaker;
            for(InfoType i : data) {
                switch(i.classification) {
                    case BASICS:
                        contact.basics.put(i.name,i.value);
                        break;
                    case SOCIAL:
                        contact.social.put(i.name,i.value);
                        break;
                    case PROFESSIONAL:
                        contact.professional.put(i.name,i.value);
                        break;
                }
            }
            Gson gson = new Gson();
            String json = gson.toJson(contact, Contact.class);
            URL url;
            try {
                url = new URL("https://jsonblob.com/api/jsonBlob");
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }
            HttpURLConnection con;
            OutputStreamWriter wr;
            try {
                con = (HttpURLConnection) url.openConnection();
                con.setDoOutput(true);
                con.setRequestMethod("POST");
                String b = con.getRequestMethod();
                con.setRequestProperty("Content-Type","application/json");
                con.setRequestProperty("Accept","application/json");
                wr = new OutputStreamWriter(con.getOutputStream());
                wr.write(json);
                wr.flush();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            String loc = con.getHeaderField("Location");

            return loc;//.substring(34);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            link = s;
            DialogFragment f = new GetLinkDialogFragment();
            f.show(getFragmentManager(), "What does this text do");

        }


    }

    public static class GetLinkDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = requireActivity().getLayoutInflater();
            View alertView = inflater.inflate(R.layout.fragment_window,null);
            ImageView iv = alertView.findViewById(R.id.qr_display);
            TextView tv = alertView.findViewById(R.id.link_display);
            tv.setText(link);

            QRGEncoder qrgEncoder = new QRGEncoder(link,null,QRGContents.Type.TEXT, 1200);
            try {
                // getting our qrcode in the form of bitmap.
                Bitmap bitmap = qrgEncoder.encodeAsBitmap();
                // the bitmap is set inside our image
                // view using .setimagebitmap method.
                iv.setImageBitmap(bitmap);
            } catch (WriterException e) {
                // this method is called for
                // exception handling.
                Log.e("Tag", e.toString());
            }

            builder.setTitle(R.string.share_info_popup).setView(alertView).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button
                }
            });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }

    private static float fractionWidth(Context context, float fraction) {
        return fraction * context.getResources().getDisplayMetrics().widthPixels;
    }
}

