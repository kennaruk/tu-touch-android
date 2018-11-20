package codes.justsource.tu_touch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.lang.String;
import java.util.List;

import models.Course;

public class DashboardActivity extends AppCompatActivity {
    private static final String TAG = DashboardActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Intent intent = getIntent();
        List<Course> courses = (List<Course>) intent.getSerializableExtra("courses");
        for(Course course: courses) {
            Log.e(TAG, "CourseId = "+course.getCourseId());
        }
    }



}
