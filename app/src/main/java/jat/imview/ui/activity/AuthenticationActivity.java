package jat.imview.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import jat.imview.R;

public class AuthenticationActivity extends DrawerActivity {

    @Override
    public NavigationDrawerItem getCurrentNavDrawerItem() {
        return NavigationDrawerItem.AUTHENTICATION;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentification);
    }
}
