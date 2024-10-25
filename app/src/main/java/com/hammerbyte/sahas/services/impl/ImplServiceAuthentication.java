package com.hammerbyte.sahas.services.impl;

import com.hammerbyte.sahas.R;
import com.hammerbyte.sahas.activities.ActivityAccount;

import com.hammerbyte.sahas.activities.ActivitySplash;
import com.hammerbyte.sahas.services.api.ServiecAuthentication;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;

public class ImplServiceAuthentication implements ServiecAuthentication {

    private final WeakReference<ActivityAccount> parentActivity;

    public ImplServiceAuthentication(WeakReference<ActivityAccount> parentActivity) {
        this.parentActivity = parentActivity;
    }


    @Override
    public void onAuthenticationSuccess() {

    }

    @Override
    public void onAuthenticationFailed() {

    }

    @Override
    public void onAuthenticationCanceled() {

    }

    @Override
    public void StartAuthentication(String email) {
      HitAPI(new WeakReference<>(parentActivity.get().getAppInstance()),"Auth.php",null);
    }

    @Override
    public void onUserNotFound() {

    }

    @Override
    public void onAuthenticationProgress(int progress) {

    }

    @Override
    public void runOnUiThread(Runnable runnable) {

    }

    @Override
    public void onAPIRequestStart() {

    }

    @Override
    public void onAPIResponseReceived(HashMap<String, Object> apiResponse) {

        try {
            JSONObject responseBody = (JSONObject) apiResponse.get(parentActivity.get().getString(R.string.RESPONSE_BODY));
            if (responseBody != null && responseBody.has("OTP") ) {
                String otp = responseBody.getString("OTP");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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
