package jat.imview.rest.ResponseMessages;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import jat.imview.model.Image;
import jat.imview.model.ImageList;

/**
 * Created by bulat on 23.12.15.
 */
public class ImageListResponse {
    public ImageListResponse(String responseBody) {
        try {
            JSONArray jsonImages = new JSONArray(responseBody);
            for (int i = 0; i < jsonImages.length(); ++i) {
                JSONObject jsonImage = (JSONObject) jsonImages.get(i);
                Image image = new Image(
                        jsonImage.getInt("id"),
                        jsonImage.getString("path"),
                        jsonImage.getBoolean("is_featured"),
                        jsonImage.getInt("rating")
                );
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public ImageList getImageList() {
        return new ImageList();
    }
}
