package com.innozol.stallion.wishes;

import java.io.File;

import javax.crypto.spec.IvParameterSpec;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ViewAnimator;

import com.innozol.stallion.wishes.AnimationFactory.FlipDirection;

public class Home extends Activity implements OnClickListener {
	
	ImageView face,conta,manu,genrel,subface,contasub,manusub,genrelsub;
    RelativeLayout rFb,rCot,rMan,rGen;
	Intent i;
	ViewAnimator viewAnimator,viewAnimator1,viewAnimator2,viewAnimator3;
	   
	int ht,wt;
	   Bitmap b_fb,b_cn,b_mn,b_gn,fb_bk,cn_bk,mn_bk,gn_bk;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.scal);        
       viewAnimator = (ViewAnimator)this.findViewById(R.id.vf_cont);
       viewAnimator1 = (ViewAnimator)this.findViewById(R.id.vf_fb);
       viewAnimator2 = (ViewAnimator)this.findViewById(R.id.vf_gn);
       viewAnimator3 = (ViewAnimator)this.findViewById(R.id.vf_man);
      face  =(ImageView)findViewById(R.id.iv_fb);
      conta=(ImageView)findViewById(R.id.iv_cont);
      manu=(ImageView)findViewById(R.id.iv_man);
      genrel=(ImageView)findViewById(R.id.iv_gen);      
      subface=(ImageView)findViewById(R.id.iv_fb_sub);
      contasub=(ImageView)findViewById(R.id.iv_cont_sub);
      manusub=(ImageView)findViewById(R.id.iv_man_sub);
      genrelsub=(ImageView )findViewById(R.id.iv_gen_sub);      
      face.setOnClickListener(this);
      conta.setOnClickListener(this);
      manu.setOnClickListener(this);
      genrel.setOnClickListener(this);      
      DisplayMetrics metrics = new DisplayMetrics();      
      getWindowManager().getDefaultDisplay().getMetrics(metrics);
      wt = metrics.widthPixels;
      ht = metrics.heightPixels;
      System.out.println("Width......:"+wt+"Height.....:"+ht);     
      rFb = (RelativeLayout) findViewById(R.id.r_fb);
      rCot = (RelativeLayout) findViewById(R.id.r_cont);
      rMan = (RelativeLayout) findViewById(R.id.r_man);
      rGen = (RelativeLayout) findViewById(R.id.r_gn);   
      
      
      
      //*********************************************************
      RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)rFb.getLayoutParams();      
      params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
      params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
      params.height = ht/2;
      params.width = wt/2;      
      rFb.setLayoutParams(params);              
      BitmapFactory.Options opts = new BitmapFactory.Options();
      opts.inScaled = false;
      b_fb = BitmapFactory.decodeResource(getResources(), R.drawable.facebook,opts);
      System.out.println("Before change facebook bitmap Width......:"+b_fb.getWidth()+"bitHeight.....:"+b_fb.getHeight());
      b_fb=Bitmap.createScaledBitmap(b_fb, wt/2, ht/2, true);
      System.out.println("After change facebook bitmap Width......:"+b_fb.getWidth()+"bitHeight.....:"+b_fb.getHeight());
      BitmapDrawable d = new BitmapDrawable(b_fb);      
      FrameLayout.LayoutParams params3 = (FrameLayout.LayoutParams)face.getLayoutParams();      
      params3.height = ht/2;
      params3.width = wt/2;
      face.setLayoutParams(params3);
      face.setImageDrawable(d);
      File f=new File(Environment.getExternalStorageDirectory()+"/wish/facebook.jpg");
      if(f.exists()){
    	  
    	 fb_bk=BitmapFactory.decodeFile(f.getAbsolutePath(), opts);
      }
      else{
    	  fb_bk=BitmapFactory.decodeResource(getResources(), R.drawable.facebookback, opts);
      }
      fb_bk=Bitmap.createScaledBitmap(fb_bk, wt/2, ht/2, true);
      System.out.println("After change facebook bitmap Width......:"+b_fb.getWidth()+"bitHeight.....:"+b_fb.getHeight());
      BitmapDrawable d_fbbk = new BitmapDrawable(fb_bk);
      FrameLayout.LayoutParams params_fbk = (FrameLayout.LayoutParams)subface.getLayoutParams();
      params_fbk.height = ht/2;
      params_fbk.width = wt/2;
      subface.setLayoutParams(params_fbk);
      subface.setImageDrawable(d_fbbk);
       
      
      //**********************************
      RelativeLayout.LayoutParams cnParams = (RelativeLayout.LayoutParams)rCot.getLayoutParams();
      cnParams.addRule(RelativeLayout.RIGHT_OF,rFb.getId());
      cnParams.addRule(RelativeLayout.ALIGN_BOTTOM,rFb.getId());
      cnParams.height = ht/2;
      cnParams.width = wt/2;
      rCot.setLayoutParams(cnParams);
      b_cn = BitmapFactory.decodeResource(getResources(), R.drawable.contacts,opts);
      System.out.println("Before change contacts bitmap Width......:"+b_cn.getWidth()+"bitHeight.....:"+b_cn.getHeight());
      b_cn=Bitmap.createScaledBitmap(b_cn, wt/2, ht/2, true);
      System.out.println("After change contacts bitmap Width......:"+b_cn.getWidth()+"bitHeight.....:"+b_cn.getHeight());

      BitmapDrawable d2 = new BitmapDrawable(b_cn);
      
      FrameLayout.LayoutParams params2 = (FrameLayout.LayoutParams)conta.getLayoutParams();
      
      params2.height = ht/2;
      params2.width = wt/2;

      conta.setLayoutParams(params2);
      
      conta.setImageDrawable(d2);
      
      cn_bk=BitmapFactory.decodeResource(getResources(), R.drawable.contactback,opts);
      cn_bk=Bitmap.createScaledBitmap(cn_bk, wt/2, ht/2, true);
      BitmapDrawable d_bk=new BitmapDrawable(cn_bk);
 FrameLayout.LayoutParams params_cbk = (FrameLayout.LayoutParams)contasub.getLayoutParams();
      
      params_cbk.height = ht/2;
      params_cbk.width = wt/2;

      contasub.setLayoutParams(params_cbk);
      
      contasub.setImageDrawable(d_bk);
      
      
      
