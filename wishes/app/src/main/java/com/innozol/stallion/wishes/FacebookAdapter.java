package com.innozol.stallion.wishes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.json.JSONObject;

import com.facebook.model.GraphUser;
import com.innozol.stallion.wishes.database.WishesDatabaseHelper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;


public class FacebookAdapter extends ArrayAdapter<GraphUser> implements Filterable{

	// private static final String TAG = "FacebookAdapter";
	private List<GraphUser> renderList;
	private int mLayout;
	private ImageLoader imageLoader;
	private Activity mContext;
	public static boolean isCheckAll = false;
	private LayoutInflater mInflater;
	private List<GraphUser> renderListNew;
	private Filter usrFilter;

	public FacebookAdapter(int textViewResourceId, List<GraphUser> objects,
			int layout, ImageLoader imageLoader, Activity context) {
		super(context, textViewResourceId, objects);
		renderList = objects;
		mContext = context;
		mLayout = layout;
		this.imageLoader = imageLoader;
	}
	
	public FacebookAdapter(LayoutInflater inflater,int textViewResourceId, List<GraphUser> objects,
			int layout, ImageLoader imageLoader, Activity context) {
		super(context, textViewResourceId, objects);
		renderList = objects;
		mContext = context;
		mLayout = layout;
		this.imageLoader = imageLoader;
		mInflater = inflater;
		renderListNew = objects;
	}

	@Override
	public Activity getContext() {
		return mContext;
	}

	private class ViewHolder {
		TextView name;
		TextView dob;
		ImageView pic;
		CheckBox select;
	}

	@Override
	public int getCount() {
		return renderList.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View rowView = convertView;
		final int pos = position;

		if (rowView == null) {
			//rowView = WishAdapter.getInflator().inflate(mLayout, null);
			rowView = mInflater.inflate(mLayout, null);
			ViewHolder holder = new ViewHolder();
			holder.name = (TextView) rowView.findViewById(R.id.fb_prof_name);
			holder.dob = (TextView) rowView.findViewById(R.id.fb_prf_dob);
			holder.pic = (ImageView) rowView.findViewById(R.id.fb_prof_pic);
			holder.select = (CheckBox) rowView
					.findViewById(R.id.fb_select_check);
			rowView.setTag(holder);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();

		final String name = renderList.get(position).getName();
		final String bDay = CalendarUtils.formatDate(renderList.get(position)
				.getBirthday());
		final String imgUrl = getImageURL((JSONObject) renderList.get(pos)
				.getProperty("picture"));
		if (isCheckAll == false) {
			holder.select.setOnCheckedChangeListener(null);
			WishesDatabaseHelper helper = new WishesDatabaseHelper(getContext());
			if (helper.isProfileExists("fb" + renderList.get(position).getId())) {
				holder.select.setChecked(true);
			} else {
				holder.select.setChecked(false);
			}
			helper.close();
		}else{
			holder.select.setChecked(isCheckAll);
		}
		
		holder.select.setButtonDrawable(R.drawable.checkbox_selector_fb);

		holder.select.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					new Thread() {
						public void run() {
							final WishesDatabaseHelper helper = new WishesDatabaseHelper(
									getContext());
							if (!helper.isProfileExists("fb"
									+ renderList.get(pos).getId())) {
								ContentValues values = new ContentValues();
								values.put(WishesDatabaseHelper._id, "fb"
										+ renderList.get(pos).getId());
								values.put(WishesDatabaseHelper.KEY_NAME, name);
								values.put(WishesDatabaseHelper.KEY_DOB, bDay);
								values.put(WishesDatabaseHelper.KEY_IMAGE,
										imgUrl);
								values.put(WishesDatabaseHelper.KEY_EVENT,
										"B'Day");
								values.put(WishesDatabaseHelper.KEY_TYPE,
										WishesDatabaseHelper.TYPE_FB);
								helper.insertProfile(values);
							}
							if (!helper.isMiscExists("fb"
									+ renderList.get(pos).getId())) {
								ContentValues values = new ContentValues();
								values.put(WishesDatabaseHelper._id, "fb"
										+ renderList.get(pos).getId());
								values.put(WishesDatabaseHelper.KEY_MOBILE, " ");
								values.put(WishesDatabaseHelper.KEY_EMAIL, " ");
								helper.insertMiscEvent(values);
							}
							helper.close();
						};
					}.start();
				} else if (!isChecked) {
					new Thread() {
						public void run() {
							final WishesDatabaseHelper helper = new WishesDatabaseHelper(
									getContext());
							if (helper.isProfileExists("fb"
									+ renderList.get(pos).getId())) {
								helper.deleteProfile("fb"
										+ renderList.get(pos).getId());
							}
							helper.close();
						};
					}.start();
				}
			}
		});
		Calendar cal = CalendarUtils.stringToCalendar(bDay);
		holder.dob.setText(Html.fromHtml(CalendarUtils.styleDate(cal)));
		holder.name.setText(name);
		holder.dob.setShadowLayer(0, 0, 0, 0);
		holder.name.setShadowLayer(0, 0, 0, 0);
		imageLoader.DisplayImage(imgUrl, holder.pic, ImageLoader.TYPE_WEB);
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.fadein);
		rowView.startAnimation(animation);
		rowView.setBackgroundResource(R.drawable.fb_bordr);
		return rowView;
	}

	public void resetData() {
		renderList = renderListNew;
	}
	
	public String getImageURL(JSONObject picture) {
		String imgUrl = "";
		JSONObject jsonObject = picture;
		try {
			imgUrl = jsonObject.getJSONObject("data").getString("url");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imgUrl;
	}
	
	
	@Override
	public Filter getFilter() {
		if( usrFilter == null)
			usrFilter = new UserFilter();
		return usrFilter;		
	}
	
	private class UserFilter extends Filter{

		@SuppressLint("DefaultLocale")
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults results = new FilterResults();
			if (constraint == null || constraint.length() == 0) {
				// No filter implemented we return all the list
				results.values = renderList;
				results.count = renderList.size();
			}
			else {
				// We perform filtering operation
				List<GraphUser> nRenderList = new ArrayList<GraphUser>();

				for (GraphUser p : renderList) {
					if (p.getName().toUpperCase().startsWith(constraint.toString().toUpperCase()))
						nRenderList.add(p);
				}

				results.values = nRenderList;
				results.count = nRenderList.size();

			}
			return results;
		}

		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			// Now we have to inform the adapter about the new list filtered
						if (results.count == 0)
							notifyDataSetInvalidated();
						else {
							System.out.println("Size:"+renderList.size());
							renderList = (List<GraphUser>) results.values;
							notifyDataSetChanged();
						}
		}
		
	}

}
