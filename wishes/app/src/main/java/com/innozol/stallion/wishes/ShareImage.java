package com.innozol.stallion.wishes;

import android.content.Context;
import android.content.Intent;

public class ShareImage {
	
	Context context;
	
	public ShareImage(Context context) {
		this.context = context;
	}
	
	public void share(Intent intent){
		
		context.startActivity(Intent.createChooser(intent, "Share Image"));
		
	}
	

}
