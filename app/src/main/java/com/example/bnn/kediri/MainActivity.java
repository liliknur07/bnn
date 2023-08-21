package com.example.bnn.kediri;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.bnn.kediri.activities.LoginActivity;
import com.example.bnn.kediri.activities.admin.abuse.AbuseActivity;
import com.example.bnn.kediri.activities.admin.counseling.CounselingActivity;
import com.example.bnn.kediri.activities.admin.rehab.RehabActivity;
import com.example.bnn.kediri.activities.admin.socialization.SocializationP4gnActivity;
import com.example.bnn.kediri.activities.users.FormAbuseActivity;
//import com.example.bnn.kediri.activities.users.FormCounselingActivity;
//import com.example.bnn.kediri.activities.users.FormRehabActivity;
import com.example.bnn.kediri.controller.BaseActivity;
import com.example.bnn.kediri.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    ActivityMainBinding binding;
    ImageButton btnImgPeta;
    ImageButton btnImgLaporan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        btnImgPeta = findViewById(R.id.btn_img_peta);
        btnImgLaporan = findViewById(R.id.btn_img_laporan);

        if (sessions.isLogin()) {
            //binding.btnInformation.setVisibility(View.GONE);
            //binding.btnAbout.setVisibility(View.GONE);
            //binding.btnLoginAdmin.setVisibility(View.GONE);
            //binding.btnLogout.setVisibility(View.VISIBLE);
        } else {
            //binding.btnInformation.setVisibility(View.VISIBLE);
            //binding.btnAbout.setVisibility(View.VISIBLE);
            //binding.btnLoginAdmin.setVisibility(View.VISIBLE);
            //binding.btnLogout.setVisibility(View.GONE);
        }
        binding.btnImgPeta.setOnClickListener(this);
        binding.btnImgLaporan.setOnClickListener(this);

    }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            Intent B2;
            if (view.getId() == R.id.btn_img_laporan) {
                if (sessions.isLogin()) {
                    intent = new Intent(context, FormAbuseActivity.class);
                } else {
                    intent = new Intent(context, FormAbuseActivity.class);
                }
                startActivity(intent);
            } else if (view.getId() == R.id.btn_img_peta) {
                if (sessions.isLogin()) {
                    intent = new Intent(context, FormAbuseActivity.class);
                } else {
                    Toast.makeText(context, "Belum Selesai", Toast.LENGTH_SHORT).show();
                }
            }
        }
}
