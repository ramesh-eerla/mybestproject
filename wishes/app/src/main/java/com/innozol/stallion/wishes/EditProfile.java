package com.innozol.stallion.wishes;

import java.util.Map;

import com.innozol.stallion.wishes.database.WishesDatabaseHelper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class EditProfile extends Activity implements OnClickListener{
	
	private EditText name,eventname,eventdate,email,phonenumber;
	private ImageView iv;
	private Button save,datePick;
	private Bitmap bmap;
	private ImageLoader imageLoader;
	private WishesDatabaseHelper helper;
	private SharedPreferences preferences;
	private String _id;
	
	@SuppressLint("WorldReadableFiles")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.manual);
		
		name = (EditText)findViewById(R.id.ed_name_manual);
		eventname = (EditText)findViewById(R.id.ed_eventname_manual);
		eventdate = (EditText)findViewById(R.id.ed_eventdate_manual);
		email = (EditText)findViewById(R.id.ed_email_manual);
		phonenumber = (EditText)findViewById(R.id.ed_phnumber_manual);
		iv = (ImageView)findViewById(R.id.imageView_manual);
		save = (Button)findViewById(R.id.bt_save_manual);
		datePick = (Button) findViewById(R.id.btn_date_pickr);
		bmap = BitmapFactory.decodeResource(getResources(), R.drawable.photo);
		iv.setImageBitmap(bmap);
		imageLoader = new ImageLoader(EditProfile.this);
		preferences = getSharedPreferences("profile", MODE_WORLD_READABLE);

		name.setEnabled(false);
		datePick.setEnabled(false);
		eventdate.setEnabled(false);
		eventname.setEnabled(false);
		_id = preferences.getString("_id", "");
		String sName = preferences.getString("name", "");
		String img_url = preferences.getString("image_url", "");
		String bDay = preferences.getString("bday", "");		
		imageLoader .DisplayImage(img_url, iv, ImageLoader.TYPE_LOCAL);
		helper = new WishesDatabaseHelper(EditProfile.this);
		Map<String, String> details = helper.getMiscDetails(_id);
		helper.close();
		if(details.size() > 0){
			name.setText(sName);
			eventname.setText(details.get(WishesDatabaseHelper.KEY_EVENT));
			eventdate.setText(Html.fromHtml(bDay));
			email.setText(details.get(WishesDatabaseHelper.KEY_EMAIL));
			phonenumber.setText(details.get(WishesDatabaseHelper.KEY_MOBILE));
			if(details.get(WishesDatabaseHelper.KEY_EMAIL).equals(" ")){
				email.setText("");
			}
			if(details.get(WishesDatabaseHelper.KEY_MOBILE).equals(" ")){
				phonenumber.setText("");
			}
		}
		save.setText("Update");
		save.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
    	   String e_mail = email.getText().toString().trim();
    	   String ph_num = phonenumber.getText().toString().trim();
    	   Log.d("Email", e_mail);
    	   if(e_mail.isEmpty()){
        	   e_mail = " ";
        	}
    		if(ph_num.isEmpty()){
        	   ph_num = " ";
    		}
    		helper = new WishesDatabaseHelper(EditProfile.this);
    		helper.updateProfile(e_mail,ph_num,_id);
    		helper.close();
    	   	finish();
	}
}
