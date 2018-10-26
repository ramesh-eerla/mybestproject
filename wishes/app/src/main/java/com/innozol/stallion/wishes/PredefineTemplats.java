package com.innozol.stallion.wishes;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.RelativeLayout;
import com.polites.android.GestureImageView;

public class PredefineTemplats extends Activity {
	
	Integer[] gm = { R.drawable.gm1, R.drawable.gm2, R.drawable.gm3,
			R.drawable.gm4, R.drawable.gm5, R.drawable.gm6, R.drawable.gm7,
			R.drawable.gm8, R.drawable.gm9, R.drawable.gm10 };
	Integer[] gn = { R.drawable.gn1, R.drawable.gn2, R.drawable.gn3,
			R.drawable.gn4, R.drawable.gn5, };
	Integer[] ny = { R.drawable.n1, R.drawable.n2, R.drawable.n3,
			R.drawable.n4, R.drawable.n5, R.drawable.n6, R.drawable.n7,
			R.drawable.n8, R.drawable.n9, R.drawable.n10 };
	Integer[] bd = { R.drawable.b1, R.drawable.b2, R.drawable.b3,
			R.drawable.b4, R.drawable.b5, R.drawable.b6, R.drawable.b7,
			R.drawable.b8, R.drawable.b9, R.drawable.b10 };
	Integer[] md = { R.drawable.a1, R.drawable.a2, R.drawable.a3,
			R.drawable.a4, R.drawable.a5, R.drawable.a6, R.drawable.a7,
			R.drawable.a8, R.drawable.a9, R.drawable.a10 };
	Integer[] vd = { R.drawable.v1, R.drawable.v2, R.drawable.v3,
			R.drawable.v4, R.drawable.v5, R.drawable.v6, R.drawable.v7,
			R.drawable.v8, R.drawable.v9, R.drawable.v10 };
	Integer[] fd = { R.drawable.f1, R.drawable.f2, R.drawable.f3,
			R.drawable.f4, R.drawable.f5, R.drawable.f6, R.drawable.f7,
			R.drawable.f8, R.drawable.f9, R.drawable.f10 };
	Integer[] ch = { R.drawable.x1, R.drawable.x2, R.drawable.x3,
			R.drawable.x4, R.drawable.x5, R.drawable.x6, R.drawable.x7,
			R.drawable.x8, R.drawable.x9, R.drawable.x10 };
	ArrayList<Integer[]> al;
	
	Bitmap bm;
	int posi,positions;
	RelativeLayout prebac;
	Button shre,customise;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.prediffintem);
		setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		al=new ArrayList<Integer[]>();
		al.add(gm);
		al.add(gn);
		al.add(ny);
		al.add(bd);
		al.add(md);
		al.add(vd);
		al.add(fd);
		al.add(ch);
		Bundle b=getIntent().getExtras();
		if(b!=null){
       posi=b.getInt("id");
		}
		Gallery viewPager = (Gallery) findViewById(R.id.view_pager);
		shre=(Button)findViewById(R.id.shreprediffine);	
		customise=(Button)findViewById(R.id.predefinecustomize);
		prebac=(RelativeLayout)findViewById(R.id.preback);
	    ImagePagerAdapter adapter = new ImagePagerAdapter();
	    viewPager.setAdapter(adapter);
	    viewPager.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				positions=arg2;
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
				
			}
		});
	    
	    shre.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {				
				Bitmap b=((BitmapDrawable)getResources().getDrawable(al.get(posi)[positions])).getBitmap();
				System.out.println("the bitmap value"+b);
				Intent share = new Intent(Intent.ACTION_SEND);
				share.setType("image/jpg");
				ByteArrayOutputStream bytes = new ByteArrayOutputStream();
				b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
				File f = new File(Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg");
				try {
				    f.createNewFile();
				    FileOutputStream fo = new FileOutputStream(f);
				    fo.write(bytes.toByteArray());
				} catch (IOException e) {                       
				        e.printStackTrace();
				}
				share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/temporary_file.jpg"));
				startActivity(Intent.createChooser(share, "Share Image"));
				
			}
		});
	    customise.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				Intent in=new Intent(PredefineTemplats.this,PhotoSortrActivity.class);
				in.putExtra("res",al.get(posi)[positions]);
				in.putExtra("diff",3);
				startActivity(in);
			}
		});
	   }
	
	private class ImagePagerAdapter extends BaseAdapter {		  
		@Override
		public int getCount() {
			return al.get(posi).length;
		}
		@Override
		public Object getItem(int position) {			
			return al.get(posi)[position];
		}

		@Override
		public long getItemId(int position) {
			
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			GestureImageView iv = new GestureImageView(PredefineTemplats.this);
			iv.setImageResource(al.get(posi)[position]);
			iv.setMinScale((float) 0.7);
			iv.setMaxScale((float) 10.0);			
		    System.out.println("the system position"+position);		     		      
		      return iv;
		}
	}

	
}
