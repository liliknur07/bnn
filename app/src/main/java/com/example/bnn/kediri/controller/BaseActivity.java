package com.example.bnn.kediri.controller;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.se.omapi.Session;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import com.example.bnn.kediri.MainActivity;
import com.example.bnn.kediri.R;
import com.example.bnn.kediri.helpers.Sessions;
import com.example.bnn.kediri.listener.DialogListener;
import com.example.bnn.kediri.listener.PickImageListener;
import com.example.bnn.kediri.models.ApiStatus;
import com.example.bnn.kediri.network.BaseApiService;
import com.example.bnn.kediri.network.UtilsApi;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseActivity extends AppCompatActivity {
    public final static String TAG = "BNNKed App";
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int REQUEST_CHOOSE_PHOTO = 2;
    public static final int REQUEST_PICK_LOCATION = 99;
    public static final int MY_PERMISSIONS_REQUEST_STORAGE = 3;
    public String[] permissions = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    public BaseApiService mApiService;
    public Dialog progressDialog;
    public Context context;
    public Sessions sessions;

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        initViews();
    }

    protected void initViews(){
        context = this;
        mApiService = UtilsApi.getApiService();
        sessions = new Sessions(context);
        progressDialog = new Dialog(context);
    }

    public void showProgressBar(boolean show) {
        if (show) {
            showProgress();
        } else {
            progressDialog.dismiss();
        }
    }

    public void showProgress(){
        progressDialog.setContentView(R.layout.dialog);
        TextView message = progressDialog.findViewById(R.id.tv_process);
        message.setText(R.string.label_api_process);
        ProgressBar progressBar = progressDialog.findViewById(R.id.progressBar);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public String updateDateInView(Calendar calendar) {
        String myFormat = "dd MMMM yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        return sdf.format(calendar.getTime());
    }

    public String updateTimeInView(Calendar time) {
        String myFormat = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        return sdf.format(time.getTime());
    }

    public String updateTimeToServer(Calendar time) {
        String myFormat = "HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        return sdf.format(time.getTime());
    }

    public String updateDateToServer(Calendar calendar) {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        return sdf.format(calendar.getTime());
    }

    public void logout(){
        showProgressBar(true);
        mApiService.logout("Bearer " + sessions.getData(Sessions.TOKEN)).enqueue(logoutCallback.build());
    }

    public boolean isStatusCompleted(String status) {
        return status.equalsIgnoreCase("selesai");
    }

    ApiCallback logoutCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            showToast("Logout Berhasil");
            sessions.clearSession();
            Intent i = new Intent(context, MainActivity.class);
            startActivity(i);
            finish();
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            showToast(errorMessage);
        }
    };


    public void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void showToast(int message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void alertSubmitDone(String title, String message, DialogListener listener){
        TextView textView = new TextView(context);
        textView.setText(title);
        textView.setPadding(32, 30, 32, 30);
        textView.setTextSize(20F);
        textView.setBackgroundColor(ContextCompat.getColor(context, R.color.blueSecondary));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.WHITE);

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setCustomTitle(textView)
                .setMessage(message);
        builder.setPositiveButton("Konfirmasi", (dialog, id) -> {
            if (listener != null)
                listener.onPositiveButton();
        });
        builder.setNegativeButton(R.string.label_cancel, (dialog, which) -> {
            if (listener != null)
                listener.onNegativeButton();

            dialog.dismiss();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void alertPickImage(String title, String message, PickImageListener listener){
        TextView textView = new TextView(context);
        textView.setText(title);
        textView.setPadding(32, 30, 32, 30);
        textView.setTextSize(20F);
        textView.setBackgroundColor(ContextCompat.getColor(context, R.color.blueSecondary));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.WHITE);

        View view = LayoutInflater.from(context).inflate(R.layout.custom_dialog_image, null);

        LinearLayout llCamera = view.findViewById(R.id.ll_camera);
        LinearLayout llGallery = view.findViewById(R.id.ll_gallery);
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setCustomTitle(textView)
                .setView(view);

        AlertDialog alertDialog = builder.create();
        llCamera.setOnClickListener(v -> {
            if (listener != null)
                listener.onCamera();
            alertDialog.dismiss();
        });

        llGallery.setOnClickListener(v -> {
            if (listener != null)
                listener.onGallery();
            alertDialog.dismiss();
        });
        alertDialog.show();
    }

    public boolean checkPermission(int requestCode){
        if (!checkAllPermission()) {
            ActivityCompat.requestPermissions((Activity) context,
                    permissions, requestCode);
            return false;
        } else {
            return true;
        }
    }

    public boolean checkAllPermission(){
        for (String permission : permissions) {
            return ContextCompat.checkSelfPermission(context,
                    permission) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(context, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        String result = "";
        int column_index = 0;
        if (cursor != null) {
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            result = cursor.getString(column_index);
            cursor.close();
        }
        return result;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public abstract class ApiCallback{
        private final Callback<ResponseBody> callback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, response.toString());
                if (response.isSuccessful()) {
                    try {
                        String result = response.body().string();
                        Log.d(TAG, result);
                        ApiStatus status = new Gson().fromJson(result, ApiStatus.class);
                        if(status==null){
                            onApiFailure(getString(R.string.error_parsing));
                            return;
                        }
                        if (status.getStatus()!=null) {
                            if (status.getMessage().compareToIgnoreCase("success") == 0) {
                                onApiSuccess(result);
                            } else {
                                onApiFailure(status.getMessage());
                            }
                        } else {
                            onApiFailure(getString(R.string.error_parsing));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d(TAG, e.toString());
                    }
                } else {
                    Log.d(TAG, response.message());
                    String errorMsg = "";

                    switch (response.code()){
                        case 500:
                            errorMsg = getString(R.string.error_500);
                            break;
                        case 400:
                            errorMsg = getString(R.string.error_400);
                            break;
                        case 403:
                            errorMsg = getString(R.string.error_403);
                            break;
                        case 404:
                            errorMsg = getString(R.string.error_404);
                            break;
                        default:
                            errorMsg = getString(R.string.error_xxx);
                            break;
                    }
                    onApiFailure(errorMsg);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, t.getMessage());
                onApiFailure(t.getMessage());
            }
        };

        public Callback<ResponseBody> build() {
            return callback;
        }

        public abstract void onApiSuccess(String result);
        public abstract void onApiFailure(String errorMessage);
    }
}
