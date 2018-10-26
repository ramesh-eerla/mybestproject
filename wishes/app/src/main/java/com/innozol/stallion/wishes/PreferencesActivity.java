package com.innozol.stallion.wishes;

import com.innozol.stallion.wishes.database.WishesDatabaseHelper;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TimePicker;

public class PreferencesActivity extends Activity {
	
	Button bChangeTime,bSaveTime;
	int minute = CalendarUtils.getCurrentMinutes();
	int hour24 = CalendarUtils.getCurrentHours();
	String time;
	OnTimeSetListener listener = new OnTimeSetListener() {
		
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			String hrs,mins;
			hrs = hourOfDay+"";
			mins = minute+"";
			if(minute == 0){
				mins = "00";
			}
			if(hourOfDay == 0){
				hrs = "00"; 
			}
			time = hrs+":"+mins;
			bChangeTime.setText(time);
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.preferences);
		
		bChangeTime = (Button) findViewById(R.id.b_preferences_notify_at);
		bSaveTime = (Button) findViewById(R.id.b_pref_save);
		
		WishesDatabaseHelper helper = new WishesDatabaseHelper(PreferencesActivity.this);
		String oldTime = helper.getNotificationTime();
		time = oldTime;
		helper.close();
		bChangeTime.setText(""+oldTime);
		
		bSaveTime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				WishesDatabaseHelper helper = new WishesDatabaseHelper(PreferencesActivity.this);
				helper.changeNotificationTime(time);
				helper.close();
			}
		});
		
		bChangeTime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDialog(0);
			}
		});
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		return new TimePickerDialog(PreferencesActivity.this, listener, hour24,minute, true);
	}
	
	
}
