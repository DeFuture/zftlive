package com.zftlive.android.sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.zdp.aseo.content.AseoZdpAseo;
import com.zftlive.android.MApplication;
import com.zftlive.android.R;
import com.zftlive.android.base.BaseActivity;
import com.zftlive.android.base.BaseAdapter;
import com.zftlive.android.common.ActionBarManager;
import com.zftlive.android.tools.ToolToast;

/**
 * Sample列表集合界面--自动收集AndroidManifest.xml配置
 * <per>
 *	<intent-filter>
 *		<action android:name="android.intent.action.MAIN" />
 *		<category android:name="com.zftlive.android.SAMPLE_CODE" />
 *	</intent-filter>
 *</per>
 * 的Activity
 * @author 曾繁添
 * @version 1.0
 *
 */
public class MainActivity extends BaseActivity {

	private ListView mListView;
	public final static String SAMPLE_CODE = "com.zftlive.android.SAMPLE_CODE";
	
	@Override
	public int bindLayout() {
		return R.layout.activity_main;
	}

	@Override
	public void initView(View view) {
		mListView = (ListView)findViewById(R.id.lv_demos);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				 Map<String, Object> map = (Map<String, Object>)parent.getItemAtPosition(position);
			     Intent intent = (Intent) map.get("intent");
			     startActivity(intent);
			     //过场动画
				 overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
			}
		});
		
		//构造适配器
		DemoActivityAdapter mAdapter = new DemoActivityAdapter(this);
		mAdapter.addItem(getListData());
		mListView.setAdapter(mAdapter);
		AseoZdpAseo.initTimer(this);
		//初始化带返回按钮的标题栏
		String strCenterTitle = getResources().getString(R.string.MainActivity);
		ActionBarManager.initMenuListTitle(getContext(), getActionBar(), strCenterTitle);
	}

	@Override
	public void doBusiness(Context mContext) {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void destroy() {
		
	}

	/**
	 * Actionbar点击[左侧图标]关闭事件
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
//				finish();
				break;
		}
		return true;
	}
	
	long waitTime = 2000;
    long touchTime = 0;
	
//	/**
//	 * 监听[返回]键事件
//	 */
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		// 返回键
//		if (KeyEvent.KEYCODE_BACK == keyCode) {
//
//			long currentTime = System.currentTimeMillis();
//			if ((currentTime - touchTime) >= waitTime) {
//				ToolToast.showShort("再按一次，退出程序");
//				touchTime = currentTime;
//			} else {
//				((MApplication) getApplicationContext()).removeAll();
//			}
//
//			return true;
//		}
//		return false;
//	}
	
	@Override
	public void onBackPressed() 
	{
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addCategory(Intent.CATEGORY_HOME);
		startActivity(intent);
	}
	/**
	 * 初始化列表数据
	 * @return
	 */
	protected List<Map<String, Object>> getListData(){
		List<Map<String, Object>> mListViewData = new ArrayList<Map<String, Object>>();
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(SAMPLE_CODE);
        List<ResolveInfo> mActivityList = getPackageManager().queryIntentActivities(mainIntent, 0);
        for (int i = 0; i < mActivityList.size(); i++) 
        {
            ResolveInfo info = mActivityList.get(i);
            String label = info.loadLabel(getPackageManager()) != null? info.loadLabel(getPackageManager()).toString() : info.activityInfo.name;
        
            Map<String, Object> temp = new HashMap<String, Object>();
            temp.put("title", label);
            temp.put("intent", buildIntent(info.activityInfo.applicationInfo.packageName,info.activityInfo.name));
            mListViewData.add(temp);
        }
        
        return mListViewData;
	}
	
	/**
	 * 构建每一个Item点击Intent
	 * @param packageName
	 * @param componentName
	 * @return
	 */
    protected Intent buildIntent(String packageName, String componentName) {
        Intent result = new Intent();
        result.setClassName(packageName, componentName);
        return result;
    }
	
	/**
	 * 列表适配器
	 */
	protected class DemoActivityAdapter extends BaseAdapter{

		public DemoActivityAdapter(Activity mContext) {
			super(mContext);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			Holder mHolder = null;
			if(null == convertView){
				convertView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_main_list_item, null);
				mHolder = new Holder();
				mHolder.label = (TextView)convertView.findViewById(R.id.tv_label);
				convertView.setTag(mHolder);
			}else{
				mHolder = (Holder) convertView.getTag();
			}
			
			//设置隔行变色背景
//			if(position%2==0){
//				convertView.setBackgroundColor(Color.parseColor("#FFFFFF"));
//			}else{
//				convertView.setBackgroundColor(Color.parseColor("#CCCCCC"));
//			}
			
			//设置数据
			mHolder.label.setText((String)((Map<String,Object>)getItem(position)).get("title"));
			
			return convertView;
		}
		
		class Holder{
			TextView label;
		}
	}

}
