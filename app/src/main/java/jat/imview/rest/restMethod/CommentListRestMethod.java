package jat.imview.rest.restMethod;

import android.content.Context;
import android.net.Uri;

import java.lang.ref.WeakReference;
import java.net.URI;

import jat.imview.rest.http.HTTPMethod;
import jat.imview.rest.http.Request;
import jat.imview.rest.resource.CommentListResource;
import jat.imview.rest.restMethod.base.AbstractRestMethod;

import static jat.imview.rest.http.ConnectionParams.HOST;
import static jat.imview.rest.http.ConnectionParams.SCHEME;

public class CommentListRestMethod extends AbstractRestMethod<CommentListResource> {
    private WeakReference<Context> weekContext;
    private int imageId;
    private static final String PATH = "/comment/list";
    private static final URI COMMENT_LIST_URL = URI.create(SCHEME + HOST + PATH);

    public CommentListRestMethod(Context context) {
        weekContext = new WeakReference<>(context.getApplicationContext());
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    @Override
    protected Request buildRequest() {
        URI currentURL = URI.create(
                Uri.parse(COMMENT_LIST_URL.toString()).buildUpon()
                        .appendQueryParameter("image_id", String.valueOf(imageId))
                        .build().toString()
        );
        return new Request(HTTPMethod.GET, currentURL, null);
    }

    @Override
    protected CommentListResource parseResponseBody(byte[] responseBody) throws Exception {
        return null;
    }
}
