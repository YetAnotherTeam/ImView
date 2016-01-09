package jat.imview.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import jat.imview.R;
import jat.imview.adapter.GalleryAdapter;
import jat.imview.contentProvider.DB.Table.FeaturedTable;
import jat.imview.service.SendServiceHelper;
import jat.imview.ui.view.GalleryViewPager;

public class FeaturedActivity extends DrawerActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {
    private static final String LOG_TAG = "MyFeaturedActivity";
    private static final boolean IS_FEATURED = true;
    private GalleryViewPager mViewPager;
    private GalleryAdapter mGalleryAdapter;
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

        getSupportLoaderManager().restartLoader(0, null, this);
        mGalleryAdapter = new GalleryAdapter(this);
        mViewPager = (GalleryViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(mGalleryAdapter);
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
                ShareLinkContent shareLinkContent = new ShareLinkContent.Builder()
                        .setContentTitle("ImView")
                        .setContentUrl(Uri.parse("http://yandex.ru"))
                        .setImageUrl(Uri.parse("https://yastatic.net/lego/_/X31pO5JJJKEifJ7sfvuf3mGeD_8.png"))
                        .build();
                ShareDialog.show(this, shareLinkContent);
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, FeaturedTable.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(mGalleryAdapter != null && mViewPager != null) {
            mGalleryAdapter.changeCursor(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if(mGalleryAdapter != null)
            mGalleryAdapter.changeCursor(null);
    }
}
