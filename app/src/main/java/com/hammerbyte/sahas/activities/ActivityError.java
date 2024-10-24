package com.hammerbyte.sahas.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hammerbyte.sahas.R;
import com.hammerbyte.sahas.activities.common.ActivitySuper;

public class ActivityError extends ActivitySuper  {

    private TextView tvErrorHeader;
    private ImageView imageError;
    private TextView tvErrorMessage;
    private Button btnTryAgain, btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);
    }

    @Override
    public void mapUI() {
        tvErrorHeader = findViewById(R.id.TV_ERROR_HEADER);
        tvErrorMessage = findViewById(R.id.TV_ERROR_MESSAGE);
        imageError = findViewById(R.id.IMG_ERROR);
        btnTryAgain = findViewById(R.id.BTN_RETRY);
        btnExit = findViewById(R.id.BTN_EXIT);
    }

    @Override
    public void mapUIValues() {
        tvErrorHeader.setText(getIntent().getStringExtra(getString(R.string.ERROR_HEADER)));
        tvErrorMessage.setText(getIntent().getStringExtra(getString(R.string.ERROR_MESSAGE)));
        imageError.setImageResource(getIntent().getIntExtra(getString(R.string.ERROR_IMAGE), R.drawable.vector_problem));
    }

    @Override
    public void mapVisibility() {
        btnTryAgain.setVisibility(getIntent().getBooleanExtra(getString(R.string.OPTION_TRY_AGAIN), false) ? View.VISIBLE : View.GONE);
        btnExit.setVisibility(getIntent().getBooleanExtra(getString(R.string.OPTION_EXIT), false) ? View.VISIBLE : View.GONE);
    }

    @Override
    public void mapListeners() {
        btnExit.setOnClickListener(v -> {
            this.finishAffinity();
        });
        btnTryAgain.setOnClickListener(v->{
            this.finish();
        });
    }

    @Override
    public String getActivityName() {
        return null;
    }

}
