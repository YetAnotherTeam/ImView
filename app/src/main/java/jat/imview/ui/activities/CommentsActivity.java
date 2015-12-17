package jat.imview.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import jat.imview.R;
import jat.imview.adapters.CommentsAdapter;

public class CommentsActivity extends AppCompatActivity implements CommentsAdapter.OnItemClickListener, OnClickListener {
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

    }

    @Override
    public void onItemClick(CommentsAdapter.CommentViewHolder item, int position) {

    }

    @Override
    public void onClick(View v) {

    }
}
