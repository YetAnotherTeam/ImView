package jat.imview.rest.resource;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import jat.imview.contentProvider.db.table.base.BaseParams;
import jat.imview.contentProvider.db.table.ImageTable;
import jat.imview.model.Image;
import jat.imview.rest.resource.base.Resource;
import jat.imview.util.DateUtil;

/**
 * Created by bulat on 23.12.15.
 */
public class ImageListResource implements Resource {
    public static final Uri CONTENT_URI = Uri.parse(BaseParams.SCHEME + ImageTable.AUTHORITY + "/" + ImageTable.URI_PATH);
    private static final String LOG_TAG = "MyResponse";
    private List<Image> imageList = new ArrayList<>();

    public ImageListResource(byte[] responseBody) {
        try {
            String responseString = new String(responseBody);
            Log.d(LOG_TAG, responseString);
            JSONArray jsonImages = new JSONArray(responseString);
            for (int i = 0; i < jsonImages.length(); ++i) {
                JSONObject jsonImage = (JSONObject) jsonImages.get(i);
                Image image = new Image(
                        jsonImage.getInt("id"),
                        jsonImage.getString("path"),
                        DateUtil.parseFromServerString(jsonImage.getString("publish_date")),
                        jsonImage.getInt("rating"),
                        jsonImage.getInt("comments_count")
                );
                imageList.add(image);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<Image> getImageList() {
        return imageList;
    }
}
