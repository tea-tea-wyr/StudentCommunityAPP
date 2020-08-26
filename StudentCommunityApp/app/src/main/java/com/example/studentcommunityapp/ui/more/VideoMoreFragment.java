package com.example.studentcommunityapp.ui.more;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.studentcommunityapp.R;
import com.example.studentcommunityapp.bean.Video;
import com.example.studentcommunityapp.ui.home.VideoAdapter;

import java.util.ArrayList;
import java.util.List;

public class VideoMoreFragment extends Fragment {

    private VideoMoreViewModel mViewModel;
    private ImageView image;
    private List<Video> video=new ArrayList<>();
    private StaggeredGridLayoutManager layoutManager;

    public static VideoMoreFragment newInstance() {
        return new VideoMoreFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.video_more_fragment, container, false);
        image=root.findViewById(R.id.back_home);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_videoMoreFragment_to_navigation_home);
            }
        });

        /**
         * 视频类的recyclerview
         */
        initVideos();
        RecyclerView recyclerView=root.findViewById(R.id.recycler5);
        layoutManager=new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        MoreVideoAdapter adapter=new MoreVideoAdapter(video);
        recyclerView.setAdapter(adapter);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(VideoMoreViewModel.class);
        // TODO: Use the ViewModel
    }

    public void initVideos()
    {
        for(int i=0;i<2;i++) {
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
    }

}