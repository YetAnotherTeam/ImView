package jat.imview.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;

import jat.imview.R;
import jat.imview.service.SendServiceHelper;

public abstract class BaseActivity extends AppCompatActivity {
    private static final String LOG_TAG = "MyBaseActivity";
    protected boolean isNeedToShowAd = false;
    protected AdRequest adRequest;
    protected Integer requestId;
    protected BroadcastReceiver requestReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isNeedToShowAd) {
            adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .build();
        }
    }

    protected void handleResponseErrors(int resultCode) {
        Log.d(LOG_TAG, "Handle error " + String.valueOf(resultCode));
        switch (resultCode) {
            case 401:
                Intent intent = new Intent(this, LoginActivity.class);
                Toast.makeText(getApplicationContext(), R.string.login_required, Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
            default:
                Toast.makeText(getApplicationContext(), R.string.network_unreachable, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (requestReceiver != null) {
            try {
                this.unregisterReceiver(requestReceiver);
            } catch (IllegalArgumentException e) {
                Log.e(LOG_TAG, e.getLocalizedMessage(), e);
            }
        }
    }
}
