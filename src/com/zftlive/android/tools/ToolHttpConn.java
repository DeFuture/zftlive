package com.zftlive.android.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.util.Log;

/**
 * HttpURLConnection工具类
 * @author 曾繁添
 * @version 1.0
 *
 */
public class ToolHttpConn {

	// 含有3个线程的线程池
	private static final ExecutorService executorService = Executors.newFixedThreadPool(3);
	private final static String TAG = "ToolHttpConnection";
	
	public static Map<String,Object> download(String url,String savePath) { 
		Map<String,Object> result = new HashMap<String, Object>();
		HttpURLConnection connection = null;
		try {
			 connection = getConnection(url);
			String contentDisposition = getHttpHeader(connection).get("Content-Disposition");
			String displayNmae = gainFileName(contentDisposition);
			String ext = displayNmae.substring(displayNmae.lastIndexOf("."), displayNmae.length());
			File file = writeFile(connection.getInputStream(), savePath+ToolString.gainUUID()+ext);
			result.put("file", file);
			result.put("displayNmae", displayNmae);
			return result;
		} catch (Exception e) {
			Log.e(TAG, "下载文件失败，原因："+e.getMessage());
		}finally{
			if(null != connection){
				connection.disconnect();
			}
		}
		return null;
	}

	/**
	 * 获取文件名称
	 * @param contentDisposition 报文头信息
	 * @return
	 */
	private static String gainFileName(String contentDisposition){
		String filename = "";
		try {
			filename = new String(contentDisposition.getBytes("ISO-8859-1"),"UTF-8");
		} catch (Exception e) {
			Log.e(TAG, "获取文件名称失败，原因："+e.getMessage());
		}
        int startIndex = filename.lastIndexOf("filename=")+9;
        filename = filename.substring(startIndex, filename.length());
        
        return filename;
	}
	
	/**
	 * 写入文件
	 * 
	 * @param inputStream下载文件的字节流对象
	 * @param filePath文件的存放路径
	 */
	public static File writeFile(InputStream inputStream, String filePath) {
		OutputStream outputStream = null;
		// 在指定目录创建一个空文件并获取文件对象
		File fileDir = new File(filePath);
		if (!fileDir.getParentFile().exists())
			fileDir.getParentFile().mkdirs();
		try {
			outputStream = new FileOutputStream(fileDir);
			byte buffer[] = new byte[4 * 1024];
			int lenght = 0 ;
			while ((lenght = inputStream.read(buffer)) > 0) {
				outputStream.write(buffer,0,lenght);
			}
			outputStream.flush();
			return fileDir;
		} catch (Exception e) {
			Log.e(TAG, "写入文件失败，原因："+e.getMessage());
		}finally{
			try {
				inputStream.close();
				outputStream.close();
			} catch (IOException e) {
			}
		}
		return null;
	}

	/**
	 * 获取连接
	 * 
	 * @param strURL
	 *            网址
	 * @return
	 * @throws IOException
	 */
	public static HttpURLConnection getConnection(String strURL)
			throws IOException {
		URL url = new URL(strURL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		configConnection(conn, strURL);
		return conn;
	}

	/**
	 * 获取Header信息，可以获取文件名称、文件长度等等信息
	 * 
	 * @param conn
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static Map<String, String> getHttpHeader(HttpURLConnection conn) {
		Map<String, String> header = new LinkedHashMap<String, String>();
		for (int i = 0;; i++) {
			String mine = conn.getHeaderField(i);
			if (mine == null)
				break;
			header.put(conn.getHeaderFieldKey(i), mine);
		}
		return header;
	}

	/**
	 * 配置连接
	 * 
	 * @param conn
	 *            链接
	 * @param strURL
	 *            网址
	 * @return
	 * @throws ProtocolException
	 *             不支持的请求方式
	 */
	private static void configConnection(HttpURLConnection conn, String strURL)
			throws ProtocolException {
		// 设置 HttpURLConnection超时时间
		conn.setConnectTimeout(60 * 1000);
		// 设置 HttpURLConnection的请求方式
		conn.setRequestMethod("POST");
		// 设置 HttpURLConnection的接收的文件类型
		conn.setRequestProperty(
				"Accept",
				"image/gif, image/jpeg, image/pjpeg, image/pjpeg, "
						+ "application/x-shockwave-flash, application/xaml+xml, "
						+ "application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, "
						+ "application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
		// 设置 HttpURLConnection的接收语言
		conn.setRequestProperty("Accept-Language", Locale.getDefault()
				.toString());
		// 指定请求uri的源资源地址
		conn.setRequestProperty("Referer", strURL);
		// 设置 HttpURLConnection的字符编码
		conn.setRequestProperty("Accept-Charset", "UTF-8");
		// 检查浏览页面的访问者在用什么操作系统（包括版本号）浏览器（包括版本号）和用户个人偏好
		conn.setRequestProperty(
				"User-Agent",
				"Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.101 Safari/537.36");
		// 保持持久链接
		conn.setRequestProperty("Connection", "Keep-Alive");
	}

	/**
	 * 回调接口
	 */
	public interface HttpResponseHandler {

		public void onSucced(InputStream result);

		public void onFailure(String result);
	}
}
