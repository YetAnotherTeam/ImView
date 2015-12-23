package jat.imview.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import jat.imview.R;
import jat.imview.adapters.CommentsAdapter;

public class CommentsActivity extends BaseActivity implements CommentsAdapter.OnItemClickListener, OnClickListener {
    private RecyclerView mCommentsRecyclerView;
    private CommentsAdapter mCommentsAdapter;
    private EditText mMessageTextInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        mCommentsRecyclerView = (RecyclerView) findViewById(R.id.comments_recycler_view);
        mCommentsAdapter = new CommentsAdapter();
        mCommentsAdapter.setOnItemClickListener(this);

        mCommentsRecyclerView.setAdapter(mCommentsAdapter);
        mCommentsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mCommentsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplication().getApplicationContext()));

        mMessageTextInput = (EditText) findViewById(R.id.message_text_input);
        findViewById(R.id.send_button).setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(jat.imview.R.id.pref_toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

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
            default:
                break;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(jat.imview.R.menu.preference_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
