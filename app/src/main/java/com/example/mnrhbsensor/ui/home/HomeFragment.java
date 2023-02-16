package com.example.mnrhbsensor.ui.home;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mnrhbsensor.AnalyseActivity;
import com.example.mnrhbsensor.ColorFinder;
import com.example.mnrhbsensor.MainActivity;
import com.example.mnrhbsensor.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    final int CAMERA_CAPTURE = 1;
    final int PIC_CROP = 2;
    private Uri picUri;

    public MainActivity activity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (MainActivity) activity;
    }
    ImageView imageView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        imageView = binding.imageTaken;
        //final TextView textView = binding.textHome;
        final Button cameraBtn = binding.cameraBtn;
        final Button analyseBtn = binding.analyseBtn;

        cameraBtn.setOnClickListener(view -> {
            try {
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera, CAMERA_CAPTURE);
            } catch (ActivityNotFoundException anfe) {
                String errorMessage = "Your device doesn't support capturing images!";
                Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        analyseBtn.setOnClickListener(view ->{
            startActivity(new Intent(activity, AnalyseActivity.class));
        });

        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //Bitmap photo = (Bitmap) data.getExtras().get("data");
            //clickedImg.setImageBitmap(photo);
            if (requestCode == CAMERA_CAPTURE) {
                picUri = data.getData();
                //performCrop();
                Bundle extras = data.getExtras();
                Bitmap photo = extras.getParcelable("data");
                imageView.setImageBitmap(photo);
                new ColorFinder(new ColorFinder.CallbackInterface() {
                    @Override
                    public void onCompleted(String color) {
                        Toast.makeText(activity, "Color : " + color, Toast.LENGTH_SHORT).show();
                    }
                }).findDominantColor(photo);
            }
        }
    }
}