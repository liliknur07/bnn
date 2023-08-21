package com.example.bnn.kediri.activities.admin.socialization;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.bnn.kediri.activities.admin.rehab.DetailRehabActivity;
import com.example.bnn.kediri.adapters.DataAdapter;
import com.example.bnn.kediri.controller.BaseActivity;
import com.example.bnn.kediri.databinding.ActivitySocializationP4gnBinding;
import com.example.bnn.kediri.helpers.Sessions;
import com.example.bnn.kediri.listener.AdapterListener;
import com.example.bnn.kediri.models.ApiData;
import com.example.bnn.kediri.models.Rehab;
import com.example.bnn.kediri.models.SocializationP4GN;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class SocializationP4gnActivity extends BaseActivity {

    ActivitySocializationP4gnBinding binding;
    DataAdapter<SocializationP4GN> adapter;
    List<SocializationP4GN> dataSocialize = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySocializationP4gnBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.rvSocialize.setLayoutManager(new LinearLayoutManager(context));

        adapter = new DataAdapter<>(context, "socialize", dataSocialize, new AdapterListener<SocializationP4GN>() {
            @Override
            public void onItemSelected(SocializationP4GN data) {
                Intent intent = new Intent(context, DetailSocializationP4gnActivity.class);
                intent.putExtra("socialize_id", data.getId());
                startActivity(intent);
            }
        });

        showProgressBar(true);
        mApiService.getAllSocialize("Bearer " + sessions.getData(Sessions.TOKEN))
                .enqueue(getAllSocialize.build());
    }

    @Override
    protected void onResume() {
        super.onResume();
        showProgressBar(true);
        mApiService.getAllSocialize("Bearer " + sessions.getData(Sessions.TOKEN))
                .enqueue(getAllSocialize.build());
    }

    ApiCallback getAllSocialize = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            ApiData<SocializationP4GN> socialize = new Gson().fromJson(result, new TypeToken<ApiData<SocializationP4GN>>(){}.getType());
            dataSocialize = socialize.getData();
            adapter.setData(dataSocialize);
            binding.rvSocialize.setAdapter(adapter);
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            showToast(errorMessage);
        }
    };
}