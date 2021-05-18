package com.example.socialexchangeapp;

import java.io.Serializable;

public class InfoType implements Serializable {
    public String name;
    public Classification classification;
    public Classification categoryClass;
    public String value = "default";
    private boolean active;

    public InfoType(String n, Classification c) {
        name = n;
        classification = c;
    }

    public InfoType(String n, String v) {
        name = n;
        value = v;
        classification = Classification.OTHER;
    }

    public InfoType(String n, String v, Classification c) {
        name = n;
        value = v;
        classification = c;
    }

    public String getJSON() {
        if (name == null || value == null) {
            return "";
        }
        return '"' + name.toLowerCase() + "\":\"" + value + '"';
    }
}

enum Classification {
    SOCIAL, PROFESSIONAL, CATEGORY, BASICS, OTHER
}