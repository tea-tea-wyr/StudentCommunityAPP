package com.example.studentcommunityapp.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.studentcommunityapp.R;
import com.example.studentcommunityapp.bean.Audio;
import com.example.studentcommunityapp.bean.Essay;
import com.example.studentcommunityapp.bean.LoginState;
import com.example.studentcommunityapp.bean.Video;
import com.example.studentcommunityapp.service.loginstatemessage;
import com.google.gson.Gson;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment {
    private AlertDialog.Builder builder;
    private SharedPreferences Infos;
    private HomeViewModel homeViewModel;
    private ViewPager mViewPaper;
    private List<ImageView> images;
    private List<View> dots;
    private List<Essay> essay=new ArrayList<>();
    private List<Video> video=new ArrayList<>();
    private List<Audio> audio=new ArrayList<>();
    private RecyclerView mRecyclerView1;
    private RecyclerView mRecyclerView2;
    private RecyclerView mRecyclerView3;
    private Essay showEssay;
    private ArrayList<Essay> essay_list=new ArrayList<>();
    private EssayAdapter essayAdapter;
    private Video showVideo;
    private ArrayList<Video> video_list=new ArrayList<>();
    private VideoAdapter videoAdapter;
    private Audio showAudio;
    private ArrayList<Audio> audio_list=new ArrayList<>();
    private AudioAdapter audioAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.LayoutManager layoutManager1;
    private RecyclerView.LayoutManager layoutManager2;
    private int currentItem;    //记录上一次点的位置
    private int oldPosition = 0;
    private int[] imageIds = new int[]{
            R.drawable.a,
            R.drawable.b,
            R.drawable.c
    };    //存放图片的id

    private ViewPagerAdapter adapter;
    private ScheduledExecutorService scheduledExecutorService;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        final ImageView image=root.findViewById(R.id.imageView2);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_articleMoreFragment);
            }
        });
        final ImageView image1=root.findViewById(R.id.imageView3);
        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_videoMoreFragment);
            }
        });

        final ImageView image2=root.findViewById(R.id.imageView5);
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_audioMoreFragment);
            }
        });

        final Button login = root.findViewById(R.id.home_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.action_navigation_home_to_loginFragment);
            }
        });

        /**
         * 轮播图部分
         */
        mViewPaper = root.findViewById(R.id.vp);
        //显示的图片
        images = new ArrayList<ImageView>();
        for(int i = 0; i < imageIds.length; i++){
            ImageView imageView = new ImageView(getActivity());
            imageView.setBackgroundResource(imageIds[i]);
            images.add(imageView);
        }
        //显示的小点
        dots = new ArrayList<View>();
        dots.add(root.findViewById(R.id.dot_0));
        dots.add(root.findViewById(R.id.dot_1));
        dots.add(root.findViewById(R.id.dot_2));

        adapter = new ViewPagerAdapter();
        mViewPaper.setAdapter(adapter);

        mViewPaper.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                dots.get(position).setBackgroundResource(R.drawable.indicator_select);
                dots.get(oldPosition).setBackgroundResource(R.drawable.indicator_no_select);

                oldPosition = position;
                currentItem = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

        /**
         * 文章类的recyclerview
         */
        initEssay("http://81.70.27.208:8000/api/get_part_data?op=article&max=3");
        mRecyclerView1=root.findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        mRecyclerView1.setLayoutManager(layoutManager);
        essayAdapter=new EssayAdapter(essay);
        mRecyclerView1.setAdapter(essayAdapter);

        /**
         * 视频类的recyclerview
         */
        initVideo("http://81.70.27.208:8000/api/get_part_data?op=video&max=8");
        mRecyclerView2=root.findViewById(R.id.recycler1);
        layoutManager1=new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView2.setLayoutManager(layoutManager1);
        videoAdapter=new VideoAdapter(video);
        mRecyclerView2.setAdapter(videoAdapter);

        /**
         * 音频类的recyclerview
         */
        initAudio("http://81.70.27.208:8000/api/get_part_data?op=audio&max=8");
        mRecyclerView3=root.findViewById(R.id.recycler2);
        layoutManager2=new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView3.setLayoutManager(layoutManager2);
        audioAdapter=new AudioAdapter(audio);
        mRecyclerView3.setAdapter(audioAdapter);
        return root;
    }

    /**
     * 自定义Adapter
     *
     */
    public class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            // TODO Auto-generated method stub
