package com.robin.lazy.sms;

import android.os.Handler;
import android.os.Message;

/**
 * 短信处理
 * 
 * @author  江钰锋 0152
 * @version  [版本号, 2015年9月17日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SmsHandler extends Handler {
	
	private SmsResponseCallback mCallback;
	
	public SmsHandler(SmsResponseCallback callback) {
		this.mCallback=callback;
	}
	
	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		if (msg.what == SmsObserver.MSG_RECEIVED_CODE) {
			String code = (String) msg.obj;
			mCallback.onCallbackSmsContent(code);
		}
	}
}
