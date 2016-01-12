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
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import jat.imview.R;
import jat.imview.adapter.GalleryAdapter;
import jat.imview.contentProvider.db.table.FeaturedTable;
import jat.imview.model.Image;
import jat.imview.service.SendServiceHelper;
import jat.imview.ui.view.GalleryViewPager;

public class FeaturedActivity extends DrawerActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor>, ViewPager.OnPageChangeListener {
    private static final String LOG_TAG = "MyFeaturedActivity";
    private static final boolean IS_FEATURED = true;
    private int currentImageId;
    private GalleryViewPager mViewPager;
    private GalleryAdapter mGalleryAdapter;

    private LinearLayout mVoteUpButton;
    private LinearLayout mCommentsButton;
    private ImageButton mShareButton;

    private TextView mCommentsCount;
    private TextView mVoteUpCount;

    private Integer requestId;
    private BroadcastReceiver requestReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_featured);

        mVoteUpButton = (LinearLayout) findViewById(R.id.vote_up_button);
        mCommentsButton = (LinearLayout) findViewById(R.id.comments_button);
        mShareButton = (ImageButton) findViewById(R.id.share_button);
        mCommentsCount = (TextView) findViewById(R.id.comments_count);
        mVoteUpCount = (TextView) findViewById(R.id.vote_up_count);

        mVoteUpButton.setOnClickListener(this);
        mCommentsButton.setOnClickListener(this);
        mShareButton.setOnClickListener(this);

        SendServiceHelper.getInstance(this).requestImageList(true);
        getSupportLoaderManager().restartLoader(0, null, this);
        mGalleryAdapter = new GalleryAdapter(this, getSupportFragmentManager());
        mViewPager = (GalleryViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(mGalleryAdapter);
        mViewPager.addOnPageChangeListener(this);
        updateActivityFromPosition(0);
    }

    @Override
    public NavigationDrawerItem getCurrentNavDrawerItem() {
        return NavigationDrawerItem.FEATURED;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vote_up_button:
                SendServiceHelper.getInstance(this).requestImageVote(currentImageId, true);
                break;
            case R.id.comments_button:
                Intent intent = new Intent(this, CommentsActivity.class);
                intent.putExtra(CommentsActivity.IMAGE_ID_EXTRA, currentImageId);
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
        switch (item.getItemId()) {
            case R.id.action_favorite:
                Intent intent = new Intent(this, ImageNewActivity.class);
                startActivity(intent);
                break;
            default:
                return false;
        }
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
                int resultCode = intent.getIntExtra(SendServiceHelper.EXTRA_RESULT_CODE, 0);
                Log.d(LOG_TAG, String.valueOf(resultCode));
                if (resultRequestId == requestId) {
                    resultCode = intent.getIntExtra(SendServiceHelper.EXTRA_RESULT_CODE, 0);
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        updateActivityFromPosition(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    private void updateActivityFromPosition(int position) {
        Cursor cursor = getContentResolver().query(FeaturedTable.CONTENT_URI, null, null, null, null);
        if (cursor.moveToPosition(position)) {
            Image image = Image.getByCursor(cursor);
            currentImageId = image.getId();
            mCommentsCount.setText(String.valueOf(image.getCommentsCount()));
            mVoteUpCount.setText(String.valueOf(image.getRating()));
        }
    }
}
