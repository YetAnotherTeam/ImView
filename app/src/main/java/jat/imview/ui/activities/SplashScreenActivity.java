package jat.imview.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import jat.imview.R;
import jat.imview.asyncTasks.PreloadTask;
import jat.imview.network.NetworkAdapter;
import rx.schedulers.Schedulers;

public class SplashScreenActivity extends AppCompatActivity {
    private PreloadTask mPreloadTask;
    private final boolean FEATURED = true;
    private final boolean NOT_FEATURED = false;

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
    }

    public void startMainActivity() {
        Intent i = new Intent(this, FeaturedActivity.class);
        startActivity(i);
        finish();
    }

    private void preloadImageInfo() {
        NetworkAdapter service = NetworkAdapter.getInstance();
        service.getImageList(true)
                .observeOn(Schedulers.io());
//                .subscribe(      }
//                );



    }
}
