package jat.imview.rest;

import android.content.Context;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.net.URI;

import jat.imview.model.Image;
import jat.imview.rest.ResponseMessages.ImageListResponse;

import static jat.imview.rest.ConnectionParams.HOST;
import static jat.imview.rest.ConnectionParams.SCHEMA;

public class GetImageRestMethod extends AbstractRestMethod<Image[]> {
    private WeakReference<Context> weekContext;
    private static final URI IMAGE_URI = URI.create(SCHEMA + HOST + "image/list");

    public GetImageRestMethod(Context context) {
        weekContext = new WeakReference<>(context.getApplicationContext());
    }

    @Override
    protected Request buildRequest() {
        return new Request(HTTPMethod.GET, IMAGE_URI, null, null);
    }

    @Override
    protected Image[] parseResponseBody(String responseBody) throws Exception {
        JSONObject json = new JSONObject(responseBody);
        return new ImageListResponse(json).getImageArray();
    }

    @Override
    protected Context getContext() {
        return weekContext.get();
    }

}
