package jat.imview.processor.responseParser;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import jat.imview.model.Image;
import jat.imview.rest.Response;
import jat.imview.util.DateUtil;

/**
 * Created by bulat on 23.12.15.
 */
public class ImageListParser {
    private static final String LOG_TAG = "MyResponse";
    private List<Image> imageList = new ArrayList<>();
    public ImageListParser(Response response) {
        try {
            String responseBody = new String(response.body);
            Log.d(LOG_TAG, responseBody);
            JSONArray jsonImages = new JSONArray(responseBody);
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
