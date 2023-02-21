package com.example.mnrhbsensor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ResultActivity extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://hbsensorprototype-default-rtdb.firebaseio.com/");
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

        databaseReference.child("userdata").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String id = MemoryData.getUserId(ResultActivity.this);
                databaseReference.child("userdata").child(id).child("hemoLevel").setValue(res);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        homeBtn.setOnClickListener(view -> {
            startActivity(new Intent(ResultActivity.this, MainActivity.class));
            finish();
        });

    }
}