package com.innozol.stallion.wishes;

import java.util.ArrayList;
import java.util.Calendar;

import com.innozol.stallion.wishes.database.WishesDatabaseHelper;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class ContactsAdapter extends BaseAdapter{
	
	private class ViewHolder{
		TextView name;
		TextView dob;
		ImageView pic;
		CheckBox select;
	}
	
	private ArrayList<String> renderList;
	private int mLayout;
	private LayoutInflater mInflater;
	private ImageLoader mLoader;
	private Uri photoUri;
	private Context mContext;
	private String phNumber = " ";
	
	public ContactsAdapter(ArrayList<String> details, int fromFbItem, LayoutInflater layoutInflater, ImageLoader imageLoader,Context context) {
		renderList = details;
		mLayout = fromFbItem;
		mInflater = layoutInflater;
		mLoader = imageLoader;
		mContext = context;
	}

	@Override
	public int getCount() {
		return renderList.size();
	}

	@Override
	public Object getItem(int position) {
		return renderList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View rowView = convertView;
		
		if(rowView == null){
			rowView = mInflater.inflate(mLayout, null);
			ViewHolder holder = new ViewHolder();
			holder.name = (TextView) rowView.findViewById(R.id.fb_prof_name);
			holder.dob = (TextView) rowView.findViewById(R.id.fb_prf_dob);
			holder.pic = (ImageView) rowView.findViewById(R.id.fb_prof_pic);
			holder.select = (CheckBox) rowView.findViewById(R.id.fb_select_check);
			rowView.setTag(holder);
		}
		
		ViewHolder holder = (ViewHolder) rowView.getTag();
		
		final String name = renderList.get(position).split("~")[0];
		Log.d(this.getClass().getName(), renderList.get(position).split("~")[1]);
		final String  bDay = CalendarUtils.formatContactDate(renderList.get(position).split("~")[1]);
		photoUri = ContentUris.withAppendedId (ContactsContract.Contacts.CONTENT_URI, Long.valueOf(renderList.get(position).split("~")[3]));
		final String imgUri = photoUri.toString();
		holder.select.setOnCheckedChangeListener(null);
		phNumber = renderList.get(position).split("~")[2];//Improve it
		if(phNumber.equals("null")){
			phNumber = " ";
		}
		WishesDatabaseHelper helper = new WishesDatabaseHelper(mContext);
		final String _id = "cnt"+renderList.get(position).split("~")[3];
		if(helper.isProfileExists(_id)){
			holder.select.setChecked(true);
		}
		else{
			holder.select.setChecked(false);
		}
		helper.close();
		
		holder.select.setButtonDrawable(R.drawable.checkbox_selector_cnts);
		
		holder.select.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					new Thread(){
						public void run() {
							final WishesDatabaseHelper helper = new WishesDatabaseHelper(mContext);
							if(!helper.isProfileExists(_id)){
								ContentValues values = new ContentValues();
								values.put(WishesDatabaseHelper._id, _id);
								values.put(WishesDatabaseHelper.KEY_NAME, name);
								values.put(WishesDatabaseHelper.KEY_DOB, bDay);
								values.put(WishesDatabaseHelper.KEY_IMAGE, imgUri);
								values.put(WishesDatabaseHelper.KEY_EVENT, "B'Day");
								values.put(WishesDatabaseHelper.KEY_TYPE, WishesDatabaseHelper.TYPE_CNTCT);
								helper.insertProfile(values);
							}
							if(!helper.isMiscExists(_id)){
								ContentValues values = new ContentValues();
								values.put(WishesDatabaseHelper._id,_id);
								values.put(WishesDatabaseHelper.KEY_MOBILE, phNumber);
								values.put(WishesDatabaseHelper.KEY_EMAIL, " ");
								helper.insertMiscEvent(values);
							}
							helper.close();
						};
					}.start();
				}
				else if(!isChecked){
					new Thread(){
						public void run() {
							final WishesDatabaseHelper helper = new WishesDatabaseHelper(mContext);
							if(helper.isProfileExists(_id)){
								helper.deleteProfile(_id);
							}
							helper.close();
						};
					}.start();
				}
			}
		});
		
		Calendar cal = CalendarUtils.stringToCalendar(bDay);
		holder.dob.setText(Html.fromHtml(CalendarUtils.styleDate(cal)));
		holder.name.setText(name);
		mLoader.DisplayImage(imgUri, holder.pic,ImageLoader.TYPE_CONTACT);
		
		rowView.setBackgroundResource(R.drawable.cnts_bordr);
		
		return rowView;
	}
	
}
