package com.innozol.stallion.wishes;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("WorldReadableFiles")
public class SendMessageActivity extends Activity {
	
	private ImageView profImage,cntctBrwse,sendMsgBtn;
	private TextView profName;
	private EditText phNumber,messageBody;
	private SharedPreferences preferences;
	private ImageLoader imageLoader;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.send_sms);
		bindComponents();
		
		preferences = getSharedPreferences("profile", MODE_WORLD_READABLE);
		imageLoader = new ImageLoader(SendMessageActivity.this);
		
		imageLoader.DisplayImage(preferences.getString("image_url", ""), profImage, ImageLoader.TYPE_WEB);
		profName.setText(preferences.getString("name", ""));
		
		cntctBrwse.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
	            intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
	            startActivityForResult(intent, 1);
			}
		});
		
		sendMsgBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(!phNumber.getText().toString().trim().isEmpty() && !messageBody.getText().toString().trim().isEmpty()){
					Intent intent = new Intent(SendMessageActivity.this,SendMessageService.class);
		            intent.putExtra("phnumber", "");
		            intent.putExtra("msg", "");
		            startService(intent);
				}
				else{
					if(phNumber.getText().toString().trim().isEmpty()){
						phNumber.setError("enter phone number");
						phNumber.requestFocus();
					}
					if(messageBody.getText().toString().trim().isEmpty()){
						messageBody.setError("compose message");
						messageBody.requestFocus();
					}
				}
			}
		});
		
	}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null) {
	        Uri uri = data.getData();

	        if (uri != null) {
	            Cursor c = null;
	            try {
	                c = getContentResolver().query(uri, new String[]{ 
	                            ContactsContract.CommonDataKinds.Phone.NUMBER,  
	                            ContactsContract.CommonDataKinds.Phone.TYPE },
	                        null, null, null);

	                if (c != null && c.moveToFirst()) {
	                    String number = c.getString(0);
	                    phNumber.setText(""+number);
	                    int type = c.getInt(1);
//	                    showSelectedNumber(type, number);
	                }
	            } finally {
	                if (c != null) {
	                    c.close();
	                }
	            }
	        }
	    }
		
	}
	
	public void showSelectedNumber(int type, String number) {
	    Toast.makeText(this, type + ": " + number, Toast.LENGTH_LONG).show();      
	}
	
	private void bindComponents() {
		profImage = (ImageView) findViewById(R.id.sms_imgView);
		profName = (TextView) findViewById(R.id.sms_prof_name);
		phNumber = (EditText) findViewById(R.id.sms_ph_numbr);
		messageBody = (EditText) findViewById(R.id.sms_message);
		cntctBrwse = (ImageView) findViewById(R.id.sms_conct_brws);
		sendMsgBtn = (ImageView) findViewById(R.id.sms_send_btn);
	}
	

}
