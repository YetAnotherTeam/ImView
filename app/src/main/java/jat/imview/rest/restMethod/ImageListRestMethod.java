package jat.imview.rest.restMethod;

import android.content.Context;
import android.net.Uri;

import java.lang.ref.WeakReference;
import java.net.URI;
import java.util.List;

import jat.imview.model.Image;
import jat.imview.rest.HTTPMethod;
import jat.imview.rest.Request;

import static jat.imview.rest.restMethod.ConnectionParams.HOST;
import static jat.imview.rest.restMethod.ConnectionParams.SCHEME;

public class ImageListRestMethod extends AbstractRestMethod<List<Image>> {
    private WeakReference<Context> weekContext;
    private boolean isFeatured = false;
    private static final String PATH = "/image/list";
    private static final URI IMAGE_URL = URI.create(SCHEME + HOST + PATH);

    public ImageListRestMethod(Context context) {
        weekContext = new WeakReference<>(context.getApplicationContext());
    }

    public void setIsFeatured(boolean isFeatured) {
        this.isFeatured = isFeatured;
    }

    @Override
    protected Request buildRequest() {
        URI currentURL = IMAGE_URL;
        if (isFeatured) {
            currentURL = URI.create(
                    Uri.parse(IMAGE_URL.toString()).buildUpon()
                            .appendQueryParameter("is_featured", "1")
                            .build().toString()
            );
        }
        return new Request(HTTPMethod.GET, currentURL);
    }
}
