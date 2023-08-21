package com.example.bnn.kediri.activities.admin.counseling;

import android.os.Bundle;
import android.view.View;

import com.example.bnn.kediri.controller.BaseActivity;
import com.example.bnn.kediri.databinding.ActivityDetailCounselingBinding;
import com.example.bnn.kediri.helpers.Sessions;
import com.example.bnn.kediri.listener.DialogListener;
import com.example.bnn.kediri.models.ApiSingle;
import com.example.bnn.kediri.models.Counseling;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class DetailCounselingActivity extends BaseActivity {

    ActivityDetailCounselingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailCounselingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int counselingId = getIntent().getIntExtra("counseling_id", 0);
        showProgressBar(true);
        mApiService.getSingleCounseling("Bearer "+ sessions.getData(Sessions.TOKEN), counselingId)
                .enqueue(dataCounseling.build());

        binding.btnBack.setOnClickListener(view -> finish());
        binding.btnConfirmation.setOnClickListener(view -> alertSubmitDone("Konfirmasi",
                "Anda yakin ingin konfirmasi permintaan Konseling ini ?",
                new DialogListener() {
                    @Override
                    public void onPositiveButton() {
                        showProgressBar(true);
                        mApiService.confirmationCounseling("Bearer "+ sessions.getData(Sessions.TOKEN), counselingId)
                                .enqueue(confirmCounseling.build());
                    }

                    @Override
                    public void onNegativeButton() {

                    }
                }));
    }

    ApiCallback dataCounseling = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            ApiSingle<Counseling> data = new Gson().fromJson(result, new TypeToken<ApiSingle<Counseling>>(){}.getType());

            Counseling counseling = data.getData();
            binding.tvName.setText(counseling.getFullName());
            binding.tvAddress.setText(counseling.getAddress());
            binding.tvGender.setText(counseling.getGender());
            binding.tvDateBirth.setText(counseling.getDateBirth());
            binding.tvZatName.setText(counseling.getZat());
            binding.tvUsageTime.setText(counseling.getUsageTime());
            binding.tvPhoneNumber.setText(counseling.getPhoneNumber());

            if (isStatusCompleted(counseling.getStatus())) {
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

    ApiCallback confirmCounseling = new ApiCallback() {
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