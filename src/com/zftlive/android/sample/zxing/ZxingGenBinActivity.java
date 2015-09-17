package com.zftlive.android.sample.zxing;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.zftlive.android.R;
import com.zftlive.android.base.BaseActivity;
import com.zftlive.android.common.ActionBarManager;
import com.zftlive.android.tools.ToolAlert;
import com.zftlive.android.tools.ToolFile;
import com.zftlive.android.tools.ToolPicture;
import com.zftlive.android.tools.ToolString;

/**
 * 生成二维码示例
 * @author 曾繁添
 * @version 1.0
 *
 */
public class ZxingGenBinActivity extends BaseActivity {

	private EditText et_qr_text;
	private Button btn_make_qr;
	private Button btn_make_bar;
	private ImageView qr_image,validate_image;
	private Bitmap qrImage,validateCodeImage;

	@Override
	public int bindLayout() {
		return R.layout.activity_gen_qr_image;
	}

	@Override
	public void initView(View view) {
		et_qr_text = (EditText)findViewById(R.id.et_qr_text);
		btn_make_qr = (Button)findViewById(R.id.btn_make_qr);
		btn_make_bar = (Button)findViewById(R.id.btn_make_bar);
		qr_image = (ImageView)findViewById(R.id.qr_image);
		validate_image = (ImageView)findViewById(R.id.validate_image);
		
		//初始化带返回按钮的标题栏
		String strCenterTitle = getResources().getString(R.string.ZxingGenBinActivity);
		ActionBarManager.initBackTitle(getContext(), getActionBar(), strCenterTitle);
	}

	@Override
	public void doBusiness(Context mContext) {
		//初始化值
		if("".equals(et_qr_text.getText().toString())){
			et_qr_text.setText("https://itunes.apple.com/us/app/zhong-guo-zhai-quan-xin-xi-wang/id956379885?l=zh&ls=1&mt=8");
		}
		
		btn_make_qr.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				try {
					if("".equals(et_qr_text.getText().toString())){
						ToolAlert.toastShort("请输入要生成二维码内容！");
						return ;
					}
					
					getOperation().showLoading("正在生成...");
					
					//回收bitmap
					if(null != qrImage && !qrImage.isRecycled()){
						qrImage.recycle();
						qrImage = null;
					}
					
				    qrImage = ToolPicture.makeQRImage(et_qr_text.getText().toString(), 400, 400);
					qr_image.setImageBitmap(qrImage);
					
					//生成图片
					String filePath = ToolFile.gainSDCardPath() + "/MyLive/QRImage/"+ToolString.gainUUID()+".jpg";
					ToolFile.saveAsJPEG(qrImage, filePath);
					
					getOperation().closeLoading();
					
					ToolAlert.toastShort("二维码已经保存在："+filePath);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});	
		
		btn_make_bar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				try {
					
					getOperation().showLoading("正在生成...");
					
					//回收bitmap
					if(null != validateCodeImage && !validateCodeImage.isRecycled()){
						validateCodeImage.recycle();
						validateCodeImage = null;
					}
					
					//生成图片
				    validateCodeImage = ToolPicture.makeValidateCode(200, 30);
				    validate_image.setImageBitmap(validateCodeImage);
					
					ToolAlert.toastShort("验证码值："+ToolPicture.gainValidateCodeValue());
					
					getOperation().closeLoading();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});	
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void destroy() {
		//回收bitmap
		if(null != qrImage && !qrImage.isRecycled()){
			qrImage.recycle();
			qrImage = null;
		}
		
		if(null != validateCodeImage && !validateCodeImage.isRecycled()){
			validateCodeImage.recycle();
			validateCodeImage = null;
		}
		
		System.gc();
	}
}
