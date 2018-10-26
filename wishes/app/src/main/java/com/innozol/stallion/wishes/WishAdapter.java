package com.innozol.stallion.wishes;

import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import com.commonsware.cwac.endless.EndlessAdapter;
import com.facebook.model.GraphUser;
import com.innozol.stallion.wishes.database.WishesDatabaseHelper;

public class WishAdapter extends EndlessAdapter {
	
	private static final String TAG = "WishAdapter";
//	private Context mContext;
	protected static LayoutInflater inflater;
	private RotateAnimation rotate=null;
//	private List<BirthDayDooot> tempList = new ArrayList<BirthDayDooot>();
//	private List<GraphUser> fbTempList = new ArrayList<GraphUser>();
	private int BATCH_SIZE = 15;
	private int mLastOffset = 0;
//	private int LIST_SIZE;
//	private static Object adapterObject;
	
	public WishAdapter(int layout,LayoutInflater inflater,List<GraphUser> list,ImageLoader imageLoader,Activity context) {
		super(new FacebookAdapter(layout,list,layout,imageLoader,context));
		//super((BaseAdapter)(adapterObject = (new FacebookAdapter(layout,list,layout,imageLoader,context))));
//		WishAdapter.this.mContext = context;
		WishAdapter.inflater = inflater;
//		LIST_SIZE = list.size();
		setLastOffset(BATCH_SIZE);
		rotate=new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
			     0.5f, Animation.RELATIVE_TO_SELF,
			     0.5f);
			   rotate.setDuration(600);
			   rotate.setRepeatMode(Animation.RESTART);
			   rotate.setRepeatCount(Animation.INFINITE);
	}

	public WishAdapter(Activity context,int layout,LayoutInflater inflater,List<BirthDayDooot> list,ImageLoader imageLoader) {
		super(new FinalAdapter(context,layout,list,layout,imageLoader));
		//super((BaseAdapter)(adapterObject = (new FinalAdapter(context,layout,list,layout,imageLoader))));
		Log.d(TAG, "constructor");
//		WishAdapter.this.mContext = context;
		WishAdapter.inflater = inflater;
		WishesDatabaseHelper rowCount = new WishesDatabaseHelper(context);
//		LIST_SIZE = rowCount.getRowCount();
		rowCount.close();
		setLastOffset(BATCH_SIZE);
		rotate=new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
			     0.5f, Animation.RELATIVE_TO_SELF,
			     0.5f);
			   rotate.setDuration(600);
			   rotate.setRepeatMode(Animation.RESTART);
			   rotate.setRepeatCount(Animation.INFINITE);
	}
	
	
	protected static LayoutInflater getInflator(){
		return WishAdapter.inflater;
	}
	
	@Override
	protected View getPendingView(ViewGroup parent) {
	   View row=getInflator().inflate(R.layout.row, null);
	 
	   View child=row.findViewById(android.R.id.text1);
	   child.setVisibility(View.GONE);
	   child=row.findViewById(R.id.throbber);
	   child.setVisibility(View.VISIBLE);
	   child.startAnimation(rotate);
	 
	   return(row);
	}
	
	public void setLastOffset(int i){
		mLastOffset = i;
	}
	
	public int getLastOffset(){
		return mLastOffset;
	}
	
	
	@Override
	protected boolean cacheInBackground() {
		
		/*if(adapterObject instanceof FinalAdapter){
			tempList.clear();
			int lastOffset = getLastOffset();
			if(lastOffset < LIST_SIZE){
				int endOffset = lastOffset + BATCH_SIZE;
				WishesDatabaseHelper helper = new WishesDatabaseHelper(mContext);
				if(endOffset < LIST_SIZE){
					tempList = helper.getLimitProfiles(lastOffset, endOffset);
				}
				else{
					tempList = helper.getLimitProfiles(lastOffset, LIST_SIZE);
				}
				helper.close();
				setLastOffset(endOffset);
		
				if(endOffset < LIST_SIZE){
					return true;
				}
				else{
					return false;
				}
			}
		}*/
		
		return false;
	}

	@Override
	protected void appendCachedData() {
		
		/*if( adapterObject instanceof FinalAdapter){
			FinalAdapter adaptor = (FinalAdapter)getWrappedAdapter();
			
			for(BirthDayDooot dooot : tempList){
				adaptor.add(dooot);
			}
		}*/
		/*else if(true){
			FacebookAdapter adapter = (FacebookAdapter)getWrappedAdapter();
			
			for(GraphUser user : fbTempList){
				adapter.add(user);
			}
		}*/
	}
}
