package com.example.bnn.kediri.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;

import com.example.bnn.kediri.MainActivity;
import com.example.bnn.kediri.controller.BaseActivity;
import com.example.bnn.kediri.databinding.ActivityLoginBinding;
import com.example.bnn.kediri.models.ApiSingle;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;

public class LoginActivity extends BaseActivity {
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLogin.setOnClickListener(view -> {
            if (validate()) {
                login();
            }
        });

        binding.cbShowPassword.setOnCheckedChangeListener((compoundButton, checked) -> {
            if (checked) {
                binding.etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                binding.etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });
    }

        private boolean validate () {
        boolean isValid = false;
        String nik = binding.etNik.getText().toString();
        String password = binding.etPassword.getText().toString();
        if (nik.isEmpty()) {
            binding.etNik.setError("Nik harus diisi");
        } else {
            binding.etNik.setError(null);
        }

        if (password.isEmpty()) {
            binding.etPassword.setError("Password harus diisi");
        } else {
            isValid = true;
            binding.etPassword.setError(null);
        }
        return isValid;
    }

    private void login() {
        String nik = binding.etNik.getText().toString();
        String password = binding.etPassword.getText().toString();

        showProgressBar(true);
        mApiService.loginAdmin(nik, password).enqueue(loginCallback.build());
    }

    ApiCallback loginCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            showToast("Login Berhasil");
            ApiSingle<HashMap<String, String>> status = new Gson().fromJson(result, new TypeToken<ApiSingle<HashMap<String, String>>>(){}.getType());
            if(status.getData().get("token") !=null) {
                sessions.createSession(status.getData().get("token"));
                Intent intent = new Intent(context, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        }

        @Override
        public void onApiFailure(String errorMessage) {

        }
    };
}