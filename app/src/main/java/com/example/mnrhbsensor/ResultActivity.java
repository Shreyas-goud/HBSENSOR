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
        final TextView result = findViewById(R.id.result);
        final Button homeBtn = findViewById(R.id.homeBtn);

        fullname.setText(MemoryData.getPname(ResultActivity.this));
        String res = String.valueOf(Hex.getHemoLevel(ResultActivity.this));
        result.setText(res);

        homeBtn.setOnClickListener(view -> {
            startActivity(new Intent(ResultActivity.this, MainActivity.class));
            finish();
        });

    }
}