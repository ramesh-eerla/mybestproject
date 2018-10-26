package com.innozol.stallion.wishes.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.innozol.stallion.wishes.BirthDayDooot;
import com.innozol.stallion.wishes.CalendarUtils;

public class WishesDatabaseHelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "wishes_test_db_13";
	private static final int DB_VERSION = 1;
	private SQLiteDatabase database;
	private final String FINAL_LIST_TABLE = "final_list";
	private final String TABLE_PROFILE = "profile";
	public static final String _id = "_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_DOB = "dob";
	public static final String KEY_IMAGE = "image";
	public static final String KEY_TYPE = "type";
	public static final String KEY_EVENT = "event";
	public static final String KEY_MOBILE = "mobile";
	public static final String KEY_EMAIL = "email";
	public static final String TYPE_FB = "facebook";
	public static final String TYPE_MN = "manual";
	public static final String TYPE_CNTCT = "contacts";
	public static int END_OFFSET;
	
	public WishesDatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table if not exists mod_change(old integer,new integer,ischanged integer)");
		db.execSQL("create table if not exists "+FINAL_LIST_TABLE+"("+_id+" varchar2 primary key,name varchar2,dob datetime,image varchar2,type text,event varchar2)");
		db.execSQL("create table if not exists "+TABLE_PROFILE+"("+_id+" varchar2, mobile varchar2, email varchar2, foreign key("+_id+") references "+FINAL_LIST_TABLE+"("+_id+") ON DELETE CASCADE)");
		db.execSQL("create table if not exists preferences(notify_time varchar2, ischanged integer)");
		db.execSQL("insert into preferences values('23:59',0)");
	}
	
	public void changeNotificationTime(String time){
		database = getWritableDatabase();
		database.execSQL("update preferences set notify_time = '"+time+"',ischanged = 1");
	}
	
	public boolean isNotifyTimeChanged(){
		boolean flag = false;
		database = getReadableDatabase();
		Cursor cursor = database.rawQuery("select ischanged from preferences", null);
		if(cursor != null)
			if(cursor.getCount() == 1){
				cursor.moveToNext();
				if(cursor.getInt(0) == 1)
					flag = true;
			}
		cursor.close();
		return flag;
	}
	
	public void resetNotify() {
		database = getWritableDatabase();
		database.execSQL("update preferences set ischanged = 0");
	}
	
	public String getNotificationTime(){
		String time = null;
		database = getReadableDatabase();
		Cursor cursor = database.rawQuery("select notify_time from preferences", null);
		if(cursor != null)
			if(cursor.getCount() == 1){
				cursor.moveToNext();
					time = cursor.getString(0);
			}
		cursor.close();
		return time;
	}
	
	public boolean isModModify(){
		boolean flag = false;
		database = getReadableDatabase();
		Cursor cursor = database.rawQuery("select ischanged from mod_change", null);
		if(cursor != null)
			if(cursor.getCount() == 1){
				cursor.moveToNext();
				if(cursor.getInt(0) == 1)
					flag = true;
			}
		cursor.close();
		return flag;
	}
	
	public void insertMiscEvent(ContentValues values){
		database = getWritableDatabase();
		database.insert(TABLE_PROFILE, null, values);
	}
	
	public long insertProfile(ContentValues values){
		database = getWritableDatabase();
		int old = getRowCount();
		long new_id = database.insert(FINAL_LIST_TABLE, null, values);
		if(new_id != 0){
			if(isModEmpty())
				database.execSQL("insert into mod_change values(0,1,1)");
			else
				database.execSQL("update mod_change set old = "+old+", new = "+getRowCount()+", ischanged = 1");
		}
		return new_id;
	}
	
	public void updateProfile(String email,String phnmbr,String _id){
		database = getWritableDatabase();
		database.execSQL("update "+TABLE_PROFILE+" set "+KEY_EMAIL+" = '"+email+"',"+KEY_MOBILE+" = '"+phnmbr+"' where "+WishesDatabaseHelper._id+" = '"+_id+"'");
	}
	
	/**
	 * 
	 * @param _id
	 * @return event,mobile,email
	 */
	public Map<String, String> getMiscDetails(String _id){
		Map<String, String> dets = new HashMap<String, String>();
		database = getReadableDatabase();
		Cursor c = database.rawQuery("select * from "+TABLE_PROFILE+" where "+WishesDatabaseHelper._id+" = ?", new String[]{_id});
		if(c!=null){
			if(c.getCount() > 0){
				c.moveToNext();
//				dets.put(KEY_EVENT, c.getString(c.getColumnIndexOrThrow(KEY_EVENT)));
				dets.put(KEY_MOBILE, c.getString(c.getColumnIndexOrThrow(KEY_MOBILE)));
				dets.put(KEY_EMAIL, c.getString(c.getColumnIndexOrThrow(KEY_EMAIL)));
			}
		}
		c.close();
		return dets;
	}
	
	public boolean isMiscExists(String id){
		boolean flag = false;
		database = getReadableDatabase();
		Cursor cursor = database.rawQuery("select * from "+TABLE_PROFILE+" where "+_id+" = ?", new String[]{id});
		if(cursor != null)
			if(cursor.getCount() == 1)
				flag = true;
		cursor.close();
		return flag;
	}
	
	public int deleteProfile(String id){
		int rowEffcted = 0;
		database = getWritableDatabase();
		int old = getRowCount();
		rowEffcted = database.delete(FINAL_LIST_TABLE, "_id = ?", new String[]{id});
		if(rowEffcted > 0)
			database.execSQL("update mod_change set old = "+old+", new = "+getRowCount()+", ischanged = 1");
		return rowEffcted;
	}
	
	public String getEvent(String _id){
		String evnt = null;
		database = getReadableDatabase();
		Cursor c = database.rawQuery("select "+KEY_EVENT+" from "+FINAL_LIST_TABLE+" where "+WishesDatabaseHelper._id+" = ?", new String[]{_id});
		if( c!= null){
			if(c.getCount() >0){
				c.moveToNext();
				evnt = c.getString(c.getColumnIndexOrThrow(KEY_EVENT));
			}
		}
		c.close();
		return evnt;
	}
	
	public boolean isModEmpty(){
		boolean flag = false;
		database = getReadableDatabase();
		Cursor cursor = database.rawQuery("select * from mod_change", null);
		if(cursor != null)
			if(cursor.getCount() == 0)
				flag = true;
		cursor.close();
		return flag;
	}
	
	
	public boolean isProfileExists(String id){
		boolean flag = false;
		database = getReadableDatabase();
		Cursor cursor = database.rawQuery("select * from "+FINAL_LIST_TABLE+" where "+_id+" = ?", new String[]{id});
		if(cursor != null)
			if(cursor.getCount() == 1)
				flag = true;
		cursor.close();
		return flag;
	}

	public List<BirthDayDooot> getAllProfiles(){
		List<BirthDayDooot> dooots = new ArrayList<BirthDayDooot>();
		database = getReadableDatabase();
		Cursor cursor = database.query(FINAL_LIST_TABLE, null, null, null, null, null, null);
		if(cursor != null)
			if(cursor.getCount() > 0){
				while(cursor.moveToNext()){
					String name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME));
					String bDay = cursor.getString(cursor.getColumnIndexOrThrow(KEY_DOB));
					Log.d("Database", ""+cursor.getString(cursor.getColumnIndexOrThrow(KEY_IMAGE)));
					String imgUrl = cursor.getString(cursor.getColumnIndexOrThrow(KEY_IMAGE));
					String id = cursor.getString(cursor.getColumnIndexOrThrow(_id));
					String type = cursor.getString(cursor.getColumnIndexOrThrow(KEY_TYPE));
					String event = cursor.getString(cursor.getColumnIndexOrThrow(KEY_EVENT));
					BirthDayDooot dooot = new BirthDayDooot(name, CalendarUtils.stringToCalendar(bDay), imgUrl, id,type,event);
					dooots.add(dooot);
				}
			}
		cursor.close();
		return dooots;
	}
	
	public List<BirthDayDooot> getLimitProfiles(int startOffset,int endOffset){
		List<BirthDayDooot> dooots = new ArrayList<BirthDayDooot>();
		database = getReadableDatabase();
		Cursor cursor = database.rawQuery("select * from "+FINAL_LIST_TABLE+" order by "+KEY_DOB+" LIMIT "+startOffset+", "+endOffset, null);
		if(cursor != null)
			if(cursor.getCount() > 0){
				while(cursor.moveToNext()){
					String name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME));
					String bDay = cursor.getString(cursor.getColumnIndexOrThrow(KEY_DOB));
					String imgUrl = cursor.getString(cursor.getColumnIndexOrThrow(KEY_IMAGE));
					String id = cursor.getString(cursor.getColumnIndexOrThrow(_id));
					String type = cursor.getString(cursor.getColumnIndexOrThrow(KEY_TYPE));
					String event = cursor.getString(cursor.getColumnIndexOrThrow(KEY_EVENT));
					BirthDayDooot dooot = new BirthDayDooot(name, CalendarUtils.stringToCalendar(CalendarUtils.formatDate(bDay)), imgUrl, id,type,event);
					dooots.add(dooot);
				}
			}
		cursor.close();
		return dooots;
	}
	
	public List<BirthDayDooot> getTypeProfiles(String pType){
		List<BirthDayDooot> dooots = new ArrayList<BirthDayDooot>();
		database = getReadableDatabase();
		Cursor cursor = database.query(FINAL_LIST_TABLE, null, KEY_TYPE+" = ?", new String[]{pType}, null, null, null);
		if(cursor != null)
			if(cursor.getCount() > 0){
				while(cursor.moveToNext()){
					String name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME));
					Log.d("Database Profile type "+pType, "DOB : "+cursor.getString(cursor.getColumnIndexOrThrow(KEY_DOB)));
					String bDay = cursor.getString(cursor.getColumnIndexOrThrow(KEY_DOB));
//					Log.d("Database Profile type "+pType, "Image : "+cursor.getString(cursor.getColumnIndexOrThrow(KEY_IMAGE)));
					String imgUrl = cursor.getString(cursor.getColumnIndexOrThrow(KEY_IMAGE));
					String id = cursor.getString(cursor.getColumnIndexOrThrow(_id));
					String type = cursor.getString(cursor.getColumnIndexOrThrow(KEY_TYPE));
					String event = cursor.getString(cursor.getColumnIndexOrThrow(KEY_EVENT));
					BirthDayDooot dooot = new BirthDayDooot(name, CalendarUtils.stringToCalendar(bDay), imgUrl, id,type,event);
					dooots.add(dooot);
				}
			}
		cursor.close();
		return dooots;
	}
	
	public BirthDayDooot getProfile(String _id){
		BirthDayDooot dooot = null;
		database = getReadableDatabase();
		Cursor cursor = database.query(FINAL_LIST_TABLE, null, " _id = ?", new String[]{_id}, null, null, null);
		if(cursor != null)
			if(cursor . getCount() > 0){
				cursor.moveToNext();
				String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
				String bDay = cursor.getString(cursor.getColumnIndexOrThrow("dob"));
				String imgUrl = cursor.getString(cursor.getColumnIndexOrThrow("image"));
				String id = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
				String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
				String event = cursor.getString(cursor.getColumnIndexOrThrow(KEY_EVENT));
				dooot = new BirthDayDooot(name, CalendarUtils.stringToCalendar(CalendarUtils.formatDate(bDay)), imgUrl, id,type,event);
			}
		cursor.close();
		return dooot;
	}
	
	public int getRowCount(){
		database = getReadableDatabase();
		Cursor cursor = database.rawQuery("select count(*) from "+FINAL_LIST_TABLE,null);
		if(cursor != null)
			if(cursor.getCount() > 0){
				cursor.moveToNext();
				END_OFFSET = cursor.getInt(0);
			}
		cursor.close();
		return END_OFFSET;
	}
	
	
	public void resetMod() {
		database = getWritableDatabase();
		database.execSQL("update mod_change set old = "+getRowCount()+", new = "+getRowCount()+", ischanged = 0");
	}

	public int deleteAll(String typeMn) {
		int rowEffcted = 0;
		database = getWritableDatabase();
		int old = getRowCount();
		rowEffcted = database.delete(FINAL_LIST_TABLE, KEY_TYPE+" = ?", new String[]{typeMn});
		if(rowEffcted > 0)
			database.execSQL("update mod_change set old = "+old+", new = "+getRowCount()+", ischanged = 1");
		return rowEffcted;	
	}
	
	
	@Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

}
