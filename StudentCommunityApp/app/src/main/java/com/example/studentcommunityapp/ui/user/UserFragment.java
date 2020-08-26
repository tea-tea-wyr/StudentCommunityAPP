package com.example.studentcommunityapp.ui.user;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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

import com.bumptech.glide.Glide;
import com.example.studentcommunityapp.R;
import com.example.studentcommunityapp.bean.Article;
import com.example.studentcommunityapp.bean.User;
import com.example.studentcommunityapp.service.UserInfo;
import com.example.studentcommunityapp.ui.login.LoginFragment;
import com.example.studentcommunityapp.util.CircleImageView;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;

import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserFragment extends Fragment {
    private Handler handler;
    private String TAG = "UserFragment";
    private User mUserData;
    private UserViewModel userViewModel;
    private List<Article> mData;
    private Article showarticle;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private UserAdapter userAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        userViewModel =
                ViewModelProviders.of(this).get(UserViewModel.class);
        View root = inflater.inflate(R.layout.fragment_user, container, false);
        getUserData(root);
//        initData("http://81.70.27.208:8000/api/get_user_publish?op=1&userid=000002");
        initData();
        mRecyclerView = root.findViewById(R.id.user_recyclerview);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        userAdapter = new UserAdapter(mData);
        mRecyclerView.setAdapter(userAdapter);
        return root;
    }
    public void initData(String url) {
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)//要访问的链接
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = client.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
            }
            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {

                String res = response.body().string();
                Log.d(TAG, "onResponse: " + res);
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    String state = String.valueOf(jsonObject.get("status"));
                    if(state.equals("1")){
                        JSONArray jsonArray = new JSONArray(String.valueOf(jsonObject.getJSONObject("data").get("data")));
                        for (int i = 0; i<jsonArray.length();i++)
                        {
                            JSONObject object = (JSONObject) jsonArray.get(i);
                            showarticle = new Article(object);
                            mData.add(new Article(showarticle.getImgUrl(),showarticle.getName(),showarticle.getType(),showarticle.getTitle(),showarticle.getPicture(),showarticle.getContent()));
                        }
//                        System.out.println(mData.size());
//                        mData.clear();
//                        mData.addAll(mData);
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                userAdapter.notifyDataSetChanged();
//                            }
//                        });
                    }
                    else {
                        Log.d(TAG, "null");
                    }
                } catch (JSONException e){
                    Log.e(TAG, "onResponse_catch: ", e);
//                    e.printStackTrace();
                }

            }
        });



    }



    private void initData() {
        String url = "http://81.70.27.208:8000/api/get_user_info?op=1&userid=000006";
        mData = new ArrayList<>();
        for(int i=0; i<50; i++){
            mData.add(new Article("http://81.70.27.208:8088/icon/car.jpg","小西","发表了文章","C语言基础","http://world.people.com.cn/NMediaFile/2018/0913/MAIN201809131000000359718834615.jpg","C语言是一种计算机程序设计语言，它既有高级语言的特点，又具有汇编......"));
        }
    }

    public void getUserData(final View view) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://81.70.27.208:8000/api/get_user_info?op=1&userid=000006").build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String res = response.body().string();
                Log.d(TAG, "onResponse: " + res);
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    String state = String.valueOf(jsonObject.get("status"));
                    if(state.equals("1")){
                        JSONArray jsonArray = new JSONArray(String.valueOf(jsonObject.getJSONObject("data").get("user_info")));
                        JSONObject object = (JSONObject) jsonArray.get(0);
                        final User user = new User(object);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                final TextView name = view.findViewById(R.id.user_header_name);
                                final CircleImageView icon = view.findViewById(R.id.user_header_avatar);
                                final TextView level = view.findViewById(R.id.user_header_level);
                                final TextView university = view.findViewById(R.id.user_header_university);
                                final TextView college = view.findViewById(R.id.user_header_college);
                                final TextView Class = view.findViewById(R.id.user_header_class);
                                name.setText(user.getName());
                                Log.d(TAG,"年级： "+user.getLevel());
                                String year = user.getLevel()+"级";
                                level.setText(year);
                                university.setText(user.getUniversity());
                                college.setText(user.getCollege());
                                Class.setText(user.getaClass());
                                Glide.with(view)
                                        .load(user.getImgUrl())
                                        .into(icon);
                            }
                        });
                    }
                    else {
                        Log.d(TAG, "null");
                    }
                } catch (JSONException e){
                    Log.e(TAG, "onResponse_catch: ", e);
//                    e.printStackTrace();
                }

            }
        });
    }

}
