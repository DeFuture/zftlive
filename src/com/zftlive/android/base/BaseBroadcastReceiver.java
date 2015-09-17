package com.zftlive.android.base;

import android.content.BroadcastReceiver;

/**
 *  android 系统中的四大组件之一BroadcastReceiver基类<生命周期只有十秒左右，耗时操作需开service来做><br>
 * @author 曾繁添
 * @version 1.0
 *
 */
public abstract class BaseBroadcastReceiver extends BroadcastReceiver {

	/**日志输出标志**/
	protected final String TAG = this.getClass().getSimpleName();
}
