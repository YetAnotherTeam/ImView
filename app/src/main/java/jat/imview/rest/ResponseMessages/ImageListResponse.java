package jat.imview.rest.ResponseMessages;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import jat.imview.model.Image;

/**
 * Created by bulat on 23.12.15.
 */
public class ImageListResponse {
    public ImageListResponse(JSONObject jsonObject) {
        try {
            JSONArray jsonPaths = jsonObject.getJSONArray("paths");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public Image[] getImageArray() {
        return null;
    }
}
