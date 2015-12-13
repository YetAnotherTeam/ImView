package jat.imview.asyncTasks;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import jat.imview.ui.SplashScreenActivity;

/**
 * Created by bulat on 06.12.15.
 */
public class PreloadTask extends AsyncTask<Void, Void, Void> {
    private final int ACTIVITY_SLEEP_TIME = 2;
    private WeakReference<SplashScreenActivity> activityWeakReference;

    public PreloadTask(SplashScreenActivity splashScreenActivity) {
        activityWeakReference = new WeakReference<>(splashScreenActivity);
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            TimeUnit.SECONDS.sleep(ACTIVITY_SLEEP_TIME);
        } catch (InterruptedException ignored) {
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        activityWeakReference.get().startMainActivity();
    }
}
