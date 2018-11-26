package codes.justsource.tu_touch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.lang.String;
import java.util.List;

import models.Course;

public class DashboardActivity extends AppCompatActivity {
    private static final String TAG = DashboardActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
//        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Intent intent = getIntent();
        List<Course> courses = (List<Course>) intent.getSerializableExtra("courses");


        mAdapter = new MyAdapter(courses);
        mRecyclerView.setAdapter(mAdapter);
    }



}
