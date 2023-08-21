package com.example.bnn.kediri.activities.admin.abuse;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.bnn.kediri.adapters.DataAdapter;
import com.example.bnn.kediri.controller.BaseActivity;
import com.example.bnn.kediri.databinding.ActivityAbuseBinding;
import com.example.bnn.kediri.helpers.Sessions;
import com.example.bnn.kediri.listener.AdapterListener;
import com.example.bnn.kediri.models.Abuse;
import com.example.bnn.kediri.models.ApiData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class AbuseActivity extends BaseActivity {

    ActivityAbuseBinding binding;
    DataAdapter<Abuse> adapter;
    List<Abuse> dataAbuse = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAbuseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.rvAbuse.setLayoutManager(new LinearLayoutManager(context));
        adapter = new DataAdapter<Abuse>(context, "abuse", dataAbuse, new AdapterListener<Abuse>() {
            @Override
            public void onItemSelected(Abuse data) {
                Intent intent = new Intent(context, DetailAbuseActivity.class);
                intent.putExtra("abuse_id", data.getId());
                startActivity(intent);
            }
        });

        showProgressBar(true);
        mApiService.getAllAbuse("Bearer " + sessions.getData(Sessions.TOKEN))
                .enqueue(getAllAbuse.build());
    }

    ApiCallback getAllAbuse = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            ApiData<Abuse> abuse = new Gson().fromJson(result, new TypeToken<ApiData<Abuse>>(){}.getType());
            dataAbuse = abuse.getData();
            adapter.setData(dataAbuse);
            binding.rvAbuse.setAdapter(adapter);
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            showToast(errorMessage);
        }
    };
}