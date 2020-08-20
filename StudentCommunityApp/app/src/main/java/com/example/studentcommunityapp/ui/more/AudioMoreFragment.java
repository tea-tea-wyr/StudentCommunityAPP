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
import com.example.studentcommunityapp.bean.Audio;
import java.util.ArrayList;
import java.util.List;

public class AudioMoreFragment extends Fragment {

    private AudioMoreViewModel mViewModel;
    private List<Audio> audio=new ArrayList<>();
    private StaggeredGridLayoutManager layoutManager;
    private ImageView image;
    public static AudioMoreFragment newInstance() {
        return new AudioMoreFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.audio_more_fragment, container, false);
        image=root.findViewById(R.id.back_home);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_audioMoreFragment_to_navigation_home);
            }
        });
        /**
         * 音频类的recyclerview
         */
        initAudios();
        RecyclerView recyclerView=root.findViewById(R.id.recycler4);
        layoutManager=new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        MoreAudioAdapter adapter=new MoreAudioAdapter(audio);
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AudioMoreViewModel.class);
        // TODO: Use the ViewModel
    }

    public void initAudios()
    {
        for(int i=0;i<2;i++) {
            Audio audio1 = new Audio(R.drawable.w, "java入门");
            audio.add(audio1);
            Audio audio2 = new Audio(R.drawable.v, "C#入门");
            audio.add(audio2);
            Audio audio3 = new Audio(R.drawable.r, "C++从入门到精通");
            audio.add(audio3);
            Audio audio4 = new Audio(R.drawable.q, "编译原理");
            audio.add(audio4);
            Audio audio5 = new Audio(R.drawable.p, "数据结构");
            audio.add(audio5);
            Audio audio6 = new Audio(R.drawable.t, "计算机网络");
            audio.add(audio6);
            Audio audio7 = new Audio(R.drawable.k, "计算机组成原理");
            audio.add(audio7);
            Audio audio8 = new Audio(R.drawable.d, "操作系统");
            audio.add(audio8);
        }
    }

}