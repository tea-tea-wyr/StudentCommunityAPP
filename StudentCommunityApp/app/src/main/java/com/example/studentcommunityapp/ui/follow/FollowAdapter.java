package com.example.studentcommunityapp.ui.follow;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.studentcommunityapp.R;
import com.example.studentcommunityapp.bean.Article;
import com.example.studentcommunityapp.ui.user.UserAdapter;
import com.example.studentcommunityapp.util.CircleImageView;

import java.util.List;

public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.ViewHolder> {
        private Context context;
        private List<Article> mdataset;
        private FollowAdapter .OnItemClickListener onItemClickListener;

    static class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView imgurl;
        TextView name;
        TextView type;
        ImageView picture;
        TextView title;
        TextView content;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgurl = itemView.findViewById(R.id.follow_items_imageurl);
            name = itemView.findViewById(R.id.follow_items_name);
            type = itemView.findViewById(R.id.follow_items_type);
            picture = itemView.findViewById(R.id.follow_items_picture);
            title = itemView.findViewById(R.id.follow_items_title);
            content = itemView.findViewById(R.id.follow_items_content);
            cardView = itemView.findViewById(R.id.follow_card);
        }
    }
    public FollowAdapter(List<Article> data){
            mdataset = data;
        }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.follow_fragment_item,parent,false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ViewHolder myViewHolder = (ViewHolder) holder;
        Article article = mdataset.get(position);
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
        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener !=null){
                    int pos = getRealPosition(myViewHolder);
                    onItemClickListener.onItemClick(myViewHolder.cardView,pos);
                }
            }
        });
        myViewHolder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemClickListener !=null){
                    int pos = getRealPosition(myViewHolder);
                    onItemClickListener.onItemClick(myViewHolder.cardView,pos);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
    public void setOnItemClickListener(FollowAdapter.OnItemClickListener listener){
        this.onItemClickListener = listener;
    }
    private int getRealPosition(RecyclerView.ViewHolder holder){
        int position = holder.getLayoutPosition();
        return position;
    }
}
