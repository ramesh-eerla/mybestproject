package com.innozol.stallion.wishes;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.innozol.stallion.wishes.MultiTouchController.MultiTouchObjectCanvas;
import com.innozol.stallion.wishes.MultiTouchController.PointInfo;
import com.innozol.stallion.wishes.MultiTouchController.PositionAndScale;

public class PhotoSortrActivity extends Activity {

	private RelativeLayout slidingPanel;
	private ListView listView, listView1, lv;
	private RelativeLayout buu;
	private ImageView menuViewButton, delete, save, share;
	private String savedImagePath = null;
	RelativeLayout slide;
	int viewposition = 0;
	int checkimage = 0;
	ImageView i1, i2, i3, i4, i5, i6, i7, i8;
	final String[] croptions = { "Custom Crop", "Square crop", "Circle crop" };
	final String[] imag = { "delete", "Move" };
	ArrayAdapter<String> as;
	AlertDialog.Builder builder1;
	Bitmap conv_bm;
	View view;
	int type;
	MultiTouchEntity img;
	int backpos;
	int status = 0;
	private Uri mImageCaptureUri;
	Integer[] dr = { R.drawable.l1_00a8ff, R.drawable.l2_00ff2a,
			R.drawable.l3_00ff7e, R.drawable.l4_00fff6, R.drawable.l5_5f9600,
			R.drawable.l6_009ba1, R.drawable.l7_0018ff, R.drawable.l8_0072ff,
			R.drawable.l9_5400ff, R.drawable.l10_017207, R.drawable.l11_a200ff,
			R.drawable.l12_a14000, R.drawable.l13_bd2400,
			R.drawable.l14_cc9900, R.drawable.l15_de00ff,
			R.drawable.l16_ff0000, R.drawable.l17_ff00c0,
			R.drawable.l18_ff8400, R.drawable.l19_ffba00,
			R.drawable.l20_ffea00, R.drawable.l21_ffc000,
			R.drawable.l22_000000, R.drawable.l23_ffffff, R.drawable.l24_9999cc };
	String[] colors = { "00a8ff", "00ff2a", "00ff7e", "00fff6", "5f9600",
			"009ba1", "0018ff", "0072ff", "5400ff", "017207", "a200ff",
			"a14000", "bd2400", "cc9900", "de00ff", "ff0000", "ff00c0",
			"ff8400", "ffba00", "ffea00", "ffc000", "000000", "ffffff",
			"9999cc" };
	String colorcode;

	FrameLayout.LayoutParams menuPanelParameters;
	List<ResolveInfo> list;
	FrameLayout.LayoutParams slidingPanelParameters;
	RelativeLayout.LayoutParams headerPanelParameters;
	LinearLayout.LayoutParams listViewParameters;
	Integer[] backgroundsPort = { R.drawable.gallerys, R.drawable.ct1,
			R.drawable.ct2, R.drawable.ct3, R.drawable.ct4, R.drawable.ct5,
			R.drawable.ct6, R.drawable.ct7, R.drawable.ct8, R.drawable.ct9,
			R.drawable.ct10

	};
	Integer[] backgroundsland = { R.drawable.gallerys, R.drawable.ctp1,
			R.drawable.ctp2, R.drawable.ctp3, R.drawable.ctp4, R.drawable.ctp5,
			R.drawable.ctp6, R.drawable.ctp7, R.drawable.ctp8, R.drawable.ctp9,
			R.drawable.ctp10 };

