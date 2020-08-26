package com.example.studentcommunityapp.ui.home;

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

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.ViewHolder>{
    private List<Audio> mAudioList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView Audiopicture;
        TextView Audiotitle;
        public ViewHolder(View itemView) {
            super(itemView);
            Audiotitle=(TextView)itemView.findViewById(R.id.Audio_name);
            Audiopicture=(ImageView) itemView.findViewById(R.id.Audio_image);
        }
    }
    public AudioAdapter(List<Audio> audioList){
        mAudioList=audioList;
    }

    @Override
    public AudioAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_part3,parent,false);
        //将之前写好的list_view封装到一个View中
        AudioAdapter.ViewHolder holder=new AudioAdapter.ViewHolder(view);

        holder.Audiopicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_articleFragment);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(AudioAdapter.ViewHolder holder, int position) {
        Audio audio=mAudioList.get(position);
        Glide.with(holder.itemView)
                .load(audio.getAudio_picture())
                .into(holder.Audiopicture);
        holder.Audiotitle.setText(audio.getAudio_title());
    }

    @Override
    public int getItemCount() {
        return mAudioList.size();
    }
}