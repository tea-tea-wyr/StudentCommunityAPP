package com.example.studentcommunityapp.ui.more;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.studentcommunityapp.R;
import com.example.studentcommunityapp.bean.Audio;
import java.util.List;


public class MoreAudioAdapter extends RecyclerView.Adapter<MoreAudioAdapter.ViewHolder>{
    private List<Audio> mAudioList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView Audioimage;
        TextView Audioname;
        public ViewHolder(View itemView) {
            super(itemView);
            Audioname=(TextView)itemView.findViewById(R.id.MoreAudio_name);
            Audioimage=(ImageView) itemView.findViewById(R.id.MoreAudio_image);
        }
    }
    public MoreAudioAdapter(List<Audio> audioList){
        mAudioList=audioList;
    }

    @Override
    public MoreAudioAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_audio,parent,false);
        //将之前写好的list_view封装到一个View中
        MoreAudioAdapter.ViewHolder holder=new MoreAudioAdapter.ViewHolder(view);

        holder.Audioimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_audioMoreFragment_to_articleFragment);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MoreAudioAdapter.ViewHolder holder, int position) {
        Audio audio=mAudioList.get(position);
        Glide.with(holder.itemView)
                .load(audio.getAudio_picture())
                .into(holder.Audioimage);
        holder.Audioname.setText(audio.getAudio_title());
    }

    @Override
    public int getItemCount() {
        return mAudioList.size();
    }

}
