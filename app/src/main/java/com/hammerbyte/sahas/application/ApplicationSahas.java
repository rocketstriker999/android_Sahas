package com.hammerbyte.sahas.application;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.Nullable;

import com.hammerbyte.sahas.R;
import com.hammerbyte.sahas.exceptions.NoNetWorkException;
import com.hammerbyte.sahas.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class ApplicationSahas extends Application {

    private JSONObject loggedInUser;
    private DBHelper dbHelper;
    private ConnectivityManager connectivityManager;

    public JSONObject getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(JSONObject loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        dbHelper = new DBHelper(this);
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //generate and save device id if not exist
        putDeviceId();
    }

    public void putUsageData(HashMap<String, String> usageData) {
        dbHelper.insertUsageData(usageData);
    }

    public JSONObject getUsageData() {
        return dbHelper.getAllUsageData();
    }

    public void clearUsageData() {
        dbHelper.truncateUsageData();
    }

    public String getApplicationVersion() {
        try {
            return getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (Exception e) {
            return "0.0.0";
        }
    }

    @SuppressLint("HardwareIds")
    public void putDeviceId() {
        if (getDeviceID() == null)
            getSharedPreferences(getString(R.string.APP_NAME), Context.MODE_PRIVATE).edit().putString("device_id", Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID)).apply();

    }

    public String getDeviceID() {
        return getSharedPreferences(getString(R.string.APP_NAME), Context.MODE_PRIVATE).getString("device_id", null);
    }

    public String getAuthToken() {
        return getSharedPreferences(getString(R.string.APP_NAME), Context.MODE_PRIVATE).getString("auth_token", null);
    }

    public String requestUpdateBinary(String endPoint) throws IOException, JSONException, NoNetWorkException{
        if (Utils.isNetworkAvailable(connectivityManager)) {

            return Utils.getUpdateBinary(endPoint, Objects.requireNonNull(getApplicationContext().getExternalFilesDir(null)).getAbsolutePath(), prepareRequestPayLoad(null));

        } else {
            throw new NoNetWorkException();
        }
    }

    public JSONObject prepareRequestPayLoad(JSONObject requestPayLoad) throws JSONException {
        if (requestPayLoad == null) requestPayLoad = new JSONObject();
        // Add additional parameters to the postBody
        requestPayLoad.put("app_version", getApplicationVersion());
        requestPayLoad.put("auth_token", getAuthToken());
        requestPayLoad.put("device_id", getDeviceID());
        requestPayLoad.put("device_brand", Build.BRAND);
        requestPayLoad.put("device_model", Build.MODEL);
        requestPayLoad.put("device_os", "ANDROID");
        requestPayLoad.put("device_os_version", Build.VERSION.RELEASE);
        return  requestPayLoad;

    }

    public HashMap<String,Object> requestAPI(String endPoint, @Nullable JSONObject postBody) throws IOException, JSONException, NoNetWorkException {
        if (Utils.isNetworkAvailable(connectivityManager)) {
            return Utils.getAPIResponse(endPoint, prepareRequestPayLoad(postBody));
        } else {
            throw new NoNetWorkException();
        }
    }


}
