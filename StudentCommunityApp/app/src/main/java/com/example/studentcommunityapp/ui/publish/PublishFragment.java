package com.example.studentcommunityapp.ui.publish;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.studentcommunityapp.R;

public class PublishFragment extends Fragment {

    private PublishViewModel publishViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        publishViewModel =
                ViewModelProviders.of(this).get(PublishViewModel.class);
        View root = inflater.inflate(R.layout.fragment_publish, container, false);
        final TextView textView = root.findViewById(R.id.text_publish);
        publishViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
