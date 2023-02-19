package com.example.mnrhbsensor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class CropView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_view);

        ImageView croppedImage = findViewById(R.id.croppedImage);
        Button homeBtn = findViewById(R.id.homeBtn);

        croppedImage.setImageBitmap(MemoryData.getBitmap(CropView.this));


        homeBtn.setOnClickListener(view -> {
            startActivity(new Intent(CropView.this, MainActivity.class));
            finish();
        });
    }
}