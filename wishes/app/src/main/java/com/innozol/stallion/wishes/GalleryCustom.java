
package com.innozol.stallion.wishes;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Gallery;

public class GalleryCustom extends Gallery {

	public GalleryCustom(Context context) {
		super(context);
	}

	public GalleryCustom(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public GalleryCustom(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		onTouchEvent(ev);
		return false;
	}

}
