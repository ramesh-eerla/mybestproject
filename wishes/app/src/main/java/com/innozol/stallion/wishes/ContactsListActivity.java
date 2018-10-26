package com.innozol.stallion.wishes;

import java.util.ArrayList;
import java.util.List;

import com.innozol.stallion.wishes.database.WishesDatabaseHelper;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.util.Log;
import android.view.Window;

public class ContactsListActivity extends ListActivity {
	
	private String phnumber, name;
	private ArrayList<String> details = new ArrayList<String>();
	private long id;
	private MyAsyn task;
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;
	private ProgressDialog progressDialog;
	ContactsAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getListView().setBackgroundColor(Color.WHITE);
		getListView().setCacheColorHint(Color.parseColor("#00000000"));
		ColorDrawable divider = new ColorDrawable(getResources().getColor(R.color.list_divider));
		getListView().setDivider(divider);
		getListView().setDividerHeight(4);
		getListView().setSelector(android.R.color.transparent);
		task = new MyAsyn();
		task.execute();
		
/*		WishesDatabaseHelper helper = new WishesDatabaseHelper(ContactsListActivity.this);
		List<BirthDayDooot> contactList = helper.getTypeProfiles(WishesDatabaseHelper.TYPE_CNTCT);
		helper.close();
		*/
	}
	
	@SuppressLint("WorldWriteableFiles")
	@Override
	protected void onResume() {
		super.onResume();
		preferences = getSharedPreferences("HOLD_ON", MODE_WORLD_WRITEABLE);
		editor = preferences.edit();
	}
	
	private class MyAsyn extends AsyncTask<Void, Integer, ArrayList<String>>{
		
//		int totCount = 0, cnt = 0;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
//			progressDialog = ProgressDialog.show(ContactsListActivity.this, "", "Searching events...");
			progressDialog = new ProgressDialog(ContactsListActivity.this);
			progressDialog.setMessage("Searching...");
			progressDialog.setOnCancelListener(new OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					UIUtils.showToast(ContactsListActivity.this, "Cancelled");
					task.cancel(true);
					finish();
				}
			});
			progressDialog.show();
		}
		
		@Override
		protected ArrayList<String> doInBackground(Void... params) {
			ContentResolver cr = getContentResolver();
			String[] projection = new String[] { ContactsContract.Contacts._ID,
					ContactsContract.Contacts.DISPLAY_NAME };
			Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
					projection, null, null, ContactsContract.Contacts.DISPLAY_NAME
							+ " COLLATE LOCALIZED ASC");
			while (cur.moveToNext()) {
				id = cur.getLong(cur.getColumnIndex(ContactsContract.Contacts._ID));
				String contactId = cur.getString(cur.getColumnIndex(ContactsContract.Data._ID));
				Uri u1 = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
				ContentResolver cr1 = getContentResolver();
				String phoneid = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
				Cursor c1 = cr1.query(u1, null, phoneid + "=?",
						new String[] { contactId }, null);
				// **************************************************************************************//
				String columns[] = {
						ContactsContract.CommonDataKinds.Event.START_DATE,
						ContactsContract.CommonDataKinds.Event.TYPE,
						ContactsContract.CommonDataKinds.Event.MIMETYPE, };

				String where = Event.TYPE + "=" + Event.TYPE_BIRTHDAY + " and "
						+ Event.MIMETYPE + " = '" + Event.CONTENT_ITEM_TYPE
						+ "' and " + ContactsContract.Data.CONTACT_ID + " = "
						+ contactId;

				String[] selectionArgs = null;
				String sortOrder = ContactsContract.Contacts.DISPLAY_NAME;
				if( c1 != null )
				while (c1.moveToNext()) {
					phnumber = c1
							.getString(c1
									.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
					name = c1
							.getString(c1
									.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
										
				}
				
				c1.close();
				
				Cursor birthdayCur = cr.query(ContactsContract.Data.CONTENT_URI,
						columns, where, selectionArgs, sortOrder);
				if( birthdayCur != null)
				if (birthdayCur.getCount() > 0) {
					while (birthdayCur.moveToNext()) {
						String birthday = birthdayCur
								.getString(birthdayCur
										.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE));
						details.add(name+"~"+birthday+"~"+phnumber+"~"+id);
					}
				}
				birthdayCur.close();

			}
			return details;
		}
		
		@Override
		protected void onPostExecute(ArrayList<String> result) {
			super.onPostExecute(result);
			if(progressDialog != null)
					progressDialog.dismiss();
			if(result.size() == 0 ){
				UIUtils.showToast(ContactsListActivity.this, "No Contacts with Events");
				startActivity(new Intent(ContactsListActivity.this,Home.class));
				//finish();
			}
			adapter = new ContactsAdapter(details,R.layout.from_fb_item,getLayoutInflater(),new ImageLoader(ContactsListActivity.this),ContactsListActivity.this);
			setListAdapter(adapter);
			
		}
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		String welcome = preferences.getString("welcome", "UNKNOWN");
		if(welcome.equals("NEW")){
			editor.clear();
			editor.commit();
			Log.d("ContactsActivity : NEW", welcome);
			startActivity(new Intent(this,WishListActivity.class));
			finish();
		}
		else{
			Log.d("ContactsActivity : UNK", welcome);
		}
	}
	
}
