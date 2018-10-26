package com.innozol.stallion.wishes;

import java.io.FileNotFoundException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.innozol.stallion.wishes.database.WishesDatabaseHelper;

public class WishListActivity extends Activity{
	
	private static final String TAG = "WishListActivity";
	private ArrayList<BirthDayDooot> mainList;
	private final String FIRST_RUN = "firstrun";
	private ImageButton home;
	private boolean isFirstRun = false;
	private SharedPreferences isFirtPref;
	private ListView listView;
	private ImageView settings;
	private Dialog preferencesDialog;
	private Button bTimeChange,bTimeSave;
	private TextView tTimeDisplay;
	private int minute = CalendarUtils.getCurrentMinutes();
	private int hour24 = CalendarUtils.getCurrentHours();
	private String time;
	
	OnTimeSetListener listener = new OnTimeSetListener() 
	{
		
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
			tTimeDisplay.setText(time);
		}
	};
	
	final UncaughtExceptionHandler handler = new UncaughtExceptionHandler() {
		
		@Override
		public void uncaughtException(Thread thread, Throwable ex) {
			Log.d(TAG, "Thread Name : "+thread.getName()+"\nThrowable : "+ex.getMessage() );
			handler.uncaughtException(thread, ex);
		}
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.wishlist);
		listView = (ListView) findViewById(R.id.wish_listview);
		
		listView.setBackgroundColor(Color.WHITE);
		listView.setCacheColorHint(Color.parseColor("#00000000"));
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.mainlist_title);
		isFirtPref = PreferenceManager.getDefaultSharedPreferences(WishListActivity.this);
		isFirstRun = isFirtPref.getBoolean(FIRST_RUN, true);
		ColorDrawable divider = new ColorDrawable(getResources().getColor(R.color.list_divider));
		listView.setDivider(divider);
		listView.setDividerHeight(4);
		Log.d(TAG, "FirstRun : "+isFirstRun);
		
		preferencesDialog = new Dialog(WishListActivity.this,R.style.PopupAnimation);
		preferencesDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		preferencesDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
		preferencesDialog.setContentView(R.layout.preferences);
		bTimeChange = (Button) preferencesDialog.findViewById(R.id.b_preferences_notify_at);
		bTimeSave = (Button) preferencesDialog.findViewById(R.id.b_pref_save);
		tTimeDisplay = (TextView) preferencesDialog.findViewById(R.id.t_preferences_notify_at_time);
		
		WishesDatabaseHelper timeHelper = new WishesDatabaseHelper(WishListActivity.this);
		String oldTime = timeHelper.getNotificationTime();
		time = oldTime;
		timeHelper.close();
		Log.d(TAG, tTimeDisplay+":"+oldTime);
		tTimeDisplay.setText(""+oldTime);
		
		bTimeSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				WishesDatabaseHelper helper = new WishesDatabaseHelper(WishListActivity.this);
				helper.changeNotificationTime(time);
				helper.close();
				if(preferencesDialog.isShowing())
					preferencesDialog.dismiss();
			}
		});
		
		bTimeChange.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDialog(0);
			}
		});
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				BirthDayDooot dooot = mainList.get(position);
				Bundle b = new Bundle();
				b.putParcelable("profile", dooot);
				Intent i = new Intent(WishListActivity.this,ProfileAcivity.class);
				i.putExtra("prof", b);
				startActivity(i);
			}
		});
		
		if(isFirstRun){
			WishesDatabaseHelper helper = new WishesDatabaseHelper(WishListActivity.this);
			String time = helper.getNotificationTime();
			helper.close();
			runRun(time);
			SharedPreferences.Editor ed = isFirtPref.edit();
			ed.putBoolean(FIRST_RUN, false);
			ed.commit();
		}else{
				WishesDatabaseHelper helper = new WishesDatabaseHelper(WishListActivity.this);
				if(helper.isNotifyTimeChanged()){
					String time = helper.getNotificationTime();
					runRun(time);
					helper.resetNotify();
				}
				helper.close();
		}
		
		home = (ImageButton) getWindow().findViewById(R.id.button_home);
		settings=(ImageView)getWindow().findViewById(R.id.settings_imageview) ;
		settings.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//startActivity(new Intent(WishListActivity.this,PreferencesActivity.class));
				preferencesDialog.show();
			}
		});
		
		home.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(WishListActivity.this,Home.class));
			}
		});

		SortingWishList list = null;
		try{
			list = SortedListFile.getFromFile(this);
		}
		catch(FileNotFoundException e){}
		if(list != null){
			Log.d(TAG, "Executing normal");
			mainList = list.getSortedList(); // This has to work.
			refreshList(mainList);
		}
		else{
			//Back up plan, or an alternative if file not exists.
			Log.d(TAG, "Executing Back-up Plan");
			WishesDatabaseHelper helper = new WishesDatabaseHelper(getApplicationContext());
			ArrayList<BirthDayDooot> unSortList = (ArrayList<BirthDayDooot>) helper.getAllProfiles();
			refreshList(unSortList);
			helper.close();
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		WishesDatabaseHelper helper = new  WishesDatabaseHelper(this);
		if(helper.isModModify()){
			helper.resetMod();
			refreshList((ArrayList<BirthDayDooot>)helper.getAllProfiles());
		}
		
		if(helper.isNotifyTimeChanged()){
			String time = helper.getNotificationTime();
			runRun(time);
			helper.resetNotify();
		}
		helper.close();
		
	}
	
	/*@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		BirthDayDooot dooot = mainList.get(position);
		Bundle b = new Bundle();
		b.putParcelable("profile", dooot);
		Intent i = new Intent(WishListActivity.this,ProfileAcivity.class);
		i.putExtra("prof", b);
		startActivity(i);
	}*/
	
	public void refreshList(ArrayList<BirthDayDooot> UnSortdooots){
		SortListService.sortList(getApplicationContext(), UnSortdooots, new SortListService.SortCallback() {
			@Override
			public void onSort(ArrayList<BirthDayDooot> dooots) {
				mainList = dooots;
				showList();
			}
		});
	}
	
	public void showList(){
		WishAdapter adapter = new WishAdapter(WishListActivity.this,
				R.layout.list_item,getLayoutInflater(),mainList,
				new ImageLoader(getApplicationContext()));
		adapter.setRunInBackground(true);
		listView.setAdapter(adapter);
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		return new TimePickerDialog(WishListActivity.this, listener, hour24,minute, true);
	}
	
	
	private void runRun(String time){
		Intent intent = new Intent(WishListActivity.this,AlarmSortReceiver.class);
		PendingIntent senderOld = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_NO_CREATE);
		boolean firstCreate = (senderOld == null);
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		if(firstCreate == true){
			Log.d("Old Alarm Cancelled", ""+firstCreate);
			am.cancel(senderOld);
		}
		Log.d("Old Alarm Continue", ""+firstCreate);
		PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		int hours = Integer.parseInt(time.split(":")[0]);
		int minutes = Integer.parseInt(time.split(":")[1]);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, hours);
		calendar.set(Calendar.MINUTE, minutes);
		calendar.set(Calendar.SECOND, 59);
		calendar.add(Calendar.SECOND, 5);
		am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 86400000, sender);
	}
	
}
