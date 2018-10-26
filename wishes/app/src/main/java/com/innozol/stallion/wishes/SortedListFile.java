package com.innozol.stallion.wishes;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import android.content.Context;
import android.util.Log;

public class SortedListFile {
	private static final String TAG = "SortedListFile";
	private static String FILENAME = "sortedlist.dat";

	public static void saveToFile(Context context,SortingWishList object){
		try {
			FileOutputStream fout = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
			ObjectOutputStream stream = new ObjectOutputStream(fout);
			stream.writeObject(object);
			Log.d(TAG, "Object saved to "+FILENAME);
			stream.flush();
			stream.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static SortingWishList getFromFile(Context ctx) throws FileNotFoundException{
		SortingWishList object = null;
		try {
			
			FileInputStream fin = ctx.openFileInput(FILENAME);
			ObjectInputStream oin = new ObjectInputStream(fin);
			object = (SortingWishList) oin.readObject();
			oin.close();
			Log.d(TAG, "Object read from "+FILENAME);
			
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return object;
	}

}
