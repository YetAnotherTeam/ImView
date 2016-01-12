package jat.imview.rest.resource;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import jat.imview.model.Comment;
import jat.imview.model.UserProfile;
import jat.imview.rest.http.Response;
import jat.imview.rest.resource.base.Resource;
import jat.imview.util.DateUtil;

/**
 * Created by bulat on 23.12.15.
 */
public class CommentListResource implements Resource {
    private static final String LOG_TAG = "MyResponse";
    private List<Comment> commentList = new ArrayList<>();

    public CommentListResource(Response response) {
        try {
            String responseBody = new String(response.body);
            Log.d(LOG_TAG, responseBody);
            JSONArray jsonComments = new JSONArray(responseBody);
            for (int i = 0; i < jsonComments.length(); ++i) {
                JSONObject jsonComment = (JSONObject) jsonComments.get(i);
                JSONObject jsonUserProfile = jsonComment.getJSONObject("author");
                UserProfile userProfile = new UserProfile(
                        jsonUserProfile.getInt("id"),
                        jsonUserProfile.getString("name")
                );
                Comment comment = new Comment(
                        jsonComment.getInt("id"),
                        jsonComment.getInt("image_id"),
                        userProfile,
                        DateUtil.parseFromServerString(jsonComment.getString("publish_date")),
                        jsonComment.getString("text"),
                        jsonComment.getInt("rating")
                );
                commentList.add(comment);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<Comment> getCommentList() {
        return commentList;
    }
}
