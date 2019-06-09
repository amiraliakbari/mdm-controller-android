package com.sabaos.mdmcontroller;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    SharedPreferences sharedPreferences;

    public SharedPref(Context context) {

        sharedPreferences = context.getSharedPreferences("NetworkUsageData", Context.MODE_PRIVATE);
    }

    public void saveData(String key, String value) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String loadData(String key) {

        String result = sharedPreferences.getString(key, "empty");

        return result;
    }
}
