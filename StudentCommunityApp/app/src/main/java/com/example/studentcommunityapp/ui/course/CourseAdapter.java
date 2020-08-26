package com.example.studentcommunityapp.ui.course;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.studentcommunityapp.R;
import com.example.studentcommunityapp.bean.Course;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Course> mDataSet;
    private final int week_day_zero =0;
    private final int week_day_one =1;
    private Context context;
    public CourseAdapter(List<Course> data){
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
//        if(viewType ==0){
//            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_course_tab,parent,false);
//            return new ZeroViewHolder(v);
//
//
////            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_course_show,parent,false);
////            return new MyViewHolder(v);
//        }

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_course_show,parent,false);
        return new MyViewHolder(v);


//         View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_course_tab,parent,false);
//         return new ZeroViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case week_day_zero:
                break;
            default:
                MyViewHolder myViewHolder = (MyViewHolder) holder;
                Course course = mDataSet.get(position);
                myViewHolder.week_course.setText(course.getWeek_course());
                myViewHolder.week_day.setText(course.getWeek_day());
                myViewHolder.time.setText(course.getTime());
                myViewHolder.place.setText(course.getPlace());
//                Glide.with(myViewHolder.itemView)
//                        .load(course.getImgUrl())
//                        .into(myViewHolder.imgurl);
//                Glide.with(myViewHolder.itemView)
//                        .load(course.getPicture())
//                        .into(myViewHolder.picture);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return week_day_zero;
        }
        else {
            return week_day_one;
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView week_course;
        TextView week_day;
        TextView time;
        TextView place;
        public MyViewHolder(View itemView){
            super(itemView);
            week_course = itemView.findViewById(R.id.course_week_course);
            week_day = itemView.findViewById(R.id.course_week_day);
            time = itemView.findViewById(R.id.course_week_time);
            place = itemView.findViewById(R.id.course_week_place);
        }
    }

    class ZeroViewHolder extends RecyclerView.ViewHolder {
        public ZeroViewHolder(View itemView){
            super(itemView);
        }
    }
}
