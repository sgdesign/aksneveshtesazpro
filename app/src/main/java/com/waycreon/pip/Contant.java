package com.waycreon.pip;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Contant extends Activity {

	Button btn_send, btn_mail;
	EditText textMessage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contant);

		btn_mail = (Button) findViewById(R.id.buttonemail);
		btn_send = (Button) findViewById(R.id.buttonSend);
		textMessage = (EditText) findViewById(R.id.editTextSMS);

		btn_send.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

						String message = textMessage.getText().toString();
						
						if ((message == null) || message.isEmpty()) {				
							Toast.makeText(getBaseContext(), "لطفا ابتدا نظر، انتقاد و پیشنهاد خود را بنویسید سپس اقدام به ارسال کنید!",Toast.LENGTH_SHORT).show();
						}else{

								Intent sendSms=new Intent(Intent.ACTION_VIEW);
								sendSms.putExtra("sms_body", message);
								sendSms.putExtra("address", "+989372986776");
								sendSms.setType("vnd.android-dir/mms-sms");
								startActivity(sendSms);
						}
			}
		});
		
		btn_mail.setOnClickListener(new View.OnClickListener() {
			 
			@Override
			public void onClick(View v) {
	 
				  String to = "support@javancomputer.com".toString();
				  String message = textMessage.getText().toString();
				  
					if ((message == null) || message.isEmpty()) {				
						Toast.makeText(getBaseContext(), "لطفا ابتدا نظر، انتقاد و پیشنهاد خود را بنویسید سپس اقدام به ارسال کنید!",Toast.LENGTH_SHORT).show();
					}else{
	 
					  Intent email = new Intent(Intent.ACTION_SEND);
					  email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
					  email.putExtra(Intent.EXTRA_SUBJECT,"ارسال شده از طریق اپلیکیشن عکس نوشته ساز");
					  email.putExtra(Intent.EXTRA_TEXT, message);
					  //need this to prompts email client only
					  email.setType("message/rfc822");	  
					  startActivity(Intent.createChooser(email, "ایمیل با چه برنامه ای ارسال شود؟"));
					}
				  
				}
	
		});


	}
}