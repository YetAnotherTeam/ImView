package jat.imview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jat.imview.R;
import jat.imview.model.Comment;

/**
 * Created by bulat on 16.12.15.
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {
    private final List<Comment> comments;
    private OnItemClickListener onItemClickListener;

    public CommentsAdapter() {
        this.comments = new ArrayList<>();
        Comment comment = new Comment();
        // MOCK TODO delete after network done
        comment.setRating(12);
        comment.setPublishDate(new Date());
        comment.setMessage("dlsad;lkqlsdklasd");
        for (int i = 0; i < 90; ++i) {
            comments.add(comment);
        }
    }

    public CommentsAdapter(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.comment, parent, false));
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        Comment comment = comments.get(position);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, int itemViewId);
    }

    public void add(Comment comment) {
        comments.add(comment);
        notifyItemInserted(getItemCount());
    }

    public List<Comment> getComments() {
        return comments;
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView mUsername;
        private final TextView mPublishDate;
        private final TextView mCommentText;
        private final TextView mRating;
        private final RelativeLayout mVoteUpButton;
        private final ImageButton mVoteDownButton;
        private final ImageView mUserAvatar;

        public CommentViewHolder(View itemView) {
            super(itemView);
            mUsername = (TextView) itemView.findViewById(R.id.username);
            mPublishDate = (TextView) itemView.findViewById(R.id.publish_date);
            mCommentText = (TextView) itemView.findViewById(R.id.comment_text);
            mUserAvatar = (ImageView) itemView.findViewById(R.id.user_avatar);
            mRating = (TextView) itemView.findViewById(R.id.rating);
            mVoteUpButton = (RelativeLayout) itemView.findViewById(R.id.vote_up_button);
            mVoteDownButton = (ImageButton) itemView.findViewById(R.id.vote_down_button);

            itemView.setOnClickListener(this);
            mUsername.setOnClickListener(this);
            mUserAvatar.setOnClickListener(this);
            mVoteUpButton.setOnClickListener(this);
            mVoteDownButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final OnItemClickListener listener = getOnItemClickListener();
            listener.onItemClick(getAdapterPosition(), v.getId());
        }
    }
}
