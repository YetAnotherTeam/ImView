package jat.imview.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MyBaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void handleResponseErrors(int resultCode) {
        Log.d(LOG_TAG, "Handle error " + String.valueOf(resultCode));
        switch (resultCode) {
            case 401:
                Intent intent = new Intent(this, LoginActivity.class);
                Toast.makeText(getApplicationContext(), "Login Required", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
            default:
                Log.d(LOG_TAG, "Unhandled error: " + String.valueOf(resultCode));
                break;
        }
    }
}
