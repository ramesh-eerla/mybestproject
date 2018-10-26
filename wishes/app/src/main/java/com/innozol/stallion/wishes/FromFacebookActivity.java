package com.innozol.stallion.wishes;

import java.io.File;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.LoggingBehavior;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Settings;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

@SuppressLint("WorldWriteableFiles")
public class FromFacebookActivity extends Activity {
	
	@SuppressWarnings("unused")
    private Session gSession;
	private FacebookAdapter adapter2;
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;
	private ListView mListView;
	private EditText mSearch;
	private RelativeLayout internet_layout;
	private CheckWeb checkWeb;
	private ImageView imgWarning;
	private TextView tNetToggle;
	private ProgressDialog progressDialog;
	private GetBirthDaysAsync birthDaysAsync;
	private List<GraphUser> onlyBirthDay;
	private String version;
	
	Session.StatusCallback callback = new StatusCallback() 
	{
		
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			gSession = session;
			Log.i("Login", "session : "+session);
			if(state.isOpened()){
				//getBirthdays();
				birthDaysAsync.execute();
			}
			else if(state.isClosed()){
				//UIUtils.showToast(FromFacebookActivity.this, "Not Logged in");
			}
			else{
				//UIUtils.showToast(FromFacebookActivity.this, "Unable to Login");
				if(internet_layout.isShown()){
					internet_layout.setVisibility(View.GONE);
					tNetToggle.setVisibility(View.GONE);
					imgWarning.setVisibility(View.GONE);
				}
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.listview_parent);
    	try {
    		PackageInfo info = getPackageManager().getPackageInfo(
    		"com.innozol.stallion.wishes",
    		PackageManager.GET_SIGNATURES);
    		for (Signature signature : info.signatures) {
    		MessageDigest md = MessageDigest.getInstance("SHA");
    		md.update(signature.toByteArray());
    		Log.d("Hash:", Base64.encodeToString(md.digest(), Base64.DEFAULT)); //bMX0x1gDlQkz0errlfThknkqIvY=
    		}
    		} catch (NameNotFoundException e) {

    		} catch (NoSuchAlgorithmException e) {

    		}
    	
    	
    	mSearch = (EditText) findViewById(R.id.search_by_name);
    	mListView = (ListView) findViewById(R.id.list);
		internet_layout = (RelativeLayout)findViewById(R.id.internet_layout);
		mListView.setBackgroundColor(Color.WHITE);
		mListView.setCacheColorHint(Color.parseColor("#00000000"));
		ColorDrawable divider = new ColorDrawable(getResources().getColor(R.color.list_divider));
		mListView.setDivider(divider);
		mListView.setDividerHeight(4);
		mListView.setTextFilterEnabled(true);
		imgWarning = (ImageView) findViewById(R.id.img_warning);
		tNetToggle = (TextView) findViewById(R.id.txt_warning);
		checkWeb = new CheckWeb(FromFacebookActivity.this);
		toggleShowNoNetLayout();
		mSearch.setEnabled(false);
		birthDaysAsync = new GetBirthDaysAsync();
		
		if(isFacebookAppInstalled()){
			PackageInfo pInfo;
			try {
				pInfo = getPackageManager().getPackageInfo("com.facebook.katana", 0);
				version = pInfo.versionName;
				Log.d("FacebookActivity", version);
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
			if(version.substring(0, 2).equals("1.")){
				Toast.makeText(getApplicationContext(), "You are using 1.9.x or earlier version of facebook.\nPlease Update.",
						Toast.LENGTH_LONG).show();
				beforeFinishActivity();
			}else{
				Log.d("FacebookActivity", "New Version");
				Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
				Session session = new Session(this);
				Session.setActiveSession(session);
				session.openForRead(new Session.OpenRequest(this).setCallback(callback).setPermissions(Arrays.asList("friends_birthday")));
			}
		}
		else{
			Log.d("FacebookActivity", "Facebook App not installed");
			Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
			Session session = new Session(this);
			Session.setActiveSession(session);
			session.openForRead(new Session.OpenRequest(this).setCallback(callback).setPermissions(Arrays.asList("friends_birthday")));
		}
		
		/*Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
		Session session = new Session(this);
		Session.setActiveSession(session);
		session.openForRead(new Session.OpenRequest(this).setCallback(callback).setPermissions(Arrays.asList("friends_birthday")));*/
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		 Session.getActiveSession().addCallback(callback);
	}
	
   @Override
   protected void onResume() {
		super.onResume();
		toggleShowNoNetLayout();
		preferences = getSharedPreferences("HOLD_ON", MODE_WORLD_WRITEABLE);
		editor = preferences.edit();
	}
   
   @Override
   public void onStop() {
       super.onStop();
       Session.getActiveSession().removeCallback(callback);
   }
   
   @Override
   protected void onSaveInstanceState(Bundle outState) {
       super.onSaveInstanceState(outState);
       Session session = Session.getActiveSession();
       Session.saveSession(session, outState);
   }
   
	public void getBirthdays(){
		Request fBdays = Request.newMyFriendsRequest(Session.getActiveSession(), new Request.GraphUserListCallback() {
			@Override
			public void onCompleted(List<GraphUser> users, Response response) {
				if(response.getError() == null){
					onlyBirthDay = new ArrayList<GraphUser>();
					for(GraphUser user : users){
						if(user.getBirthday() != null){
							onlyBirthDay.add(user);
						}
					}
					if(internet_layout.isShown()){
						runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								internet_layout.setVisibility(View.GONE);
								tNetToggle.setVisibility(View.GONE);
								imgWarning.setVisibility(View.GONE);
							}
						});
					}
				}
			}
		});
		Bundle requestParams = fBdays.getParameters();
	    requestParams.putString("fields", "name,birthday,picture");
	    fBdays.setParameters(requestParams);
	    fBdays.executeAndWait();
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		
     	beforeFinishActivity();
	}
	private void beforeFinishActivity(){
		View v=getWindow().getDecorView();
		v.setDrawingCacheEnabled(true);		
		Bitmap bim=Bitmap.createBitmap(v.getDrawingCache());
		v.setDrawingCacheEnabled(false);		
		 // bim.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
		  
		  File file = new File( Environment.getExternalStorageDirectory() +"/wish/facebook.jpg");
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
		  		  ostream.close();
			  }
		}catch(Exception e){}
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

	private boolean isFacebookAppInstalled(){
		try{
			ApplicationInfo info = getPackageManager().
		            getApplicationInfo("com.facebook.katana", 0 );
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	private void toggleShowNoNetLayout(){
		if(!checkWeb.isNetConnect()){
			internet_layout.setVisibility(View.VISIBLE);
			tNetToggle.setVisibility(View.VISIBLE);
			imgWarning.setVisibility(View.VISIBLE);
		}
		else{
			/*Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
			Session session = new Session(this);
			Session.setActiveSession(session);
			session.openForRead(new Session.OpenRequest(this).setCallback(callback).setPermissions(Arrays.asList("friends_birthday")));*/
			internet_layout.setBackgroundColor(Color.parseColor("#7BCDFC"));
			tNetToggle.setText("Searching...please wait");
			internet_layout.setVisibility(View.VISIBLE);
			tNetToggle.setVisibility(View.VISIBLE);
			imgWarning.setVisibility(View.GONE);
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		/* Session session = Session.getActiveSession();
		 if(session != null)
	        if (!session.isClosed()) {
	            session.closeAndClearTokenInformation();
	        }*/
	}
	
	
	private class GetBirthDaysAsync extends AsyncTask<Void, Void, Void>{
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = ProgressDialog.show(FromFacebookActivity.this, "", "Loading...");
			progressDialog.setOnCancelListener(new OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					if(birthDaysAsync != null)
						birthDaysAsync.cancel(true);
					finish();
				}
			});
		}
		
		
		@Override
		protected Void doInBackground(Void... params) {
			getBirthdays();
			return null;
		}
		
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			
			if(progressDialog.isShowing())
				progressDialog.dismiss();
			
			adapter2 = new FacebookAdapter(getLayoutInflater(),R.layout.from_fb_item, onlyBirthDay, R.layout.from_fb_item, new ImageLoader(getApplicationContext()), FromFacebookActivity.this);
			mListView.setAdapter(adapter2);
			mSearch.setEnabled(true);
			mSearch.addTextChangedListener(new TextWatcher() {
			@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
//					System.out.println("Text ["+s+"] - Start ["+start+"] - Before ["+before+"] - Count ["+count+"]");
					if(count < before){
						adapter2.resetData();
					}
					adapter2.getFilter().filter(s.toString());
				}
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {
					
				}
				
				@Override
				public void afterTextChanged(Editable s) {
				}
			});
		}
	}
}
