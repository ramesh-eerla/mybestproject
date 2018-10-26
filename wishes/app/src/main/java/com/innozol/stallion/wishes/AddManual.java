package com.innozol.stallion.wishes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.innozol.stallion.wishes.database.WishesDatabaseHelper;

public class AddManual extends FragmentActivity implements OnClickListener{

	EditText name,eventname,eventdate,email,phonenumber;
	ImageView iv;
	Button save,datePick;
	String img;
	Bitmap bmap;
	Calendar c;
	int mYear = CalendarUtils.getCurrentYear(), mMonth = CalendarUtils.getCurrentMonth(), mDay = CalendarUtils.getCurrentDay();
	String bDay;
	String imgUrl = null;
	 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.manual);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title);
		
		name = (EditText)findViewById(R.id.ed_name_manual);
		eventname = (EditText)findViewById(R.id.ed_eventname_manual);
		eventdate = (EditText)findViewById(R.id.ed_eventdate_manual);
		email = (EditText)findViewById(R.id.ed_email_manual);
		phonenumber = (EditText)findViewById(R.id.ed_phnumber_manual);
		iv = (ImageView)findViewById(R.id.imageView_manual);
		save = (Button)findViewById(R.id.bt_save_manual);
		datePick = (Button) findViewById(R.id.btn_date_pickr);
		bmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_dflt);
		iv.setImageBitmap(bmap);
		save.setOnClickListener(this);
		iv.setOnClickListener(this);
		datePick.setOnClickListener(this);
		
		
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		
		case R.id.imageView_manual:
			
			uploadphoto();
			
			break;
			
       case R.id.bt_save_manual:
			
    	   String nme = name.getText().toString().trim();
    	   String evnt_name = eventname.getText().toString().trim();
    	   String e_mail = email.getText().toString().trim();
    	   String ph_num = phonenumber.getText().toString().trim();
    	   
    	   if(evnt_name.isEmpty()){
    		   eventname.setError("Enter event");
    		   eventname.requestFocus();
    	   }
    	   else if(eventdate.getText().toString().trim().isEmpty()){
    		   eventdate.setError("Event date");
    		   eventdate.requestFocus();
    	   }
    	   else if(nme.isEmpty()){
    		   name.setError("Enter name");
    		   name.requestFocus();
    	   }
    	   else{
    		   if(!e_mail.isEmpty()){
        		   e_mail = " ";
        	   }
    		   if(ph_num.isEmpty()){
        		   ph_num = " ";
        	   }
    		   	ContentValues values = new ContentValues();
    		   	String _id = "mn"+System.currentTimeMillis();
	       		values.put(WishesDatabaseHelper._id, _id);
	       		values.put(WishesDatabaseHelper.KEY_NAME, nme);
	       		values.put(WishesDatabaseHelper.KEY_DOB, bDay);
	       		Log.d("Manual img:", ""+imgUrl);
	       		values.put(WishesDatabaseHelper.KEY_IMAGE, imgUrl);
	       		values.put(WishesDatabaseHelper.KEY_EVENT, evnt_name);
	       		values.put(WishesDatabaseHelper.KEY_TYPE, WishesDatabaseHelper.TYPE_MN);
	       		WishesDatabaseHelper helper = new WishesDatabaseHelper(getApplicationContext());
	       		helper.insertProfile(values);
	       		ContentValues values1 = new ContentValues();
	       		values1.put(WishesDatabaseHelper._id, _id);
	       		values1.put(WishesDatabaseHelper.KEY_MOBILE, ph_num);
	       		values1.put(WishesDatabaseHelper.KEY_EMAIL, e_mail);
	       		helper.insertMiscEvent(values1);
	       		helper.close();
    	   		finish();
    	   }
    	   
			break;
       case R.id.btn_date_pickr:
    	 // showDialog(0);
    	   
    	   DialogFragment dfrag = new DatePickerFragment();
    	   dfrag.show(getSupportFragmentManager(), "datePicker");
		}
	}
	
	/*DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
			bDay = CalendarUtils.formatDate((monthOfYear+1)+"/"+dayOfMonth+"/"+year);
			eventdate.setText((monthOfYear+1)+"/"+dayOfMonth+"/"+year);
		}
	};
	
	protected android.app.Dialog onCreateDialog(int id) {
		return  new DatePickerDialog(AddManual.this, onDateSetListener, mYear, mMonth, mDay);
	};*/
	
	private void uploadphoto() {
		
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
	    intent.setType("image/*");
	    intent.putExtra("return-data", true);
	    startActivityForResult(intent, 1);
	   
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == 1 && data != null && data.getData() != null) 
		{
		   Uri uri = data.getData();
		   if (uri!=null)
		   {
			   String col = android.provider.MediaStore.Images.Media.DATA;
			   Cursor cursor = getContentResolver().query(uri, new String[]{col}, null, null, null);
			   cursor.moveToFirst();
			   //Link to the image
               final String imageFilePath = cursor.getString(cursor.getColumnIndex(col));
               Log.v("imageFilePath", imageFilePath);
               imgUrl = imageFilePath;
               File photos= new File(imageFilePath);
               bmap = decodeFile(photos);
               bmap = Bitmap.createScaledBitmap(bmap,80, 80, true);
               iv.setImageBitmap(bmap);
            
               cursor.close();
		   }
		}
		
	}

	private Bitmap decodeFile(File photos) {
		 try {
	            //decode image size
	            BitmapFactory.Options o = new BitmapFactory.Options();
	            o.inJustDecodeBounds = true;
	            BitmapFactory.decodeStream(new FileInputStream(photos),null,o);

	            //Find the correct scale value. It should be the power of 2.
	            final int REQUIRED_SIZE=70;
	            int width_tmp=o.outWidth, height_tmp=o.outHeight;
	            int scale=1;
	            while(true){
	                if(width_tmp/2<REQUIRED_SIZE || height_tmp/2<REQUIRED_SIZE)
	                    break;
	                width_tmp/=2;
	                height_tmp/=2;
	                scale++;
	            }

	            //decode with inSampleSize
	            BitmapFactory.Options o2 = new BitmapFactory.Options();
	            o2.inSampleSize=scale;
	            return BitmapFactory.decodeStream(new FileInputStream(photos), null, o2);
	        } catch (FileNotFoundException e) {
	        	e.printStackTrace();
	        }
	        return null;
	}
	
	public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

	    @Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	       
	    	return  new DatePickerDialog(getActivity(),this, mYear, mMonth, mDay);
	    }

	    public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
	    	bDay = CalendarUtils.formatDate((monthOfYear+1)+"/"+dayOfMonth+"/"+year);
			eventdate.setText((monthOfYear+1)+"/"+dayOfMonth+"/"+year);
	    }
	   
	}
}
