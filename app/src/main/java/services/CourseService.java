package services;

import java.util.List;

import models.Course;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CourseService {
    @GET("/")
    Call<List<Course>> getCourses(@Query("stdId") String stdId, @Query("stdPwd") String stdPwd, @Query("stdRfid") String stdRfid);
}
