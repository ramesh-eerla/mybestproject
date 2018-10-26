package com.innozol.stallion.wishes;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;

public class NotifyBDay extends Service{

	
	ArrayList<BirthDayDooot> list;
	SortingWishList wishList;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				
				try{
					wishList = SortedListFile.getFromFile(NotifyBDay.this);
				}catch(FileNotFoundException fnf){}
				if(wishList != null){
					if(!wishList.isListSorted()){
						wishList.sort();
						list = wishList.todayList();
					}
					else{
						list = wishList.todayList();
					}
				}
				
				NotificationManager ntfmngr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
				int id = 100;
				for(BirthDayDooot dooot : list){
					Intent i = new Intent(NotifyBDay.this,ProfileAcivity.class);
					Bundle b = new Bundle();
					b.putParcelable("profile", dooot);
					i.putExtra("prof", b);
					PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), id, i, PendingIntent.FLAG_ONE_SHOT);
					Notification mNotification = new Notification(R.drawable.micon, dooot.getEvent(), System.currentTimeMillis());
					mNotification.flags = Notification.FLAG_AUTO_CANCEL;
					Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
					if(uri!=null){
						
						mNotification.sound=uri;
					}
					
					String text = "Today "+dooot.getDootName()+"'s "+dooot.getEvent();
					mNotification.setLatestEventInfo(getApplicationContext(), dooot.getEvent(), text, pi);
					ntfmngr.notify(id, mNotification);
					id++;
				}
				
			}
		}, 2000);
	}
}
