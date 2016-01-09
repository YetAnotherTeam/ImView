package jat.imview.contentProvider.DB.Table;

import android.net.Uri;

/**
 * Created by bulat on 07.01.16.
 */
public interface FeaturedTable extends BaseParams, ImageListParams {
    String TABLE_NAME = "featured";

    String URI_PATH = "featured";
    Uri CONTENT_URI = Uri.parse(SCHEME + ImageTable.AUTHORITY + "/" + ImageTable.URI_PATH + "/" + URI_PATH);
}
