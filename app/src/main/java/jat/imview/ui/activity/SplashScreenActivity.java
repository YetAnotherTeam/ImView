package jat.imview.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import jat.imview.R;
import jat.imview.asyncTask.PreloadTask;

public class SplashScreenActivity extends AppCompatActivity {
    private PreloadTask mPreloadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        if (mPreloadTask == null) {
            mPreloadTask = new PreloadTask(this);
            mPreloadTask.execute();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPreloadTask.cancel(true);
    }

    public void startMainActivity() {
        Intent i = new Intent(this, FeaturedActivity.class);
        startActivity(i);
        finish();
    }
}
