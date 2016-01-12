package jat.imview.rest.restMethod;

import android.content.Context;
import android.net.Uri;

import java.lang.ref.WeakReference;
import java.net.URI;

import jat.imview.rest.http.HTTPMethod;
import jat.imview.rest.http.Request;
import jat.imview.rest.resource.ImageListResource;
import jat.imview.rest.restMethod.base.AbstractRestMethod;

import static jat.imview.rest.http.ConnectionParams.HOST;
import static jat.imview.rest.http.ConnectionParams.SCHEME;

public class ImageVoteRestMethod extends AbstractRestMethod<ImageListResource> {
    private WeakReference<Context> weekContext;
    private boolean isUpVote = true;
    private int imageId;
    private static final String PATH = "/image/vote";
    private static final URI IMAGE_URL = URI.create(SCHEME + HOST + PATH);

    public ImageVoteRestMethod(Context context) {
        weekContext = new WeakReference<>(context.getApplicationContext());
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public void setIsUpVote(boolean isUpVote) {
        this.isUpVote = isUpVote;
    }

    @Override
    protected Request buildRequest() {
        URI currentURL = IMAGE_URL;
        currentURL = URI.create(
                Uri.parse(IMAGE_URL.toString()).buildUpon()
                        .appendQueryParameter("is_featured", "1")
                        .build().toString()
        );
        return new Request(HTTPMethod.POST, currentURL, null);
    }

    @Override
    protected ImageListResource parseResponseBody(byte[] responseBody) throws Exception {
        return null;
    }
}