	Integer[] clips = { R.drawable.a120, R.drawable.balloon,
			R.drawable.balloon1, R.drawable.balloon2, R.drawable.balloon3,
			R.drawable.clip1, R.drawable.clip2, R.drawable.clip3,
			R.drawable.clip4, R.drawable.clip5, R.drawable.clip6,
			R.drawable.clip7, R.drawable.clip8, R.drawable.clip9,
			R.drawable.clip10, R.drawable.clip11, R.drawable.clip12,
			R.drawable.clip13, R.drawable.clip14, R.drawable.clip15,
			R.drawable.clip16, R.drawable.clip17, R.drawable.clip18,
			R.drawable.clip19, R.drawable.clip20, R.drawable.clip21,
			R.drawable.clip22, R.drawable.clip24, R.drawable.clip25,
			R.drawable.clip26, R.drawable.clip27, R.drawable.clip28,
			R.drawable.clip29, R.drawable.clip30, R.drawable.clip31,
			R.drawable.clip32, R.drawable.clip33, R.drawable.clip34,
			R.drawable.clip35, R.drawable.clip36, R.drawable.clip37,
			R.drawable.clip38, R.drawable.clip39, R.drawable.clip40,
			R.drawable.clip41, R.drawable.clip42, R.drawable.clip43,
			R.drawable.clip44, R.drawable.clip45, R.drawable.clip46,
			R.drawable.clip47, R.drawable.clip48, R.drawable.clip49,
			R.drawable.clip50, R.drawable.clip51, R.drawable.clip52,
			R.drawable.clip53, R.drawable.clip54, R.drawable.clip55,
			R.drawable.clip56, R.drawable.clip57, R.drawable.clip58 };
	Integer[] frame = { R.drawable.circ, R.drawable.frame1, R.drawable.frame2,
			R.drawable.frame3, R.drawable.frame4, R.drawable.frame5, R.drawable.frame6,
			R.drawable.frame7, R.drawable.frame8, R.drawable.frame9, R.drawable.frame10,
			R.drawable.frame11, R.drawable.frame12, R.drawable.frame13, R.drawable.frame14,
			R.drawable.frame15, R.drawable.frame16 };

	int imgCnt = 1;

	PhotoSortrView photoSorter, photoSorter1;
	View v;
	Integer[] icons = { R.drawable.background, R.drawable.clips,
			R.drawable.gallerys, R.drawable.camera, R.drawable.frame,
			R.drawable.types };
	Integer[] icons1 = { 0, R.drawable.clips, R.drawable.gallerys,
			R.drawable.camera, R.drawable.frame, R.drawable.types };

	ArrayList<Drawable> ad;
	private ArrayList<MultiTouchEntity> mImages = new ArrayList<MultiTouchEntity>();
	private ArrayList<MultiTouchEntity> mgallery = new ArrayList<MultiTouchEntity>();

