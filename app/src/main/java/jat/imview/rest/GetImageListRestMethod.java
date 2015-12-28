package jat.imview.rest;

import android.content.Context;
import android.net.Uri;

import java.lang.ref.WeakReference;
import java.net.URI;
import java.util.List;

import jat.imview.model.Image;
import jat.imview.rest.ResponseMessages.ImageListResponse;

import static jat.imview.rest.ConnectionParams.HOST;
import static jat.imview.rest.ConnectionParams.SCHEME;

public class GetImageListRestMethod extends AbstractRestMethod<List<Image>> {
    private WeakReference<Context> weekContext;
    private boolean isFeatured;
    private static final String PATH = "/image/list";
    private static final URI IMAGE_URI = URI.create(SCHEME + HOST + PATH);

    public GetImageListRestMethod(Context context) {
        weekContext = new WeakReference<>(context.getApplicationContext());
    }

    public void setIsFeatured(boolean isFeatured) {
        this.isFeatured = isFeatured;
    }

    @Override
    protected Request buildRequest() {
        URI currentURI = IMAGE_URI;
        if (isFeatured) {
            currentURI = URI.create(
                    Uri.parse(IMAGE_URI.toString()).buildUpon()
                            .appendQueryParameter("featured", "1")
                            .build().toString()
            );
        }
        return new Request(HTTPMethod.GET, currentURI, null);
    }

    @Override
    protected List<Image> parseResponseBody(String responseBody) throws Exception {
        return new ImageListResponse(responseBody).getImageList();
    }
}
