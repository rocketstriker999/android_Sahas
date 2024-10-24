package com.hammerbyte.sahas.fragments.updates;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hammerbyte.sahas.R;
import com.hammerbyte.sahas.activities.ActivityUpdate;
import com.hammerbyte.sahas.databinding.FragmentUpdateCheckBinding;
import com.hammerbyte.sahas.fragments.common.FragmentSuper;

public class FragmentUpdateCheck extends FragmentSuper {
    private FragmentUpdateCheckBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentUpdateCheckBinding.inflate(inflater, container, false); // Inflate the layout with binding
        return binding.getRoot();
    }


    @Override
    protected void mapUI(View fragmentView) {
    }

    @Override
    protected void mapUIValues() {

    }

    @Override
    protected void mapVisibility(){

    }

    @Override
    protected void mapListeners() {

    }



}
