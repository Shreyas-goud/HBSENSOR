package com.example.mnrhbsensor;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://hbsensorprototype-default-rtdb.firebaseio.com/");

    private static final int CAMERA_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText phone = findViewById(R.id.phone);
        final EditText password = findViewById(R.id.password);
        final Button loginBtn = findViewById(R.id.loginBtn);
        final TextView registerNowBtn = findViewById(R.id.registerNowBtn);

        //to show loading on screen
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");

        //check if user already logged in
        if(!MemoryData.getData(this).isEmpty()) {
            String phoneTxt = MemoryData.getData(this);
            databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String email = snapshot.child(phoneTxt).child("email").getValue(String.class);
                    String username = snapshot.child(phoneTxt).child("fullname").getValue(String.class);
                    assert email != null;
                    MemoryData.saveEmail(email,LoginActivity.this);
                    assert username != null;
                    MemoryData.saveUsername(username,LoginActivity.this);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        loginBtn.setOnClickListener(view -> {

            final String phoneTxt = phone.getText().toString();
            final String passwordTxt = password.getText().toString();

            if (phoneTxt.isEmpty() || passwordTxt.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            }
            else if(!Validator.phoneValidator(phone)){
                Toast.makeText(this, "Enter valid mobile number !", Toast.LENGTH_SHORT).show();
            }
            else{
                //shows loading screen
                progressDialog.show();
                databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        progressDialog.dismiss();

                        //check if mobile is registered in database
                        if(snapshot.hasChild(phoneTxt)){
                            //user already exists
                            //now get password of user and match with entered password
                            final String getPassword;
                            try {
                                getPassword = AESCrypt.decrypt(snapshot.child(phoneTxt).child("password").getValue(String.class));
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }

                            if(getPassword.equals(passwordTxt)){

                                //Remembering user
                                MemoryData.saveData(phoneTxt,LoginActivity.this);
                                databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String email = snapshot.child(phoneTxt).child("email").getValue(String.class);
                                        String username = snapshot.child(phoneTxt).child("fullname").getValue(String.class);
                                        assert email != null;
                                        MemoryData.saveEmail(email,LoginActivity.this);
                                        assert username != null;
                                        MemoryData.saveUsername(username,LoginActivity.this);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                                //checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);
                                Toast.makeText(LoginActivity.this,"Logged in successfully",Toast.LENGTH_SHORT).show();
                                //open main activity on successful login
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                Toast.makeText(LoginActivity.this,"Wrong Password", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(LoginActivity.this,"User does not exist",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        progressDialog.dismiss();
                    }
                });
            }
        });

        registerNowBtn.setOnClickListener(view -> {
            //shows loading screen
            progressDialog.show();
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });

    }

    // Function to check and request permission
    public void checkPermission(String permission, int requestCode)
    {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(LoginActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(LoginActivity.this, new String[] { permission }, requestCode);
        }
        else {
            Toast.makeText(LoginActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

    // This function is called when user accept or decline the permission.
// Request Code is used to check which permission called this function.
// This request code is provided when user is prompt for permission.
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {

            // Checking whether user granted the permission or not.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // Showing the toast message
                Toast.makeText(LoginActivity.this, "Camera Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LoginActivity.this, "Camera Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}