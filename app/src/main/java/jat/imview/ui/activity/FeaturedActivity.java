package jat.imview.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import jat.imview.R;
import jat.imview.adapter.GalleryAdapter;
import jat.imview.service.SendService;
import jat.imview.service.SendServiceHelper;
import jat.imview.ui.view.GalleryViewPager;

public class FeaturedActivity extends DrawerActivity implements View.OnClickListener {
    private static final String LOG_TAG = "MyFeaturedActivity";
    private static final boolean IS_FEATURED = true;
    private GalleryViewPager mViewPager;

    private LinearLayout mVoteUpButton;
    private LinearLayout mCommentsButton;
    private ImageButton mShareButton;
    private Integer requestId;
    private BroadcastReceiver requestReceiver;

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
        SendServiceHelper.getInstance(this).requestImageList(true);
        mViewPager = (GalleryViewPager) findViewById(R.id.view_pager);
        Cursor cursor = null;// getContentResolver().query();
        mViewPager.setAdapter(new GalleryAdapter(this, cursor));
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
        super.onResume();
        IntentFilter filter = new IntentFilter(SendServiceHelper.ACTION_REQUEST_RESULT);
        requestReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int resultRequestId = intent.getIntExtra(SendServiceHelper.EXTRA_REQUEST_ID, 0);
                Log.d(LOG_TAG, "Received intent " + intent.getAction() + ", request ID " + resultRequestId);
                if (resultRequestId == requestId) {
                    int resultCode = intent.getIntExtra(SendServiceHelper.EXTRA_RESULT_CODE, 0);
                }
            }
        };
        registerReceiver(requestReceiver, filter);
        if (requestId == null) {
            requestId = SendServiceHelper.getInstance(this).requestImageList(IS_FEATURED);
        } else {

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (requestReceiver != null) {
            try {
                this.unregisterReceiver(requestReceiver);
            } catch (IllegalArgumentException e) {
                Log.e(LOG_TAG, e.getLocalizedMessage(), e);
            }
        }
    }
}
