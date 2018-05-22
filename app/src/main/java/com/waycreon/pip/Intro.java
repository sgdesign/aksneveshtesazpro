package com.waycreon.pip;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.vlonjatg.android.apptourlibrary.AppTour;
import com.vlonjatg.android.apptourlibrary.MaterialSlide;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * @author Vlonjat Gashi (vlonjatg)
 */
public class Intro extends AppTour {
    private boolean opened;

    @Override
    public void init(Bundle savedInstanceState) {


        opened = Global.preferences.getBoolean("OPENED", false);
        if (!opened) {

        }
        if (opened) {
            Intent i = new Intent(Intro.this,MainActivity.class);
            startActivity(i);
            Intro.this.finish();
        }

        //Color of fragments page
        int firstColor = Color.parseColor("#e3346c");
        int secondColor = Color.parseColor("#669999");
        int thirdColor = Color.parseColor("#990000");
        int forthColor = Color.parseColor("#e0d91c");
        int customSlideColor = Color.parseColor("#0b74ac");

        //Create Fragments
        Fragment firstSlide = MaterialSlide.newInstance(R.drawable.slide1, "عکس نوشته ساز",
                "به سادگی، در کوتاه ترین زمان عکس های بسیار شیک و جذاب بسازید", Color.WHITE, Color.WHITE);

        Fragment secondSlide = MaterialSlide.newInstance(R.drawable.slide2, "تنوع بسیار زیاد",
                "گلچینی از قاب عکس های بسیار زیبا و کاربرپسند در اختیار شماست", Color.WHITE, Color.WHITE);

        Fragment thirdSlide = MaterialSlide.newInstance(R.drawable.slide3, "عکس نوشته بسازید",
                "متن را به تصاویرتان اضافه کنید، فونت های منتخب و زیبا در اختیار شماست", Color.WHITE, Color.WHITE);

        Fragment forthSlide = MaterialSlide.newInstance(R.drawable.slide4, "بدون حد و مرز",
                "عکس نوشته ساز محدودیتی ندارد، هر تعداد عکس که می خواهید به راحتی ویرایش کرده و در دستگاه خودتان ذخیره کنید", Color.WHITE, Color.WHITE);

        //Add slides
        addSlide(firstSlide, firstColor);
        addSlide(secondSlide, secondColor);
        addSlide(thirdSlide, thirdColor);
        addSlide(forthSlide, forthColor);

        //create Custom slide
        addSlide(new CustomSlide(), customSlideColor);

        //Customize tour
        setSkipButtonTextColor(Color.WHITE);
        setNextButtonColorToWhite();
        setDoneButtonTextColor(Color.WHITE);
        setDoneText("بیخیال");
        setSkipText("رد کردن");
        setInactiveDocsColor(Color.GRAY);
        setActiveDotColor(Color.WHITE);
    }

    @Override
    public void onSkipPressed() {
        //Do something after clicking Skip button.
        //E.x: Go to the sign up slide.
        setCurrentSlide(4);
    }

    @Override
    public void onDonePressed() {
        Toast.makeText(this, "با امتیاز دادن به برنامه از ما حمایت کنید", Toast.LENGTH_LONG).show();
        startMainActivity();
    }
    //load Main Activity and Sheard Prefrences
    public void startMainActivity(){
        SharedPreferences.Editor editor = Global.preferences.edit();
        editor.putBoolean("OPENED", true);
        editor.commit();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        Intro.this.finish();
    }
    //change font
    @Override
    public void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
