package com.example.mnrhbsensor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        final TextView fullname = findViewById(R.id.fullname);
        final TextView age = findViewById(R.id.result);
        final Button homeBtn = findViewById(R.id.homeBtn);

        fullname.setText(MemoryData.getHexColor(ResultActivity.this));

        homeBtn.setOnClickListener(view -> {
            startActivity(new Intent(ResultActivity.this, MainActivity.class));
            finish();
        });

    }
}