package com.example.bnn.kediri.activities.admin.socialization;

import android.os.Bundle;
import android.view.View;

import com.example.bnn.kediri.controller.BaseActivity;
import com.example.bnn.kediri.databinding.ActivityDetailSocializationP4gnBinding;
import com.example.bnn.kediri.helpers.Sessions;
import com.example.bnn.kediri.listener.DialogListener;
import com.example.bnn.kediri.models.ApiSingle;
import com.example.bnn.kediri.models.SocializationP4GN;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class DetailSocializationP4gnActivity extends BaseActivity {

    ActivityDetailSocializationP4gnBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailSocializationP4gnBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int socializeId = getIntent().getIntExtra("socialize_id", 0);
        showProgressBar(true);
        mApiService.getSingleSocialize("Bearer "+ sessions.getData(Sessions.TOKEN), socializeId)
                .enqueue(dataSocialize.build());

        binding.btnBack.setOnClickListener(view -> finish());
        binding.btnConfirmation.setOnClickListener(view -> alertSubmitDone("Konfirmasi",
                "Anda yakin ingin konfirmasi Permintaan Sosialisasi P4GN ini ?",
                new DialogListener() {
                    @Override
                    public void onPositiveButton() {
                        showProgressBar(true);
                        mApiService.confirmationSocialize("Bearer "+ sessions.getData(Sessions.TOKEN), socializeId)
                                .enqueue(confirmSocialize.build());
                    }

                    @Override
                    public void onNegativeButton() {

                    }
                }));
    }

    ApiCallback dataSocialize = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            ApiSingle<SocializationP4GN> data = new Gson().fromJson(result, new TypeToken<ApiSingle<SocializationP4GN>>(){}.getType());
            SocializationP4GN socialize = data.getData();

            binding.tvName.setText(socialize.getFullName());
            binding.tvCompany.setText(socialize.getCompany());
            binding.tvRequested.setText(socialize.getRequested());
            binding.tvDate.setText(socialize.getDate());
            binding.tvTime.setText(socialize.getTime());

            if (isStatusCompleted(socialize.getStatus())) {
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

    ApiCallback confirmSocialize = new ApiCallback() {
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