	String[] fontttf = { "green_fuz.otf", "Airstream.ttf",
			"Arizonia-Regular-OTF.otf", "BEARPAW_.ttf", "cac_champagne.ttf",
			"daniel.ttf", "danielbd.ttf", "danielbk.ttf",
			"EuphoriaScript-Regular.otf", "FontleroyBrown.ttf",
			"Gladifilthefte.ttf", "GreatVibes-Regular.otf", "HarabaraHand.ttf",
			"HenryMorganHand.ttf", "journal.ttf",
			"Kingthings_Calligraphica_2.ttf",
			"Kingthings_Calligraphica_Italic.ttf",
			"Kingthings_Calligraphica_Light.ttf", "Kristi.ttf",
			"Matchbook.otf", "Montez-Regular.ttf", "NewRocker-Regular.otf",
			"NosiferCaps-Regular.ttf", "PLUMP.ttf", "SpicyRice-Regular.otf",
			"spilt-ink.ttf", "Top_Secret.ttf", "waterst.ttf", "waterst2.ttf",
			"Windsong.ttf", "Yearsupplyoffairycakes.ttf" };
	String[] fonts = { "green_fuz", "Airstream", "Arizonia-Regular-OTF",
			"BEARPAW", "cac_champagne", "daniel", "danielbd", "danielbk",
			"EuphoriaScript-Regular", "FontleroyBrown", "Gladifilthefte",
			"GreatVibes-Regular", "HarabaraHand", "HenryMorganHand", "journal",
			"Kingthings_Calligraphica_2", "Kingthings_Calligraphica_Italic",
			"Kingthings_Calligraphica_Light", "Kristi", "Matchbook",
			"Montez-Regular", "NewRocker-Regular", "NosiferCaps-Regular",
			"PLUMP", "SpicyRice-Regular", "spilt-ink", "Top_Secret", "waterst",
			"waterst2", "Windsong", "Yearsupplyoffairycakes" };
	EditText typdata;
	TextView tv;
	int fontposition;
	ArrayList<ActionItem> actionitem;
	private PointInfo currTouchPoint = new PointInfo();
	private boolean mShowDebugInfo = true;
	private static final int UI_MODE_ROTATE = 1, UI_MODE_ANISOTROPIC_SCALE = 2;
	private int mUIMode = UI_MODE_ROTATE;
	Context context1;
	private static final int ID_UP = 1;
	MultiTouchEntity deleteimage;
	QuickAction quickAction;
	QuickAction1 quickAction1;
	private Paint mLinePaintTouchPointCircle = new Paint();
	private static final float SCREEN_MARGIN = 100;
	private int displayWidth, displayHeight;
	private Dialog d, d1;
	int diff;
	ArrayList<ActionItem> actionitem1;
	private Bitmap bmdec;
	private Bundle bundel;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);
		photoSorter = new PhotoSortrView(this);
		ad = new ArrayList<Drawable>();
		buu = (RelativeLayout) findViewById(R.id.buttons);
		slidingPanel = (RelativeLayout) findViewById(R.id.slidingPanel);
		slidingPanel.addView(photoSorter);
		listView = (ListView) findViewById(R.id.list);
		listView1 = (ListView) findViewById(R.id.list1);

		listView.setCacheColorHint(Color.parseColor("#00000000"));
		listView1.setCacheColorHint(Color.parseColor("#00000000"));
		menuViewButton = (ImageView) findViewById(R.id.menuViewButton);

		bundel = getIntent().getExtras();
		diff = bundel.getInt("diff");
		if (diff == 1) {

			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		} else if (diff == 2) {

			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		} else if (diff == 3) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			int resid = bundel.getInt("res");
			photoSorter.setBackgroundResource(resid);

		}

		menuViewButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				listView.bringToFront();
				listView.setVisibility(View.VISIBLE);
				if (diff == 3) {

					listView.setAdapter(new MyAdapter(5));
				} else {
					listView.setAdapter(new MyAdapter(0));
				}

			}
		});
		quickAction = new QuickAction(PhotoSortrActivity.this,
				QuickAction.VERTICAL, 2);
		quickAction1 = new QuickAction1(PhotoSortrActivity.this,
				QuickAction1.HORIZONTAL);
		actionitem1 = new ArrayList<ActionItem>();
		for (int i = 0; i < dr.length; i++) {
			ActionItem coleritem = new ActionItem(1, dr[i]);
			coleritem.setSticky(true);
			actionitem1.add(coleritem);
		}

		for (int j = 0; j < actionitem1.size(); j++) {
			quickAction1.addActionItem(actionitem1.get(j));
		}

		quickAction1
				.setOnActionItemClickListener(new QuickAction1.OnActionItemClickListener() {
					@Override
					public void onItemClick(QuickAction1 source, int pos,
							int actionId) {
						quickAction1.getActionItem(pos);
						colorcode = colors[pos];
						typdata.setTextColor(Color.parseColor("#" + colorcode));
						typdata.setBackgroundColor(Color.parseColor("#ffffff"));

						quickAction1.dismiss();

					}
				});
		as = new ArrayAdapter<String>(this,
				android.R.layout.select_dialog_item, croptions);
		d1 = new Dialog(this);
		d1.setTitle("Crop Options");
		d1.setContentView(R.layout.crop_dialog);
		lv = (ListView) d1.findViewById(R.id.croplistView1);
		lv.setAdapter(as);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				d1.dismiss();
				doCrop(arg2);
			}
		});

		listView.setOnItemClickListener(new OnItemClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				type = arg2;

				if (arg2 == 2) {

					listView.setVisibility(View.GONE);
					uploadimagefromGallaery(1);
				} else if (arg2 == 3) {

					listView.setVisibility(View.GONE);
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

					File imagepat = new File(Environment
							.getExternalStorageDirectory().getAbsolutePath()
							+ "/wish");
					if (!imagepat.exists()) {
						imagepat.mkdir();
					}

					mImageCaptureUri = Uri.fromFile(new File(imagepat, "wish"
							+ String.valueOf(System.currentTimeMillis())
							+ ".jpg"));

					intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
							mImageCaptureUri);

					try {
						intent.putExtra("return-data", true);

						startActivityForResult(intent, 1);
					} catch (ActivityNotFoundException e) {
						e.printStackTrace();
					}

				} else if (arg2 == 5) {

					System.out.println("iam in 4");

					listView.setVisibility(View.INVISIBLE);

					d = new Dialog(PhotoSortrActivity.this);
					d.requestWindowFeature(Window.FEATURE_NO_TITLE);
					d.setContentView(R.layout.add_text_);
					actionitem = new ArrayList<ActionItem>();
					typdata = (EditText) d.findViewById(R.id.typetext);
					Button typeddata = (Button) d.findViewById(R.id.entertext);
					Button fontdata = (Button) d.findViewById(R.id.fonttype);
					Button colordata = (Button) d.findViewById(R.id.colorfont);
					tv = (TextView) d.findViewById(R.id.formattext);

					final TextWatcher txwatcher = new TextWatcher() {
						public void beforeTextChanged(CharSequence s,
								int start, int count, int after) {
						}

						public void onTextChanged(CharSequence s, int start,
								int before, int count) {

						}

						public void afterTextChanged(Editable s) {
							if (s.toString().length() <= 30) {
								tv.setText(s.toString().length() + "/30");
							}
						}
					};
					typdata.addTextChangedListener(txwatcher);
					d.setOnDismissListener(new DialogInterface.OnDismissListener() {

						@Override
						public void onDismiss(DialogInterface dialog) {

						}
					});

					for (int i = 0; i < fontttf.length; i++) {
						ActionItem nextItem = new ActionItem(ID_UP, fonts[i]);
						nextItem.setSticky(true);
						actionitem.add(nextItem);
					}

					for (int j = 0; j < actionitem.size(); j++) {
						quickAction.addActionItem(actionitem.get(j));
					}

					quickAction
							.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
								@Override
								public void onItemClick(QuickAction source,
										int pos, int actionId) {
									quickAction.getActionItem(pos);
									fontposition = pos;

									Typeface tf = Typeface.createFromAsset(
											getAssets(), fontttf[pos]);
									typdata.setTypeface(tf);
									quickAction.dismiss();

								}
							});

					fontdata.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {

							if (!typdata.getText().toString().equals("")) {

								quickAction.show(v);
							} else {

								Toast.makeText(getApplicationContext(),
										"Enter Text", Toast.LENGTH_SHORT)
										.show();

							}
						}
					});
					colordata.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							quickAction1.show(v);
						}
					});
					typeddata.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							String txt = typdata.getText().toString().trim();
							if (!txt.isEmpty()) {
								loadtext(typdata.getText().toString());
								d.dismiss();

							} else {
								Toast.makeText(PhotoSortrActivity.this,
										"Nothing Entered", Toast.LENGTH_SHORT)
										.show();
							}

						}

						private void loadtext(String string) {
							Bitmap b = Bitmap.createBitmap(200, 200,
									Bitmap.Config.ARGB_8888);
							Canvas c = new Canvas(b);
							c.drawBitmap(b, 0, 0, null);
							TextPaint textPaint = new TextPaint();
							textPaint.setAntiAlias(true);
							textPaint.setTextSize(30.0F);
							if (fontposition != 0) {
								Typeface tf = Typeface.createFromAsset(
										getAssets(), fontttf[fontposition]);

								textPaint.setTypeface(tf);
							} else {
								Typeface tf = Typeface.createFromAsset(
										getAssets(), fontttf[0]);

								textPaint.setTypeface(tf);
							}
							if (colorcode != null)
								if (!colorcode.equals("")) {
									textPaint.setColor(Color.parseColor("#"
											+ colorcode));
								}
							StaticLayout sl = new StaticLayout(string,
									textPaint, b.getWidth() - 8,
									Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
							c.translate(6, 40);
							sl.draw(c);
							ByteArrayOutputStream bos = new ByteArrayOutputStream();
							b.compress(Bitmap.CompressFormat.PNG, 90, bos);
							byte[] by = bos.toByteArray();

							String s = Base64.encodeToString(by, 1);
							photoSorter.loadImages(PhotoSortrActivity.this, s);

						}
					});
					d.show();

				} else {
					listView.setVisibility(View.GONE);
					listView1.bringToFront();
					listView1.setVisibility(View.VISIBLE);

					if (arg2 == 0) {
						if (diff == 1) {
							listView1.setAdapter(new MyAdapter(1));
						} else if (diff == 2) {
							listView1.setAdapter(new MyAdapter(2));
						} else if (diff == 3) {
							Toast.makeText(PhotoSortrActivity.this, "Empty",
									Toast.LENGTH_SHORT).show();
						}
					} else if (arg2 == 1) {
						listView1.setAdapter(new MyAdapter(3));

					} else if (arg2 == 4) {

						listView1.setAdapter(new MyAdapter(4));

					}

				}

			}
		});

		listView1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int pos,
					long arg3) {
				savedImagePath = null;
				listView1.setVisibility(View.GONE);

				if (type == 0) {
					if (pos == 0) {
						uploadimagefromGallaery(2);

					} else if (diff == 1) {
						photoSorter.setBackgroundResource(backgroundsPort[pos]);
					} else if (diff == 2) {
						photoSorter.setBackgroundResource(backgroundsland[pos]);
					}
				} else if (type == 1) {
					bmdec = decodfile(null, clips[pos]);
					addclips(bmdec);
				} else if (type == 4) {
					bmdec = decodfile(null, frame[pos]);
					addclips(bmdec);
				}

			}

			private void addclips(Bitmap bmdec) {
				getResources();
				photoSorter1 = new PhotoSortrView(PhotoSortrActivity.this);
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				bmdec.compress(Bitmap.CompressFormat.PNG, 90, bos);
				byte[] br = bos.toByteArray();
				String brr = Base64.encodeToString(br, 1);
				photoSorter.loadImages(PhotoSortrActivity.this, brr);
			}
		});

		delete = (ImageView) findViewById(R.id.imageDelete);
		save = (ImageView) findViewById(R.id.saveimage);
		share = (ImageView) findViewById(R.id.Sharimage);
		share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (savedImagePath != null) {
					Intent sharingIntent = new Intent(Intent.ACTION_SEND);
					File file = new File(savedImagePath);
					Uri screenshotUri = Uri.fromFile(file);
					Log.d("ImagePath", savedImagePath + "");
					sharingIntent.setType("image/*");
					sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
					startActivity(Intent.createChooser(sharingIntent, "Wish"));
				} else {
					String path = imageSave(true);
					if (path != null) {
						Intent sharingIntent = new Intent(Intent.ACTION_SEND);
						File file = new File(path);
						Uri screenshotUri = Uri.fromFile(file);
						Log.d("ImagePath", path + "");
						sharingIntent.setType("image/*");
						sharingIntent.putExtra(Intent.EXTRA_STREAM,
								screenshotUri);
						startActivity(Intent.createChooser(sharingIntent,
								"Wish"));
					}
				}
			}
		});

		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (savedImagePath != null)
					Toast.makeText(PhotoSortrActivity.this,
							"Image already saved", Toast.LENGTH_SHORT).show();
				else

					imageSave(false);
			}
		});

		delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mImages.size() != 0) {
					mImages.remove(mImages.size() - 1);
					photoSorter.invalidate();
				} else {
					Toast.makeText(getApplicationContext(),
							"No images to delete", Toast.LENGTH_SHORT).show();
				}
			}
		});

	}

	private Bitmap decodfile(File f, Integer clips2) {

		try {
			if (f != null) {

				BitmapFactory.Options bo = new BitmapFactory.Options();
				bo.inJustDecodeBounds = true;
				BitmapFactory.decodeStream(new FileInputStream(f), null, bo);
				final int REQUIRED_SIZE = 70;
				int width_tmp = bo.outWidth, height_tmp = bo.outHeight;
				int scale = 1;
				while (true) {
					if (width_tmp / 2 < REQUIRED_SIZE
							|| height_tmp / 2 < REQUIRED_SIZE)
						break;
					width_tmp /= 2;
					height_tmp /= 2;
					scale++;
				}
				BitmapFactory.Options bo1 = new BitmapFactory.Options();
				bo1.inSampleSize = scale;
				return BitmapFactory.decodeStream(new FileInputStream(f), null,
						bo1);

			} else {
				int IMAGE_MAX_SIZE = 190;
				Bitmap b = null;
				BitmapFactory.Options o = new BitmapFactory.Options();
				o.inJustDecodeBounds = true;
				BitmapFactory.decodeResource(getResources(), clips2, o);
				int scale = 1;
				if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
					scale = (int) Math.pow(
							2,
							(int) Math.round(Math.log(IMAGE_MAX_SIZE
									/ (double) Math
											.max(o.outHeight, o.outWidth))
									/ Math.log(0.5)));
				}
				BitmapFactory.Options o2 = new BitmapFactory.Options();
				o2.inSampleSize = scale;
				b = BitmapFactory.decodeResource(getResources(), clips2, o2);
				return b;

			}
		} catch (FileNotFoundException fn) {
		}
		return null;

	}

	protected void uploadimagefromGallaery(int sap) {
		Intent intent = new Intent();

		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		if (sap == 1) {
			startActivityForResult(
					Intent.createChooser(intent, "Complete action using"), 2);
		} else if (sap == 2) {
			startActivityForResult(
					Intent.createChooser(intent, "Complete action using"), 4);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
			photoSorter.trackballClicked();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public class MyAdapter extends BaseAdapter {

		int place;

		public MyAdapter(int i) {
			place = i;

		}

		@Override
		public int getCount() {
			if (place == 0) {
				return icons.length;
			} else if (place == 1) {
				return backgroundsPort.length;
			} else if (place == 2) {
				return backgroundsland.length;
			} else if (place == 3) {
				return clips.length;
			} else if (place == 5) {
				return icons1.length;
			} else {

				return frame.length;// clips.length;
			}
		}

		@Override
		public Object getItem(int position) {
			return icons[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View row = null;
			row = getLayoutInflater().inflate(R.layout.listitem, parent, false);
			ImageView iv = (ImageView) row.findViewById(R.id.imageView1);
			if (place == 0) {
				iv.setImageResource(icons[position]);
			} else if (place == 3) {
				Bitmap bm = decodfile(null, clips[position]);
				iv.setImageBitmap(bm);

			} else if (place == 1) {

				Bitmap bm1 = decodfile(null, backgroundsPort[position]);
				iv.setImageBitmap(bm1);

			} else if (place == 2) {
				Bitmap bm2 = decodfile(null, backgroundsland[position]);
				iv.setImageBitmap(bm2);

			} else if (place == 4) {

				Bitmap bm3 = decodfile(null, frame[position]);
				iv.setImageBitmap(bm3);

			} else if (place == 5) {

				Bitmap bm4 = decodfile(null, icons1[position]);
				iv.setImageBitmap(bm4);
			}
			return row;
		}

	}

	@SuppressLint("NewApi")
	private String imageSave(final boolean isShare) {

		new AsyncTask<Void, Void, Void>() {
			ProgressDialog progDialog;

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				buu.setVisibility(View.GONE);
				progDialog = ProgressDialog.show(PhotoSortrActivity.this, "",
						"Saving...");
			}

			@Override
			protected Void doInBackground(Void... params) {

				String IMAGE_NAME = "IMAGE_WISH";
				File parentDirectory = new File(
						Environment.getExternalStorageDirectory(), "wish");

				parentDirectory.setWritable(false);
				parentDirectory.setReadable(true, false);

				if (parentDirectory.exists()) {
				} else {
					parentDirectory.mkdir();
				}
				String path = Environment.getExternalStorageDirectory()
						.toString()
						+ "/wish/"
						+ IMAGE_NAME
						+ System.currentTimeMillis() + ".jpeg";
				Bitmap bitmap = null;
				try {
					OutputStream out = null;
					File imagefile = new File(path);
					out = new FileOutputStream(imagefile);
					Canvas bitmapCanvas = new Canvas();
					try {
						bitmap = Bitmap.createBitmap(
								slidingPanel.getWidth() * 2,
								slidingPanel.getHeight() * 2,
								Bitmap.Config.RGB_565);
					} catch (Exception e) {

					}
					bitmapCanvas.setBitmap(bitmap);
					bitmapCanvas.scale(2.0f, 2.0f);
					slidingPanel.draw(bitmapCanvas);
					bitmap.compress(Bitmap.CompressFormat.PNG, 0, out);
					savedImagePath = path;
					out.flush();
					out.close();
					MediaStore.Images.Media.insertImage(getContentResolver(),
							bitmap, "Screen", "screen");
				} catch (Exception e) {
					if (progDialog != null)
						progDialog.dismiss();
					UIUtils.showToast(PhotoSortrActivity.this,
							"Could not Save. Retry");

					return null;
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				if (progDialog != null)
					progDialog.dismiss();
				Toast.makeText(getApplicationContext(),
						"Image Saved Successfully", Toast.LENGTH_SHORT).show();
				if (isShare) {
					Intent sharingIntent = new Intent(Intent.ACTION_SEND);
					File file = new File(savedImagePath);
					Uri screenshotUri = Uri.fromFile(file);
					Log.d("ImagePath", savedImagePath + "");
					sharingIntent.setType("image/*");
					sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
					startActivity(Intent.createChooser(sharingIntent, "Wish"));
				}
				buu.setVisibility(View.VISIBLE);

			}

		}.execute();

		return savedImagePath;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case 1:
			if (resultCode == RESULT_OK) {

				d1.show();

			} else {

			}

			break;

		case 2:
			System.out.println(builder1);

			if (resultCode == RESULT_OK) {
				mImageCaptureUri = data.getData();

				d1.show();
			} else {

			}
			break;

		case 3:
			if (resultCode == RESULT_OK) {
				System.out.println("ha iam in crop from camera");
				Bundle extras = data.getExtras();
				System.out.println("the values" + extras);
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				if (extras != null) {
					int posii = extras.getInt("posi");
					conv_bm = extras.getParcelable("data");
					if (posii == 2) {
						// for circular crop
						Bitmap resized = Bitmap.createScaledBitmap(conv_bm,
								100, 100, true);
						conv_bm = getRoundedRectBitmap(resized, 100);

					}
					conv_bm.compress(Bitmap.CompressFormat.PNG, 90, bos);
					byte[] br = bos.toByteArray();
					String img = Base64.encodeToString(br, 1);
					photoSorter.loadImages(PhotoSortrActivity.this, img);

				}

			} else {

			}
			break;
		case 4:
			Uri path = data.getData();
			if (path != null) {
				Cursor cursor = getContentResolver()
						.query(path,
								new String[] { android.provider.MediaStore.Images.ImageColumns.DATA },
								null, null, null);
				cursor.moveToFirst();
				final String imageFilePath = cursor.getString(0);
				File f = new File(imageFilePath);
				Bitmap bm = decodfile(f, 0);
				BitmapDrawable bd = new BitmapDrawable(bm);
				photoSorter.setBackgroundDrawable(bd);

			}
			break;

		}

	}

	private Bitmap getRoundedRectBitmap(Bitmap bitmap, int i) {
		Bitmap result = null;
		try {
			result = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(result);
			int color = 0xff424242;
			Paint paint = new Paint();
			Rect rect = new Rect(0, 0, 200, 200);
			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawCircle(50, 50, 50, paint);
			canvas.drawBitmap(bitmap, rect, rect, paint);
		} catch (NullPointerException e) {
		} catch (OutOfMemoryError o) {
		}
		return result;
	}

	private void doCrop(int posi) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setType("image/*");
		list = getPackageManager().queryIntentActivities(intent, 0);
		System.out.println("My list" + list + "Size" + list.size());
		int size = list.size();
		if (size == 0) {
			Toast.makeText(this, "Can not find image crop app",
					Toast.LENGTH_SHORT).show();

			return;
		} else {
			System.out.println("capture image:" + mImageCaptureUri);
			intent.setData(mImageCaptureUri);
			if (posi == 1) {
				intent.putExtra("aspectX", 1);
				intent.putExtra("aspectY", 1);

			} else if (posi == 2) {
				intent.putExtra("circleCrop", "true");
			}
			intent.putExtra("outputX", 200);
			intent.putExtra("outputY", 200);

			intent.putExtra("scale", true);
			intent.putExtra("return-data", true);
			if (size == 1) {
				Intent i = new Intent(intent);
				ResolveInfo res = list.get(0);
				i.setComponent(new ComponentName(res.activityInfo.packageName,
						res.activityInfo.name));
				i.putExtra("posi", posi);
				PhotoSortrActivity.this.startActivityForResult(i, 3);

			} else {
				System.out.println("hai this is else part k na");
			}
		}
	}

	public class PhotoSortrView extends View implements
			MultiTouchObjectCanvas<MultiTouchEntity> {
		private MultiTouchController<MultiTouchEntity> multiTouchController = new MultiTouchController<MultiTouchEntity>(
				this);

		public PhotoSortrView(Context context) {
			this(context, null);

		}

		public PhotoSortrView(Context context, AttributeSet attrs) {
			this(context, attrs, 0);
		}

		public PhotoSortrView(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);

		}

		public void unloadImages(MultiTouchEntity delete) {

			mImages.remove(delete);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			int n = mImages.size();
			for (int i = 0; i < n; i++)
				mImages.get(i).draw(canvas);
			if (mShowDebugInfo)
				drawMultitouchDebugMarks(canvas);
		}

		public void trackballClicked() {
			mUIMode = (mUIMode + 1) % 3;
			invalidate();
		}

		private void drawMultitouchDebugMarks(Canvas canvas) {
			if (currTouchPoint.isDown()) {
				float[] xs = currTouchPoint.getXs();
				float[] ys = currTouchPoint.getYs();
				float[] pressures = currTouchPoint.getPressures();
				int numPoints = Math.min(currTouchPoint.getNumTouchPoints(), 2);
				for (int i = 0; i < numPoints; i++)
					canvas.drawCircle(xs[i], ys[i], 50 + pressures[i] * 80,
							mLinePaintTouchPointCircle);
				if (numPoints == 2)
					canvas.drawLine(xs[0], ys[0], xs[1], ys[1],
							mLinePaintTouchPointCircle);
			}
		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			return multiTouchController.onTouchEvent(event);
		}

		public MultiTouchEntity getDraggableObjectAtPoint(PointInfo pt) {
			float x = pt.getX(), y = pt.getY();
			int n = mImages.size();
			for (int i = n - 1; i >= 0; i--) {
				ImageEntity im = (ImageEntity) mImages.get(i);
				if (im.containsPoint(x, y))
					return im;
			}
			return null;
		}

		public void selectObject(final MultiTouchEntity img,
				PointInfo touchPoint) {
			savedImagePath = null;
			PhotoSortrActivity.this.img = img;
			if (img != null) {
				mImages.remove(img);
				mImages.add(img);
				mgallery.add(img);
			} else {
			}
			currTouchPoint.set(touchPoint);
			if (listView.isShown()) {
				listView.setVisibility(View.GONE);
			} else if (listView1.isShown()) {
				listView1.setVisibility(View.GONE);
			}
			invalidate();
		}

		public void getPositionAndScale(MultiTouchEntity img,
				PositionAndScale objPosAndScaleOut) {
			objPosAndScaleOut.set(img.getCenterX(), img.getCenterY(),
					(mUIMode & UI_MODE_ANISOTROPIC_SCALE) == 0,
					(img.getScaleX() + img.getScaleY()) / 2,
					(mUIMode & UI_MODE_ANISOTROPIC_SCALE) != 0,
					img.getScaleX(), img.getScaleY(),
					(mUIMode & UI_MODE_ROTATE) != 0, img.getAngle());
		}

		public boolean setPositionAndScale(MultiTouchEntity img,
				PositionAndScale newImgPosAndScale, PointInfo touchPoint) {
			currTouchPoint.set(touchPoint);
			boolean ok = ((ImageEntity) img).setPos(newImgPosAndScale);
			if (ok)
				invalidate();
			return ok;
		}

		public boolean pointInObjectGrabArea(PointInfo pt, MultiTouchEntity img) {
			return false;
		}

		public void loadImages(PhotoSortrActivity context, String bg) {
			checkimage = 1;
			Resources res = context.getResources();
			Bitmap bm = null;
			byte[] data = Base64.decode(bg, 1);
			bm = BitmapFactory.decodeByteArray(data, 0, data.length);
			Drawable imag = new BitmapDrawable(bm);
			mImages.add(new ImageEntity(imag, res, context1));
			mLinePaintTouchPointCircle.setColor(Color.TRANSPARENT);
			mLinePaintTouchPointCircle.setStrokeWidth(0);
			mLinePaintTouchPointCircle.setStyle(Style.STROKE);
			mLinePaintTouchPointCircle.setAntiAlias(true);
			DisplayMetrics metrics = res.getDisplayMetrics();
			displayWidth = res.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? Math
					.max(metrics.widthPixels, metrics.heightPixels) : Math.min(
					metrics.widthPixels, metrics.heightPixels);
			displayHeight = res.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? Math
					.min(metrics.widthPixels, metrics.heightPixels) : Math.max(
					metrics.widthPixels, metrics.heightPixels);
			int n = mImages.size();
			float cx = SCREEN_MARGIN
					+ (float) (Math.random() * (displayWidth - 2 * SCREEN_MARGIN));
			float cy = SCREEN_MARGIN
					+ (float) (Math.random() * (displayHeight - 2 * SCREEN_MARGIN));
			mImages.get(n - 1).load(imag, context, cx, cy);

		}

	}

	@Override
	public void onBackPressed() {

		if (checkimage == 1) {

			d = new Dialog(PhotoSortrActivity.this, R.style.PopupAnimation);
			d.requestWindowFeature(Window.FEATURE_NO_TITLE);
			d.setContentView(R.layout.dialog);
			TextView tv1 = (TextView) d.findViewById(R.id.dailogtext);
			tv1.setText("Are you sure you want to exit without saving?");
			Button bt1 = (Button) d.findViewById(R.id.btn1);
			Button bt2 = (Button) d.findViewById(R.id.btn2);
			bt1.setText("Yes");
			bt2.setText("No");
			View v1 = (View) d.findViewById(R.id.view);
			View v2 = (View) d.findViewById(R.id.view1);
			v1.bringToFront();
			v2.bringToFront();

			bt1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					finish();

				}
			});
			bt2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					d.dismiss();
				}
			});

			d.show();

		} else {
			finish();
		}

	}

}