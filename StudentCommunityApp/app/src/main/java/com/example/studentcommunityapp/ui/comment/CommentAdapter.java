package com.example.studentcommunityapp.ui.comment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.studentcommunityapp.R;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{
    private List<Comment> mCommentList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View CommentView;
        TextView CommentId;
        TextView CommentTime;
        TextView CommentContent;
        public ViewHolder(View itemView) {
            super(itemView);
            CommentView=itemView;
            CommentId=(TextView)itemView.findViewById(R.id.comment_id);
            CommentTime=(TextView)itemView.findViewById(R.id.comment_time);
            CommentContent=(TextView) itemView.findViewById(R.id.comment_content);
        }
    }
    public CommentAdapter(List<Comment> commentList){
        mCommentList=commentList;
    }

    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item,parent,false);
        //将之前写好的list_view封装到一个View中
        final CommentAdapter.ViewHolder holder=new CommentAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CommentAdapter.ViewHolder holder, int position) {
        Comment comment=mCommentList.get(position);
        holder.CommentId.setText(comment.getComment_id());
        holder.CommentTime.setText(comment.getComment_time());
        holder.CommentContent.setText(comment.getComment_content());
    }

    @Override
    public int getItemCount() {
        return mCommentList.size();
    }
}
