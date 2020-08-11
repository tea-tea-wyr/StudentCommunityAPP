package com.example.studentcommunityapp.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.studentcommunityapp.R;
import com.example.studentcommunityapp.bean.Video;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder>{
    private List<Video> mVideoList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView video_image;
        public ViewHolder(View itemView) {
            super(itemView);
            video_image=(ImageView) itemView.findViewById(R.id.imageview);
        }
    }
    public VideoAdapter(List<Video> videoList){
        mVideoList=videoList;
    }

    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_part2,parent,false);
        //将之前写好的list_view封装到一个View中
        VideoAdapter.ViewHolder holder=new VideoAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(VideoAdapter.ViewHolder holder, int position) {
        Video video=mVideoList.get(position);
        holder.video_image.setImageResource(video.getVideo_imageID());

    }

    @Override
    public int getItemCount() {
        return mVideoList.size();
    }
}