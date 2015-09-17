
package com.zftlive.android.tools;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.zftlive.android.MApplication;
import com.zftlive.android.model.NotificationMessage;
import com.zftlive.android.view.ProgressDialog;

/**
 * 对话框工具类
 * @author 曾繁添
 * @version 1.0
 */
public class ToolAlert {

	private static ProgressDialog mProgressDialog;
	
	/**
	 * 显示ProgressDialog
	 * @param context 上下文
	 * @param message 消息
	 */
	public static void loading(Context context, String message){
		
		loading(context,message,true);
	}
	
	/**
	 * 显示ProgressDialog
	 * @param context 上下文
	 * @param message 消息
	 */
	public static void loading(Context context, String message,final ILoadingOnKeyListener listener){
		
		loading(context,message,true,listener);
	}
	
	/**
	 * 显示ProgressDialog
	 * @param context 上下文
	 * @param message 消息
	 * @param cancelable 是否可以取消
	 */
	public static void loading(Context context, String message,boolean cancelable){
		
		if (mProgressDialog == null) {
			mProgressDialog = new ProgressDialog(context,message);
			mProgressDialog.setCancelable(cancelable);
		}
		if(mProgressDialog.isShowing()){mProgressDialog.cancel();mProgressDialog.dismiss();}
		mProgressDialog.show();
	}
	
