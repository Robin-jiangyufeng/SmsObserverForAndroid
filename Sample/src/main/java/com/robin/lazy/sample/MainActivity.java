package com.robin.lazy.sample;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.robin.lazy.sms.SmsObserver;
import com.robin.lazy.sms.SmsResponseCallback;
import com.robin.lazy.sms.VerificationCodeSmsFilter;

public class MainActivity extends AppCompatActivity implements SmsResponseCallback{

    private TextView textView;
    private SmsObserver smsObserver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        smsObserver=new SmsObserver(this,this,new VerificationCodeSmsFilter("180"));
        smsObserver.registerSMSObserver();
        textView=(TextView)findViewById(R.id.textView);
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.READ_SMS)
                .withListener(new PermissionListener() {
                    @Override public void onPermissionGranted(PermissionGrantedResponse response) {/* ... */}
                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {/* ... */}
                    @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {/* ... */}
                }).check();
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
