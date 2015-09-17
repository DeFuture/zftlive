package com.zftlive.android.base;

import android.content.ContentProvider;

/**
 * android 系统中的四大组件之一ContentProvider基类
 * @author 曾繁添
 * @version 1.0
 *
 */
public abstract class BaseContentProvider extends ContentProvider {

	/**日志输出标志**/
	protected final String TAG = this.getClass().getSimpleName();
}
