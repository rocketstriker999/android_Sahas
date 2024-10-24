package com.hammerbyte.sahas.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.hammerbyte.sahas.R;
import com.hammerbyte.sahas.activities.common.ActivitySuper;

public class ActivityAccount extends ActivitySuper {

    private ProgressBar pbLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
    }

    @Override
    protected void mapUI() {
        pbLoading = findViewById(R.id.PB_LOADING);
    }

    @Override
    protected void mapUIValues() {

    }

    @Override
    protected void mapVisibility() {
        pbLoading.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void mapListeners() {

    }

    @Override
    protected String getActivityName() {
        return "LOGIN";
    }


    @Override
    protected void onResume() {
        super.onResume();
    }
}
