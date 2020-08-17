package com.example.studentcommunityapp.ui.publish;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class PublishAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;

    public PublishAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        // TODO Auto-generated constructor stub
        mFragments=fragments;
    }

    @Override
    public Fragment getItem(int arg0) {
        // TODO Auto-generated method stub
        return mFragments.get(arg0);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mFragments.size();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.os.Bundle;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentPagerAdapter;
//import androidx.viewpager.widget.ViewPager;
//
//import com.example.studentcommunityapp.R;
//import com.google.android.material.tabs.TabLayout;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class PublishAdapter extends AppCompatActivity {
//    private TabLayout tabLayout;
//    private ViewPager viewPager;
//    private Context mContext;
//
//    private List<Fragment> list;
//    private PublishAdapter adapter;
//    private String[] titles = {"文章", "视频", "音频"};
//
//
//    @SuppressLint("CutPasteId")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_publish);
//        //实例化
//        viewPager = (ViewPager) findViewById(R.id.viewpager);
//        tabLayout = (TabLayout) findViewById(R.id.tablayout);
//        //页面，数据源
//        list = new ArrayList<>();
//        list.add(new ArticleFragment());
//        list.add(new VideoFragment());
//        list.add(new VfFragment());
//        //ViewPager的适配器
//        adapter = new PublishAdapter(getSupportFragmentManager());
//        viewPager.setAdapter(adapter);
//        //绑定
//        tabLayout.setupWithViewPager(viewPager);
//
//        //去除标题栏
//        this.getSupportActionBar().hide();
//    }
//    class PublishAdapter extends FragmentPagerAdapter {
//        public PublishAdapter(FragmentManager fm) {
//            super(fm);
//            @Override
//            public Fragment getItem(int position) {
//                return list.get(position);
//            }
//        }
//        @Override
//        public int getCount() {
//            return list.size();
//        }
//
//        //重写这个方法，将设置每个Tab的标题
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return titles[position];
//        }
//    }
//}