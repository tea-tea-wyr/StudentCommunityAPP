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
        final View root = inflater.inflate(R.layout.follow_fragment, container, false);

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
        followAdapter.setOnItemClickListener(new FollowAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Navigation.findNavController(root).navigate(R.id.action_followFragment_to_articleFragment);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Navigation.findNavController(root).navigate(R.id.action_followFragment_to_articleFragment);
            }
        });
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
        mData.add(new Article("http://81.70.27.208:8088/icon/006.jpg","小郑","发表了文章","java教程","http://81.70.27.208:8088/picture/b.jpg","Java 是一门面向对象编程语言，不仅吸收了 C++ 语言的各种优点，还摒弃了 C++ 里难以理解的多继承、指针等概念。Java 不但可以用来开发网站后台、PC 客户端和 Android APP，还在数据分析、网络爬虫、云计算领域大显身手。"));
        mData.add(new Article("http://world.people.com.cn/NMediaFile/2019/0610/MAIN201906100858000177823780005.jpg","小西","发表了文章","电子电路技术讲解","http://81.70.27.208:8088/picture/f.jpg","倘若把一张复杂的电路图按功能模块拆分成不同的基础电路再去分析就简单很多了。所以《电子懒人的基础硬件电路图讲解》中的基础电路图图文讲解很适合我这样的初学者，让我再分析这样的基础电路的同时，能知道自己基础薄弱点，从而弥补。我想这样实际分析电路比我看书进步要更快。"));
        mData.add(new Article("http://81.70.27.208:8088/icon/003.jpg","小智","发表了文章","数据结构知识点汇总","http://81.70.27.208:8088/picture/g.jpg","数据结构是一门研究非数值计算的程序设计问题中，计算机的操作对象以及它们之间的关系和操作的学科。数据元素是数据的基本单位，在计算机程序中通常作为一个整体进行考虑和处理。"));
        mData.add(new Article("http://81.70.27.208:8088/icon/004.jpg","小龙","发表了文章","学习编译原理的心得","http://81.70.27.208:8088/picture/A.jpg","而编译原理这门课程讲的就是比较专注解决一种的算法了。在20世纪50年代，编译器的编写一直被认为是十分困难的事情，第一Fortran的编译器据说花了18年的时间才完成。在人们尝试编写编译器的同时，诞生了许多跟编译相关的理论和技术，而这些理论和技术比一个实际的编译器本身价值更大。"));
        mData.add(new Article("http://81.70.27.208:8088/icon/005.jpg","小如","发表了文章","电路第一章知识点总结","http://81.70.27.208:8088/picture/f.jpg","这篇博客本来想写的是邱关源电路第五版的第一章内容， 由于我看的资料是第四版的， 所以此篇博客内容是按照第四版来的。 第四版和第五版的区别就是章节的排布有点出入， 总体内容并没有太大差别。"));
        mData.add(new Article("http://81.70.27.208:8088/icon/005.jpg","小如","发表了文章","计算机网络","http://81.70.27.208:8088/picture/002.jpg","网络为我们的生活提供了很大的方便！但是作为一个学计算机的学生不仅要会用它，我们需要了解它，甚至要熟记于心。除了作为一门考试课，更是作为自己的一门技能与知识。"));
        mData.add(new Article("http://81.70.27.208:8088/icon/004.jpg","小龙","发表了文章","大学物理","http://81.70.27.208:8088/picture/004.jpg","本书保持原书体系合理，适应面宽等特点，添加了部分近代物理的内容，加强用现代观点来诠释经典物理的思想，从而体现出物理学的发展对人类认识自然所起到的基础性作用"));
        mData.add(new Article("http://81.70.27.208:8088/icon/003.jpg","小智","发表了文章","对面向对象的理解","http://81.70.27.208:8088/picture/i.jpg","面向对象和面向过程的思想有着本质上的区别，作为面向对象的思维来说，当你拿到一个问题时，你分析这个问题不再是第一步先做什么，第二步再做什么，这是面向过程的思维，你应该分析这个问题里面有哪些类和对象，这是第一点，然后再分析这些类和对象应该具有哪些属性和方法。这是第二点。最后分析类和类之间具体有什么关系，这是第三点。"));


    }
}
