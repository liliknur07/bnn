package com.example.bnn.kediri.activities.admin.abuse;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.example.bnn.kediri.R;
import com.example.bnn.kediri.controller.BaseActivity;
import com.example.bnn.kediri.databinding.ActivityDetailAbuseBinding;
import com.example.bnn.kediri.helpers.Sessions;
import com.example.bnn.kediri.listener.DialogListener;
import com.example.bnn.kediri.models.Abuse;
import com.example.bnn.kediri.models.ApiSingle;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class DetailAbuseActivity extends BaseActivity {

    ActivityDetailAbuseBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailAbuseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int abuseId = getIntent().getIntExtra("abuse_id", 0);
        showProgressBar(true);
        mApiService.getSingleAbuse("Bearer "+ sessions.getData(Sessions.TOKEN), abuseId)
                .enqueue(dataAbuse.build());

        binding.btnBack.setOnClickListener(view -> finish());
        binding.btnConfirmation.setOnClickListener(view -> alertSubmitDone("Konfirmasi",
                "Anda yakin ingin konfirmasi Laporan Penyalahguanaan ini ?",
                new DialogListener() {
                    @Override
                    public void onPositiveButton() {
                        showProgressBar(true);
                        mApiService.confirmationAbuse("Bearer "+ sessions.getData(Sessions.TOKEN), abuseId)
                                .enqueue(confirmAbuse.build());
                    }

                    @Override
                    public void onNegativeButton() {

                    }
                }));
    }

    ApiCallback dataAbuse = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            ApiSingle<Abuse> data = new Gson().fromJson(result, new TypeToken<ApiSingle<Abuse>>(){}.getType());

            Abuse abuse = data.getData();
            binding.tvPhoneNumber.setText(abuse.getPhoneNumber());
            binding.tvDistrict.setText(abuse.getDistrict());
            binding.tvAddress.setText(abuse.getAddress());
            binding.tvPointLocation.setText("Titik Lokasi (Klik untuk melihat)");
            binding.tvPointLocation.setLinksClickable(true);
            binding.tvPointLocation.setLinkTextColor(ContextCompat.getColor(context, R.color.blueSecondary));
            binding.tvPointLocation.setOnClickListener(view -> {
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", Double.parseDouble(abuse.getLatitude()), Double.parseDouble(abuse.getLongitude()));
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                context.startActivity(intent);
            });
            Picasso.get().load(abuse.getPhoto())
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.broken_image).into(binding.ivPhoto);
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            showToast(errorMessage);
        }
    };

    ApiCallback confirmAbuse = new ApiCallback() {
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