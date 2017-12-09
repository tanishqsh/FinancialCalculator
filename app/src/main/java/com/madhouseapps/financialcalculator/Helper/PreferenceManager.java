package com.madhouseapps.financialcalculator.Helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by tanishqsharma on 07/12/17.
 */

public class PreferenceManager {

    Context mContext;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public PreferenceManager(Context context){
        mContext = context;
        preferences = mContext.getSharedPreferences("FinCalPreferences", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public boolean addIntegerPreference(String prefName, int Value){
        editor.putInt(prefName, Value);
        return editor.commit();
    }

    public boolean addStringPreference(String prefName, String Value){
        editor.putString(prefName, Value);
        return editor.commit();
    }

    public boolean clearPreferences(){
        editor.clear();
        return true;
    }

    public int getIntegerPreferences(String prefName){
        return preferences.getInt(prefName, 0);
    }

    public String getStringPreferences(String prefName){
        return preferences.getString(prefName, "");
    }


}
