package jat.imview.contentProvider.DB.Table;

import android.net.Uri;

import jat.imview.contentProvider.DB.Table.Base.BaseParams;

/**
 * Created by bulat on 10.01.16.
 */
public interface CommentTable extends BaseParams, ResourceTable {
    String TABLE_NAME = "comments";
    /* Columns */
    String TEXT = "text";
    String IMAGE_ID = "image_id";
    String USER_ID = "user_id";

    String AUTHORITY = "jat.imview.commentProvider";
    String URI_PATH = "comments";
    String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + URI_PATH;
    String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + AUTHORITY + "." + URI_PATH;
    Uri CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + "/" + URI_PATH);
}
