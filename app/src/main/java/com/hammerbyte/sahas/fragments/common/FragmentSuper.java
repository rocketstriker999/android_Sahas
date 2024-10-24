package com.hammerbyte.sahas.fragments.common;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONException;

import java.util.HashMap;

public abstract class FragmentSuper extends Fragment {

    private View fragmentView;

    @Override
    public void onResume() {
        super.onResume();
        mapUI(fragmentView);
        mapUIValues();
        mapVisibility();
        mapListeners();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.fragmentView=view;

    }

    protected abstract void mapUI(View fragmentView);

    protected abstract void mapUIValues();

    protected abstract void mapVisibility();

    protected abstract void mapListeners();

}
