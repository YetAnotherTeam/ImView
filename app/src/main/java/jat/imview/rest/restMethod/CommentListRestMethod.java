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

public class CommentListRestMethod extends AbstractRestMethod<List<Image>> {
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
        return new Request(HTTPMethod.GET, currentURL);
    }
}
