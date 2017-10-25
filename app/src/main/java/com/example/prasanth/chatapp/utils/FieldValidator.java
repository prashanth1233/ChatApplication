package com.example.prasanth.chatapp.utils;

import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Patterns;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Prasanth on 9/25/2017.
 */

public class FieldValidator {

    private int minCharLength = 5, maxCharLength = 10;

    public boolean validateUserNameSpace(EditText view) {
        String userName = view.getText().toString();
        if (userName.trim().equalsIgnoreCase("")) {
            String message = "Field should not be empty";
            setErrorTextToField(view, message);
            return false;
        }
        if (userName.length() > maxCharLength || userName.length() < minCharLength) {
            String message = "UserName should have min 4 char and max 10 char";
            setErrorTextToField(view, message);
            return false;
        }
        if (userName.contains(" ")) {
            String message = "This input does not fit the requiered pattern";
            setErrorTextToField(view, message);
            return false;
        }
        return true;
    }

    public boolean validatePassword(EditText view) {
        String password = view.getText().toString();
        if (password.trim().equalsIgnoreCase("")) {
            String message = "Password Field should not be empty";
            setErrorTextToField(view, message);
            return false;
        }
        if (password.length() > maxCharLength || password.length() < minCharLength) {
            String message = "Password should have min 4 char and max 10 char";
            setErrorTextToField(view, message);
            return false;
        }
        if (password.contains(" ") || password.contains(".")) {
            String message = "This input does not fit the requiered pattern";
            setErrorTextToField(view, message);
            return false;
        }
        return true;
    }

    public boolean validatePasswordMatch(EditText password, EditText reEnterPassword) {
        String firstPassword = password.getText().toString();
        String secondPassword = reEnterPassword.getText().toString();
        if (!firstPassword.equalsIgnoreCase(secondPassword)) {
            String message = "Password did not match";
            setErrorTextToField(reEnterPassword, message);
            return false;
        }
        return true;
    }


    public boolean validatePhoneNumberSpace(EditText view) {

        if (view.getText().toString().toString().trim().equalsIgnoreCase("")) {
            String message = "This field should not be empty.";
            setErrorTextToField(view, message);
            return false;
        }
        if ((view.getText().toString().toString().length() < 10)) {
            String message = "Please enter the correct phone number.";
            setErrorTextToField(view, message);
            return false;
        }
        return true;
    }

    public boolean validateEmailAddress(EditText view) {

       /* Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(view.getText().toString().trim());*/
        if (view.getText().toString().trim().equalsIgnoreCase("")) {
            String message = "Please enter email address";
            setErrorTextToField(view, message);
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(view.getText().toString().trim()).matches()) {
            String message = "Please enter valid email address";
            setErrorTextToField(view, message);
            return false;
        }

      /*  if (!matcher.matches()) {
            String message = "Please enter valid email address";
            setErrorTextToField(view, message);
            return false;
        }*/
        if (view.getText().toString().contains(" ")) {
            String message = "This input does not fit the requiered pattern";
            setErrorTextToField(view, message);
            return false;
        }
        return true;
    }

    public void setErrorTextToField(EditText view, String message) {
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.WHITE);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(message);
        spannableStringBuilder.setSpan(foregroundColorSpan, 0, message.length(), 0);
        view.requestFocus();
        view.setError(spannableStringBuilder);
    }
}



