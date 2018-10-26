
package com.innozol.stallion.wishes;  

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ViewAnimator;


public class AnimationFactory {
	
	
	public static enum FlipDirection {
		LEFT_RIGHT, 
		RIGHT_LEFT;
		
		public float getStartDegreeForFirstView() {
			return 0;
		}
		
		public float getStartDegreeForSecondView() {
			switch(this) {
			case LEFT_RIGHT:
				return -90;
			case RIGHT_LEFT:
				return 90;
			default:
				return 0;
			}
		}
		
		public float getEndDegreeForFirstView() {
			switch(this) {
			case LEFT_RIGHT:
				return 90;
			case RIGHT_LEFT:
				return -90;
			default:
				return 0;
			}
		}
		
		public float getEndDegreeForSecondView() {
			return 0;
		}
	};
	 
	
	
	public static Animation[] flipAnimation(final View fromView, final View toView, FlipDirection dir, long duration) {
		
		Animation[] result = new Animation[2];
		float centerY;

		centerY = fromView.getHeight() / 2.0f; 
		
		Animation outFlip = new FlipAnimation(dir.getStartDegreeForFirstView(), dir.getEndDegreeForFirstView(), true, centerY, dir, fromView.getWidth());
		outFlip.setDuration(duration);
		outFlip.setFillAfter(true);
		outFlip.setInterpolator(new AccelerateInterpolator()); 

		AnimationSet outAnimation = new AnimationSet(true);
		outAnimation.addAnimation(outFlip); 
		result[0] = outAnimation; 
		
		Animation inFlip = new FlipAnimation(dir.getStartDegreeForSecondView(), dir.getEndDegreeForSecondView(), false, centerY, dir, fromView.getWidth());
		inFlip.setDuration(duration);
		inFlip.setFillAfter(true);
		inFlip.setInterpolator(new DecelerateInterpolator());
		inFlip.setStartOffset(duration);
		
		AnimationSet inAnimation = new AnimationSet(true); 
		inAnimation.addAnimation(inFlip); 
		result[1] = inAnimation;  
		
		return result;
	}
	
	
	public static void flipTransition(final ViewAnimator viewAnimator, FlipDirection dir, long duration) {   
		
		final View fromView = viewAnimator.getCurrentView();
		
		final int currentIndex = viewAnimator.getDisplayedChild();
		final int nextIndex = (currentIndex + 1) % viewAnimator.getChildCount();
		
		final View toView = viewAnimator.getChildAt(nextIndex);

		Animation[] animc = AnimationFactory.flipAnimation(fromView, toView, dir, duration);
  
		viewAnimator.setOutAnimation(animc[0]);
		viewAnimator.setInAnimation(animc[1]);
		
		viewAnimator.setDisplayedChild(nextIndex); 
	}
	
