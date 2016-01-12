package jat.imview.contentProvider.db.table;

import android.net.Uri;

import jat.imview.contentProvider.db.table.base.BaseParams;
import jat.imview.contentProvider.db.table.base.ImageListParams;

/**
 * Created by bulat on 07.01.16.
 */
public interface FeaturedTable extends BaseParams, ImageListParams {
    String TABLE_NAME = "featured";

    String URI_PATH = "featured";
    Uri CONTENT_URI = Uri.parse(SCHEME + ImageTable.AUTHORITY + "/" + ImageTable.URI_PATH + "/" + URI_PATH);
}
