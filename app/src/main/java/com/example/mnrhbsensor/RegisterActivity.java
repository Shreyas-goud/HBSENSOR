package com.example.mnrhbsensor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://hbsensorprototype-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText fullname = findViewById(R.id.fullname);
        final EditText phone = findViewById(R.id.phone);
        final EditText email = findViewById(R.id.email);
        final EditText password = findViewById(R.id.password);
        final EditText repassword = findViewById(R.id.repassword);

        final Button registerBtn = findViewById(R.id.registerBtn);
        final TextView loginNowBtn = findViewById(R.id.loginNowBtn);

        //to show loading on screen
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");


        registerBtn.setOnClickListener(view -> {

            final String fullnameTxt = fullname.getText().toString();
            final String emailTxt = email.getText().toString();
            final String phoneTxt = phone.getText().toString();
            final String passwordTxt = password.getText().toString();
            final String repasswordTxt = repassword.getText().toString();

            if(fullnameTxt.isEmpty() || emailTxt.isEmpty() || phoneTxt.isEmpty() || passwordTxt.isEmpty() || repasswordTxt.isEmpty()){
                Toast.makeText(RegisterActivity.this, "Please fill all fields",Toast.LENGTH_SHORT).show();
            }
            else if(!passwordTxt.equals(repasswordTxt)){
                Toast.makeText(RegisterActivity.this,"Passwords are not matching",Toast.LENGTH_SHORT).show();
            }
            else if(!Validator.emailValidator(email)){
                Toast.makeText(this, "Enter valid Email address !", Toast.LENGTH_SHORT).show();
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

                        //Check if phone is not registered before
                        if(snapshot.hasChild(phoneTxt)){
                            Toast.makeText(RegisterActivity.this, "Phone is already registered", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            //Remembering user info
                            MemoryData.saveData(phoneTxt,RegisterActivity.this);
                            MemoryData.saveEmail(emailTxt,RegisterActivity.this);
                            MemoryData.saveUsername(fullnameTxt,RegisterActivity.this);

                            //Sending data to firebase Realtime Database
                            //We are using phone number as unique identity of every user
                            //so all the other fields of the user comes under phone number
                            databaseReference.child("users").child(phoneTxt).child("fullname").setValue(fullnameTxt);
                            databaseReference.child("users").child(phoneTxt).child("email").setValue(emailTxt);
                            databaseReference.child("users").child(phoneTxt).child("phone").setValue(phoneTxt);
                            try {
                                databaseReference.child("users").child(phoneTxt).child("password").setValue(AESCrypt.encrypt(passwordTxt));
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }

                            //show a success message then finish the activity
                            Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        progressDialog.dismiss();
                    }
                });
            }
        });

        loginNowBtn.setOnClickListener(view -> {
            progressDialog.show();
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });
    }
}