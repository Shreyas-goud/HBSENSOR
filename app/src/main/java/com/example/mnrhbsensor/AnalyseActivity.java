package com.example.mnrhbsensor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AnalyseActivity extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://hbsensorprototype-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyse);

        final EditText fullname = findViewById(R.id.fullname);
        final EditText age = findViewById(R.id.age);
        final EditText gender = findViewById(R.id.gender);
        final Button resultBtn = findViewById(R.id.resultBtn);

        resultBtn.setOnClickListener(view -> {
            startActivity(new Intent(AnalyseActivity.this,ResultActivity.class));
        });

    }
}