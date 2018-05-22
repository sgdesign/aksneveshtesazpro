package com.waycreon.pip;

		import android.app.Activity;
		import android.content.Intent;
		import android.content.pm.PackageInfo;
		import android.content.pm.PackageManager.NameNotFoundException;
		import android.os.Bundle;
		import android.view.View;
		import android.view.Window;
		import android.view.View.OnClickListener;
		import android.widget.LinearLayout;
		import android.widget.TextView;

public class about_us extends Activity {

	int size;
	TextView ver;
	String versions;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_us);

		PackageInfo pInfo;
		try {
			pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			versions = pInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		ver = (TextView)findViewById(R.id.ver);
		ver.setTextSize(size);
		ver.setText(versions);

		LinearLayout btn_moarefi = (LinearLayout) findViewById(R.id.moarefiLine);
		btn_moarefi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent sharei = new Intent (Intent.ACTION_SEND);
				sharei.setType("text/plain");
				sharei.putExtra(Intent.EXTRA_TEXT,"اگه به عکاسی و ویرایش عکس ها علاقه داری، اپلیکیشن اندرویدی (شیک و مجلسی) رو از کافه بازار دریافت کن و یه حالی به عکس هات بده :) https://cafebazaar.ir/app/ir.saeedgh.aksneveshteh/?l=fa");
				startActivity(Intent.createChooser(sharei,"معرفی برنامه به دوستان از طریق..."));

			}
		});


		LinearLayout feed = (LinearLayout) findViewById(R.id.feedbackLine);
		feed.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent (about_us.this , Contant.class);
				startActivity(intent);

			}
		});

	}
}