package com.hammerbyte.sahas.services.impl;

import com.hammerbyte.sahas.R;
import com.hammerbyte.sahas.activities.ActivitySplash;
import com.hammerbyte.sahas.services.api.ServiceUsageData;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;

public class ImplServiceUsageData implements ServiceUsageData {

    private final WeakReference<ActivitySplash> parentActivity;

    public ImplServiceUsageData(WeakReference<ActivitySplash> parentActivity) {
        this.parentActivity = parentActivity;
    }

    @Override
    public void submitUsageData(JSONObject usageData) {
        HitAPI(new WeakReference<>(parentActivity.get().getAppInstance()), "usage_data.php", usageData);


    }

    @Override
    public void onUsageDataSubmitted() {
        parentActivity.get().getAppInstance().clearUsageData();
    }


    @Override
    public void onAPIRequestStart() {

    }



    @Override
    public void onAPIResponseReceived(HashMap<String, Object> apiResponse) {

        try {
            JSONObject responseBody = (JSONObject) apiResponse.get(parentActivity.get().getString(R.string.RESPONSE_BODY));
            if (responseBody != null && responseBody.has("clear_usage_data") && responseBody.getBoolean("clear_usage_data")) {
                onUsageDataSubmitted();
            }
        } catch (JSONException e) {
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
