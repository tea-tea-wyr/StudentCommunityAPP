package com.example.studentcommunityapp.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentcommunityapp.R;
import com.example.studentcommunityapp.bean.Essay;

import java.util.List;

public class EssayAdapter extends RecyclerView.Adapter<EssayAdapter.ViewHolder>{
    private List<Essay> mEssayList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View EssayView;
        ImageView Essayimage;
        TextView Essayname;
        TextView Essaydescription;
        public ViewHolder(View itemView) {
            super(itemView);
            EssayView=itemView;
            Essayname=(TextView)itemView.findViewById(R.id.Essay_name);
            Essaydescription=(TextView)itemView.findViewById(R.id.Essay_description);
            Essayimage=(ImageView) itemView.findViewById(R.id.Essay_image);
        }
    }
    public EssayAdapter(List<Essay> essayList){
        mEssayList=essayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_part,parent,false);
        //将之前写好的list_view封装到一个View中
        final ViewHolder holder=new ViewHolder(view);
        holder.EssayView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_articleFragment);
            }
        });

        holder.Essayimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_articleFragment);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Essay essay=mEssayList.get(position);
        holder.Essayimage.setImageResource(essay.getEssay_imageID());
        holder.Essayname.setText(essay.getEssay_name());
        holder.Essaydescription.setText(essay.getEssay_description());
    }

    @Override
    public int getItemCount() {
        return mEssayList.size();
    }
}