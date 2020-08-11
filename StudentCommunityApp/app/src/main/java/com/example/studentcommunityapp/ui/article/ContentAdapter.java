package com.example.studentcommunityapp.ui.article;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.studentcommunityapp.R;
import com.example.studentcommunityapp.bean.Content;

import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ViewHolder>{
    private List<Content> mContentList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView ContentID;
        TextView ContentTitle;
        public ViewHolder(View itemView) {
            super(itemView);
            ContentID=(TextView)itemView.findViewById(R.id.Content_id);
            ContentTitle=(TextView)itemView.findViewById(R.id.Content_title);

        }
    }
    public ContentAdapter(List<Content> contentList){
        mContentList=contentList;
    }

    @Override
    public ContentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.article_item,parent,false);
        //将之前写好的list_view封装到一个View中
        ContentAdapter.ViewHolder holder=new ContentAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ContentAdapter.ViewHolder holder, int position) {
        Content content=mContentList.get(position);
        holder.ContentID.setText(content.getContent_id());
        holder.ContentTitle.setText(content.getContent_name());
    }

    @Override
    public int getItemCount() {
        return mContentList.size();
    }
}

