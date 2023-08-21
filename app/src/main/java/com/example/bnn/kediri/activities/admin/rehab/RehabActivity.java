package com.example.bnn.kediri.activities.admin.rehab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.bnn.kediri.activities.admin.counseling.DetailCounselingActivity;
import com.example.bnn.kediri.adapters.DataAdapter;
import com.example.bnn.kediri.controller.BaseActivity;
import com.example.bnn.kediri.databinding.ActivityRehabBinding;
import com.example.bnn.kediri.helpers.Sessions;
import com.example.bnn.kediri.listener.AdapterListener;
import com.example.bnn.kediri.models.ApiData;
import com.example.bnn.kediri.models.Counseling;
import com.example.bnn.kediri.models.Rehab;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class RehabActivity extends BaseActivity {

    ActivityRehabBinding binding;
    DataAdapter<Rehab> adapter;
    List<Rehab> dataRehab = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =  ActivityRehabBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.rvRehab.setLayoutManager(new LinearLayoutManager(context));
        adapter = new DataAdapter<Rehab>(context, "rehab", dataRehab, new AdapterListener<Rehab>() {
            @Override
            public void onItemSelected(Rehab data) {
                Intent intent = new Intent(context, DetailRehabActivity.class);
                intent.putExtra("rehab_id", data.getId());
                startActivity(intent);
            }
        });

        showProgressBar(true);
        mApiService.getAllRehab("Bearer " + sessions.getData(Sessions.TOKEN))
                .enqueue(getAllRehab.build());
    }

    @Override
    protected void onResume() {
        super.onResume();
        showProgressBar(true);
        mApiService.getAllRehab("Bearer " + sessions.getData(Sessions.TOKEN))
                .enqueue(getAllRehab.build());
    }

    ApiCallback getAllRehab = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            ApiData<Rehab> rehab = new Gson().fromJson(result, new TypeToken<ApiData<Rehab>>(){}.getType());
            dataRehab = rehab.getData();
            adapter.setData(dataRehab);
            binding.rvRehab.setAdapter(adapter);
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            showToast(errorMessage);
        }
    };
}