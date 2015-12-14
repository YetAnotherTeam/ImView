package jat.imview.ui.activities;

import android.support.v4.view.ViewPager;
import android.os.Bundle;

import jat.imview.R;
import jat.imview.adapters.ImageSwipeAdapter;

public class FeaturedActivity extends DrawerActivity {
    ImageSwipeAdapter mImageSwipeAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_featured);
        //mViewPager = (ViewPager) findViewById(R.id.view_pager);
        //mImageSwipeAdapter = new ImageSwipeAdapter(getSupportFragmentManager());
        //mViewPager.setAdapter(mImageSwipeAdapter);
    }

    @Override
    public NavigationDrawerItem getCurrentNavDrawerItem() {
        return NavigationDrawerItem.MAIN;
    }
}
