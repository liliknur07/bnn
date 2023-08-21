package com.example.bnn.kediri.activities.admin.rehab;


import android.os.Bundle;
import android.view.View;

import com.example.bnn.kediri.controller.BaseActivity;
import com.example.bnn.kediri.databinding.ActivityDetailRehabBinding;
import com.example.bnn.kediri.helpers.Sessions;
import com.example.bnn.kediri.listener.DialogListener;
import com.example.bnn.kediri.models.ApiSingle;
import com.example.bnn.kediri.models.Rehab;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class DetailRehabActivity extends BaseActivity {

    ActivityDetailRehabBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailRehabBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int rehabId = getIntent().getIntExtra("rehab_id", 0);
        showProgressBar(true);
        mApiService.getSingleRehab("Bearer "+ sessions.getData(Sessions.TOKEN), rehabId)
                .enqueue(dataRehab.build());

        binding.btnBack.setOnClickListener(view -> finish());
        binding.btnConfirmation.setOnClickListener(view -> alertSubmitDone("Konfirmasi",
                "Anda yakin ingin konfirmasi permintaan Rehabilitasi ini ?",
                new DialogListener() {
                    @Override
                    public void onPositiveButton() {
                        showProgressBar(true);
                        mApiService.confirmationRehab("Bearer "+ sessions.getData(Sessions.TOKEN), rehabId)
                                .enqueue(confirmRehab.build());
                    }

                    @Override
                    public void onNegativeButton() {

                    }
                }));
    }

    ApiCallback dataRehab = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            ApiSingle<Rehab> data = new Gson().fromJson(result, new TypeToken<ApiSingle<Rehab>>(){}.getType());

            Rehab rehab = data.getData();
            binding.tvName.setText(rehab.getFullName());
            binding.tvAddress.setText(rehab.getAddress());
            binding.tvGender.setText(rehab.getGender());
            binding.tvDateBirth.setText(rehab.getDateBirth());
            binding.tvZatName.setText(rehab.getZat());
            binding.tvUsageTime.setText(rehab.getUsageTime());
            binding.tvPhoneNumber.setText(rehab.getPhoneNumber());

            if (isStatusCompleted(rehab.getStatus())) {
                binding.btnConfirmation.setVisibility(View.GONE);
            } else {
                binding.btnConfirmation.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            showToast(errorMessage);

        }
    };

    ApiCallback confirmRehab = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            showToast("Konfirmasi Berhasil");
            finish();
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            showToast(errorMessage);
        }
    };
}