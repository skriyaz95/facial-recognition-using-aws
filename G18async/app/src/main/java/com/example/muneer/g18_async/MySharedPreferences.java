package com.example.muneer.g18_async;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Muneer on 01-11-2017.
 */

public class MySharedPreferences {
    public static final String SP_NAME = "mySharedPreferencesDatabase";
    SharedPreferences mySharedPreferencesDatabase;

    public MySharedPreferences(Context context) {
        mySharedPreferencesDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    public void setKiosk(String kiosk) {
        SharedPreferences.Editor userLocalDatabaseEditor = mySharedPreferencesDatabase.edit();
        userLocalDatabaseEditor.putString("kiosk", kiosk);
        userLocalDatabaseEditor.commit();
    }

    public String getKiosk() {
        String kiosk = mySharedPreferencesDatabase.getString("kiosk", "kiosk-1");
        return  kiosk;
    }
}
