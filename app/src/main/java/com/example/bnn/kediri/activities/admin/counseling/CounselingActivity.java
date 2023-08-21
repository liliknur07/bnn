package com.example.bnn.kediri.activities.admin.counseling;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.bnn.kediri.activities.admin.abuse.DetailAbuseActivity;
import com.example.bnn.kediri.adapters.DataAdapter;
import com.example.bnn.kediri.controller.BaseActivity;
import com.example.bnn.kediri.databinding.ActivityCounselingBinding;
import com.example.bnn.kediri.helpers.Sessions;
import com.example.bnn.kediri.listener.AdapterListener;
import com.example.bnn.kediri.models.Abuse;
import com.example.bnn.kediri.models.ApiData;
import com.example.bnn.kediri.models.Counseling;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class CounselingActivity extends BaseActivity {

    ActivityCounselingBinding binding;
    DataAdapter<Counseling> adapter;
    List<Counseling> dataCounseling = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCounselingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.rvCounseling.setLayoutManager(new LinearLayoutManager(context));
        adapter = new DataAdapter<Counseling>(context, "counseling", dataCounseling, new AdapterListener<Counseling>() {
            @Override
            public void onItemSelected(Counseling data) {
                Intent intent = new Intent(context, DetailCounselingActivity.class);
                intent.putExtra("counseling_id", data.getId());
                startActivity(intent);
            }
        });

        showProgressBar(true);
        mApiService.getAllCounseling("Bearer " + sessions.getData(Sessions.TOKEN))
                .enqueue(getAllCounseling.build());
    }

    @Override
    protected void onResume() {
        super.onResume();
        showProgressBar(true);
        mApiService.getAllCounseling("Bearer " + sessions.getData(Sessions.TOKEN))
                .enqueue(getAllCounseling.build());
    }

    ApiCallback getAllCounseling = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            ApiData<Counseling> counseling = new Gson().fromJson(result, new TypeToken<ApiData<Counseling>>(){}.getType());
            dataCounseling = counseling.getData();
            adapter.setData(dataCounseling);
            binding.rvCounseling.setAdapter(adapter);
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            showToast(errorMessage);
        }
    };
}