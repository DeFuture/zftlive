package com.zftlive.android.sample.progressbar;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.zftlive.android.R;
import com.zftlive.android.base.BaseActivity;
import com.zftlive.android.common.ActionBarManager;
import com.zftlive.android.view.progressbar.RoundProgressBar;
import com.zftlive.android.view.progressbar.VerticalProgressBar;

/**
 * 进度条示例（水平进度条+垂直进度条+圆形进度条）
 * @author 曾繁添
 * @version 2.0
 *
 */
public class ProgressBarActivity extends BaseActivity implements OnClickListener {
	private RoundProgressBar mRoundProgressBar1 ,mRoundProgressBar2, mRoundProgressBar3, mRoundProgressBar4;
	private VerticalProgressBar pb_vertical_custom;
	private ProgressBar pb_horizontal_custom,pb_google_styled;
	private Button btn_go,btn_reset;
	private int progress = 0;

	@Override
	public int bindLayout() {
		return R.layout.activity_progressbar;
	}

	@Override
	public void initView(View view) {
		
		mRoundProgressBar1 = (RoundProgressBar) findViewById(R.id.roundProgressBar1);
		mRoundProgressBar2 = (RoundProgressBar) findViewById(R.id.roundProgressBar2);
		mRoundProgressBar3 = (RoundProgressBar) findViewById(R.id.roundProgressBar3);
		mRoundProgressBar4 = (RoundProgressBar) findViewById(R.id.roundProgressBar4);
		pb_vertical_custom = (VerticalProgressBar) findViewById(R.id.pb_vertical_custom);
		pb_horizontal_custom = (ProgressBar) findViewById(R.id.pb_horizontal_custom);
		pb_google_styled = (ProgressBar) findViewById(R.id.pb_google_styled);
		
		btn_go = (Button)findViewById(R.id.btn_go);
		btn_reset = (Button)findViewById(R.id.btn_reset);
		
		//初始化带返回按钮的标题栏
		String strCenterTitle = getResources().getString(R.string.ProgressBarActivity);
		ActionBarManager.initBackTitle(getContext(), getActionBar(), strCenterTitle);
	}

	@Override
	public void doBusiness(Context mContext) {
		
		//设置进度监听器
		mRoundProgressBar1.setOnLoadFinishListener(new RoundProgressBar.OnLoadFinishListener() {
			
			@Override
			public void onLoadFinished() {
				Toast.makeText(getApplicationContext(), "进度条加载完成事件触发了", Toast.LENGTH_LONG).show();
			}
		});
		
		btn_go.setOnClickListener(this);
		btn_reset.setOnClickListener(this);
	}


	@Override
	public void resume() {
		
	}


	@Override
	public void destroy() {
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_go:
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					while(progress <= 100){
						progress += 3;
						mRoundProgressBar1.setProgress(progress);
						mRoundProgressBar2.setProgress(progress);
						mRoundProgressBar3.setProgress(progress);
						mRoundProgressBar4.setProgress(progress);
						pb_vertical_custom.setProgress(progress);
						pb_horizontal_custom.setProgress(progress);
						pb_google_styled.setProgress(progress);
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					
				}
			}).start();
			break;
		case R.id.btn_reset:
			progress = 0;
			mRoundProgressBar1.setProgress(progress);
			mRoundProgressBar2.setProgress(progress);
			mRoundProgressBar3.setProgress(progress);
			mRoundProgressBar4.setProgress(progress);
			pb_vertical_custom.setProgress(progress);
			pb_horizontal_custom.setProgress(progress);
			pb_google_styled.setProgress(progress);
			break;
		default:
			break;
		}
	}
}
