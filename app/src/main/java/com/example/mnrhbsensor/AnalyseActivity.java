package com.example.mnrhbsensor;

import static java.lang.Integer.parseInt;
import static java.lang.System.currentTimeMillis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class AnalyseActivity extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://hbsensorprototype-default-rtdb.firebaseio.com/");
    private boolean flag = false;
    StorageReference reference = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyse);

        final EditText fullname = findViewById(R.id.fullname);
        final EditText age = findViewById(R.id.age);
        final EditText gender = findViewById(R.id.gender);
        final Button resultBtn = findViewById(R.id.resultBtn);

        resultBtn.setOnClickListener(view -> {
            Uri pic = MemoryData.getURI(AnalyseActivity.this);
            //uploadToDatabase(pic);
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
                        MemoryData.saveName(AnalyseActivity.this,fullnameTxt);
                        MemoryData.saveUserId(AnalyseActivity.this, key);
                        databaseReference.child("userdata").child(key).child("fullname").setValue(fullnameTxt);
                        databaseReference.child("userdata").child(key).child("age").setValue(p_age);
                        databaseReference.child("userdata").child(key).child("gender").setValue(genderTxt);
                        databaseReference.child("userdata").child(key).child("imageUrl").setValue(MemoryData.getImageUrl(AnalyseActivity.this));
                        startActivity(new Intent(AnalyseActivity.this, ResultActivity.class));
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

        });

    }
    private void uploadToDatabase(Uri uri){
        StorageReference fileRef = reference.child(System.currentTimeMillis() + "." +getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        MemoryData.saveImageUrl(AnalyseActivity.this,uri.toString());
                        Toast.makeText(AnalyseActivity.this, "uploaded successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AnalyseActivity.this, "image upload failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private String getFileExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));

      /*  ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] data = stream.toByteArray();
        StorageReference imageRef = reference.child("images/" + System.currentTimeMillis());
        Task<Uri> urlTask = imageRef.putBytes(data).continueWithTask(task -> {
            if (!task.isSuccessful()) {
                throw task.getException();
            }

            // Continue with the task to get the download URL
            return imageRef.getDownloadUrl();
        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Uri downloadUri = task.getResult();
                String uri = downloadUri.toString();
                MemoryData.saveImageUrl(AnalyseActivity.this,uri);
            } else {
                // Handle failures
                // ...
            }
        }); */
    }
}