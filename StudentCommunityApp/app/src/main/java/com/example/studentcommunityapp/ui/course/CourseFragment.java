package com.example.studentcommunityapp.ui.course;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import java.util.ArrayList;
import java.util.List;

public class CourseFragment extends Fragment {

    private UserViewModel courseViewModel;

    private List<Course> mData;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CourseAdapter courseAdapter;


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

        initData();
        mRecyclerView = root.findViewById(R.id.course_recyclerview);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        courseAdapter = new CourseAdapter(mData);
        mRecyclerView.setAdapter(courseAdapter);
        return root;
    }

    private void initData(){
        mData = new ArrayList<>();
        for(int i=0; i<10; i++) {
            mData.add(new Course("编译原理","2","1-3节课（1-15周）","A阶101"));
        }

    }

}