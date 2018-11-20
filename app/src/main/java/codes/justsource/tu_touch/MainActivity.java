package codes.justsource.tu_touch;

import android.content.Intent;
import android.graphics.Typeface;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import android.util.Log;
import android.widget.TextView;

import java.util.List;

import models.Course;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import services.ApiClient;
import services.CourseService;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        MainActivity3.class);
                // Pass data to AnotherActivity

                startActivity(intent);
            }
        });

        Retrofit retrofit = ApiClient.getClient();
        CourseService courseService = retrofit.create(CourseService.class);
        Call<List<Course>> courseData = courseService.getCourses("5809610248", "ilovetu", "");
        courseData.enqueue(new Callback<List<Course>>() {
            @Override
            public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                if(response.code() == 200) {
                    List<Course> courses = response.body();
                    for(Course course: courses) {
                        Log.e(TAG, "CourseId = "+course.getCourseId());
                    }
                } else {
                    Log.e(TAG, "Response code = "+response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Course>> call, Throwable t) {
                Log.e(TAG, "onFailure: Unable to get Course data: " + t.getMessage());
            }
        });

    }
}
