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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Map;
import java.util.zip.Inflater;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.example.socialexchangeapp.Classification.CATEGORY;
import static com.example.socialexchangeapp.Classification.PROFESSIONAL;
import static com.example.socialexchangeapp.Classification.SOCIAL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //declare ImageButton
    private ImageButton editFriend1;
    private ImageButton editFriend2;
    private ImageButton editFriend3;
    private ImageButton editFriend4;
    private ImageButton editFriend5;

    public static final String[] names ={"Shivani Kamtikar", "Eddie Rajcevic", "Andrew Benington", "Bryan Goldenberg"};
    public static final Contact[] names2 = new Contact[0];
    private static ArrayList<Contact> contacts;
    private ListView listView;
    private static Context context;


    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(Context c) {
        context = c;
//        contacts = new ArrayList<Contact>();
        HomeFragment fragment = new HomeFragment();
//        for (String s : names) {
//            contacts.add(new Contact(s));
//        }
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        contacts = new ArrayList<Contact>();
        HomeFragment fragment = new HomeFragment();
        for (String s : names) {
            contacts.add(new Contact(s));
        }
    }

    @Override
    public void onViewCreated(@NonNull View v, Bundle savedInstanceState) {
        super.onViewCreated(v,savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        contacts = new ArrayList<Contact>(Utils.getContactMap(getContext()).values());
        listView = (ListView) v.findViewById(R.id.contactlist);
        ContactListAdapter s_adapter = new ContactListAdapter(getActivity(), contacts);
        listView.setAdapter(s_adapter);

        FloatingActionButton addContactBut = v.findViewById(R.id.add_contact_button);
        addContactBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addscreen = new Intent(getActivity(), AddContact.class);
                getActivity().startActivityForResult(addscreen, 1);

            }
        });

        return v;
    }

}