package com.hammerbyte.sahas.services.api;

public interface ServiceOTPVerify extends ServiceAPI{

    void onOTPVerifySuccess();

    void onOTPVerifyFailed();

    void onOTPVerifyCanceled();

    void StartOTPVerification(String otp);

    void runOnUiThread(Runnable runnable);
}
