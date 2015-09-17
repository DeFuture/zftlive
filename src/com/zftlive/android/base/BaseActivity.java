package com.zftlive.android.base;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;

import com.zdp.aseo.content.AseoZdpAseo;
import com.zftlive.android.MApplication;
import com.zftlive.android.R;
import com.zftlive.android.view.SwipeBackLayout;

/**
 * android 系统中的四大组件之一Activity基类
 * @author 曾繁添
 * @version 1.0
 *
 */
public abstract class BaseActivity extends Activity implements IBaseActivity{

	/***整个应用Applicaiton**/
	private MApplication mApplication = null;
	/**当前Activity的弱引用，防止内存泄露**/
	private WeakReference<Activity> context = null;
	/**当前Activity渲染的视图View**/
	private View mContextView = null;
	/**共通操作**/
	private Operation mBaseOperation = null;
	/**右滑关闭当前Activity顶层容器**/
	protected SwipeBackLayout rootView;
	/**日志输出标志**/
	protected final String TAG = this.getClass().getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "BaseActivity-->onCreate()");
		
		//设置渲染视图View
		mContextView = LayoutInflater.from(this).inflate(bindLayout(), null);
		setContentView(mContextView);
		AseoZdpAseo.initType(this, AseoZdpAseo.INSERT_TYPE);
		//获取应用Application
		mApplication = (MApplication)getApplicationContext();
		
		//将当前Activity压入栈
		context = new WeakReference<Activity>(this);
		mApplication.pushTask(context);
		
		//实例化共通操作
		mBaseOperation = new Operation(this);
		
		//初始化控件
		initView(mContextView);
		
		//业务操作
		doBusiness(this);
		AseoZdpAseo.initType(this, AseoZdpAseo.SCREEN_TYPE);
		//显示VoerFlowMenu
		displayOverflowMenu(getContext());
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		Log.d(TAG, "BaseActivity-->onRestart()");
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.d(TAG, "BaseActivity-->onStart()");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "BaseActivity-->onResume()");
		resume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d(TAG, "BaseActivity-->onPause()");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.d(TAG, "BaseActivity-->onStop()");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "BaseActivity-->onDestroy()");
		
		destroy();
		mApplication.removeTask(context);
	}
	
	/**
	 * 显示Actionbar菜单图标
	 */
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
			if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
				try {
					Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);//显示
				} catch (Exception e) {
					Log.e(TAG, "onMenuOpened-->"+e.getMessage());
				}
			}
		}
		return super.onMenuOpened(featureId, menu);
	}
	
	/**
	 * 显示OverFlowMenu按钮
	 * @param mContext 上下文Context
	 */
	public void displayOverflowMenu(Context mContext) {
	     try {
	        ViewConfiguration config = ViewConfiguration.get(mContext);
	        Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
	        if(menuKeyField != null) {
	            menuKeyField.setAccessible(true);
	            menuKeyField.setBoolean(config, false);//显示
	        }
	    } catch (Exception e) {
	        Log.e("ActionBar", e.getMessage());
	    }
	}
	
	/**
	 * 获取当前Activity
	 * @return
	 */
	protected Activity getContext(){
		if(null != context)
			return context.get();
		else
			return null;
	}
	
	/**
	 * 获取共通操作机能
	 */
	public Operation getOperation(){
		return this.mBaseOperation;
	}
	
	/**
	 * Actionbar点击返回键关闭事件
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				//关闭窗体动画显示  
//			    overridePendingTransition(R.anim.activity_close,R.anim.alpha_out);
			    overridePendingTransition(0, R.anim.base_slide_right_out);
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		//关闭窗体动画显示  
//		overridePendingTransition(R.anim.activity_close,R.anim.alpha_out);
		overridePendingTransition(0, R.anim.base_slide_right_out);
	}
}
