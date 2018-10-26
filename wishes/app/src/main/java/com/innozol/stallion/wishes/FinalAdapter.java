package com.innozol.stallion.wishes;

import java.util.List;

import com.innozol.stallion.wishes.database.WishesDatabaseHelper;

import android.content.Context;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FinalAdapter extends ArrayAdapter<BirthDayDooot> {

	private static final String TAG = "FinalAdapter";
	private List<BirthDayDooot> renderList;
	private int mLayout;
	private ImageLoader imageLoader;
	private Context mContext;
	
	public FinalAdapter(Context context, int textViewResourceId,
			List<BirthDayDooot> objects,int layout,ImageLoader imageLoader) {
		super(context, textViewResourceId, objects);
		Log.d(TAG+">CustomAdaptor", "constructor");
		renderList = objects;
		mLayout = layout;
		this.imageLoader = imageLoader;
		mContext = context;
	}

	private class ViewHolder{
		TextView name;
		TextView dob;
		ImageView pic;
	}
	
	@Override
	public int getCount() {
		return renderList.size();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View rowView = convertView;
		
		if(rowView == null){
			rowView = WishAdapter.getInflator().inflate(mLayout, null);
			ViewHolder holder = new ViewHolder();
			holder.name = (TextView) rowView.findViewById(R.id.profNameItem);
			holder.dob = (TextView) rowView.findViewById(R.id.profDob);
			holder.pic = (ImageView) rowView.findViewById(R.id.profImage);
			rowView.setTag(holder);
		}
		
		ViewHolder holder = (ViewHolder) rowView.getTag();
		
		String name = renderList.get(position).getDootName();
		String bDay = renderList.get(position).getSBDay();
		String imgUrl = renderList.get(position).getDootImageUrl();
		holder.dob.setText(Html.fromHtml(bDay));
		holder.name.setText(name);
		if(renderList.get(position).getDootType().equals(WishesDatabaseHelper.TYPE_FB)){
			imageLoader.DisplayImage(imgUrl, holder.pic,ImageLoader.TYPE_WEB);
			rowView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.fb_list_bg));
		}
		else if(renderList.get(position).getDootType().equals(WishesDatabaseHelper.TYPE_MN)){
			imageLoader.DisplayImage(imgUrl, holder.pic,ImageLoader.TYPE_LOCAL);
			rowView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.mn_list_bg));
		}
		else if(renderList.get(position).getDootType().equals(WishesDatabaseHelper.TYPE_CNTCT)){
			imageLoader.DisplayImage(imgUrl, holder.pic,ImageLoader.TYPE_LOCAL);
			rowView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.cnt_list_bg));
		}
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.fadein);
		rowView.startAnimation(animation);
		
		return rowView;
	}
}
