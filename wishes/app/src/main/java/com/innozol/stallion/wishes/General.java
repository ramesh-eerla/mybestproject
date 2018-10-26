package com.innozol.stallion.wishes;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class General extends Activity{

	String[] wish_list = {"Good Morning","Good Night","New Year","Birthday Day","Marriage Day","valentines Day","Friendship Day","Christmas"};
	ListView lv,lv1;
	List<String> list;
	Dialog d,d1;
	Intent i;
	ArrayAdapter<String> as;
	final String[] croptions = { "Portrait", "LandScape" };
	int posi;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.general);		
		list = Arrays.asList(wish_list);
		d1 = new Dialog(this);
		d1.setTitle("Background");
		d1.setContentView(R.layout.crop_dialog);
		as = new ArrayAdapter<String>(this,
				android.R.layout.select_dialog_item, croptions);
		lv1 = (ListView) d1.findViewById(R.id.croplistView1);
		lv1.setAdapter(as);
		lv1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {				
				d1.dismiss();				
			Intent	in=new Intent(General.this,PhotoSortrActivity.class);
				in.putExtra("diff",arg2+1);
				startActivity(in);			
			}
		});
		lv = (ListView)findViewById(R.id.list_View);
		lv.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.lv_textview,wish_list));
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				
				posi=arg2;
				d = new Dialog(General.this,R.style.PopupAnimation);  
				d.requestWindowFeature(Window.FEATURE_NO_TITLE);
				d.getWindow().setBackgroundDrawable(new ColorDrawable(0));
				d.setContentView(R.layout.general_dialog);				   
				TextView bt1 = (TextView)d.findViewById(R.id.button1_genral);
				TextView bt2 = (TextView)d.findViewById(R.id.button2_general);
				View v1 = (View)d.findViewById(R.id.view1_general);
				bt1.setOnClickListener(new OnClickListener() {					
					@Override
					public void onClick(View v) {
						d.dismiss();						
						d1.show();						
					}
				});
				bt2.setOnClickListener(new OnClickListener() {					
					@Override
					public void onClick(View v) {
						d.dismiss();
						i=new Intent(General.this,PredefineTemplats.class);
						i.putExtra("id",posi);
						startActivity(i);						
					}
				});				  
				Animation sanim = AnimationUtils.loadAnimation(General.this, R.anim.scale_anim);
				v1.startAnimation(sanim);				   
				d.show();
			}
		});
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	startActivity(new Intent(General.this,WishListActivity.class));
	finish();
	}
}
