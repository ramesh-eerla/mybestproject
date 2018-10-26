package com.innozol.stallion.wishes;

import com.innozol.stallion.wishes.database.WishesDatabaseHelper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreen extends Activity {
	
	private int cnt = 0;
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;
	
	@SuppressLint("WorldWriteableFiles")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.splash);
		Thread th=new Thread(){
			public void run() {
				
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					preferences = getSharedPreferences("HOLD_ON", MODE_WORLD_WRITEABLE);
					editor = preferences.edit();
					WishesDatabaseHelper helper = new WishesDatabaseHelper(getApplicationContext());
					cnt = helper.getRowCount();
					Log.d("SplashScreen", "Cnt : "+cnt );
					helper.close();
					if(cnt == 0)
						newWelcome();
					else
						oldWelcome();
					//new BackView().execute();
				}
				
				
			};
		};th.start();
		
		
	}
	
	public void newWelcome(){
		editor.putString("welcome", "NEW");
		editor.commit();
		startActivity(new Intent(SplashScreen.this,Home.class));
		finish();
	}
	
	public void oldWelcome(){
		Intent i = new Intent(SplashScreen.this,WishListActivity.class);
		startActivity(i);
		finish();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}
	
	
	/*public class BackView extends AsyncTask<Void, Void, Void>{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					
				}
			});
			
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			try {
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				finish();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

		}
	}*/
}
