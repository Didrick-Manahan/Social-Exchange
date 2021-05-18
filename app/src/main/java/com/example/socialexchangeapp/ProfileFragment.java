package com.example.socialexchangeapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.example.socialexchangeapp.Classification.PROFESSIONAL;
import static com.example.socialexchangeapp.Classification.SOCIAL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //declare edit button
    private ImageButton editBut;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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

        Bundle extras = getActivity().getIntent().getExtras();
        Contact profile_data = null;

        if(extras!=null && extras.containsKey("contact")) {
            profile_data = (Contact) extras.get("contact");
            profile_data = Utils.getContact(profile_data.filename, getContext());
        } else {
            profile_data = Utils.getPersonal(getContext());
        }

        ListView interest_list = (ListView) v.findViewById(R.id.interest_list);
        InterestAdaptor interest_adaptor = null;

        if(profile_data.interests != null) {
            try {
                interest_adaptor = new InterestAdaptor(getActivity(), profile_data.interests);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            interest_list.setAdapter(interest_adaptor);
        } else {
            try {
                interest_adaptor = new InterestAdaptor(getActivity(), new String[]{"No", "Interests", "Yet"});
            } catch (JSONException e) {
                e.printStackTrace();
            }
            interest_list.setAdapter(interest_adaptor);
        }

        ListView data_entry_list = (ListView) v.findViewById(R.id.links_list);
        DataEntryAdapter data_adapter = null;
        try {
            data_adapter = new DataEntryAdapter(getActivity(), profile_data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        data_entry_list.setAdapter(data_adapter);

        TextView tview = v.findViewById(R.id.top_title);
        tview.setText(profile_data.name);

        tview = v.findViewById(R.id.circle_initials);
        tview.setText(getInitials(profile_data.name));

        tview = v.findViewById(R.id.icebreaker);
        tview.setText(profile_data.icebreaker);
    }

    private String getInitials(String name) {
        String[] splitName = name.split(" ");
        if(splitName.length == 1) {
            return String.valueOf(splitName[0].charAt(0));
        } else {
            return splitName[0].charAt(0) + String.valueOf(splitName[1].charAt(0));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //add edit button functionality

        View editView =  inflater.inflate(R.layout.fragment_profile, container, false);

        editBut = editView.findViewById(R.id.edit_but);
        editBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editScreen = new Intent(getActivity(), EditProfile.class);
                Bundle extras = getActivity().getIntent().getExtras();
                if(extras!=null && extras.containsKey("contact")) {
                    editScreen.putExtra("contact", (Contact) extras.get("contact"));
                }
                getActivity().startActivityForResult(editScreen,2);
            }
        });



        return editView;
    }
}