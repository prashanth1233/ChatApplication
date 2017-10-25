package com.example.prasanth.chatapp.cutomviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import com.example.prasanth.chatapp.R;

/**
 * Created by Prasanth on 10/6/2017.
 */

public class ChatAppCustomButton extends AppCompatButton {
    /**
     * @param context context as parameter
     */
    public ChatAppCustomButton(Context context) {
        super(context);
    }

    /**
     * @param context context as parameter
     * @param attrs   AttributeSet as parameter
     */
    public ChatAppCustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    /**
     * @param context      context as parameter
     * @param attrs        AttributeSet as parameter
     * @param defStyleAttr attribute style as parameter
     */
    public ChatAppCustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setCustomFont(context, attrs);
    }

    /**
     * @param ctx   context
     * @param attrs attributes
     */
    private void setCustomFont(Context ctx, AttributeSet attrs) {
        TypedArray typedArray = ctx.obtainStyledAttributes(attrs, R.styleable.ChatAppCustomFont);
        String customFont = typedArray.getString(R.styleable.ChatAppCustomFont_customFont);
        setCustomFontAsset(ctx, customFont);
        typedArray.recycle();
    }

    /**
     * to set custom font to button
     *
     * @param ctx   context as parameter
     * @param asset custom font style from assets
     * @return custom font
     */
    public boolean setCustomFontAsset(Context ctx, String asset) {
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
