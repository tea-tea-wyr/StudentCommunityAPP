package com.example.studentcommunityapp.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
        initEssays();
        RecyclerView recyclerView=root.findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        EssayAdapter adapter=new EssayAdapter(essay);
        recyclerView.setAdapter(adapter);

        /**
         * 视频类的recyclerview
         */
        initVideos();
        RecyclerView recyclerView1=root.findViewById(R.id.recycler1);
        layoutManager1=new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        recyclerView1.setLayoutManager(layoutManager1);
        VideoAdapter adapter1=new VideoAdapter(video);
        recyclerView1.setAdapter(adapter1);

        /**
         * 音频类的recyclerview
         */
        initAudios();
        RecyclerView recyclerView2=root.findViewById(R.id.recycler2);
        layoutManager2=new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        recyclerView2.setLayoutManager(layoutManager2);
        AudioAdapter adapter2=new AudioAdapter(audio);
        recyclerView2.setAdapter(adapter2);

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

    public void initEssays()
    {
        Essay essay1 = new Essay(R.drawable.aaa, "编译原理知识点总结","编译原理是计算机专业的一门重要专业课，旨在介绍编译程序构造的编译原理是计算机专业的......");
        essay.add(essay1);
        Essay essay2 = new Essay(R.drawable.bbb, "编译原理知识点总结","编译原理是计算机专业的一门重要专业课，旨在介绍编译程序构造的编译原理是计算机专业的......");
        essay.add(essay2);
        Essay essay3 = new Essay(R.drawable.cccc, "编译原理知识点总结","编译原理是计算机专业的一门重要专业课，旨在介绍编译程序构造的编译原理是计算机专业的......");
        essay.add(essay3);

    }

    public void initVideos()
    {
        Video video1 = new Video(R.drawable.d);
        video.add(video1);
        Video video2 = new Video(R.drawable.k);
        video.add(video2);
        Video video3 = new Video(R.drawable.p);
        video.add(video3);
        Video video4 = new Video(R.drawable.q);
        video.add(video4);
        Video video5 = new Video(R.drawable.r);
        video.add(video5);
        Video video6 = new Video(R.drawable.t);
        video.add(video6);
        Video video7 = new Video(R.drawable.v);
        video.add(video7);
        Video video8 = new Video(R.drawable.w);
        video.add(video8);
    }

    public void initAudios()
    {
        Audio audio1 = new Audio(R.drawable.w,"java入门");
        audio.add(audio1);
        Audio audio2 = new Audio(R.drawable.v,"C#入门");
        audio.add(audio2);
        Audio audio3 = new Audio(R.drawable.r,"C++从入门到精通");
        audio.add(audio3);
        Audio audio4 = new Audio(R.drawable.q,"编译原理");
        audio.add(audio4);
        Audio audio5 = new Audio(R.drawable.p,"数据结构");
        audio.add(audio5);
        Audio audio6 = new Audio(R.drawable.t,"计算机网络");
        audio.add(audio6);
        Audio audio7 = new Audio(R.drawable.k,"计算机组成原理");
        audio.add(audio7);
        Audio audio8 = new Audio(R.drawable.d,"操作系统");
        audio.add(audio8);
    }
    @Subscribe
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
            builder.create().show();// 使用show()方法显示对话框
            Looper.loop();
        }
    }
}
