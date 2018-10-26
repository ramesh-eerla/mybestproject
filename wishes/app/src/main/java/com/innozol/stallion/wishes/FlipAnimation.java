package com.innozol.stallion.wishes;

import com.innozol.stallion.wishes.AnimationFactory.FlipDirection;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;



public class FlipAnimation extends Animation { 
	
	public final String TAG = this.getClass().getSimpleName();
	
	private final float mFromDegrees;
	private final float mToDegrees;
	private final float mCenterY;
	private float mRotateX;
	private int width;
	private Camera mCamera;
	private boolean out;
	private FlipDirection direction;
	
	public FlipAnimation(float fromDegrees, float toDegrees, boolean out,float centerY, FlipDirection direction, int width) {
		mFromDegrees = fromDegrees;
		mToDegrees = toDegrees;
		mRotateX = ((direction == FlipDirection.LEFT_RIGHT && !out) || (direction == FlipDirection.RIGHT_LEFT && out)) ? width : 0;
		mCenterY = centerY;
		this.width = width;
		this.direction = direction;
		this.out = out;
	}

	@Override
	public void initialize(int width, int height, int parentWidth, int parentHeight) {
		super.initialize(width, height, parentWidth, parentHeight);
		mCamera = new Camera();
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {		
		final float fromDegrees = mFromDegrees;
		float degrees = fromDegrees + ((mToDegrees - fromDegrees) * interpolatedTime);
		final float centerY = mCenterY;
		final Camera camera = mCamera;
		final Matrix matrix = t.getMatrix();		
		int dirAmt = (direction == FlipDirection.LEFT_RIGHT) ? 1 : -1;
		int amt = (direction == FlipDirection.RIGHT_LEFT) ? width : 0;
		int start = (int) (out ? amt : (width / 2));
		float centerX = (width / 2 * interpolatedTime * dirAmt) + start;
		camera.save();		
		camera.rotateY(degrees);
		camera.getMatrix(matrix);		
		camera.restore();	
		matrix.preTranslate(-mRotateX, -centerY);
		matrix.postTranslate(centerX, centerY);
	}
}
