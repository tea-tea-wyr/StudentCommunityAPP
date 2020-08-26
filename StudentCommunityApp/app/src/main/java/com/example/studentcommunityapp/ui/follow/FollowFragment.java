package com.example.studentcommunityapp.ui.follow;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.studentcommunityapp.R;
import com.example.studentcommunityapp.bean.Article;
import com.example.studentcommunityapp.ui.home.HomeFragment;
import com.example.studentcommunityapp.ui.user.UserAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FollowFragment extends Fragment {
    private List<Article> mData;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FollowAdapter followAdapter;
    private FollowViewModel mViewModel;
    private ViewPager mViewPaper;
    private List<ImageView> images;
    private List<View> dots;
    private int currentItem;    //记录上一次点的位置
    private int oldPosition = 0;
    private int[] imageIds = new int[]{
            R.drawable.a,
            R.drawable.b,
            R.drawable.c
    };    //存放图片的id
    private ViewPagerAdapter adapter;
    private ScheduledExecutorService scheduledExecutorService;

    private ImageView image;

    public static FollowFragment newInstance() {
        return new FollowFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.follow_fragment, container, false);

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
        image=root.findViewById(R.id.back_user);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_followFragment_to_navigation_user);
            }
        });
        initData();
        mRecyclerView = root.findViewById(R.id.follow_recyclerview);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        followAdapter = new FollowAdapter(mData);
        mRecyclerView.setAdapter(followAdapter);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FollowViewModel.class);
        // TODO: Use the ViewModel
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

    private void initData(){
        mData = new ArrayList<>();
        for(int i=0; i<50; i++){
            mData.add(new Article("http://world.people.com.cn/NMediaFile/2019/0610/MAIN201906100858000177823780005.jpg","小西","发表了文章","C语言基础","http://world.people.com.cn/NMediaFile/2018/0913/MAIN201809131000000359718834615.jpg","C语言是一种计算机程序设计语言，它既有高级语言的特点，又具有汇编......"));
        }

    }
}
