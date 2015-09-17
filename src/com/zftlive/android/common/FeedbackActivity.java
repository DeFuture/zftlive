package com.zftlive.android.common;

import org.apache.http.Header;
import org.json.JSONObject;

import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.zftlive.android.R;
import com.zftlive.android.base.BaseActivity;
import com.zftlive.android.tools.ToolAlert;
import com.zftlive.android.tools.ToolString;

/**
 * 意见反馈Activity
 * @author 曾繁添
 * @version 1.0
 */
public class FeedbackActivity extends BaseActivity{

	private EditText et_message;
	private final static String FEED_BACK_URL = "";
	
	@Override
	public int bindLayout() {
		return R.layout.activity_feedback;
	}

	@Override
	public void initView(View view) {
		et_message = (EditText)findViewById(R.id.et_message);
		
		//初始化返回按钮
		String strCenterTitle = getResources().getString(R.string.FeedbackActivity);
		ActionBarManager.initBackTitle(getContext(), getActionBar(), strCenterTitle);
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//渲染菜单
		getMenuInflater().inflate(R.menu.actionbar_menu, menu);  
		
		//根据业务需要订制菜单
		ActionBarManager.initActionBarSubmitButton(menu);
		 
	     return true;  
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {  
        case R.id.action_settings:  
            Toast.makeText(this, "action_settings Menu Item refresh selected",  
                    Toast.LENGTH_SHORT).show();  
            break;  
        case R.id.action_fav:  
            Toast.makeText(this, "action_fav Menu Item about selected", Toast.LENGTH_SHORT)  
                    .show();  
            break;  
        case R.id.action_about:  
            Toast.makeText(this, " action_about Menu Item edit selected", Toast.LENGTH_SHORT)  
                    .show();  
            break;  
        case R.id.action_search:  
            Toast.makeText(this, " action_search Menu Item search selected",  
                    Toast.LENGTH_SHORT).show();  
        case R.id.action_submit:  
        	//提交表单
			if(validateForm()){
				//需要设置表单元素控件的tag属性，作为表单提交的key
//				DTO<String,Object> form = ToolData.gainForm(root, new DTO<String,Object>());
//				ToolHTTP.post(FEED_BACK_URL, form, getFeedBackHandler());
			}
            break;  
        default:  
            break;  
        }  
		
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * 表单验证
	 * @return
	 */
	private boolean validateForm(){
		String strMessage = et_message.getText().toString();
		if(ToolString.isNoBlankAndNoNull(strMessage)){
			return true;
		}else{
			ToolAlert.toastShort(getContext(), "请输入意见内容再提交，谢谢^_^");
			return false;
		}
	}
	
	/*
	 * 提交意见反馈Handler
	 */
	private JsonHttpResponseHandler getFeedBackHandler(){
		return new JsonHttpResponseHandler(){

			@Override
			public void onFailure(int statusCode, Header[] headers,String responseString, Throwable throwable) {
				ToolAlert.toastShort(getContext(), "反馈意见失败，原因："+throwable.getMessage());
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,JSONObject response) {
				ToolAlert.toastShort(getContext(), "感谢您的意见!");
				finish();
			}
		};
	}
}
