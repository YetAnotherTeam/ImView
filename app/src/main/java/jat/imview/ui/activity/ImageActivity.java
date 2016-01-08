package jat.imview.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class ImageActivity extends DrawerActivity {

    @Override
    public NavigationDrawerItem getCurrentNavDrawerItem() {
        return NavigationDrawerItem.ABYSS;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(jat.imview.R.layout.activity_image);
        Toolbar toolbar = (Toolbar) findViewById(jat.imview.R.id.toolbar);
        setSupportActionBar(toolbar);
    }

}
