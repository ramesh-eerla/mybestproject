package com.innozol.stallion.wishes;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckWeb {
	
	private Context _context;
	
	public CheckWeb(Context context) {
		_context = context;
	}
	
	public boolean isNetConnect(){
		ConnectivityManager manager = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if(manager != null){
			NetworkInfo[] info = manager.getAllNetworkInfo();
			if(info != null){
				for(int i = 0 ; i < info.length ; i ++){
					if(info[i].getState() == NetworkInfo.State.CONNECTED){
						return true;
					}
				}
			}
		}
		return false;
	}

}
