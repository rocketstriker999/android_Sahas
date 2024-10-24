package com.hammerbyte.sahas.activities;

import android.os.Bundle;
import android.view.View;



import com.hammerbyte.sahas.activities.common.ActivitySuper;
import com.hammerbyte.sahas.databinding.ActivityAccountBinding;
import com.hammerbyte.sahas.services.impl.ImplServiceAuthentication;

public class ActivityAccount extends ActivitySuper {
    private ActivityAccountBinding binding;
    private ImplServiceAuthentication implServiceAuthentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAccountBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        implServiceAuthentication=new ImplServiceAuthentication();
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
