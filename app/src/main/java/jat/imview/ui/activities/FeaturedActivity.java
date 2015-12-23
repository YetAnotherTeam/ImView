package jat.imview.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import jat.imview.R;
import jat.imview.adapters.GalleryAdapter;
import jat.imview.service.SendServiceHelper;
import jat.imview.network.ServiceHelperObserver.Observer;

public class FeaturedActivity extends DrawerActivity implements View.OnClickListener, Observer {
    GalleryAdapter mImageSwipeAdapter;
    ViewPager mViewPager;
    LinearLayout mVoteUpButton;
    LinearLayout mCommentsButton;
    ImageButton mShareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_featured);
        mVoteUpButton = (LinearLayout) findViewById(R.id.vote_up_button);
        mVoteUpButton.setOnClickListener(this);
        mCommentsButton = (LinearLayout) findViewById(R.id.comments_button);
        mCommentsButton.setOnClickListener(this);
        mShareButton = (ImageButton) findViewById(R.id.share_button);
        mShareButton.setOnClickListener(this);
        //mViewPager = (ViewPager) findViewById(R.id.view_pager);
        //mImageSwipeAdapter = new ImageSwipeAdapter(getSupportFragmentManager());
        //mViewPager.setAdapter(mImageSwipeAdapter);
    }

    @Override
    public NavigationDrawerItem getCurrentNavDrawerItem() {
        return NavigationDrawerItem.FEATURED;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vote_up_button:
                break;
            case R.id.comments_button:
                Intent intent = new Intent(this, CommentsActivity.class);
                startActivity(intent);
                break;
            case R.id.share_button:
                break;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.featured_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    @Override
    protected void onResume() {
        SendServiceHelper.getInstance(this).registerObserver(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SendServiceHelper.getInstance(this).removeObserver(this);
    }

    @Override
    public void handleResponse(String response) {

    }
}
