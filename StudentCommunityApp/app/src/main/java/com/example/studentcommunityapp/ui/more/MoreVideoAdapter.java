package com.example.studentcommunityapp.ui.more;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.studentcommunityapp.R;
import com.example.studentcommunityapp.bean.Video;
import java.util.List;

public class MoreVideoAdapter extends RecyclerView.Adapter<MoreVideoAdapter.ViewHolder> {
    private List<Video> mVideoList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView video_image;
        public ViewHolder(View itemView) {
            super(itemView);
            video_image=(ImageView) itemView.findViewById(R.id.MoreVideoimage);
        }
    }
    public MoreVideoAdapter(List<Video> videoList){
        mVideoList=videoList;
    }

    @Override
    public MoreVideoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video,parent,false);
        //将之前写好的list_view封装到一个View中
        MoreVideoAdapter.ViewHolder holder=new MoreVideoAdapter.ViewHolder(view);

        holder.video_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_videoMoreFragment_to_articleFragment);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MoreVideoAdapter.ViewHolder holder, int position) {
        Video video=mVideoList.get(position);
        Glide.with(holder.itemView)
                .load(video.getVideo_picture())
                .into(holder.video_image);

    }

    @Override
    public int getItemCount() {
        return mVideoList.size();
    }
}
