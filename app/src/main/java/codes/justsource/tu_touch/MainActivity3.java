package codes.justsource.tu_touch;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.lang.String;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listitem);

        final ListView lisView = (ListView)findViewById(R.id.listView );
        String url = "http://www.lstpch.com/android/getData.php";
        try {
            JSONArray data = new JSONArray(getJSONUrl(url));
            final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;`
            for (int i = 0; i < data.length(); i++) {
                JSONObject c = data.getJSONObject(i);
                map = new HashMap<String, String>();
                map.put("id_course", c.getString("trans_id"));
                map.put("name_course", c.getString("name"));
                map.put("unit_course", c.getString("msg"));
                map.put("grade_course", c.getString("amt"));
                MyArrList.add(map);
            }
            SimpleAdapter sAdap;
            sAdap = new SimpleAdapter(MainActivity3.this, MyArrList, R.layout.activity_main3 ,
                    new String[] {"id_course", "name_course", "unit_course", "grade_course"},
                    new int[] {R.id.id_course, R.id.name_course , R.id.unit_course ,
                            R.id.grade_course });
            lisView.setAdapter(sAdap);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}
