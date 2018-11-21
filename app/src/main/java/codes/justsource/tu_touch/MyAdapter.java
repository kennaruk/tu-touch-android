package codes.justsource.tu_touch;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import models.Course;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>  {
    private static final String TAG = DashboardActivity.class.getSimpleName();
    private List<Course> mDataset;

    public MyAdapter(List<Course> myDataset) {
        this.mDataset = myDataset;
        for (Course c : mDataset) {
            Log.e(TAG,"Chk2 = "+c.getCourseName());
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView course_id;
        public TextView course_name;
        public TextView course_credit;
        public TextView course_grade;

        public MyViewHolder(View v) {
            super(v);
            course_id = (TextView) v.findViewById(R.id.course_id);
            course_name = (TextView) v.findViewById(R.id.course_name);
            course_credit = (TextView) v.findViewById(R.id.credit);
            course_grade = (TextView) v.findViewById(R.id.grade);
        }
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

//        for (Course c : mDataset) {
//            holder.course_id.setText(c.getCourseId());
//            holder.course_name.setText(c.getCourseName());
//            holder.course_credit.setText(c.getCourseCredit());
//            holder.course_grade.setText(c.getCourseGrade());
//        }

        holder.course_id.setText(mDataset.get(position).getCourseId());
        holder.course_name.setText(mDataset.get(position).getCourseName());
        holder.course_credit.setText(mDataset.get(position).getCourseCredit());
        holder.course_grade.setText(mDataset.get(position).getCourseGrade());

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }



}