//			super.destroyItem(container, position, object);
//			view.removeView(view.getChildAt(position));
//			view.removeViewAt(position);
            view.removeView(images.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            // TODO Auto-generated method stub
            view.addView(images.get(position));
            return images.get(position);
        }

    }

    /**
     * 利用线程池定时执行动画轮播
     */
    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(
                new ViewPageTask(),
                2,
                2,
                TimeUnit.SECONDS);
    }


    /**
     * 图片轮播任务
     */
    private class ViewPageTask implements Runnable{

        @Override
        public void run() {
            currentItem = (currentItem + 1) % imageIds.length;
            mHandler.sendEmptyMessage(0);
        }
    }

    /**
     * 接收子线程传递过来的数据
     */
    private Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            mViewPaper.setCurrentItem(currentItem);
        };
    };
    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        if(scheduledExecutorService != null){
            scheduledExecutorService.shutdown();
            scheduledExecutorService = null;
        }
    }

    public void GetLoginState(loginstatemessage msg) {
        String res = msg.res;
        Gson gson = new Gson();
        LoginState state = gson.fromJson(res, LoginState.class);
        Boolean islogin = state.getData().getIs_login();
        System.out.println("登录状态" + state.getData().getIs_login());/**/
        if (!islogin) {
            Infos.edit().putString("user", "").apply();//首次启动
            builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("请先登录");// 设置标题
            builder.setCancelable(false);
            // 为对话框设置取消按钮
            builder.setPositiveButton("去登录", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    Navigation.findNavController(getView()).navigate(R.id.action_navigation_home_to_loginFragment);
                }
            });
            Looper.prepare();
            builder.create().show();// 使用show()方法   显示对话框
            Looper.loop();
        }
    }

    public void initEssay(String url) {
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)//要访问的链接
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
                        JSONArray jsonArray = new JSONArray(String.valueOf(jsonObject.getJSONObject("data").get("data_info")));
                        Log.d(TAG,"获取数据"+jsonArray);
                        for (int i = 0; i<jsonArray.length();i++)
                        {
                            JSONObject object = (JSONObject) jsonArray.get(i);
                            showEssay= new Essay(object);
                            essay_list.add(new Essay(showEssay.getEssay_ID(),showEssay.getPicture(),showEssay.getEssay_title(),showEssay.getEssay_description()));
                        }
                        System.out.println(essay_list.size());
                        essay.clear();
                        essay.addAll(essay_list);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                essayAdapter.notifyDataSetChanged();
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

    public void initVideo(String url) {
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)//要访问的链接
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
                        JSONArray jsonArray = new JSONArray(String.valueOf(jsonObject.getJSONObject("data").get("data_info")));
                        for (int i = 0; i<jsonArray.length();i++)
                        {
                            JSONObject object = (JSONObject) jsonArray.get(i);
                            showVideo= new Video(object);
                            video_list.add(new Video(showVideo.getVideo_picture(),showVideo.getVideo_ID()));
                        }
                        System.out.println(video_list.size());
                        video.clear();
                        video.addAll(video_list);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                videoAdapter.notifyDataSetChanged();
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

    public void initAudio(String url) {
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)//要访问的链接
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
                        JSONArray jsonArray = new JSONArray(String.valueOf(jsonObject.getJSONObject("data").get("data_info")));
                        for (int i = 0; i<jsonArray.length();i++)
                        {
                            JSONObject object = (JSONObject) jsonArray.get(i);
                            showAudio= new Audio(object);
                            audio_list.add(new Audio(showAudio.getAudio_ID(),showAudio.getAudio_picture(),showAudio.getAudio_title()));
                        }
                        System.out.println(audio_list.size());
                        audio.clear();
                        audio.addAll(audio_list);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                audioAdapter.notifyDataSetChanged();
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
