package com.example.bnn.kediri.activities.users;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bnn.kediri.R;
import com.example.bnn.kediri.controller.BaseActivity;
import com.example.bnn.kediri.databinding.ActivityFormAbuseBinding;
import com.example.bnn.kediri.listener.PickImageListener;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.lang.reflect.Array;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FormAbuseActivity extends BaseActivity {

    ActivityFormAbuseBinding binding;

    private Spinner spinner;
    private Spinner spinner2;
    Uri uri;
    File _filePhoto;
    double _latitude = 0;
    double _longitude = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFormAbuseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        binding.btnPickLocation.setOnClickListener(view -> {
//            Intent intent = new Intent(context, MapsActivity.class);
//            startActivityForResult(intent, REQUEST_PICK_LOCATION);
//        });

        binding.btnPickPhoto.setOnClickListener(view -> {
            alertPickImage("Pick Image", "", new PickImageListener() {
                @Override
                public void onCamera() {
                    if (checkPermission(1))
                        dispatchTakePictureIntent();
                }

                @Override
                public void onGallery() {
                    pickImageGallery();
                }
            });
        });

        binding.ivRemoveImage.setOnClickListener(v -> {
            binding.rlPreviewImage.setVisibility(View.GONE);
            _filePhoto = null;
        });

        binding.btnBack.setOnClickListener(view -> finish());

        binding.btnSubmit.setOnClickListener(view -> {
            if (validate()) {
                postAbuse();
            }
        });

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.spinner_pelayanan, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(adapter);

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                binding.spinner.setSelection(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
                this, R.array.spinner_kecamatan, R.layout.spinner_item2);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner2.setAdapter(adapter1);

        binding.spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int a, long b) {
                binding.spinner2.setSelection(a);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private boolean validate() {
        boolean isValid = false;
        String phoneNumber = binding.etPhone.getText().toString();
//        String district = binding.etDeskripsi.getText().toString();
//        String address = binding.etAddress.getText().toString();

        if (phoneNumber.isEmpty()) {
            binding.etPhone.setError("Nomor telepon harus diisi");
        } else if (phoneNumber.length() < 15) {
            binding.etPhone.setError("Nomor telepon tidak valid");
        } else {
            binding.etPhone.setError(null);
        }

        //if (district.isEmpty()) {
        //    binding.etKecamatan.setError("Kecamatan harus diisi");
        //} else {
        //    binding.etKecamatan.setError(null);
        //}

        if (_filePhoto.length() == 0) {
            Snackbar.make(binding.btnSubmit, "Gambar belum di pilih", Snackbar.LENGTH_SHORT).show();
        }

//        if (address.isEmpty()) {
//            binding.etAddress.setError("Alamat harus diisi");
//        } else {
//            binding.etAddress.setError(null);
//            isValid = true;
//        }
        return isValid;
    }



    private void postAbuse() {
        showProgressBar(true);
        String phoneNumber = binding.etPhone.getText().toString();
//        String address = binding.etAddress.getText().toString();
//        String district = binding.etKecamatan.getText().toString();
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), _filePhoto);
//        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body = MultipartBody.Part.createFormData("foto", _filePhoto.getName(), requestFile);
        RequestBody rbPhone = RequestBody.create(MultipartBody.FORM, phoneNumber);
//        RequestBody rbAddress = RequestBody.create(MultipartBody.FORM, address);
//        RequestBody rbDistrict = RequestBody.create(MultipartBody.FORM, kecamatan);
        RequestBody rbLongitude = RequestBody.create(MultipartBody.FORM, String.valueOf(_longitude));
        RequestBody rbLatitude = RequestBody.create(MultipartBody.FORM, String.valueOf(_latitude));

        showProgressBar(true);
        mApiService.postAbuse(rbPhone, rbLongitude, rbLatitude, body).enqueue(postAbuse.build());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_STORAGE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (checkAllPermission()) {
                    dispatchChoosePictureIntent();
                }
            } else {
                showToast(R.string.error_permission_denied);
            }
        }
    }

    private void pickImageGallery() {
        if(checkPermission(2))
            dispatchChoosePictureIntent();
    }

    private void dispatchChoosePictureIntent(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.title_choose_image)),
                REQUEST_CHOOSE_PHOTO);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHOOSE_PHOTO && resultCode == RESULT_OK && data != null){
            uri = data.getData();
            if (uri != null) {
                _filePhoto = new File(getRealPathFromURI(uri));
                binding.rlPreviewImage.setVisibility(View.VISIBLE);
                binding.ivPreview.setImageURI(uri);
            }
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data!=null) {
            Bundle extras = data.getExtras();
            binding.rlPreviewImage.setVisibility(View.VISIBLE);
            Bitmap imageBitmap = (Bitmap) extras.get("data");;
            uri = getImageUri(getApplicationContext(), imageBitmap);
            _filePhoto = new File(getRealPathFromURI(uri));
//            binding.ivPreview.setImageURI(uri);
            binding.ivPreview.setImageBitmap(imageBitmap);
        } else if (requestCode == REQUEST_PICK_LOCATION && resultCode == RESULT_OK && data!=null) {
            Uri _uri = data.getData();
            String[] latLng = _uri.toString().split(",");
            String latitude = latLng[0];
            String longitude = latLng[1];
//            binding.btnPickLocation.setText("Titik Lokasi Latitude("+latitude+") & Longitude("+longitude+")");
            _latitude = Double.parseDouble(latitude);
            _longitude = Double.parseDouble(longitude);
        }
    }

    ApiCallback postAbuse = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            showToast("Laporan Penyalahgunaan berhasil di laporkan");
            finish();
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            showToast(errorMessage);
        }
    };
}