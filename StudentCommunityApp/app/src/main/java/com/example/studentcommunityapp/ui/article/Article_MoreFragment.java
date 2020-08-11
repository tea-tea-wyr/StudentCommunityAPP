package com.example.studentcommunityapp.ui.article;

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
import com.example.studentcommunityapp.ui.home.EssayAdapter;

import java.util.ArrayList;
import java.util.List;

public class Article_MoreFragment extends Fragment {

    private ArticleMoreViewModel mViewModel;
    private ImageView image;

    public static Article_MoreFragment newInstance() {
        return new Article_MoreFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.article__more_fragment, container, false);
        image=root.findViewById(R.id.back_home);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_article_MoreFragment_to_navigation_home);
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ArticleMoreViewModel.class);
        // TODO: Use the ViewModel
    }

}