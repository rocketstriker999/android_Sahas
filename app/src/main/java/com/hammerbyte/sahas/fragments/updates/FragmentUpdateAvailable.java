package com.hammerbyte.sahas.fragments.updates;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hammerbyte.sahas.R;
import com.hammerbyte.sahas.activities.ActivityAccount;
import com.hammerbyte.sahas.activities.ActivityUpdate;
import com.hammerbyte.sahas.databinding.FragmentUpdateAvailableBinding;
import com.hammerbyte.sahas.fragments.common.FragmentSuper;

import java.util.Objects;

public class FragmentUpdateAvailable extends FragmentSuper {
    private FragmentUpdateAvailableBinding binding;
    private ActivityUpdate parentActivity;
//    private ImageView imageUpdate;
//    private TextView tvUpdateType, tvUpdateDescription;
//    private Button btnSkipUpdate, btnInstallUpdate;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.parentActivity = (ActivityUpdate) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentUpdateAvailableBinding.inflate(inflater, container, false); // Inflate the layout with binding
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    protected void mapUI(View fragmentView) {
//        imageUpdate = fragmentView.findViewById(R.id.IMG_STATUS);
//        tvUpdateType = fragmentView.findViewById(R.id.TV_UPDATE_TYPE);
//        tvUpdateDescription = fragmentView.findViewById(R.id.TV_UPDATE_DESCRIPTION);
//        btnSkipUpdate = fragmentView.findViewById(R.id.BTN_SKIP_UPDATE);
//        btnInstallUpdate = fragmentView.findViewById(R.id.BTN_INSTALL_UPDATE);
    }

    @Override
    protected void mapUIValues() {
        binding.TVUPDATETYPE.setText(Objects.requireNonNull(parentActivity.getUpdateData().get("update_version")).concat(Objects.equals(parentActivity.getUpdateData().get("update_type"), "non_critical") ? getResources().getString(R.string.AVAILABLE_UPDATE_NON_CRITICAL) : getResources().getString(R.string.AVAILABLE_UPDATE_CRITICAL)));
        binding.TVUPDATEDESCRIPTION.setText(Objects.equals(parentActivity.getUpdateData().get("update_type"), "non_critical") ? getString(R.string.NON_CRITICAL_UPDATE) : getString(R.string.CRITICAL_UPDATE));
        binding.IMGSTATUS.setImageResource(Objects.equals(parentActivity.getUpdateData().get("update_type"), "non_critical") ? R.drawable.vector_update_non_critical : R.drawable.vector_update_critical);
    }

    @Override
    protected void mapVisibility() {
        binding.BTNSKIPUPDATE.setVisibility(Objects.equals(parentActivity.getUpdateData().get("update_type"), "non_critical") ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void mapListeners() {
        binding.BTNSKIPUPDATE.setOnClickListener(v -> {
            parentActivity.startActivity(new Intent(parentActivity, ActivityAccount.class));
            parentActivity.finish();
        });
        binding.BTNINSTALLUPDATE.setOnClickListener(v -> {

            //start download as well
        //    parentActivity.getServiceUpdate().startUpdateDownload(parentActivity.getUpdateData().get("update_url"));
        });
    }
}
