package com.example.prasanth.chatapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Prasanth on 10/6/2017.
 */

public class PreferenceConnector {
    public static final String PREF_NAME="ChatApp_SharedPrefreneces";
    public static final int MODE= Context.MODE_PRIVATE;

    public static SharedPreferences getSharedPreference(Context context) {
        return context.getSharedPreferences(PREF_NAME, MODE);
    }

    public static SharedPreferences.Editor getEditor(Context context)
    {
        return getSharedPreference(context).edit();
    }

    public static void writeString(Context context,String key,String value)
    {
        getEditor(context).putString(key,value).commit();
    }

    public static String readString(Context context,String key,String value)
    {
        return getSharedPreference(context).getString(key,value);
    }

    public static void writeInt(Context context,String key,int value)
    {
        getEditor(context).putInt(key,value).commit();
    }

    public static int readInt(Context context,String key,int value)
    {
        return getSharedPreference(context).getInt(key,value);
    }

    public static void writeBoolean(Context context,String key,Boolean value)
    {
        getEditor(context).putBoolean(key,value).commit();
    }

    public static Boolean readBoolean(Context context,String key,Boolean value)
    {
        return getSharedPreference(context).getBoolean(key,value);
    }

    public static void removeValue(Context context,String key)
    {
        getEditor(context).remove(key).commit();
    }

    public static void clearData(Context context)
    {
        getEditor(context).clear().commit();
    }
}
