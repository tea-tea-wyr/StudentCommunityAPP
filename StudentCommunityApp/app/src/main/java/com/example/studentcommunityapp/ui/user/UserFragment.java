package com.example.studentcommunityapp.ui.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentcommunityapp.R;
import com.example.studentcommunityapp.bean.Article;

import java.util.ArrayList;
import java.util.List;

public class UserFragment extends Fragment {

    private UserViewModel userViewModel;
    private List<Article> mData;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private UserAdapter userAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        userViewModel =
                ViewModelProviders.of(this).get(UserViewModel.class);
        View root = inflater.inflate(R.layout.fragment_user, container, false);
        initData();
        mRecyclerView = root.findViewById(R.id.user_recyclerview);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        userAdapter = new UserAdapter(mData);
        mRecyclerView.setAdapter(userAdapter);
        return root;
    }

    private void initData(){
        mData = new ArrayList<>();
        for(int i=0; i<50; i++){
            mData.add(new Article("http://world.people.com.cn/NMediaFile/2019/0610/MAIN201906100858000177823780005.jpg","小西","发表了文章","C语言基础","http://world.people.com.cn/NMediaFile/2018/0913/MAIN201809131000000359718834615.jpg","C语言是一种计算机程序设计语言，它既有高级语言的特点，又具有汇编......"));
        }

    }

}
