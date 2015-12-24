package jat.imview.rest;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.net.URI;

import jat.imview.model.Image;
import jat.imview.model.ImageList;
import jat.imview.rest.ResponseMessages.ImageListResponse;

import static jat.imview.rest.ConnectionParams.HOST;
import static jat.imview.rest.ConnectionParams.SCHEMA;

public class GetImageListRestMethod extends AbstractRestMethod<ImageList> {
    private WeakReference<Context> weekContext;
    private static final URI IMAGE_URI = URI.create(SCHEMA + HOST + "/image/list?is_featured=1");

    public GetImageListRestMethod(Context context) {
        weekContext = new WeakReference<>(context.getApplicationContext());
    }

    @Override
    protected Request buildRequest() {
        return new Request(HTTPMethod.GET, IMAGE_URI, null, null);
    }

    @Override
    protected ImageList parseResponseBody(String responseBody) throws Exception {
        return new ImageListResponse(responseBody).getImageList();
    }

    @Override
    protected Context getContext() {
        return weekContext.get();
    }

}