	public static void flipTransition(final ViewAnimator viewAnimator, FlipDirection dir) {
		
		flipTransition(viewAnimator, dir, 1000);
	}
	
	
	public static Animation inFromLeftAnimation(long duration, Interpolator interpolator) {
		Animation inFromLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT,  -1.0f, Animation.RELATIVE_TO_PARENT,  0.0f,
				Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
		);
		inFromLeft.setDuration(duration);
		inFromLeft.setInterpolator(interpolator==null?new AccelerateInterpolator():interpolator); //AccelerateInterpolator
		return inFromLeft;
	}
 
	
	public static Animation outToRightAnimation(long duration, Interpolator interpolator) {
		Animation outtoRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,  +1.0f,
				Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
		);
		outtoRight.setDuration(duration);
		outtoRight.setInterpolator(interpolator==null?new AccelerateInterpolator():interpolator);
		return outtoRight;
	}
 
	
	public static Animation inFromRightAnimation(long duration, Interpolator interpolator) {

		Animation inFromRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT,  +1.0f, Animation.RELATIVE_TO_PARENT,  0.0f,
				Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
		);
		inFromRight.setDuration(duration);
		inFromRight.setInterpolator(interpolator==null?new AccelerateInterpolator():interpolator);
		return inFromRight;
	}
 
	
	public static Animation outToLeftAnimation(long duration, Interpolator interpolator) {
		Animation outtoLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,  -1.0f,
				Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
		);
		outtoLeft.setDuration(duration);
		outtoLeft.setInterpolator(interpolator==null?new AccelerateInterpolator():interpolator);
		return outtoLeft;
	} 
 
	
	public static Animation inFromTopAnimation(long duration, Interpolator interpolator) {
		Animation infromtop = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, -1.0f, Animation.RELATIVE_TO_PARENT, 0.0f
		);
		infromtop.setDuration(duration);
		infromtop.setInterpolator(interpolator==null?new AccelerateInterpolator():interpolator);
		return infromtop;
	} 
 
	
	public static Animation outToTopAnimation(long duration, Interpolator interpolator) {
		Animation outtotop = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,  0.0f,
				Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT, -1.0f
		);
		outtotop.setDuration(duration); 
		outtotop.setInterpolator(interpolator==null?new AccelerateInterpolator():interpolator); 
		return outtotop;
	} 

	public static Animation fadeInAnimation(long duration, long delay) {  
		
		Animation fadeIn = new AlphaAnimation(0, 1);
		fadeIn.setInterpolator(new DecelerateInterpolator());  
		fadeIn.setDuration(duration);
		fadeIn.setStartOffset(delay);
		
		return fadeIn;
	}

	
	public static Animation fadeOutAnimation(long duration, long delay) {   

		Animation fadeOut = new AlphaAnimation(1, 0);
		fadeOut.setInterpolator(new AccelerateInterpolator());
		fadeOut.setStartOffset(delay);
		fadeOut.setDuration(duration);

		return fadeOut;
	} 

	
	public static Animation fadeInAnimation(long duration, final View view) { 
		Animation animation = fadeInAnimation(500, 0); 

	    animation.setAnimationListener(new AnimationListener() { 

			public void onAnimationEnd(Animation animation) {
				view.setVisibility(View.VISIBLE);
			} 
			
			public void onAnimationRepeat(Animation animation) {}  
			
			public void onAnimationStart(Animation animation) {
				view.setVisibility(View.GONE); 
			} 
	    });
	    
	    return animation;
	}

	
	public static Animation fadeOutAnimation(long duration, final View view) {
		
		Animation animation = fadeOutAnimation(500, 0); 

	    animation.setAnimationListener(new AnimationListener() { 

			public void onAnimationEnd(Animation animation) {
				view.setVisibility(View.GONE);
			} 
			
			public void onAnimationRepeat(Animation animation) {}  
			
			public void onAnimationStart(Animation animation) {
				view.setVisibility(View.VISIBLE); 
			} 
	    });
	    
	    return animation;
		
	}

	
	public static Animation[] fadeInThenOutAnimation(long duration, long delay) {  
		return new Animation[] {fadeInAnimation(duration,0), fadeOutAnimation(duration, duration+delay)};
	}  
	
	public static void fadeOut(View v) { 
		if (v==null) return;  
	    v.startAnimation(fadeOutAnimation(500, v)); 
	} 
	
	
	public static void fadeIn(View v) { 
		if (v==null) return;
		
	    v.startAnimation(fadeInAnimation(500, v)); 
	}
		
	public static void fadeInThenOut(final View v, long delay) {
		if (v==null) return;
		 
		v.setVisibility(View.VISIBLE);
		AnimationSet animation = new AnimationSet(true);
		Animation[] fadeInOut = fadeInThenOutAnimation(500,delay); 
	    animation.addAnimation(fadeInOut[0]);
	    animation.addAnimation(fadeInOut[1]);
	    animation.setAnimationListener(new AnimationListener() {

			public void onAnimationEnd(Animation animation) {
				v.setVisibility(View.GONE);
			} 

			public void onAnimationRepeat(Animation animation) { 
			}  
			
			public void onAnimationStart(Animation animation) {
				v.setVisibility(View.VISIBLE); 
			} 
	    });
	    
	    v.startAnimation(animation); 
	}

}
