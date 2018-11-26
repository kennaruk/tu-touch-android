package codes.justsource.tu_touch;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
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
    private NfcAdapter nfcAdapter;
    String tagInfo,tagID="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /* init view */
        stdIdEdtText = (EditText) findViewById(R.id.stdIdField);
        stdPwdEdtText = (EditText) findViewById(R.id.stdPwdField);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        intentToDashboard = new Intent(LoginActivity.this, DashboardActivity.class);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter == null){
            Toast.makeText(this,
                    "NFC NOT supported on this devices!",
                    Toast.LENGTH_LONG).show();
            finish();
        }else if(!nfcAdapter.isEnabled()){
            Toast.makeText(this,
                    "NFC NOT Enabled!",
                    Toast.LENGTH_LONG).show();
            finish();
        }


        loginBtn.setOnClickListener(this.handleClickLogin());
    }

    @Override
    protected void onResume() {
        super.onResume();

        Tag tag;
        Intent intent;
        intent = getIntent();
        String action = intent.getAction();
        Log.e(TAG,"action is "+action);

        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
//            Toast.makeText(this,
//                    "onResume()if - ACTION_TECH_DISCOVERED",
//                    Toast.LENGTH_SHORT).show();
            tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            if(tag == null){
                Toast.makeText(this, "tag == null" ,Toast.LENGTH_SHORT).show();
            }else {

                byte[] tagId = tag.getId();
                for(int i=0; i<tagId.length; i++){
                    tagID += Integer.toHexString(tagId[i]& 0xFF);
                }
                Log.e(TAG,"Tag id is:"+tagID);
                handleClickLoginRfid();
            }
        }
    }

    private View.OnClickListener handleClickLogin() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stdId = stdIdEdtText.getText().toString();
                String stdPwd = stdPwdEdtText.getText().toString();
                String stdRfid = tagID;

                login(stdId, stdPwd, stdRfid);
            }
        };
    }

    private void handleClickLoginRfid() {
        String stdRfid = tagID;
        Log.e(TAG,stdRfid+ " in func login Rfid2 ");
        login("", "",stdRfid);

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
                Log.e(TAG,"onResponse:"+call);
                if(response.code() == 200) {
                    List<Course> courses = response.body();

                    intentToDashboard.putExtra("courses", (Serializable) courses);
                    startActivity(intentToDashboard);
                } else {
                    Log.e(TAG, "Response code = "+response.code());
//                    handleClickLogin();
                }
            }
            @Override
            public void onFailure(Call<List<Course>> call, Throwable t) {
                Log.e(TAG, "onFailure: Unable to get Course data: " + t.getMessage());
                AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
                dialog.setTitle("เตือน!");
                dialog.setMessage("ยังไม่มีข้อมูลบัตร กรุณาเข้าสู่ระบบด้วยรหัสผ่านเพื่อบันทึกข้อมูลบัตร");
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        handleClickLogin();
                    }
                });
                dialog.show();

            }
        });
    }
}
