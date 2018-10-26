package com.innozol.stallion.wishes;

import android.app.Activity;
import android.widget.Toast;

public class UIUtils {
	
	
	
	/**
	 * Show toast for Toast.LENGTH_SHORT duration
	 * @param context A Context object
	 * @param message Message to display
	 */
	public static void showToast(final Activity context,final String message){
		context.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(context, ""+message, Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	

}
