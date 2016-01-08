package jat.imview.contentProvider.DB.Table;

import android.net.Uri;

/**
 * Created by bulat on 07.01.16.
 */
public interface AbyssTable extends BaseParams {
    String TABLE_NAME = "abyss";
    String IMAGE_ID = "image_id";

    String URI_PATH = "abyss";
    Uri CONTENT_URI = Uri.parse(SCHEME + ImageTable.AUTHORITY + "/" + ImageTable.URI_PATH + "/" + URI_PATH);
}
