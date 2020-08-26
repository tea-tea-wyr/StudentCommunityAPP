package com.example.studentcommunityapp.ui.article;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentcommunityapp.R;
import com.example.studentcommunityapp.ui.comment.Comment;
import com.example.studentcommunityapp.ui.comment.CommentAdapter;
import com.example.studentcommunityapp.util.WebHelper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Article_Fragment1 extends Fragment {

    private List<Comment> comment=new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;
    //private TextView submit,time,content,id;
    private RecyclerView recyclerView;

    public static Article_Fragment1 newInstance() {
        return new Article_Fragment1();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_article_1, container, false);

        //评论框
        /*final EditText et=root.findViewById(R.id.edit_comment);
        submit=root.findViewById(R.id.textView9);
        time=root.findViewById(R.id.comment_time);
        content=root.findViewById(R.id.comment_content);
        id=root.findViewById(R.id.comment_id);*/
        initComments();
        recyclerView=root.findViewById(R.id.comment_recyclerview);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        CommentAdapter adapter=new CommentAdapter(comment);
        recyclerView.setAdapter(adapter);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void initComments()
    {
        Comment comment1=new Comment("2017040315","2020-08-25","写得不错！");
        comment.add(comment1);
        Comment comment2=new Comment("2017040321","2020-08-25","受益匪浅！");
        comment.add(comment2);
        Comment comment3=new Comment("2017040333","2020-08-25","点赞！");
        comment.add(comment3);
    }

}