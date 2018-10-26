package com.innozol.stallion.wishes;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.List;

import com.innozol.stallion.wishes.QuickAction.OnActionItemClickListener;
import com.innozol.stallion.wishes.database.WishesDatabaseHelper;
import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.Action;
import com.markupartist.android.widget.ActionBar.IntentAction;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ManualActivity extends Activity {

	private ListView lv;
	private ImageLoader imageLoader;
	private List<BirthDayDooot> dooots;
	private ActionBar actionBar;
	private int DELETE = 0, DELETE_ALL = 1;
	private MyAdapter adapter;
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;
	private RelativeLayout relCancelDelete;
	private Dialog d;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.addingevents);
		actionBar = (ActionBar) findViewById(R.id.actionbar);
		actionBar.setTitle("Manual Events");
		Intent i = new Intent(ManualActivity.this, AddManual.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Action action = new IntentAction(getApplicationContext(), i,
				R.drawable.mn_add_new);
		actionBar.addAction(action);
		lv = (ListView) findViewById(R.id.listView_addwish);
		lv.setBackgroundColor(Color.WHITE);
		lv.setCacheColorHint(Color.parseColor("#00000000"));
		ColorDrawable divider = new ColorDrawable(getResources().getColor(R.color.list_divider));
		lv.setDivider(divider);
		lv.setDividerHeight(4);
		imageLoader = new ImageLoader(getApplicationContext());
		ActionItem actionDeleteItem = new ActionItem(DELETE, "Delete");
		ActionItem actionDeleteAllItem = new ActionItem(DELETE_ALL,
				"Delete All");
		final QuickAction quickAction = new QuickAction(ManualActivity.this,
				QuickAction.VERTICAL,1);
		quickAction.addActionItem(actionDeleteItem);
		quickAction.addActionItem(actionDeleteAllItem);
		
		quickAction
				.setOnActionItemClickListener(new OnActionItemClickListener() {

					@Override
					public void onItemClick(QuickAction source, int pos,
							int actionId) {

						if (actionId == DELETE) {
							
							actionBar.addAction(new Action() {
								
								@Override
								public void performAction(View view) {
									refreshList(R.layout.list_item);
									adapter = new MyAdapter(dooots,R.layout.list_item);
									lv.setAdapter(adapter);
									int count = actionBar.getActionCount();
									actionBar.removeActionAt(count-1);
								}
								
								@Override
								public int getDrawable() {
									return R.drawable.tick;
								}
							});
							adapter = new MyAdapter(dooots,R.layout.item_with_delete);
							lv.setAdapter(adapter);
							
						} else if (actionId == DELETE_ALL) {
							
							d = new Dialog(ManualActivity.this,R.style.PopupAnimation);
							d.requestWindowFeature(Window.FEATURE_NO_TITLE);
							d.setContentView(R.layout.dialog);
							TextView tv1=(TextView)d.findViewById(R.id.dailogtext);
							tv1.setText("Are you sure you want to delete selected item?");
							Button bt1 = (Button)d.findViewById(R.id.btn1);
							Button bt2 = (Button)d.findViewById(R.id.btn2);
							View v1 = (View)d.findViewById(R.id.view);
							View v2 = (View)d.findViewById(R.id.view1);
							v1.bringToFront();
							v2.bringToFront();
							
							bt1.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									WishesDatabaseHelper helper = new WishesDatabaseHelper(
											ManualActivity.this);
									helper.deleteAll(WishesDatabaseHelper.TYPE_MN);
									helper.close();
									refreshList(R.layout.list_item);
									adapter = new MyAdapter(dooots,R.layout.list_item);
									lv.setAdapter(adapter);
									adapter.notifyDataSetChanged();
									d.dismiss();
								}
							});
			               bt2.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									d.dismiss();
								}
							});
							
							d.show();
							
							
						}

					}
				});

		actionBar.addAction(new Action() {

			@Override
			public void performAction(View view) {
				quickAction.show(view);
			}

			@Override
			public int getDrawable() {
				return R.drawable.mn_delete_old;
			}
		});
			

	}
	
	
	private void refreshList(int layout){
		WishesDatabaseHelper helper = new WishesDatabaseHelper(
				ManualActivity.this);
		dooots = helper.getTypeProfiles(WishesDatabaseHelper.TYPE_MN);
		helper.close();
		adapter = new MyAdapter(dooots,layout);
		lv.setAdapter(adapter);
	}

	@Override
	protected void onStart() {
		super.onStart();
		refreshList(R.layout.list_item);
	}

	@SuppressLint("WorldWriteableFiles")
	@Override
	protected void onResume() {
		super.onResume();
		preferences = getSharedPreferences("HOLD_ON", MODE_WORLD_WRITEABLE);
		editor = preferences.edit();
		
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		
		View v=getWindow().getDecorView();
		v.setDrawingCacheEnabled(true);		
		Bitmap bim=Bitmap.createBitmap(v.getDrawingCache());
		v.setDrawingCacheEnabled(false);	 
		  
		  File file = new File( Environment.getExternalStorageDirectory() +"/wish/manual.jpg");
		  try 
		  {
			  if(file.exists()){
				  
				  file.delete();
				  file.createNewFile();
				   FileOutputStream ostream = new FileOutputStream(file);
				   bim.compress(Bitmap.CompressFormat.JPEG,90,ostream);
				   ostream.flush();        
				   ostream.close();
			  }else{
		  
		   file.createNewFile();
		   FileOutputStream ostream = new FileOutputStream(file);
		   bim.compress(Bitmap.CompressFormat.JPEG,90,ostream);
		   ostream.flush();        
		   ostream.close();}
			  }catch(Exception e){}
		
		
		
		
		if(relCancelDelete != null)
		if(relCancelDelete.isShown()){
			relCancelDelete.setVisibility(View.GONE);
			refreshList(R.layout.list_item);
			adapter = new MyAdapter(dooots,R.layout.list_item);
			lv.setAdapter(adapter);
		}
		else{
			String welcome = preferences.getString("welcome", "UNKNOWN");
			if(welcome.equals("NEW")){
				editor.clear();
				editor.commit();
				Log.d("FromFacebookActivity : NEW", welcome);
				startActivity(new Intent(this,WishListActivity.class));
				finish();
			}
			else{
				Log.d("FromFacebookActivity : UNK", welcome);
			}
		}
		
	}

	public class MyAdapter extends BaseAdapter {

		private List<BirthDayDooot> mDooots;
		private int mLayout;

		public MyAdapter(List<BirthDayDooot> dooots,int layout) {
			mDooots = dooots;
			mLayout = layout;
		}

		private class ViewHolder {
			TextView name;
			TextView dob;
			ImageView pic;
			Button delete;
		}

		@Override
		public int getCount() {
			return mDooots.size();
		}

		@Override
		public Object getItem(int arg0) {
			return mDooots.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View rowView = convertView;
			final int pos = position;

			if (rowView == null) {
				rowView = getLayoutInflater().inflate(mLayout, null);
				ViewHolder holder = new ViewHolder();
				holder.name = (TextView) rowView
						.findViewById(R.id.profNameItem);
				holder.dob = (TextView) rowView.findViewById(R.id.profDob);
				holder.pic = (ImageView) rowView.findViewById(R.id.profImage);
				holder.delete = (Button) rowView.findViewById(R.id.b_item_delete);
				rowView.setTag(holder);
			}

			ViewHolder holder = (ViewHolder) rowView.getTag();

			String name = mDooots.get(position).getDootName();
			String bDay = mDooots.get(position).getSBDay();
			String imgUrl = mDooots.get(position).getDootImageUrl();

			Calendar cal = CalendarUtils.stringToCalendar(bDay);
			holder.dob.setTextColor(Color.parseColor("#000000"));
			holder.name.setTextColor(Color.parseColor("#000000"));
			holder.dob.setText(Html.fromHtml(CalendarUtils.styleDate(cal)));
			holder.name.setText(name);
			holder.dob.setShadowLayer(0, 0, 0, 0);
			holder.name.setShadowLayer(0, 0, 0, 0);
			holder.dob.setTextAppearance(getApplicationContext(), R.style.normalText);
			holder.name.setTextAppearance(getApplicationContext(), R.style.normalText);
			if(holder.delete != null){
				holder.delete.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						WishesDatabaseHelper helper = new WishesDatabaseHelper(ManualActivity.this);
						helper.deleteProfile(mDooots.get(pos).getDootId());
						helper.close();
						refreshList(R.layout.item_with_delete);
						notifyDataSetChanged();
					}
				});
			}
			
			rowView.setBackgroundDrawable(getResources().getDrawable(R.drawable.mn_bordr));
			Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
			rowView.startAnimation(animation);
			
			imageLoader
					.DisplayImage(imgUrl, holder.pic, ImageLoader.TYPE_LOCAL);

			return rowView;
		}

	}
	


}
