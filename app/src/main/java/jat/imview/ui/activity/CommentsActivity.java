package jat.imview.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import jat.imview.R;
import jat.imview.adapter.CommentsAdapter;

public class CommentsActivity extends DrawerActivity implements CommentsAdapter.OnItemClickListener, OnClickListener {
    private RecyclerView mCommentsRecyclerView;
    private CommentsAdapter mCommentsAdapter;
    private EditText mMessageTextInput;

    @Override
    public NavigationDrawerItem getCurrentNavDrawerItem() {
        return NavigationDrawerItem.FEATURED;
    }

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
}
