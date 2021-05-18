package com.example.socialexchangeapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Contact implements Serializable {
    public String name;
    public String[] interests = {};
    public Map<String,String> basics = new HashMap<>();
    public Map<String,String> social = new HashMap<>();
    public Map<String,String> professional = new HashMap<>();
    public String icebreaker;

    public String filename;

    public Contact(String n) {
        name = n;
    }
}

