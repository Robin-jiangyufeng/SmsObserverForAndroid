package com.robin.lazy.sms;

import android.app.Activity;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * 短信接收观察者
 * 
 * @author 江钰锋 0152
 * @version [版本号, 2015年9月17日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SmsObserver extends ContentObserver {

	private Context mContext;

	public static final int MSG_RECEIVED_CODE = 1001;
	private SmsHandler mHandler;
	
	public SmsObserver(Activity context,SmsResponseCallback callback) {
		this(new SmsHandler(callback));
		this.mContext=context;
	}
	
	public SmsObserver(SmsHandler handler) {
		super(handler);
		this.mHandler = handler;
	}

	public void match(){

	}
	
	/***
	 * 注册短信变化观察者
	 * 
	 * @see [类、类#方法、类#成员]
	 */
	public void registerSMSObserver(){
		Uri uri = Uri.parse("content://sms");
		if (mContext != null) {
			mContext.getContentResolver().registerContentObserver(uri,
					true, this);
		}
	}
	
	/***
	 * 注销短信变化观察者
	 * 
	 * @see [类、类#方法、类#成员]
	 */
	public void unregisterSMSObserver(){
		if (mContext != null) {
			mContext.getContentResolver().unregisterContentObserver(this);;
		}
		if(mHandler!=null){
			mHandler = null;
		}
	}

	@Override
	public void onChange(boolean selfChange, Uri uri) {
		super.onChange(selfChange, uri);
		String code = "";
		if (uri.toString().equals("content://sms/raw")) {
			return;
		}
		Uri inboxUri = Uri.parse("content://sms/inbox");
		try {
			Cursor c = mContext.getContentResolver().query(inboxUri, null, null,
					null, "date desc");
			if (c != null) {
				if (c.moveToFirst()) {
					String address = c.getString(c.getColumnIndex("address"));
					String body = c.getString(c.getColumnIndex("body"));
					if (!address.startsWith("1069")) {
						return;
					}
					Log.i(getClass().getName(),"发件人为：" + address + " " + "短信内容为：" + body);
					Pattern pattern = Pattern.compile("(\\d{4,8})");//匹配4-8位的数字
					Matcher matcher = pattern.matcher(body);
					if (matcher.find()&&mHandler!=null) {
						code = matcher.group(0);
						Log.e("DEBUG", "code is " + code);
						mHandler.obtainMessage(MSG_RECEIVED_CODE, code)
								.sendToTarget();
					}
				}
				c.close();
			}
		} catch (SecurityException e) {
			Log.e(getClass().getName(),"获取短信权限失败",e);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
