package jat.imview.ui.activities;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import jat.imview.R;
import jat.imview.adapters.CommentsAdapter;
import jat.imview.сontentProvider.DBHelper;

public class CommentsActivity extends AppCompatActivity implements CommentsAdapter.OnItemClickListener, OnClickListener {
    private RecyclerView mCommentsRecyclerView;
    private CommentsAdapter mCommentsAdapter;
    private EditText mMessageTextInput;
    final String LOG_TAG = "myLogs";
    DBHelper dbHelper;

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
        dbHelper = new DBHelper(this);

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
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        switch (v.getId()) {
            case R.id.send_button:
                String commentText = mMessageTextInput.getText().toString();
                if (commentText.length() > 0) {
                    cv.put("name", "Zabrodin");
                    cv.put("usertext", commentText);
                    db.insert("comment", null, cv);
                }
                break;
            case R.id.profile:
                break;
        }

    }
}
