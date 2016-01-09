package jat.imview.contentProvider.DB.Table;

import android.net.Uri;

import jat.imview.contentProvider.DB.Table.Base.BaseParams;

/**
 * Created by bulat on 23.12.15.
 */
public interface ImageTable extends BaseParams, ResourceTable {
    String TABLE_NAME = "images";
    /* Columns */
    String NETPATH = "netpath";
    String RATING = "rating";
    String PUBLISH_DATE = "publish_date";
    String FILEPATH = "filepath";

    String AUTHORITY = "jat.imview.imageProvider";
    String URI_PATH = "images";
    String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + URI_PATH;
    String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + AUTHORITY + "." + URI_PATH;
    Uri CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + "/" + URI_PATH);
}
