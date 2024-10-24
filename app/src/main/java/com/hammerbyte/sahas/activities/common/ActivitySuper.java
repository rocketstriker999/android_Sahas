package com.hammerbyte.sahas.activities.common;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.hammerbyte.sahas.R;
import com.hammerbyte.sahas.application.ApplicationSahas;

import org.json.JSONException;

import java.util.HashMap;

public abstract class ActivitySuper extends AppCompatActivity {

    private ApplicationSahas appInstance;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appInstance = (ApplicationSahas) getApplication();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (getActivityName() != null && appInstance.getAuthToken() != null) {
            try {
                HashMap<String, String> usageData = new HashMap<>();
                usageData.put("user_email", appInstance.getLoggedInUser().getString("user_email"));
                usageData.put("activity_name", getActivityName());
                appInstance.putUsageData(usageData);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        mapUI();
        mapUIValues();
        mapVisibility();
        mapListeners();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (getActivityName() != null && appInstance.getAuthToken() != null) {
            try {
                HashMap<String, String> usageData = new HashMap<>();
                usageData.put("user_email", appInstance.getLoggedInUser().getString("user_email"));
                usageData.put("activity_name", getActivityName());
                appInstance.putUsageData(usageData);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public ApplicationSahas getAppInstance() {
        return appInstance;
    }

    protected abstract void mapUI();

    protected abstract void mapUIValues();

    protected abstract void mapVisibility();

    protected abstract void mapListeners();

    protected abstract String getActivityName();

    public void showFragment(Fragment fragment, boolean allowBack) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.CONTAINER, fragment);
        if (allowBack) {
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        fragmentTransaction.commit();

    }
}
