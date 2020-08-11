package com.example.studentcommunityapp.ui.article;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.studentcommunityapp.R;
import com.example.studentcommunityapp.bean.Audio;
import com.example.studentcommunityapp.bean.Content;
import com.example.studentcommunityapp.bean.Essay;
import com.example.studentcommunityapp.ui.home.EssayAdapter;

import java.util.ArrayList;
import java.util.List;


public class Article_Fragment2 extends Fragment {
    //private RecyclerView.LayoutManager layoutManager;
    //private List<Content> content=new ArrayList<>();

    public static Article_Fragment2 newInstance() {
        return new Article_Fragment2();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_article_2, container, false);

        /**
         * 正文类的recyclerview
         */

        /*
        initContents();
        RecyclerView recyclerView=root.findViewById(R.id.recycler3);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        ContentAdapter adapter=new ContentAdapter(content);
        recyclerView.setAdapter(adapter);*/

        return root;
    }

    /*private void initContents()
    {
        Content content1= new Content(1,"词法分析与语法分析1");
        content.add(content1);
        Content content2= new Content(2, "词法分析与语法分析2");
        content.add(content2);
        Content content3= new Content(3, "词法分析与语法分析3");
        content.add(content3);

    }*/
}