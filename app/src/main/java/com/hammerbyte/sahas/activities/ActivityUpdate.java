package com.hammerbyte.sahas.activities;

import android.os.Bundle;
import android.widget.ProgressBar;

import com.hammerbyte.sahas.R;
import com.hammerbyte.sahas.activities.common.ActivitySuper;
import com.hammerbyte.sahas.fragments.updates.FragmentUpdateAvailable;
import com.hammerbyte.sahas.fragments.updates.FragmentUpdateCheck;
import com.hammerbyte.sahas.fragments.updates.FragmentUpdateInstall;
import com.hammerbyte.sahas.services.api.ServiceUpdate;
import com.hammerbyte.sahas.services.impl.ImplServiceUpdate;

import java.lang.ref.WeakReference;
import java.util.HashMap;

public class ActivityUpdate extends ActivitySuper {

    private final ServiceUpdate serviceUpdate;
    private final FragmentUpdateCheck fragmentUpdateCheck;
    private final FragmentUpdateAvailable fragmentUpdateAvailable;
    private final FragmentUpdateInstall fragmentUpdateInstall;
    private ProgressBar pbLoading;
    private HashMap<String, String> updateData;
    public ActivityUpdate() {
        serviceUpdate = new ImplServiceUpdate(new WeakReference<>(this));
        fragmentUpdateCheck = new FragmentUpdateCheck();
        fragmentUpdateAvailable = new FragmentUpdateAvailable();
        fragmentUpdateInstall = new FragmentUpdateInstall();
    }

    public FragmentUpdateInstall getFragmentUpdateInstall() {
        return fragmentUpdateInstall;
    }

    public FragmentUpdateCheck getFragmentUpdateCheck() {
        return fragmentUpdateCheck;
    }

    public FragmentUpdateAvailable getFragmentUpdateAvailable() {
        return fragmentUpdateAvailable;
    }

    public HashMap<String, String> getUpdateData() {
        return updateData;
    }

    public void setUpdateData(HashMap<String, String> updateData) {
        this.updateData = updateData;
    }

    public ServiceUpdate getServiceUpdate() {
        return serviceUpdate;
    }

    public ProgressBar getProgressLoading() {
        return pbLoading;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
    }

    @Override
    public void mapUI() {
        pbLoading = findViewById(R.id.PB_LOADING);
    }

    @Override
    public void mapUIValues() {
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
        serviceUpdate.checkForUpdate();
    }
}
