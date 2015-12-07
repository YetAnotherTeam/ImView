package jat.imview.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import jat.imview.R;
import jat.imview.adapters.ImageSwipeAdapter;

public class FeaturedActivity extends AppCompatActivity {
    ImageSwipeAdapter mImageSwipeAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_featured);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mImageSwipeAdapter = new ImageSwipeAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mImageSwipeAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
