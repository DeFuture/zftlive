package com.zftlive.android.view;

import java.util.LinkedList;
import java.util.List;

import com.zftlive.android.R;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * 下拉关闭当前Activity，解决掉Listview、ScrollView、ViewPager事件冲突问题
 * 
 * @author 曾繁添&&xiaanming
 * @blog http://blog.csdn.net/xiaanming
 * 
 */
public class PullDownRemoveLayout extends FrameLayout {
	private static final String TAG = PullDownRemoveLayout.class.getSimpleName();
	private View mContentView;
	private int mTouchSlop;
	private int downX;
	private int downY;
	private int tempY;
	private Scroller mScroller;
	private int viewHeight;
	private boolean isSilding;
	private boolean isFinish;
	private boolean isEnablePull = true;
	private Drawable mShadowDrawable;
	private Activity mActivity;
	private List<ViewPager> mViewPagers = new LinkedList<ViewPager>();
	
	public PullDownRemoveLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public PullDownRemoveLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
		mScroller = new Scroller(context);

		mShadowDrawable = getResources().getDrawable(R.drawable.view_swipeback_shadow_left);
	}
	
	
	public void attachToActivity(Activity activity) {
		mActivity = activity;
		TypedArray a = activity.getTheme().obtainStyledAttributes(
				new int[] { android.R.attr.windowBackground });
		int background = a.getResourceId(0, 0);
		a.recycle();

		ViewGroup decor = (ViewGroup) activity.getWindow().getDecorView();
		ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
		decorChild.setBackgroundResource(background);
		decor.removeView(decorChild);
		addView(decorChild);
		setContentView(decorChild);
		decor.addView(this);
	}

	/**
	 * 设置是否可以下拉
	 * @param enable
	 */
	public void setEnablePull(Boolean enable){
		this.isEnablePull = enable;
	}
	
	private void setContentView(View decorChild) {
		mContentView = (View) decorChild.getParent();
	}

	/**
	 * 事件拦截操作
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		//处理ViewPager冲突问题
		ViewPager mViewPager = getTouchViewPager(mViewPagers, ev);
		Log.i(TAG, "mViewPager = " + mViewPager);
		
		if(mViewPager != null && mViewPager.getCurrentItem() != 0){
			if(!isEnablePull)
				return super.onInterceptTouchEvent(ev);
		}

		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downY = tempY = (int) ev.getRawY();
			downX = (int) ev.getRawX();
			break;
		case MotionEvent.ACTION_MOVE:
			int moveY = (int) ev.getRawY();
			// 满足此条件屏蔽SildingFinishLayout里面子类的touch事件
			if (moveY - downY > mTouchSlop
					&& Math.abs((int) ev.getRawX() - downX) < mTouchSlop) {
				return true;
			}
			break;
		}

		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE:
			int moveY = (int) event.getRawY();
			int deltaY = tempY - moveY;
			tempY = moveY;
			if (moveY - downY > mTouchSlop
					&& Math.abs((int) event.getRawX() - downX) < mTouchSlop) {
				isSilding = true;
			}

			if (moveY - downY >= 0 && isSilding) {
				mContentView.scrollBy(0, deltaY);
			}
			break;
		case MotionEvent.ACTION_UP:
			isSilding = false;
			//TODO 控制下拉高度多少执行移除操作（默认下拉到屏幕高度1/3）
			if (mContentView.getScrollY() <= -viewHeight / 3) {
				isFinish = true;
				scrollRight();
			} else {
				scrollOrigin();
				isFinish = false;
			}
			break;
		}

		return true;
	}
	
	/**
	 * 获取SwipeBackLayout里面的ViewPager的集合
	 * @param mViewPagers
	 * @param parent
	 */
	private void getAlLViewPager(List<ViewPager> mViewPagers, ViewGroup parent){
		int childCount = parent.getChildCount();
		for(int i=0; i<childCount; i++){
			View child = parent.getChildAt(i);
			if(child instanceof ViewPager){
				mViewPagers.add((ViewPager)child);
			}else if(child instanceof ViewGroup){
				getAlLViewPager(mViewPagers, (ViewGroup)child);
			}
		}
	}
	
	
	/**
	 * 返回我们touch的ViewPager
	 * @param mViewPagers
	 * @param ev
	 * @return
	 */
	private ViewPager getTouchViewPager(List<ViewPager> mViewPagers, MotionEvent ev){
		if(mViewPagers == null || mViewPagers.size() == 0){
			return null;
		}
		Rect mRect = new Rect();
		for(ViewPager v : mViewPagers){
			v.getHitRect(mRect);
			
			if(mRect.contains((int)ev.getX(), (int)ev.getY())){
				return v;
			}
		}
		return null;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (changed) {
			viewHeight = this.getHeight();
			
			getAlLViewPager(mViewPagers, this);
			Log.i(TAG, "ViewPager size = " + mViewPagers.size());
		}
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		if (mShadowDrawable != null && mContentView != null) {

			int left = mContentView.getLeft()- mShadowDrawable.getIntrinsicWidth();
			int right = left + mShadowDrawable.getIntrinsicWidth();
			int top = mContentView.getTop();
			int bottom = mContentView.getBottom();

			mShadowDrawable.setBounds(left, top, right, bottom);
			mShadowDrawable.draw(canvas);
		}

	}


	/**
	 * 滚动出界面
	 */
	private void scrollRight() {
		if(isEnablePull){
			final int delta = (viewHeight + mContentView.getScrollY());
			// 调用startScroll方法来设置一些滚动的参数，我们在computeScroll()方法中调用scrollTo来滚动item
			mScroller.startScroll(0, mContentView.getScrollY(), 0, -delta + 1,Math.abs(delta));
			postInvalidate();
		}
	}

	/**
	 * 滚动到起始位置
	 */
	private void scrollOrigin() {
		if (isEnablePull) {
			int delta = mContentView.getScrollX();
			mScroller.startScroll(0, mContentView.getScrollX(), 0, -delta,Math.abs(delta));
			postInvalidate();
		}
	}

	@Override
	public void computeScroll() {
		// 调用startScroll的时候scroller.computeScrollOffset()返回true，
		if (isEnablePull) {
			if (mScroller.computeScrollOffset()) {
				mContentView.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
				postInvalidate();
	
				if (mScroller.isFinished() && isFinish) {
					mActivity.finish();
				}
			}
		}
	}
}
