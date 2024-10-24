package com.hammerbyte.sahas.services.impl;

import android.content.Intent;
import android.view.View;

import com.hammerbyte.sahas.R;
import com.hammerbyte.sahas.activities.ActivityAccount;
import com.hammerbyte.sahas.activities.ActivityError;
import com.hammerbyte.sahas.activities.ActivityUpdate;
import com.hammerbyte.sahas.exceptions.NoNetWorkException;
import com.hammerbyte.sahas.services.api.ServiceUpdate;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImplServiceUpdate implements ServiceUpdate {

    private final WeakReference<ActivityUpdate> parentActivity;

    public ImplServiceUpdate(WeakReference<ActivityUpdate> parentActivity) {
        this.parentActivity = parentActivity;
    }

    @Override
    public void onAPIRequestStart() {
        updateProgressVisibility(true);
        runOnUiThread(() -> parentActivity.get().showFragment(parentActivity.get().getFragmentUpdateCheck(), false));
    }

    @Override
    public void runOnUiThread(Runnable runnable) {
        parentActivity.get().runOnUiThread(runnable);
    }

    @Override
    public void onAPIResponseReceived(HashMap<String, Object> apiResponse) {
        try {
            JSONObject responseBody = (JSONObject) apiResponse.get(parentActivity.get().getString(R.string.RESPONSE_BODY));
            if (responseBody != null && responseBody.has("update_available") && responseBody.getBoolean("update_available")) {
                HashMap<String, String> updateData = new HashMap<>();
                updateData.put("update_type", responseBody.getString("update_type"));
                updateData.put("update_version", responseBody.getString("update_version"));
                updateData.put("update_url", responseBody.getString("update_url"));
                onUpdateAvailable(updateData);
            } else {
                onUpdateUnAvailable();
            }
        } catch (JSONException e) {
            e.printStackTrace();
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
        updateProgressVisibility(false);
    }

    @Override
    public void checkForUpdate() {
        HitAPI(new WeakReference<>(parentActivity.get().getAppInstance()), "update.php", null);
    }

    @Override
    public void onUpdateAvailable(HashMap<String, String> updateData) {
        parentActivity.get().setUpdateData(updateData);
        runOnUiThread(() -> parentActivity.get().showFragment(parentActivity.get().getFragmentUpdateAvailable(), false));
    }

    @Override
    public void onUpdateUnAvailable() {
        runOnUiThread(() -> {
            parentActivity.get().startActivity(new Intent(parentActivity.get(), ActivityAccount.class));
            parentActivity.get().finish();
        });

    }

    @Override
    public void updateProgressVisibility(boolean visibility) {
        runOnUiThread(() -> parentActivity.get().getProgressLoading().setVisibility(visibility ? View.VISIBLE : View.GONE));

    }



    @Override
    public void onUpdateDownloadStart() {
        //once downloaded open installation fragment
        runOnUiThread(() -> parentActivity.get().showFragment(parentActivity.get().getFragmentUpdateInstall(), true));

    }


    @Override
    public void onUpdateDownloadCompleted(long downloadId, File updateFile) {

    }

    @Override
    public void onUpdateDownloadFailed() {

    }


    @Override
    public void onUpdateInstalled() {

    }
}
