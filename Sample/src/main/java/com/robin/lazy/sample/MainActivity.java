package com.robin.lazy.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.robin.lazy.sms.CerificationCodeSmsFilter;
import com.robin.lazy.sms.SmsObserver;
import com.robin.lazy.sms.SmsResponseCallback;

public class MainActivity extends AppCompatActivity implements SmsResponseCallback{

    private TextView textView;
    private SmsObserver smsObserver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        smsObserver=new SmsObserver(this,this,new CerificationCodeSmsFilter("180"));
        smsObserver.registerSMSObserver();
        textView=(TextView)findViewById(R.id.textView);
    }

    @Override
    public void onCallbackSmsContent(String code) {
        textView.setText("短信验证码:"+code);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        smsObserver.unregisterSMSObserver();
    }
}
