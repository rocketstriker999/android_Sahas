package com.hammerbyte.sahas.services.impl;

import android.content.Intent;

import com.hammerbyte.sahas.R;
import com.hammerbyte.sahas.activities.ActivityError;
import com.hammerbyte.sahas.activities.ActivitySplash;
import com.hammerbyte.sahas.activities.ActivityUpdate;
import com.hammerbyte.sahas.services.api.ServiceMaintenance;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;

public class ImplServiceMaintenance implements ServiceMaintenance {

    private final WeakReference<ActivitySplash> parentActivity;

    public ImplServiceMaintenance(WeakReference<ActivitySplash> parentActivity) {
        this.parentActivity = parentActivity;
    }

    @Override
    public void checkMaintenance() {
        HitAPI(new WeakReference<>(parentActivity.get().getAppInstance()), "maintenance.php", null);
    }

    @Override
    public void onMaintenanceEnabled() {

        Intent intentActivityStatus = new Intent(parentActivity.get(), ActivityError.class);
        intentActivityStatus.putExtra(parentActivity.get().getString(R.string.ERROR_HEADER), parentActivity.get().getString(R.string.MAINTENANCE_ENABLED));
        intentActivityStatus.putExtra(parentActivity.get().getString(R.string.ERROR_MESSAGE), parentActivity.get().getString(R.string.MAINTENANCE_ENABLED_MESSAGE));
        intentActivityStatus.putExtra(parentActivity.get().getString(R.string.ERROR_IMAGE), R.drawable.vector_settings);
        intentActivityStatus.putExtra(parentActivity.get().getString(R.string.OPTION_EXIT), true);
        runOnUiThread(() -> parentActivity.get().startActivity(intentActivityStatus));

    }

    @Override
    public void onMaintenanceDisabled() {
        runOnUiThread(() -> {
            parentActivity.get().startActivity(new Intent(parentActivity.get(), ActivityUpdate.class));
            parentActivity.get().finish();
        });

    }

    @Override
    public void updateStatus(String status) {
        runOnUiThread(() -> parentActivity.get().getTvStatus().setText(status));
    }

    @Override
    public void onAPIRequestStart() {
        updateStatus("Checking for Maintenance...");
    }

    @Override
    public void runOnUiThread(Runnable runnable) {
        parentActivity.get().runOnUiThread(runnable);
    }

    @Override
    public void onAPIResponseReceived(HashMap<String, Object> apiResponse) {
        //keep this as it is event if getting warning
        try {
            JSONObject responseBody = (JSONObject) apiResponse.get(parentActivity.get().getString(R.string.RESPONSE_BODY));
            if (responseBody != null && responseBody.has("maintenance_mode") && responseBody.getBoolean("maintenance_mode")) {
                onMaintenanceEnabled();
            } else {
                onMaintenanceDisabled();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            onAPIResponseParseError();
        }
    }

    @Override
    public void onNoNetworkAvailable() {
        Intent intentActivityStatus = new Intent(parentActivity.get(), ActivityError.class);
        intentActivityStatus.putExtra(parentActivity.get().getString(R.string.ERROR_HEADER), parentActivity.get().getString(R.string.ERROR_NO_NETWORK));
        intentActivityStatus.putExtra(parentActivity.get().getString(R.string.ERROR_MESSAGE), parentActivity.get().getString(R.string.ERROR_NO_NETWORK_MESSAGE));
        intentActivityStatus.putExtra(parentActivity.get().getString(R.string.ERROR_IMAGE), R.drawable.vector_network_check);
        intentActivityStatus.putExtra(parentActivity.get().getString(R.string.OPTION_TRY_AGAIN), true);
        intentActivityStatus.putExtra(parentActivity.get().getString(R.string.OPTION_EXIT), true);
        runOnUiThread(() -> parentActivity.get().startActivity(intentActivityStatus));
    }

    @Override
    public void onAPIResponseNotReceived(String endPont) {
        Intent intentActivityStatus = new Intent(parentActivity.get(), ActivityError.class);
        intentActivityStatus.putExtra(parentActivity.get().getString(R.string.ERROR_HEADER), parentActivity.get().getString(R.string.ERROR_API_UNAVAILABLE));
        intentActivityStatus.putExtra(parentActivity.get().getString(R.string.ERROR_MESSAGE), parentActivity.get().getString(R.string.ERROR_API_UNAVAILABLE_MESSAGE));
        intentActivityStatus.putExtra(parentActivity.get().getString(R.string.OPTION_TRY_AGAIN), true);
        intentActivityStatus.putExtra(parentActivity.get().getString(R.string.OPTION_EXIT), true);
        runOnUiThread(() -> parentActivity.get().startActivity(intentActivityStatus));
    }

    @Override
    public void onAPIResponseParseError() {
        Intent intentActivityStatus = new Intent(parentActivity.get(), ActivityError.class);
        intentActivityStatus.putExtra(parentActivity.get().getString(R.string.OPTION_TRY_AGAIN), true);
        intentActivityStatus.putExtra(parentActivity.get().getString(R.string.ERROR_HEADER), parentActivity.get().getString(R.string.ERROR_API_PARSE));
        intentActivityStatus.putExtra(parentActivity.get().getString(R.string.ERROR_MESSAGE), parentActivity.get().getString(R.string.ERROR_API_PARSE_MESSAGE));
        runOnUiThread(() -> parentActivity.get().startActivity(intentActivityStatus));
    }

    @Override
    public void onAPIRequestEnd() {

    }
}
