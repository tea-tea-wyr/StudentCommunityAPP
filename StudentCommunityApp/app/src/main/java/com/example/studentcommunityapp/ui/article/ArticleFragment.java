package com.example.studentcommunityapp.ui.article;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.studentcommunityapp.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ArticleFragment extends Fragment {

    private ArticleViewModel mViewModel;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView image;
    private List<String> titles;
    private List<Fragment> fragments;
    private Fragment fragment1;
    private Fragment fragment2;

    public static ArticleFragment newInstance() {
        return new ArticleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.article_fragment, container, false);

        /**
         * 顶端选项卡部分
         */
        tabLayout = root.findViewById(R.id.tab);
        viewPager = root.findViewById(R.id.viewpager);
        image=root.findViewById(R.id.back_home);
        titles = new ArrayList<>();
        titles.add("详情");titles.add("内容");

        fragments = new ArrayList<>();
        fragment1 = new Article_Fragment1();
        fragment2 = new Article_Fragment2();
        fragments.add(fragment1);
        fragments.add(fragment2);

        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(),fragments,titles);
        viewPager.setAdapter(myFragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        //设置箭头点击事件
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_articleFragment_to_navigation_home);
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ArticleViewModel.class);
        // TODO: Use the ViewModel
    }

}
