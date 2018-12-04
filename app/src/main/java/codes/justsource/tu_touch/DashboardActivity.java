package codes.justsource.tu_touch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.String;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import models.Course;
import models.Term;

public class DashboardActivity extends AppCompatActivity {
    private static final String TAG = DashboardActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Spinner termSpinner;
    private List<Course> courses, renderCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        /* Pre- get courses from intent */
//        Log.i("KenDebug", "before get intent");
        Intent intent = getIntent();
        courses = (List<Course>) intent.getSerializableExtra("courses");
        renderCourses = (List<Course>) ((ArrayList)(courses)).clone();

        /* 1. Render term spinner */
        renderSpinnerByCourses(courses);

        /* 2. render recycler view by spinner selection*/
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyAdapter(renderCourses);
        mRecyclerView.setAdapter(mAdapter);
    }

    private List<Term> getTermList(List<Course> courses) {
        Set<Term> termSet = new HashSet<Term>();

        for(Course course : courses) {
            termSet.add(new Term(course.getCourseTerm(), course.getCourseYear()));
        }

        List<Term> termList = new ArrayList<Term>();
        termList.addAll(termSet);

        return termList;
    }
    private void renderSpinnerByCourses(final List<Course> courses) {
        termSpinner = (Spinner) findViewById(R.id.term_spinner);

        List<String> termList = new ArrayList<String>();
        for(Course course : courses) {
            termList.add(course.getCourseTerm()+"/"+course.getCourseYear());
        }

        Collections.sort(termList, new Comparator<String>() {
            private int getValueFromString(String termAndYear) {
                String[] splitTerm = termAndYear.split("/");
                int year = Integer.parseInt(splitTerm[1])*2;
                int term = Integer.parseInt(splitTerm[0]);
                return year+term;
            }
            @Override
            public int compare(String o1, String o2) {
                return getValueFromString(o1) - getValueFromString(o2);
            }
        });

        List<String> termChoices = new ArrayList<String>();
        Set<String> termSet = new HashSet<String>();
        for(String term : termList) {
            if(!termSet.contains(term)) {
                termChoices.add(term);
                termSet.add(term);
            }
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, termChoices);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        termSpinner.setAdapter(adapter);

        termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                Log.i("KenDebug", adapter.getItem(position));
                String termSelected = adapter.getItem(position);
                String[] termSplit = termSelected.split("/");

                String term = termSplit[0];
                String year = termSplit[1];

                renderCourses.clear();

                for(Course course : courses) {
                    if(course.getCourseTerm().equals(term) && course.getCourseYear().equals(year))
                        renderCourses.add(course);
                }

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

}