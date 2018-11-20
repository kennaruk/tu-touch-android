package codes.justsource.tu_touch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import android.util.Log;
import android.widget.EditText;

import java.io.Serializable;
import java.util.List;

import models.Course;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import services.ApiClient;
import services.CourseService;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private EditText stdIdEdtText, stdPwdEdtText;
    private Button loginBtn;
    private Intent intentToDashboard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /* init view */
        stdIdEdtText = (EditText) findViewById(R.id.stdIdField);
        stdPwdEdtText = (EditText) findViewById(R.id.stdPwdField);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        intentToDashboard = new Intent(LoginActivity.this, DashboardActivity.class);

        loginBtn.setOnClickListener(this.handleClickLogin());
    }

    private View.OnClickListener handleClickLogin() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stdId = stdIdEdtText.getText().toString();
                String stdPwd = stdPwdEdtText.getText().toString();

                login(stdId, stdPwd, "");
            }
        };
    }

    private void login(String stdId, String stdPwd, String stdRfid) {
        /* Singleton API Client */
        Retrofit retrofit = ApiClient.getClient();
        CourseService courseService = retrofit.create(CourseService.class);
        /* Initiate HTTP Request */
        Call<List<Course>> courseData = courseService.getCourses(stdId, stdPwd, stdRfid);
        courseData.enqueue(new Callback<List<Course>>() {
            @Override
            public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                if(response.code() == 200) {
                    List<Course> courses = response.body();
//                    for(Course course: courses) {
//                        Log.e(TAG, "CourseId = "+course.getCourseId());
//                    }

                    intentToDashboard.putExtra("courses", (Serializable) courses);
                    startActivity(intentToDashboard);
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