	/**
	 * 显示ProgressDialog
	 * @param context 上下文
	 * @param message 消息
	 */
	public static void loading(Context context, String message,boolean cancelable ,final ILoadingOnKeyListener listener){
		
		if(mProgressDialog == null){
			mProgressDialog = new ProgressDialog(context,message);
			mProgressDialog.setCancelable(cancelable);
		}
		
		if(mProgressDialog.isShowing()){mProgressDialog.cancel();mProgressDialog.dismiss();}
		
		if(null != listener)
		{
			mProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
	            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
	            	listener.onKey(dialog, keyCode, event);
	                return false;
	            }
	        });
		}else{
			mProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
	            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
	                if (keyCode == KeyEvent.KEYCODE_BACK) {
	                	mProgressDialog.dismiss();
	                }
	                return false;
	            }
	        });
		}
		
		mProgressDialog.show();
	}
	
	/**
	 * 判断加载对话框是否正在加载
	 * @return 是否
	 */
	public static boolean isLoading(){
		
		if(null != mProgressDialog){
			return mProgressDialog.isShowing();
		}else{
			return false;
		}
	}
	
	/**
	 * 关闭ProgressDialog
	 */
	public static void closeLoading(){
		if(mProgressDialog != null){
			mProgressDialog.dismiss();
			mProgressDialog = null;
		}
	}
	
	/**
	 * 更新ProgressDialog进度消息
	 * @param message 消息
	 */
	public static void updateProgressText(String message){
		if(mProgressDialog == null ) return ;
		
		if(mProgressDialog.isShowing()){
			mProgressDialog.setMessage(message);
		}
	}
	
    /**
     * 弹出对话框
     * @param title 对话框标题
     * @param msg  对话框内容
     * @param okBtnListenner 确定按钮点击事件
     * @param cancelBtnListenner 取消按钮点击事件
     */
    public static AlertDialog dialog(Context context,String msg) {
    	return dialog(context,"",msg);
    }
    
    /**
     * 弹出对话框
     * @param title 对话框标题
     * @param msg  对话框内容
     * @param okBtnListenner 确定按钮点击事件
     * @param cancelBtnListenner 取消按钮点击事件
     */
    public static AlertDialog dialog(Context context,String title,String msg) {
    	return dialog(context,title,msg,null);
    }
    
    /**
     * 弹出对话框
     * @param title 对话框标题
     * @param msg  对话框内容
     * @param okBtnListenner 确定按钮点击事件
     * @param cancelBtnListenner 取消按钮点击事件
     */
    public static AlertDialog dialog(Context context,String title,String msg,OnClickListener okBtnListenner) {
    	return dialog(context,title,msg,okBtnListenner,null);
    }
    
    /**
     * 弹出对话框
     * @param title 对话框标题
     * @param msg  对话框内容
     * @param okBtnListenner 确定按钮点击事件
     * @param cancelBtnListenner 取消按钮点击事件
     */
    public static AlertDialog dialog(Context context,String title,String msg,OnClickListener okBtnListenner,OnClickListener cancelBtnListenner) {
    	return dialog(context,null,title,msg,okBtnListenner,cancelBtnListenner);
    }
    
    /**
     * 弹出对话框
     * @param title 对话框标题
     * @param msg  对话框内容
     * @param okBtnListenner 确定按钮点击事件
     * @param cancelBtnListenner 取消按钮点击事件
     */
    public static AlertDialog dialog(Context context,Drawable icon,String title,String msg) {
    	return dialog(context,icon,title,msg,null);
    }
    
    /**
     * 弹出对话框
     * @param title 对话框标题
     * @param msg  对话框内容
     * @param okBtnListenner 确定按钮点击事件
     * @param cancelBtnListenner 取消按钮点击事件
     */
    public static AlertDialog dialog(Context context,Drawable icon,String title,String msg,OnClickListener okBtnListenner) {
    	return dialog(context,icon,title,msg,okBtnListenner,null);
    }
	
    /**
     * 弹出对话框
     * @param icon  标题图标
     * @param title 对话框标题
     * @param msg  对话框内容
     * @param okBtnListenner 确定按钮点击事件
     * @param cancelBtnListenner 取消按钮点击事件
     */
    public static AlertDialog dialog(Context context,Drawable icon,String title,String msg, OnClickListener okBtnListenner,OnClickListener cancelBtnListenner) {
        Builder dialogBuilder = new AlertDialog.Builder(context);
        if(null != icon){
        	dialogBuilder.setIcon(icon);
        }
        if(ToolString.isNoBlankAndNoNull(title)){
            dialogBuilder.setTitle(title);
        }
        dialogBuilder.setMessage(msg);
        if(null != okBtnListenner){
        	dialogBuilder.setPositiveButton(android.R.string.ok, okBtnListenner);
        }
        if(null != cancelBtnListenner){
        	dialogBuilder.setNegativeButton(android.R.string.cancel, cancelBtnListenner);
        }
        dialogBuilder.create();
        return dialogBuilder.show();
    }
    
    /**
     * 弹出一个自定义布局对话框
     * @param context 上下文
     * @param view 自定义布局View
     */
	public static AlertDialog dialog(Context context,View view) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setView(view);
		return builder.show();
	}
	
    /**
     * 弹出一个自定义布局对话框
     * @param context 上下文
     * @param resId 自定义布局View对应的layout id
     */
	public static AlertDialog dialog(Context context,int resId) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(resId, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setView(view);
		return builder.show();
	}
    
    /**
     * 弹出较短的Toast消息
     * @param msg 消息内容
     */
    public static void toastShort(String msg) {
        Toast.makeText(MApplication.gainContext(), msg, Toast.LENGTH_SHORT).show();
    }
    
    /**
     * 弹出较短的Toast消息
     * @param msg 消息内容
     */
    public static void toastShort(Context context,String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 弹出较长的Toast消息
     * @param msg 消息内容
     */
    public static void toastLong(String msg) {
        Toast.makeText(MApplication.gainContext(), msg, Toast.LENGTH_LONG).show();
    }
    
    /**
     * 弹出较长的Toast消息
     * @param msg 消息内容
     */
    public static void toastLong(Context context,String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
    
    /**
     * 弹出自定义时长的Toast消息
     * @param msg 消息内容
     */
    public static void toast(String msg,int duration) {
        Toast.makeText(MApplication.gainContext(), msg, duration).show();
    }
    
    /**
     * 弹出自定义时长的Toast消息
     * @param msg 消息内容
     */
    public static void toast(Context context,String msg,int duration) {
        Toast.makeText(context, msg, duration).show();
    }
    
    /**
     * 弹出Pop窗口
     * @param context 依赖界面上下文
     * @param anchor 触发pop界面的控件
     * @param viewId pop窗口界面layout
     * @param xoff 窗口X偏移量
     * @param yoff 窗口Y偏移量
     */
    public static PopupWindow popwindow(Context context,View anchor,int viewId,int xoff,int yoff){
        ViewGroup menuView = (ViewGroup) LayoutInflater.from(context).inflate(viewId, null);
        PopupWindow pw = new PopupWindow(menuView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,true);
        pw.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pw.setTouchable(true);
        pw.setFocusable(true);
        pw.setOutsideTouchable(true);
        pw.showAsDropDown(anchor, xoff, yoff);
        pw.update();
        return pw;
    }
    
    /**
     * 弹出Pop窗口
     * @param anchor 触发pop界面的控件
     * @param popView pop窗口界面
     * @param xoff 窗口X偏移量
     * @param yoff 窗口Y偏移量
     */
    public static PopupWindow popwindow(View anchor,View popView,int xoff,int yoff){
        PopupWindow pw = new PopupWindow(popView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,true);
        pw.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pw.setOutsideTouchable(true);
        pw.showAsDropDown(anchor, xoff, yoff);
        pw.update();
        return pw;
    }
    
    /**
     * 弹出Pop窗口（可设置是否点击其他地方关闭窗口）
     * @param context 依赖界面上下文
     * @param anchor 触发pop界面的控件
     * @param viewId pop窗口界面layout
     * @param xoff 窗口X偏移量
     * @param yoff 窗口Y偏移量
     * @param outSideTouchable 点击其他地方是否关闭窗口
     */
    public static PopupWindow popwindow(Context context,View anchor,int viewId,int xoff,int yoff,boolean outSideTouchable){
        ViewGroup menuView = (ViewGroup) LayoutInflater.from(context).inflate(viewId, null);
        PopupWindow pw = new PopupWindow(menuView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,true);
        pw.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pw.setTouchable(outSideTouchable);
        pw.setFocusable(outSideTouchable);
        pw.setOutsideTouchable(outSideTouchable);
        pw.showAsDropDown(anchor, xoff, yoff);
        pw.update();
        return pw;
    }
    
    /**
     * 弹出Pop窗口（可设置是否点击其他地方关闭窗口）
     * @param anchor 触发pop界面的控件
     * @param popView pop窗口界面
     * @param xoff 窗口X偏移量
     * @param yoff 窗口Y偏移量
     * @param outSideTouchable 点击其他地方是否关闭窗口
     */
    public static PopupWindow popwindow(View anchor,View popView,int xoff,int yoff,boolean outSideTouchable){
        PopupWindow pw = new PopupWindow(popView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,true);
        pw.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pw.setOutsideTouchable(outSideTouchable);
        pw.showAsDropDown(anchor, xoff, yoff);
        pw.update();
        
        return pw;
    }
    
    /**
     * 指定坐标弹出Pop窗口
     * @param pw pop窗口对象
     * @param anchor 触发pop界面的控件
     * @param popView pop窗口界面
     * @param x 窗口X
     * @param y 窗口Y
     * @param outSideTouchable 点击其他地方是否关闭窗口
     */
    public static PopupWindow popwindowLoction(PopupWindow pw,View anchor,View popView,int x,int y){
    	if(pw == null){
    		pw = new PopupWindow(popView,LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,true);
    		pw.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    		pw.setOutsideTouchable(false);
    	}
    	
    	if (pw.isShowing()) {
    		pw.update(x,y,LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		} else {
			pw.showAtLocation(anchor,Gravity.NO_GRAVITY, x,y);
		}	
        
        return pw;
    }
    
    /**
     * 往状态栏发送一条通知消息
     * @param mContext 上下文
     * @param message 消息Bean
     */
    public static void notification(Context mContext,NotificationMessage message){
        
    	//消息管理器
    	NotificationManager mNoticeManager = (NotificationManager)mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        //构造Notification
        Notification notice = new Notification();
        notice.icon = message.getIconResId();
        notice.tickerText = message.getStatusBarText();
        notice.when = System.currentTimeMillis();
        /**
         *  notification.defaults = Notification.DEFAULT_SOUND; // 调用系统自带声音
			notification.defaults = Notification.DEFAULT_VIBRATE;// 设置默认震动 
			notification.defaults = Notification.DEFAULT_ALL; // 设置铃声震动 
			notification.defaults = Notification.DEFAULT_ALL; // 把所有的属性设置成默认
         */
        notice.defaults = Notification.DEFAULT_SOUND;//通知时发出的默认声音
        /**
         *  notification.flags = Notification.FLAG_NO_CLEAR; // 点击清除按钮时就会清除消息通知,但是点击通知栏的通知时不会消失  
			notification.flags = Notification.FLAG_ONGOING_EVENT; // 点击清除按钮不会清除消息通知,可以用来表示在正在运行  
			notification.flags |= Notification.FLAG_AUTO_CANCEL; // 点击清除按钮或点击通知后会自动消失  
			notification.flags |= Notification.FLAG_INSISTENT; // 一直进行，比如音乐一直播放，知道用户响应
         */
        notice.flags |= Notification.FLAG_AUTO_CANCEL; //通知点击清除
        
        //设置通知显示的参数 
        Intent mIntent = new Intent(mContext, message.getForwardComponent());
        mIntent.setAction(ToolString.gainUUID());
        mIntent.putExtras(message.getExtras());
        mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        //自动更新PendingIntent的Extra数据
        PendingIntent pIntent = PendingIntent.getActivity(mContext,0,mIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        notice.setLatestEventInfo(mContext, message.getMsgTitle(), message.getMsgContent(),pIntent);
        
        //发送通知
        mNoticeManager.notify(message.getId(), notice);
    }
    
    
    /**
     * 发送自定义布局通知消息
     * @param mContext 上下文
     * @param message  消息Bean
     */
    public static void notificationCustomView(Context mContext,NotificationMessage message){
    	
    	//消息管理器
    	NotificationManager mNoticeManager = (NotificationManager)mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        //构造Notification
    	Notification mNotify = new Notification();  
        mNotify.icon = message.getIconResId();  
        mNotify.tickerText = message.getStatusBarText();  
        mNotify.when = System.currentTimeMillis();  
        mNotify.flags |= Notification.FLAG_AUTO_CANCEL; //通知点击清除
        mNotify.contentView = message.getmRemoteViews();
        
        //设置通知显示的参数 
        Intent mIntent = new Intent(mContext, message.getForwardComponent());
        mIntent.setAction(ToolString.gainUUID());
        mIntent.putExtras(message.getExtras());
        mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(mContext,0,mIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        mNotify.contentIntent = contentIntent;
        
        //发送通知
        mNoticeManager.notify(message.getId(), mNotify);  
    }
    
    /**
     * Loading监听对话框
     */
    public interface ILoadingOnKeyListener{
    	 public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event);
    }
}
