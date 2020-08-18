package com.example.studentcommunityapp.ui.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.example.studentcommunityapp.R;
import com.example.studentcommunityapp.bean.Article;
import com.example.studentcommunityapp.util.CircleImageView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Article> mDataSet;
    private final int type_zero =0;
    private final int type_one =1;
    private Context context;
    public  UserAdapter(List<Article> data){
        mDataSet = data;
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if(lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(holder.getLayoutPosition() == 0);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType ==0){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_user_header,parent,false);
            final ZeroViewHolder holder = new ZeroViewHolder(v);
            holder.setting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigation.findNavController(v).navigate(R.id.action_navigation_user_to_settingFragment);
                }
            });
            holder.avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigation.findNavController(v).navigate(R.id.action_navigation_user_to_modifyAvatarFragment);
                }
            });
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigation.findNavController(v).navigate(R.id.action_navigation_user_to_followFragment);
                }
            });
            return holder;
        }
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_user_items,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case type_zero:
                break;
            default:
                MyViewHolder myViewHolder = (MyViewHolder) holder;
                Article article = mDataSet.get(position);
                myViewHolder.name.setText(article.getName());
                myViewHolder.type.setText(article.getType());
                myViewHolder.title.setText(article.getTitle());
                myViewHolder.content.setText(article.getContent());
                Glide.with(myViewHolder.itemView)
                        .load(article.getImgUrl())
                        .into(myViewHolder.imgurl);
                Glide.with(myViewHolder.itemView)
                        .load(article.getPicture())
                        .into(myViewHolder.picture);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return type_zero;
        }
        else {
            return type_one;
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        CircleImageView imgurl;
        TextView name;
        TextView type;
        ImageView picture;
        TextView title;
        TextView content;
        public MyViewHolder(View itemView){
            super(itemView);
            imgurl = itemView.findViewById(R.id.user_items_imageurl);
            name = itemView.findViewById(R.id.user_items_name);
            type = itemView.findViewById(R.id.user_items_type);
            picture = itemView.findViewById(R.id.user_items_picture);
            title = itemView.findViewById(R.id.user_items_title);
            content = itemView.findViewById(R.id.user_items_content);
        }
    }

    class ZeroViewHolder extends RecyclerView.ViewHolder {
        ImageView setting;
        CircleImageView avatar;
        LinearLayout linearLayout;

        public ZeroViewHolder(View itemView){
            super(itemView);
            setting = itemView.findViewById(R.id.user_header_setting);
            avatar = itemView.findViewById(R.id.user_header_avatar);
            linearLayout = itemView.findViewById(R.id.user_header_follow);
        }
    }
}
