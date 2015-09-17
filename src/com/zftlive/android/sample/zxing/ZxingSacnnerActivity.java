package com.zftlive.android.sample.zxing;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.zftlive.android.R;
import com.zftlive.android.base.BaseActivity;
import com.zftlive.android.common.ActionBarManager;
import com.zftlive.android.zxing.CaptureActivity;

/**
 * 二维码扫描示例代码
 * @author 曾繁添
 * @version 1.0
 *
 */
public class ZxingSacnnerActivity extends BaseActivity {

	@Override
	public int bindLayout() {
		return R.layout.activity_zxing_scanner;
	}

	@Override
	public void initView(View view) {
		//初始化带返回按钮的标题栏
		String strCenterTitle = getResources().getString(R.string.ZxingSacnnerActivity);
		ActionBarManager.initBackTitle(getContext(), getActionBar(), strCenterTitle);
	}

	@Override
	public void doBusiness(Context mContext) {
		Intent intent = new Intent(this,CaptureActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void destroy() {
		
	}
}
