package codes.justsource.tu_touch;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import static android.view.View.VISIBLE;

public class NfcReadingActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();

    private ProgressBar pgsBar;

    private NfcAdapter nfcAdapter;

    private String tagInfo, tagID = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_reading);

        /* Set Progress Bar */
        pgsBar = (ProgressBar) findViewById(R.id.pBar);
        pgsBar.setVisibility(VISIBLE);

        /* Internet uncertainly handler */
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
        } else {
            Toast.makeText(this,
                    "Can't Connect to the Internet!",
                    Toast.LENGTH_LONG).show();
            startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
        }

        /* NFC Handler */
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this,
                    "NFC NOT supported on this devices!",
                    Toast.LENGTH_LONG).show();
            finish();
        } else if (!nfcAdapter.isEnabled()) {
            Toast.makeText(this,
                    "NFC NOT Enabled!",
                    Toast.LENGTH_LONG).show();
//            Toast.makeText(getApplicationContext(), "Please activate NFC and press Back to return to the application!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(android.provider.Settings.ACTION_NFC_SETTINGS));
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Tag tag;
        Intent intent;
        intent = getIntent();
        String action = intent.getAction();
        Log.e(TAG, "action is " + action);

        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
            tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            if (tag == null) {
                Toast.makeText(this, "tag == null", Toast.LENGTH_SHORT).show();
            } else {
                byte[] tagId = tag.getId();
                for (int i = 0; i < tagId.length; i++) {
                    tagID += Integer.toHexString(tagId[i] & 0xFF);
                }
                Log.e(TAG, "Tag id is:" + tagID);
//                handleClickLoginRfid();
            }
        }
    }
}
