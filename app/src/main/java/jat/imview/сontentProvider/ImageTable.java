package jat.imview.сontentProvider;

import android.net.Uri;

/**
 * Created by bulat on 23.12.15.
 */
public interface ImageTable extends ResourceTable {
    String TABLE_NAME = "images";
    /* Columns */
    String ID = "id";
    String NETPATH = "netpath";
    String RATING = "rating";
    String PUBLISH_DATE = "publish_date";
    String FILEPATH = "filepath";

    String SCHEME = "content://";
    String AUTHORITY = "jat.imview.imageProvider";
    String URI_PATH = "images";
    String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + URI_PATH;
    String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + AUTHORITY + "." + URI_PATH;
    Uri CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + "/" + TABLE_NAME);
}
