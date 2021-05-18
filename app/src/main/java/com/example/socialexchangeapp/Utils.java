package com.example.socialexchangeapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class Utils {

    public static Contact getContact(String filename, Context context) {
        try {
            String json = stringFromFile(filename,context);
            return jsonToContact(json);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Contact getPersonal(Context context) {
        try {
            String json = stringFromFile("PERSONAL",context);
            return jsonToContact(json);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    //return number of contacts
    public static Drawable getIcon(String s, Context c) {
        switch (s) {
            case "LinkedIn":
                return c.getDrawable(R.drawable.linkedin_icon);
            case "Email":
                return c.getDrawable(R.drawable.email_icon);
            case "Phone":
                return c.getDrawable(R.drawable.phone_icon);
            case "Website":
                return c.getDrawable(R.drawable.website_icon);
            case "Twitter":
                return c.getDrawable(R.drawable.twitter_icon);
            case "Facebook":
                return c.getDrawable(R.drawable.facebook_icon);
            case "Reddit":
                return c.getDrawable(R.drawable.reddit_icon);
            case "Discord":
                return c.getDrawable(R.drawable.discord_icon);
            case "GitHub":
                return c.getDrawable(R.drawable.github_icon);
            case "Instagram":
                return c.getDrawable(R.drawable.instagram_icon);
        }
        return null;
    }

    public static int getNumContacts(Context context) {
        int num = 0;

        try {
            File file = new File(context.getFilesDir(), context.getResources().getString(R.string.num_contacts_file));
            if(file.createNewFile()) {
                return 0;
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String inputLine = reader.readLine();
            if(inputLine == null) {
                return 0;
            }
            num = Integer.parseInt(inputLine);
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return -1;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        return num;
    }



    /*
     * Creates contact file and appends to list of contacts
     * */

    public static boolean saveContact(Contact contact, Context context) {
        if (contact == null || contact.name == null || contact.name == "") {
            return false;
        }
        try {
            writeToFile(contact.filename,contactToJson(contact),false, context);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public static boolean savePersonal(Contact contact, Context context) {
        if (contact == null || contact.name == null || contact.name == "") {
            return false;
        }
        contact.filename = "PERSONAL";
        try {
            writeToFile("PERSONAL",contactToJson(contact),false, context);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean addContact(String json, Context context) {
        Contact contact = jsonToContact(json);
        if (contact == null || contact.name == null || contact.name == "") {
            return false;
        }
        int numContacts = getNumContacts(context);
        String filename = contact.name.toUpperCase().replaceAll(" ","")+Integer.toString(numContacts);
        contact.filename = filename;
        try {
            writeToFile(filename,contactToJson(contact),false, context);
            writeToFile("contacts",filename + "\n", true, context);
            incNumContacts(context);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        Toast.makeText(context, "Contact added for " + contact.name, Toast.LENGTH_LONG).show();
        return true;
    }

    /*
     * Returns map from name to filename
     * */

    public static ArrayList<String> getContactList(Context context) {
        File file = new File(context.getFilesDir(), context.getResources().getString(R.string.contacts_file));
        BufferedReader reader = null;
        ArrayList<String> list = new ArrayList<String>();
        try {
            System.out.println("Reading from " + context.getFilesDir() + "/" + context.getResources().getString(R.string.contacts_file));
            reader = new BufferedReader(new FileReader(file));
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                list.add(inputLine);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    public static Map<String, Contact> getContactMap(Context context) {
        File file = new File(context.getFilesDir(), context.getResources().getString(R.string.contacts_file));
        BufferedReader reader = null;
        Map<String, Contact> map = new HashMap<String, Contact>();
        try {
            System.out.println("Reading from " + context.getFilesDir() + "/" + context.getResources().getString(R.string.contacts_file));
            reader = new BufferedReader(new FileReader(file));
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                Contact c = getContact(inputLine,context);
                map.put(inputLine,c);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return map;
    }

    private static int incNumContacts(Context context) {
        int num = 0;
        String filename = context.getResources().getString(R.string.num_contacts_file);
        try {
            File file = new File(context.getFilesDir(), filename);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String inputLine = reader.readLine();
            if(inputLine == null) {
                reader.close();
                writeToFile(filename, "1", false, context);
                return 0;
            }
            num = Integer.parseInt(inputLine);
            reader.close();
            writeToFile(filename, Integer.toString(num + 1), false, context);
            System.out.println("Incremented in " + context.getFilesDir() + "/" +filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return -1;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        return num+1;
    }

    public static void eraseAllContacts(Context context) {ArrayList<String> contacts = getContactList(context);
        if(contacts == null) {
            return;
        }
        for (String filename : contacts) {
            deleteFile(context, filename);
        }
        deleteFile(context, context.getResources().getString(R.string.contacts_file));
        deleteFile(context, context.getResources().getString(R.string.num_contacts_file));
    }

    public static void deleteContact(Contact contact, Context context) {
        ArrayList<String> contacts = getContactList(context);
        String filename = context.getResources().getString(R.string.contacts_file);
        if (deleteFile(context, filename)) {
            Toast.makeText(context, "Contact deleted", Toast.LENGTH_LONG).show();
            try {
                contacts.remove(contact.filename);
                File file = new File(context.getFilesDir(), filename);
                FileWriter writer = new FileWriter(file, false);
                for(String f : contacts) {
                    writer.write(f);
                }
                writer.flush();
                writer.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static int decNumContacts(Context context) {
        int num = 0;
        String filename = context.getResources().getString(R.string.num_contacts_file);
        try {
            File file = new File(context.getFilesDir(), filename);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String inputLine = reader.readLine();
            if(inputLine == null) {
                reader.close();
                return -1;
            }
            num = Integer.parseInt(inputLine);
            reader.close();
            writeToFile(filename, Integer.toString(num - 1), false, context);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return -1;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        return num-1;
    }

    private static void writeToFile(String name, String data, boolean append, Context context) {
        try {
            File file = new File(context.getFilesDir(), name);
            FileWriter writer = new FileWriter(file, append);
            writer.write(data);
            System.out.println("Wrote to " + context.getFilesDir() + "/" + name);
            writer.flush();
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String stringFromFile(String name, Context context) {
        File file = new File(context.getFilesDir(), name);
        BufferedReader reader = null;
        String retString = "";
        try {
            reader = new BufferedReader(new FileReader(file));
            String inputLine;
            System.out.println("Reading from " + context.getFilesDir() + "/" + name);
            while ((inputLine = reader.readLine()) != null)
                retString += inputLine;
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return retString;
    }

    private static boolean deleteFile(Context context, String filename) {
        File file = new File(context.getFilesDir(), filename);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("file deleted :" + filename);
                return true;
            } else {
                System.out.println("file not deleted :" + filename);
                return false;
            }
        }
        return false;
    }

    private static Contact jsonToContact(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Contact.class);
    }

    private static String contactToJson(Contact contact) {
        Gson gson = new Gson();
        return gson.toJson(contact);
    }

    public static Boolean userExists(Context context) {
        File file = new File(context.getFilesDir(), "PERSONAL");
        return file.exists();
    }
}
