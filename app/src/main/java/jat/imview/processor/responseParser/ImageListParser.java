package jat.imview.processor.responseParser;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jat.imview.model.Image;
import jat.imview.rest.Response;

/**
 * Created by bulat on 23.12.15.
 */
public class ImageListParser {
    private static final String LOG_TAG = "MyResponse";
    private List<Image> imageList = new ArrayList<>();
    private static final DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS", Locale.ENGLISH);
    public ImageListParser(Response response) {
        try {
            String responseBody = new String(response.body);
            Log.d(LOG_TAG, responseBody);
            JSONArray jsonImages = new JSONArray(responseBody);
            for (int i = 0; i < jsonImages.length(); ++i) {
                JSONObject jsonImage = (JSONObject) jsonImages.get(i);
                Image image = null;
                try {
                    image = new Image(
                            jsonImage.getInt("id"),
                            jsonImage.getString("path"),
                            format.parse(jsonImage.getString("publish_date")),
                            jsonImage.getInt("rating")
                    );
                } catch (ParseException e) {
                    e.printStackTrace();
                }
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
