package com.hammerbyte.sahas.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.hammerbyte.sahas.BuildConfig;
import com.hammerbyte.sahas.R;
import com.hammerbyte.sahas.activities.common.ActivitySuper;
import com.hammerbyte.sahas.databinding.ActivitySplashBinding;
import com.hammerbyte.sahas.services.api.ServiceMaintenance;
import com.hammerbyte.sahas.services.api.ServiceUsageData;
import com.hammerbyte.sahas.services.impl.ImplServiceMaintenance;
import com.hammerbyte.sahas.services.impl.ImplServiceUsageData;

import java.lang.ref.WeakReference;

public class ActivitySplash extends ActivitySuper {
    private final ServiceMaintenance serviceMaintenance;
    private final ServiceUsageData serviceUsageData;

    private TextView tvStatus;
    private TextView tvAppVersion;

    public ActivitySplash() {
        this.serviceMaintenance = new ImplServiceMaintenance(new WeakReference<>(this));
        this.serviceUsageData = new ImplServiceUsageData(new WeakReference<>(this));
    }

    public TextView getTvStatus() {
        Log.d("getTvStatus", tvStatus.toString());
        return tvStatus;
    }

    public void setTvStatus(TextView tvStatus) {
        Log.d("getTvStatus1", tvStatus.toString());

        this.tvStatus = tvStatus;
    }

    public TextView getTvAppVersion() {
        return tvAppVersion;
    }

    public void setTvAppVersion(TextView tvAppVersion) {
        this.tvAppVersion = tvAppVersion;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    public void mapUI() {
        //UI Mapping
        setTvStatus(findViewById(R.id.TV_STATUS));
        setTvAppVersion(findViewById(R.id.TV_APP_VERSION));
    }

    @Override
    public void mapUIValues() {
        //set Values
        getTvAppVersion().setText(getAppInstance().getApplicationVersion());
    }

    @Override
    public void mapVisibility() {

    }

    @Override
    public void mapListeners() {

    }

    @Override
    public String getActivityName() {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //submit collected data
        //check if we have data then only send
        if(getAppInstance().getUsageData()!=null){
            serviceUsageData.submitUsageData(getAppInstance().getUsageData());
        }else{
            System.out.println("[-] Nothing To Send For Usage Data");
        }

        //if debug mode or developer is working no need to check for maintenance
        //when signin or release app make sure putting ! in front of condition
        if (BuildConfig.BUILD_TYPE.equalsIgnoreCase("debug")) {
            serviceMaintenance.checkMaintenance();
        } else serviceMaintenance.onMaintenanceDisabled();
    }
}
