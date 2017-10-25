package com.example.prasanth.chatapp.cutomviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.widget.EditText;

import com.example.prasanth.chatapp.R;

/**
 * Created by Prasanth on 10/6/2017.
 */

public class ChatAppCustomEditTextView extends AppCompatEditText {
    public ChatAppCustomEditTextView(Context context) {
        super(context);
    }

    public ChatAppCustomEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context,attrs);
    }

    public ChatAppCustomEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setCustomFont(context,attrs);
    }

    public void setCustomFont(Context context, AttributeSet attributeSet)
    {
        TypedArray typedArray=context.obtainStyledAttributes(attributeSet,R.styleable.ChatAppCustomFont);
        String customFont=typedArray.getString(R.styleable.ChatAppCustomFont_customFont);
        setCustomFont(context,customFont);
        typedArray.recycle();
    }

    public boolean setCustomFont(Context ctx, String asset) {
        Typeface typeface = null;
        try {
            typeface = Typeface.createFromAsset(ctx.getAssets(), asset);
        } catch (Exception e) {
            return false;
        }
        setTypeface(typeface);
        return true;
    }
}
