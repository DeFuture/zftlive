package com.zftlive.android.sample.soap;

import java.util.HashMap;

import org.ksoap2.serialization.SoapObject;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zftlive.android.R;
import com.zftlive.android.base.BaseActivity;
import com.zftlive.android.common.ActionBarManager;
import com.zftlive.android.tools.ToolAlert;
import com.zftlive.android.tools.ToolSOAP;

/**
 * 调用WebService接口获取天气信息Activity
 * @author 曾繁添
 * @version 1.0
 *
 */
public class WeatherActivity extends BaseActivity{

	private TextView mTextWeather;

	@Override
	public int bindLayout() {
		return R.layout.activity_soap_weather;
	}

	@Override
	public void initView(View view) {
		mTextWeather = (TextView) findViewById(R.id.weather);
		//初始化带返回按钮的标题栏
		ActionBarManager.initBackTitle(getContext(), getActionBar(), "天气信息");
	}

	@Override
	public void doBusiness(final Context mContext) {
		//等待对话框
		ToolAlert.loading(mContext, "数据加载中...");
		
		HashMap<String, String> properties = new HashMap<String, String>();
		properties.put("theCityName", String.valueOf(getOperation().getParameters("city")));
		
		ToolSOAP.callService(ProviceActivity.WEB_SERVER_URL,ProviceActivity.NAME_SPACE,"getWeatherbyCityName", properties, new ToolSOAP.WebServiceCallBack() {
			
			@Override
			public void onSucced(SoapObject result) {
				//关闭等待对话框
				ToolAlert.closeLoading();
				if(result != null){
					SoapObject detail = (SoapObject) result.getProperty("getWeatherbyCityNameResult");
					StringBuilder sb = new StringBuilder();
					for(int i=0; i<detail.getPropertyCount(); i++){
						sb.append(detail.getProperty(i)).append("\r\n");
					}
					mTextWeather.setText(sb.toString());
				}else{
					ToolAlert.toastShort(mContext, "呼叫WebService-->getWeatherbyCityName失败");
				}
			}

			@Override
			public void onFailure(String result) {
				//关闭等待对话框
				ToolAlert.closeLoading();
				
				ToolAlert.toastShort(mContext, "呼叫WebService-->getWeatherbyCityName失败，原因："+result);
			}
		});
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void destroy() {
		
	}
}
