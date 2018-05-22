package com.waycreon.pip;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * @author Vlonjat Gashi (vlonjatg)
 */
public class CustomSlide extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_custom_slide, container, false);
        Button twitter = (Button) rootView.findViewById(R.id.twitterButton);
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://cafebazaar.ir/developer/754498987128/?l=fa"));
                    intent.setPackage("com.farsitel.bazaar");
                    startActivity(intent);
                } catch (Exception e) {
                    Intent next = new Intent(Intent.ACTION_VIEW);
                    next.setData(Uri.parse("https://cafebazaar.ir/developer/754498987128/?l=fa"));
                    startActivity(next);
                }
            }
        });


        return rootView;
    }

}
