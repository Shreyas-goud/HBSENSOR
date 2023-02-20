package com.example.mnrhbsensor;

import static java.lang.Integer.parseInt;
import static java.lang.System.currentTimeMillis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AnalyseActivity extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://hbsensorprototype-default-rtdb.firebaseio.com/");
    private boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyse);

        final EditText fullname = findViewById(R.id.fullname);
        final EditText age = findViewById(R.id.age);
        final EditText gender = findViewById(R.id.gender);
        final Button resultBtn = findViewById(R.id.resultBtn);

        resultBtn.setOnClickListener(view -> {
            final String fullnameTxt = fullname.getText().toString();
            final String p_age = age.getText().toString();
            final String genderTxt = gender.getText().toString();

            if(fullnameTxt.isEmpty() || p_age.isEmpty() || genderTxt.isEmpty()){
                Toast.makeText(AnalyseActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            }else{
                databaseReference.child("userdata").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String key = String.valueOf(currentTimeMillis());
                        databaseReference.child("userdata").child(key).child("fullname").setValue(fullnameTxt);
                        databaseReference.child("userdata").child(key).child("age").setValue(p_age);
                        databaseReference.child("userdata").child(key).child("gender").setValue(genderTxt);

                        MemoryData.saveName(AnalyseActivity.this,fullnameTxt);
                        flag = true;
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
            if(flag) {
                startActivity(new Intent(AnalyseActivity.this, ResultActivity.class));
                finish();
            }else{
                Toast.makeText(this, "Please enter the details before proceeding further", Toast.LENGTH_SHORT).show();
            }
        });

    }
}