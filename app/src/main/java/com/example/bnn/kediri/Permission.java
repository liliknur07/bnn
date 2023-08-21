package com.example.bnn.kediri;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Permission extends AppCompatActivity {

    CheckBox cb1;
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permission);

        cb1 = (CheckBox) findViewById(R.id.checkBox);


        cb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lanjut1 = new Intent(Permission.this, MainActivity.class);
                startActivity(lanjut1);
            }
        });
    }
}
