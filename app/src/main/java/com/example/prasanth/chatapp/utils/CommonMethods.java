package com.example.prasanth.chatapp.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

public class CommonMethods {

    /*private ProgressDialog progressDialog;*/

    public static void getToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /*public static void loadProgressDialog()
    {

    }*/
}
