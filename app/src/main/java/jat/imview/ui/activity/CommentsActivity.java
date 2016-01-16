package jat.imview.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import jat.imview.R;
import jat.imview.adapter.CommentsAdapter;
import jat.imview.contentProvider.db.table.CommentTable;
import jat.imview.service.SendServiceHelper;

public class CommentsActivity extends DrawerActivity implements CommentsAdapter.OnItemClickListener, OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {
    private static final String LOG_TAG = "MyCommentsActivity";
    public static final String IMAGE_ID_EXTRA = "IMAGE_ID_EXTRA";
    private RecyclerView mCommentsRecyclerView;
    private CommentsAdapter mCommentsAdapter;
    private EditText mMessageTextInput;
    private int imageId;

    private Integer requestId;
    private BroadcastReceiver requestReceiver;

    @Override
    public NavigationDrawerItem getCurrentNavDrawerItem() {
        return NavigationDrawerItem.FEATURED;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate");
        Intent intent = getIntent();
        imageId = intent.getIntExtra(IMAGE_ID_EXTRA, -1);
        Log.d(LOG_TAG, "Image ID: " + String.valueOf(imageId));

        getSupportLoaderManager().restartLoader(0, null, this);

        setContentView(R.layout.activity_comments);
        mCommentsRecyclerView = (RecyclerView) findViewById(R.id.comments_recycler_view);
        mCommentsAdapter = new CommentsAdapter();
        mCommentsAdapter.setOnItemClickListener(this);

        mCommentsRecyclerView.setAdapter(mCommentsAdapter);
        mCommentsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mCommentsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplication().getApplicationContext()));

        mMessageTextInput = (EditText) findViewById(R.id.message_text_input);
        findViewById(R.id.send_button).setOnClickListener(this);

        requestId = SendServiceHelper.getInstance(this).requestCommentList(imageId);
    }

    @Override
    public void onItemClick(int position, int itemViewId) {
        switch (itemViewId) {
            case R.id.vote_up_button:
                break;
            case R.id.vote_down_button:
                break;
            // на случай дальнейших улучшений
            case R.id.user_avatar:
            case R.id.username:
                break;
            default:

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_button:
                String commentText = mMessageTextInput.getText().toString();
                if (commentText.length() > 0) {

                }
                break;
            case R.id.profile:
                break;
        }

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
                if (resultCode != 200) {
                    handleResponseErrors(resultCode);
                }
            }
        };
        registerReceiver(requestReceiver, filter);
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
        return new CursorLoader(this, CommentTable.CONTENT_URI, null, null, new String[] {String.valueOf(imageId)}, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(mCommentsAdapter != null) {
            Log.d(LOG_TAG, "Cursor items count: " + String.valueOf(data.getCount()));
            mCommentsAdapter.changeCursor(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if(mCommentsAdapter != null)
            mCommentsAdapter.changeCursor(null);
    }
}
