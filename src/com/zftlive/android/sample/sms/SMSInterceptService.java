package com.zftlive.android.sample.sms;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsMessage;

import com.zftlive.android.base.BaseBroadcastReceiver;
import com.zftlive.android.base.BaseService;
import com.zftlive.android.tools.ToolAlert;

public class SMSInterceptService extends BaseService {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		IntentFilter filter = new IntentFilter();
		filter.addAction("android.provider.Telephony.SMS_RECEIVED");
		filter.setPriority(Integer.MAX_VALUE-1);
		registerReceiver(new SmsReceiver(), filter);
	}

	/**
	 * 订阅短信广播
	 * 
	 */
	protected class SmsReceiver extends BaseBroadcastReceiver 
	{

		@Override
		public void onReceive(Context context, Intent intent) 
		{
			Bundle bundle = intent.getExtras();
			if (bundle != null) 
			{
				Object[] pdusObjects = (Object[]) bundle.get("pdus");
				SmsMessage[] messages = new SmsMessage[pdusObjects.length];
				for (int i = 0; i < pdusObjects.length; i++) {
					messages[i] = SmsMessage.createFromPdu((byte[]) pdusObjects[i]);
				}

				for (SmsMessage message : messages) {
					System.out.println("SMSInterceptService-->来信号码："+ message.getDisplayOriginatingAddress()+ "\n短信内容：" + message.getDisplayMessageBody());
					ToolAlert.toastLong(context,"SMSInterceptService.SmsReceiver-->拦截到来自【" + message.getDisplayOriginatingAddress()+ "】的短信-->"+ message.getDisplayMessageBody());
				}
			}
		}

	}

}
