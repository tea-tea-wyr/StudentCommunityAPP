package com.example.studentcommunityapp.ui.course;


import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentcommunityapp.R;
import com.example.studentcommunityapp.bean.Course;
import com.example.studentcommunityapp.ui.user.UserViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class CourseFragment extends Fragment {

    private UserViewModel courseViewModel;

    private List<Course> mData;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CourseAdapter courseAdapter;
    private Course showCourse;
    private List<Course> courseList = new ArrayList<>();
    ArrayList<Course> course_list = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        courseViewModel =
                ViewModelProviders.of(this).get(UserViewModel.class);
//        startActivity(new Intent(getActivity(), CourseActivity.class));
        View root = inflater.inflate(R.layout.fragment_course, container, false);


        final FloatingActionButton button = root.findViewById(R.id.course_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_course_to_addCourseFragment);
            }
        });

//        RxVolley.get( "http://81.70.27.208:8000/api/get_classtable", new HttpCallback() {		//url为要请求的网址
//            //成功返回json数据--onSuccess为重写方法
//            @Override
//            public void onSuccess(String t) {
//                Log.d(TAG,"dayi");
//                Loger.debug("请求到的数据"+t);		//t为请求成功时获得的json数据
//                initData(t);
//            }
//        });

        initData("http://81.70.27.208:8000/api/get_classtable");
//        getUserData();
        mRecyclerView = root.findViewById(R.id.course_recyclerview);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        courseAdapter = new CourseAdapter(course_list);
        mRecyclerView.setAdapter(courseAdapter);
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
                        JSONArray jsonArray = new JSONArray(String.valueOf(jsonObject.getJSONObject("data").get("user_classtable")));
//                        ArrayList<Course> course_list = new ArrayList<>();
                        for (int i = 0; i<jsonArray.length();i++)
                        {
                            JSONObject object = (JSONObject) jsonArray.get(i);
                            showCourse = new Course(object);
                            course_list.add(new Course(showCourse.getWeek_course(),showCourse.getWeek_day(),showCourse.getTime(),showCourse.getPlace()));
                        }
                        System.out.println(course_list.size());
                        courseList.clear();
                        courseList.addAll(course_list);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                courseAdapter.notifyDataSetChanged();
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


//    private void initData(){
//        mData = new ArrayList<>();
//        for(int i=0; i<10; i++) {
//            mData.add(new Course("编译原理","2","1-3节课（1-15周）","A阶101"));
//        }
//
//    }

//    public void getUserData(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    OkHttpClient client = new OkHttpClient();
//                    Request request =  new Request.Builder().url("http://81.70.27.208:8000/api/get_classtable").build();
//                    Response response = null;
//                    response = client.newCall(request).execute();
//                    if(response.isSuccessful()){
//                        Log.d("kwwl","response.code()=="+response.code());
//                        Log.d("kwwl","response.message()=="+response.message());
//                        JSONObject jsonObject = new JSONObject(response.body().string());
//                        String state = String.valueOf(jsonObject.get("status"));
//                        if(state.equals("1")){
//                            JSONArray jsonArray = new JSONArray(String.valueOf(jsonObject.getJSONObject("data").get("user_classtable")));
//                            System.out.println(jsonArray);
//                            JSONObject object = (JSONObject) jsonArray.get(0);
//                            showCourse = new Course(object);
//                            course_list.add(new Course(showCourse.getWeek_course(),showCourse.getWeek_day(),showCourse.getTime(),showCourse.getPlace()));
//
//
//
//                        }
//                    }
//                } catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }
