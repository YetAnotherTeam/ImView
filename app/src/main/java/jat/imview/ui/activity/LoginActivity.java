package jat.imview.ui.activity;

import android.os.Bundle;

import jat.imview.R;

public class LoginActivity extends DrawerActivity {
    private static String LOG_TAG = "MyLoginActivity";

    @Override
    public NavigationDrawerItem getCurrentNavDrawerItem() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
