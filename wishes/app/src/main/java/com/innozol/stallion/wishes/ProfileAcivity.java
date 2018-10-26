package com.innozol.stallion.wishes;

import com.innozol.stallion.wishes.database.WishesDatabaseHelper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import android.widget.AdapterView.OnItemClickListener;

public class ProfileAcivity extends Activity {
	
	private BirthDayDooot dooot;
	private Button fbImage,snd_btn,snd_greetng;
	private Button bday_info_1,bday_info_2;
	private ImageView profImage;
	private ImageLoader imageLoader;
	private TextView profName,profEvent;
	private SharedPreferences sharedPreferences;
	private SharedPreferences.Editor editor;
	private Animation left,left_slow,right,right_slow;
	private AnimationSet left_set,right_set;
	final String[] croptions = { "Portrait", "LandScape" };
	private ListView lv;
	ArrayAdapter<String> as;
	private Dialog d,d1;
	private Intent in;
	private Bundle b,profile;
	
	@SuppressLint("WorldWriteableFiles")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.profile);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		 b = getIntent().getExtras();
		profile = b.getBundle("prof");
		sharedPreferences = getSharedPreferences("profile", MODE_WORLD_WRITEABLE);
		as = new ArrayAdapter<String>(this,
				android.R.layout.select_dialog_item, croptions);
		d1 = new Dialog(this);
		d1.setTitle("Background");
		d1.setContentView(R.layout.crop_dialog);
		lv = (ListView) d1.findViewById(R.id.croplistView1);
		lv.setAdapter(as);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
		long arg3) {				
				d1.dismiss();
				
				in=new Intent(ProfileAcivity.this,PhotoSortrActivity.class);
				in.putExtra("diff",arg2+1);				
				startActivity(in);			
				
			}
		});
		
				
		editor = sharedPreferences.edit();
		imageLoader = new ImageLoader(getApplicationContext());
		profImage = (ImageView) findViewById(R.id.prof_imgView);
		profName = (TextView) findViewById(R.id.profile_name);
		profEvent = (TextView) findViewById(R.id.profile_event);
		snd_greetng = (Button) findViewById(R.id.prof_send_grtng);
		fbImage = (Button) findViewById(R.id.profile_fb);
		snd_btn = (Button) findViewById(R.id.prof_send_sms);
		bday_info_1 = (Button) findViewById(R.id.bday_info_1);
		bday_info_2 = (Button) findViewById(R.id.bday_info_2);
		
		snd_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(ProfileAcivity.this,SendMessageActivity.class));
			}
		});
		
		snd_greetng.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				d = new Dialog(ProfileAcivity.this,R.style.PopupAnimation);
				d.requestWindowFeature(Window.FEATURE_NO_TITLE);
				d.getWindow().setBackgroundDrawable(new ColorDrawable(0));
				d.setContentView(R.layout.general_dialog);
				TextView custm = (TextView)d.findViewById(R.id.button1_genral);
				TextView preTemp = (TextView)d.findViewById(R.id.button2_general);
				custm.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
												
						d.dismiss();
                         d1.show();
					
					}
				});
				
				preTemp.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						startActivity(new Intent(ProfileAcivity.this,General.class));
						
						d.dismiss();
					}
				});
				
				View v1 = (View)d.findViewById(R.id.view1_general);
				Animation sanim = AnimationUtils.loadAnimation(ProfileAcivity.this, R.anim.scale_anim);
				v1.startAnimation(sanim);
				d.show();
			}
		});
		
		
		fbImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String _id = dooot.getDootId();
				if(dooot.getDootType().equals(WishesDatabaseHelper.TYPE_FB)){
					String fb_id = _id.substring(2, _id.length());
					startActivity(getOpenFacebookIntent(getApplicationContext(),fb_id));
				}
				else{
					startActivity(new Intent(ProfileAcivity.this,EditProfile.class));
				}
			}
		});
		
		dooot = profile.getParcelable("profile");
		editor.putString("_id", dooot.getDootId());
		editor.putString("image_url", dooot.getDootImageUrl());
		editor.putString("name", dooot.getDootName());
		editor.putString("bday", dooot.getSBDay());
		editor.commit();
		
		bday_info_1.setText("Birthday                          "+Html.fromHtml(dooot.getSBDay()));
		bday_info_2.setText("Weekday                           "+CalendarUtils.getWeekDay(dooot.getDootBirthDay()));
		
		profName.setText(""+Html.fromHtml("<b>"+dooot.getDootName()+"</b>"));
		if(dooot.getDootType().equals(WishesDatabaseHelper.TYPE_FB)){
			imageLoader.DisplayImage(dooot.getDootImageUrl(), profImage, ImageLoader.TYPE_WEB);
			profEvent.setText("B'Day");
		}
		else{
			imageLoader.DisplayImage(dooot.getDootImageUrl(), profImage, ImageLoader.TYPE_LOCAL);
			WishesDatabaseHelper helper = new WishesDatabaseHelper(ProfileAcivity.this);
			profEvent.setText(helper.getEvent(dooot.getDootId()));
			helper.close();
			fbImage.setText("Update Profile");
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		left = AnimationUtils.loadAnimation(ProfileAcivity.this, R.anim.slide_in_left);
		left_slow = AnimationUtils.loadAnimation(ProfileAcivity.this, R.anim.slide_in_left1);
		right = AnimationUtils.loadAnimation(ProfileAcivity.this, R.anim.slide_in_right);
		right_slow = AnimationUtils.loadAnimation(ProfileAcivity.this, R.anim.slide_in_right1);
		
		left_set = new AnimationSet(true);
		right_set = new AnimationSet(true);
		
		left_set.addAnimation(left);
		left_set.addAnimation(left_slow);
		
		right_set.addAnimation(right);
		right_set.addAnimation(right_slow);
		
		Animation left_delay = AnimationUtils.loadAnimation(ProfileAcivity.this, R.anim.slide_in_left);
		Animation left_slow_delay = AnimationUtils.loadAnimation(ProfileAcivity.this, R.anim.slide_in_left1);
		
		AnimationSet left_set_delay = new AnimationSet(true);
		
		left_set_delay.addAnimation(left_delay);
		left_set_delay.addAnimation(left_slow_delay);
		left_set_delay.setStartOffset(750);
		
		snd_greetng.startAnimation(left_set);
		bday_info_1.startAnimation(left_set);
		fbImage.startAnimation(left_set_delay);
		snd_btn.startAnimation(right_set);
		bday_info_2.startAnimation(right_set);
		
	}
	
	
	
	public static Intent getOpenFacebookIntent(Context context,String fb_id) {

		   try {
		    context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
		    return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/"+fb_id));
		   } catch (Exception e) {
		    return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/"+fb_id));
		   }
		}

}
