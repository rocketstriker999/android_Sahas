package com.hammerbyte.sahas.services.impl;


import com.hammerbyte.sahas.activities.ActivityOTPVerify;
import com.hammerbyte.sahas.services.api.ServiceOTPVerify;

import java.lang.ref.WeakReference;
import java.util.HashMap;

public class ImplServiceOTPVerify implements ServiceOTPVerify {

    private final WeakReference<ActivityOTPVerify> parentActivity;

    public ImplServiceOTPVerify(WeakReference<ActivityOTPVerify> parentActivity) {
        this.parentActivity = parentActivity;
    }
    @Override
    public void onOTPVerifySuccess() {

    }

    @Override
    public void onOTPVerifyFailed() {

    }

    @Override
    public void onOTPVerifyCanceled() {

    }

    @Override
    public void StartOTPVerification(String otp) {
    HitAPI(new WeakReference<>());
    }

    @Override
    public void runOnUiThread(Runnable runnable) {

    }

    @Override
    public void onAPIRequestStart() {

    }

    @Override
    public void onAPIResponseReceived(HashMap<String, Object> apiResponse) {

    }

    @Override
    public void onNoNetworkAvailable() {

    }

    @Override
    public void onAPIResponseNotReceived(String endPont) {

    }

    @Override
    public void onAPIResponseParseError() {

    }

    @Override
    public void onAPIRequestEnd() {

    }
}
