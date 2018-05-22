package com.waycreon.pip;

import android.app.Application;
import android.graphics.Bitmap;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Waycreon on 7/31/2015.
 */
public class Global extends Application {

    Bitmap image;
    private int position = 0;

    private int color = 0xFFFF0000;


    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public static Context context;
    public static LayoutInflater layoutInflater;
    public static SharedPreferences preferences;


    @Override
    public void onCreate() {
        super.onCreate();
        FontsOverride.setDefaultFont(this, "MONOSPACE", "IRANSans.ttf");
        context=getApplicationContext();
        layoutInflater= (LayoutInflater) getSystemService(context.LAYOUT_INFLATER_SERVICE);


        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("IRANSans.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        preferences= PreferenceManager.getDefaultSharedPreferences(context);


    }
}
