package com.example.coronatrackersimple.sharedprefs;

import android.content.Context;
import android.content.SharedPreferences;

public class SavedPrefs
{
        /////// Not Used ////////////

    private static SavedPrefs savedPrefs;
    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor editor;


    public static final String REMOTE_DATA = "RemoteData";
    public static final String STORED_DATA = "COVID_STATS";



    private SavedPrefs(Context context) {
        sharedPrefs = context.getSharedPreferences(REMOTE_DATA, Context.MODE_PRIVATE);
        editor = sharedPrefs.edit();
        editor.apply();
    }

    public static SavedPrefs getPreferences(Context context) {
        if (savedPrefs== null)
        {
            savedPrefs = new SavedPrefs(context);
        }
        return savedPrefs;
    }



    public void savePrefs(String data)
    {
        editor.putString(STORED_DATA,data);
        editor.commit();
    } //

    public String getPrefs()
    {
        return sharedPrefs.getString(STORED_DATA,"default");
    }

} // SavedPrefs class closed
