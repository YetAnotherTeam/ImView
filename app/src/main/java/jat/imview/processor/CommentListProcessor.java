package jat.imview.processor;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import java.lang.ref.WeakReference;
import java.util.List;

import jat.imview.contentProvider.db.table.CommentTable;
import jat.imview.model.Comment;
import jat.imview.rest.resource.CommentListResource;
import jat.imview.rest.http.Response;

/**
 * Created by bulat on 23.12.15.
 */
public class CommentListProcessor {
    private WeakReference<Context> weakContext;
    private int imageId;

    public CommentListProcessor(Context context, int imageId) {
        weakContext = new WeakReference<>(context);
        this.imageId = imageId;
    }

    public void getCommentList(ProcessorCallback processorCallback) {
        /*CommentListRestMethod commentListRestMethod = CommentListResource.getRestMethod(CommentTable.CONTENT_URI, HTTPMethod.GET);
        commentListRestMethod.setImageId(imageId);
        Response response = commentListRestMethod.execute();

        if (response.status == 200) {
            updateContentProvider(response);
        }
        processorCallback.send(response.status);*/
    }

    private void updateContentProvider(Response response) {
        CommentListResource commentListParser = new CommentListResource(response);
        List<Comment> commentList = commentListParser.getCommentList();
        ContentValues[] commentValuesArray = new ContentValues[commentList.size()];
        for (int i = 0; i < commentList.size(); ++i) {
            Comment comment = commentList.get(i);
            ContentValues values = new ContentValues();
            values.put(CommentTable.ID, comment.getId());
            values.put(CommentTable.IMAGE_ID, comment.getImageId());
            values.put(CommentTable.USER_ID, comment.getUserProfile().getId());
            values.put(CommentTable.PUBLISH_DATE, String.valueOf(comment.getPublishDate()));
            values.put(CommentTable.MESSAGE, String.valueOf(comment.getMessage()));
            values.put(CommentTable.RATING, comment.getRating());
            commentValuesArray[i] = values;
        }
        ContentResolver contentResolver = weakContext.get().getContentResolver();
        contentResolver.bulkInsert(CommentTable.CONTENT_URI, commentValuesArray);
    }
}