//      rCot.setLayoutParams(new RelativeLayout.LayoutParams(wt/2, ht/2));
      
      //***************************************
      
      
      RelativeLayout.LayoutParams mnParams = (RelativeLayout.LayoutParams)rMan.getLayoutParams();
      
      mnParams.addRule(RelativeLayout.ALIGN_LEFT,rFb.getId());
      mnParams.addRule(RelativeLayout.BELOW,rFb.getId());
      mnParams.height = ht/2;
      mnParams.width = wt/2;
      
      rMan.setLayoutParams(mnParams);
      
      
      b_mn = BitmapFactory.decodeResource(getResources(), R.drawable.manual,opts);
      System.out.println("Before change manual bitmap Width......:"+b_mn.getWidth()+"bitHeight.....:"+b_mn.getHeight());

      b_mn=Bitmap.createScaledBitmap(b_mn, wt/2, ht/2, true);
      System.out.println("After change manual bitmap Width......:"+b_mn.getWidth()+"bitHeight.....:"+b_mn.getHeight());

      BitmapDrawable d3 = new BitmapDrawable(b_mn);
      
      FrameLayout.LayoutParams params4 = (FrameLayout.LayoutParams)manu.getLayoutParams();
      
      params4.height = ht/2;
      params4.width = wt/2;

      manu.setLayoutParams(params4);
 
      
      manu.setImageDrawable(d3);
      
      File f1=new File(Environment.getExternalStorageDirectory()+"/wish/manual.jpg");
      if(f1.exists()){
    	  
    	 mn_bk=BitmapFactory.decodeFile(f1.getAbsolutePath(), opts);
      }
      else{
    	  mn_bk=BitmapFactory.decodeResource(getResources(), R.drawable.manualback, opts);
      }
      mn_bk=Bitmap.createScaledBitmap(mn_bk, wt/2, ht/2, true);
      System.out.println("After change facebook bitmap Width......:"+b_fb.getWidth()+"bitHeight.....:"+b_fb.getHeight());
      BitmapDrawable d_mnbk = new BitmapDrawable(mn_bk);
      
      FrameLayout.LayoutParams params_mnk = (FrameLayout.LayoutParams)manusub.getLayoutParams();
      
      params_mnk.height = ht/2;
      params_mnk.width = wt/2;

      manusub.setLayoutParams(params_mnk);
      
     manusub.setImageDrawable(d_mnbk);
      
      
      
      
      
      
 //     rMan.setLayoutParams(new RelativeLayout.LayoutParams(wt/2, ht/2));
      
      //*************************************
      RelativeLayout.LayoutParams gnParams = (RelativeLayout.LayoutParams)rGen.getLayoutParams();
      
      gnParams.addRule(RelativeLayout.RIGHT_OF,rMan.getId());
      gnParams.addRule(RelativeLayout.ALIGN_BOTTOM,rMan.getId());
      gnParams.height = ht/2;
      gnParams.width = wt/2;
      
      rGen.setLayoutParams(gnParams);
      
      
      
      b_gn = BitmapFactory.decodeResource(getResources(), R.drawable.general,opts);
      System.out.println("Before change general bitmap Width......:"+b_gn.getWidth()+"bitHeight.....:"+b_gn.getHeight());
      b_gn=Bitmap.createScaledBitmap(b_gn, wt/2, ht/2, true);
      System.out.println("After change general bitmap Width......:"+b_gn.getWidth()+"bitHeight.....:"+b_gn.getHeight());
      BitmapDrawable d4 = new BitmapDrawable(b_gn);
      
      FrameLayout.LayoutParams params5 = (FrameLayout.LayoutParams)genrel.getLayoutParams();
      
      params5.height = ht/2;
      params5.width = wt/2;

      genrel.setLayoutParams(params5);
      
      genrel.setImageDrawable(d4);
      
      
      gn_bk=BitmapFactory.decodeResource(getResources(), R.drawable.generalback,opts);
      System.out.println("Before change general bitmap Width......:"+b_gn.getWidth()+"bitHeight.....:"+b_gn.getHeight());
      gn_bk=Bitmap.createScaledBitmap(gn_bk, wt/2, ht/2, true);
      System.out.println("After change general bitmap Width......:"+b_gn.getWidth()+"bitHeight.....:"+b_gn.getHeight());
      BitmapDrawable d_gn_bk = new BitmapDrawable(gn_bk);
      
      FrameLayout.LayoutParams params4_bk = (FrameLayout.LayoutParams)genrelsub.getLayoutParams();
      
      params4_bk.height = ht/2;
      params4_bk.width = wt/2;

      genrelsub.setLayoutParams(params4_bk);
      
      genrelsub.setImageDrawable(d_gn_bk);
      
      
      
      
      
 //     rGen.setLayoutParams(new RelativeLayout.LayoutParams(wt/2, ht/2));

      }
   
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_fb:
			  i = new Intent(Home.this,FromFacebookActivity.class);
			  conta.setClickable(false);
			  manu.setClickable(false);
			  genrel.setClickable(false);
			  			 
			startanimation(viewAnimator1,i,1);
			
			break;

		case R.id.iv_cont :
			 i = new Intent(Home.this,ContactsListActivity.class);
			 face.setClickable(false);
			  manu.setClickable(false);
			  genrel.setClickable(false);
			 startanimation(viewAnimator,i,2);
			break;

		case R.id.iv_man:
			 i = new Intent(Home.this,ManualActivity.class);
			 conta.setClickable(false);
			  face.setClickable(false);
			  genrel.setClickable(false);
			 startanimation(viewAnimator3,i,3);
			break;

		case R.id.iv_gen:
			 i = new Intent(Home.this,General.class);
			 conta.setClickable(false);
			  manu.setClickable(false);
			  face.setClickable(false);
			 startanimation(viewAnimator2,i,4);
			break;

		default:
			break;
		}
		
		
	}


	private void startanimation(ViewAnimator viewAnimator4, final Intent i2,final int c) {
		// TODO Auto-generated method stub
		if(c==4 || c==2){
			AnimationFactory.flipTransition(viewAnimator4, FlipDirection.LEFT_RIGHT );
		}else{
			AnimationFactory.flipTransition(viewAnimator4, FlipDirection.RIGHT_LEFT);
		}Thread th=new Thread(){
			public void run() {
				try {
					
					sleep(1700);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
				finally{
					i2.putExtra("cli",c);
					startActivity(i2);
					finish();
					overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
					
				}
			};
		};th.start();		
		
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent i = getIntent();
		Log.d("Home", "Intent: "+i);
		if(i == null){
			startActivity(new Intent(this,WishListActivity.class));
			finish();
		}
	}
}
