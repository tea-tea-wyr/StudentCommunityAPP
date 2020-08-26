package com.example.studentcommunityapp.ui.more;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.studentcommunityapp.R;
import com.example.studentcommunityapp.bean.Essay;
import java.util.ArrayList;
import java.util.List;

public class ArticleMoreFragment extends Fragment {

    private ArticleMoreViewModel mViewModel;
    private ImageView image;
    private List<Essay> essay=new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;

    public static ArticleMoreFragment newInstance() {
        return new ArticleMoreFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.article_more_fragment, container, false);

        image=root.findViewById(R.id.back_home);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_articleMoreFragment_to_navigation_home);
            }
        });

        for(int i=0;i<3;i++)
        {
        Essay essay1 = new Essay(R.drawable.aaa, "编译原理知识点总结","编译原理是计算机专业的一门重要专业课，旨在介绍编译程序构造的编译原理是计算机专业的一门重要专......");
        essay.add(essay1);
        Essay essay2 = new Essay(R.drawable.bbb, "编译原理知识点总结","编译原理是计算机专业的一门重要专业课，旨在介绍编译程序构造的编译原理是计算机专业的一门重要专......");
        essay.add(essay2);
        Essay essay3 = new Essay(R.drawable.cccc, "编译原理知识点总结","编译原理是计算机专业的一门重要专业课，旨在介绍编译程序构造的编译原理是计算机专业的一门重要专......");
        essay.add(essay3);}

        RecyclerView recyclerView=root.findViewById(R.id.recycler3);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        MoreEssayAdapter adapter=new MoreEssayAdapter(essay);
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ArticleMoreViewModel.class);
        // TODO: Use the ViewModel
    }

}