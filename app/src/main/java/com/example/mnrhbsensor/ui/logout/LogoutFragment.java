package com.example.mnrhbsensor.ui.logout;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mnrhbsensor.LoginActivity;
import com.example.mnrhbsensor.MainActivity;
import com.example.mnrhbsensor.MemoryData;
import com.example.mnrhbsensor.databinding.FragmentLogoutBinding;

import java.io.FileNotFoundException;


public class LogoutFragment extends Fragment {
    private FragmentLogoutBinding binding;

    public MainActivity activity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (MainActivity) activity;
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LogoutViewModel logoutViewModel =
                new ViewModelProvider(this).get(LogoutViewModel.class);

        binding = FragmentLogoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //to show loading on screen
        ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");

        final TextView textView = binding.viewLogout;
        final Button logoutBtn = binding.logoutBtn;

        logoutBtn.setOnClickListener(view -> {
            //shows loading screen
            progressDialog.show();
            try {
                MemoryData.clearUserData(activity);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            Intent intent = new Intent(activity, LoginActivity.class);
            startActivity(intent);
        });

        //logoutViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
