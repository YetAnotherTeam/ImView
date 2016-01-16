package jat.imview.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.FacebookSdk;

import jat.imview.R;
import jat.imview.asyncTask.PreloadTask;
import jat.imview.service.SendServiceHelper;

public class SplashScreenActivity extends BaseActivity {
    private static final String LOG_TAG = "MySplashScreenActivity";
    private final long ACTIVITY_SLEEP_TIME_MILLISECONDS = 1500;
    private final long ACTIVITY_MAX_SLEEP_TIME_MILLISECONDS = 5000;
    private PreloadTask mPreloadTask;
    private long startLoadingTime;
    private boolean preloadTimeLimitSucceed = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        startLoadingTime = System.currentTimeMillis();
        if (mPreloadTask == null) {
            mPreloadTask = new PreloadTask(this);
            mPreloadTask.execute(ACTIVITY_MAX_SLEEP_TIME_MILLISECONDS);
        }
        FacebookSdk.sdkInitialize(getApplicationContext());

        SendServiceHelper.getInstance(this).requestImageList(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPreloadTask.cancel(true);
    }


    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(SendServiceHelper.ACTION_REQUEST_RESULT);
        requestReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                preloadTimeLimitSucceed = false;
                int resultRequestId = intent.getIntExtra(SendServiceHelper.EXTRA_REQUEST_ID, 0);
                Log.d(LOG_TAG, "Received intent " + intent.getAction() + ", request ID " + resultRequestId);
                int resultCode = intent.getIntExtra(SendServiceHelper.EXTRA_RESULT_CODE, 0);
                if (resultCode != 200) {
                    handleResponseErrors(resultCode);
                }
                if (mPreloadTask != null) {
                    mPreloadTask.cancel(true);
                    mPreloadTask = new PreloadTask((SplashScreenActivity)context);
                    long newSleepTime = ACTIVITY_SLEEP_TIME_MILLISECONDS - (System.currentTimeMillis() - startLoadingTime);
                    if (newSleepTime > 0) {
                        mPreloadTask.execute(newSleepTime);
                    } else {
                        startMainActivity();
                    }
                }
            }
        };
        registerReceiver(requestReceiver, filter);
    }

    public void startMainActivity() {
        Intent intent = new Intent(this, FeaturedActivity.class);
        if (preloadTimeLimitSucceed) {
            Toast.makeText(getApplicationContext(), R.string.time_limit_succeed, Toast.LENGTH_SHORT).show();
            intent.putExtra(FeaturedActivity.FROM_PRELOADER_EXTRA, false);
        } else {
            intent.putExtra(FeaturedActivity.FROM_PRELOADER_EXTRA, true);
        }
        startActivity(intent);
        finish();
    }
}
