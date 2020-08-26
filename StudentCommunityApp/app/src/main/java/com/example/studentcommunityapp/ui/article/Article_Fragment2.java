package com.example.studentcommunityapp.ui.article;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.studentcommunityapp.R;

public class Article_Fragment2 extends Fragment {

    public static Article_Fragment2 newInstance() {
        return new Article_Fragment2();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_article_2, container, false);


        return root;
    }
}