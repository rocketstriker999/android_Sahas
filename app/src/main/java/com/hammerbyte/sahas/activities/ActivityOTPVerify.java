package com.hammerbyte.sahas.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.hammerbyte.sahas.activities.common.ActivitySuper;
import com.hammerbyte.sahas.databinding.ActivityOtpverifyBinding;
import com.hammerbyte.sahas.services.api.ServiceOTPVerify;
import com.hammerbyte.sahas.services.impl.ImplServiceOTPVerify;


import java.lang.ref.WeakReference;

public class ActivityOTPVerify extends ActivitySuper {
    private ActivityOtpverifyBinding binding;
    private ServiceOTPVerify ImplServiceOTPVerify;


  public ActivityOTPVerify(){
      this.ImplServiceOTPVerify=new ImplServiceOTPVerify(new WeakReference<>(this));
  }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOtpverifyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

    @Override
    protected void mapUI() {
        binding.otpEditText1.addTextChangedListener(new OTPTextWatcher(binding.otpEditText1, binding.otpEditText2));
        binding.otpEditText2.addTextChangedListener(new OTPTextWatcher(binding.otpEditText2, binding.otpEditText3));
        binding.otpEditText3.addTextChangedListener(new OTPTextWatcher(binding.otpEditText3, binding.otpEditText4));
        binding.otpEditText4.addTextChangedListener(new OTPTextWatcher(binding.otpEditText4, binding.otpEditText5));
        binding.otpEditText5.addTextChangedListener(new OTPTextWatcher(binding.otpEditText5, binding.otpEditText6));


    }

    @Override
    protected void mapUIValues() {

    }

    @Override
    protected void mapVisibility() {

    }

    @Override
    protected void mapListeners() {
    binding.SUBMITBTN.setOnClickListener(v -> {
        String otp = binding.otpEditText1.getText().toString() +
                binding.otpEditText2.getText().toString() +
                binding.otpEditText3.getText().toString() +
                binding.otpEditText4.getText().toString() +
                binding.otpEditText5.getText().toString() +
                binding.otpEditText6.getText().toString();



    });
    }

    @Override
    protected String getActivityName() {
        return "OTP Verify";
    }



    private class OTPTextWatcher implements TextWatcher {
        private final EditText currentEditText;
        private final EditText nextEditText;
        private final EditText previousEditText;

        public OTPTextWatcher(EditText current, EditText next) {
            this(current, next, null);
        }

        public OTPTextWatcher(EditText current, EditText next, EditText previous) {
            this.currentEditText = current;
            this.nextEditText = next;
            this.previousEditText = previous;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 1 && nextEditText != null) {
                nextEditText.requestFocus(); // Move to the next EditText
            } else if (s.length() == 0 && previousEditText != null) {
                previousEditText.requestFocus(); // Move back to the previous EditText
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }


    }



}




