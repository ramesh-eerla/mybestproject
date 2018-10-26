package com.innozol.stallion.wishes;

import java.util.ArrayList;
import com.innozol.stallion.wishes.database.WishesDatabaseHelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmSortReceiver extends BroadcastReceiver {

	
	@Override
	public void onReceive(final Context context, Intent intent) {
		
		WishesDatabaseHelper helper = new WishesDatabaseHelper(context);
		ArrayList<BirthDayDooot> unSortList = (ArrayList<BirthDayDooot>) helper.getAllProfiles();
		Log.d("AlarmSortReceiver", "List un-sort");
		SortListService.sortList(context, unSortList, new SortListService.SortCallback() {
			@Override
			public void onSort(ArrayList<BirthDayDooot> dooots) {
				Log.d("AlarmSortReceiver", "Sorted @" + System.currentTimeMillis());
				
				Intent i = new Intent(context,NotifyBDay.class);
				context.startService(i);
				
			}
		});
		helper.close();

	}

}
