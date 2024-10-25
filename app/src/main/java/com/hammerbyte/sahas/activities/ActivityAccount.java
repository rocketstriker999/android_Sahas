package com.hammerbyte.sahas.activities;

import android.os.Bundle;
import android.view.View;



import com.hammerbyte.sahas.activities.common.ActivitySuper;
import com.hammerbyte.sahas.databinding.ActivityAccountBinding;
import com.hammerbyte.sahas.services.api.ServiecAuthentication;
import com.hammerbyte.sahas.services.impl.ImplServiceAuthentication;
import com.hammerbyte.sahas.services.impl.ImplServiceMaintenance;
import com.hammerbyte.sahas.services.impl.ImplServiceUsageData;

import java.lang.ref.WeakReference;

public class ActivityAccount extends ActivitySuper {
    private ActivityAccountBinding binding;
    private ServiecAuthentication implServiceAuthentication;


    public ActivityAccount() {
        this.implServiceAuthentication = new ImplServiceAuthentication(new WeakReference<>(this));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAccountBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        implServiceAuthentication.StartAuthentication("");

    }

    @Override
    protected void mapUI() {

   // implServiceAuthentication.StartAuthentication(binding.EMAILEDITTEXT.getText().toString());
    }

    @Override
    protected void mapUIValues() {

    }

    @Override
    protected void mapVisibility() {
        binding.PBLOADING.setVisibility(View.INVISIBLE);

    }

    @Override
    protected void mapListeners() {
        binding.LOGINBTN.setOnClickListener(v -> {
            implServiceAuthentication.StartAuthentication(binding.EMAILEDITTEXT.getText().toString());
        });

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
