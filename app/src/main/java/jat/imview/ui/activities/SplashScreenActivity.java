package jat.imview.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import jat.imview.R;
import jat.imview.asyncTasks.PreloadTask;

public class SplashScreenActivity extends AppCompatActivity {
    private PreloadTask preloadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        if (preloadTask == null) {
            preloadTask = new PreloadTask(this);
            preloadTask.execute();
        }
    }

    public void startMainActivity() {
        Intent i = new Intent(this, FeaturedActivity.class);
        startActivity(i);
        finish();
    }
}
