package jat.imview.сontentProvider;

import android.net.Uri;

/**
 * Created by bulat on 23.12.15.
 */
public class ImageConstants {
    public static final String AUTHORITY = "jat.imview.images";
    public static final String TABLE_NAME = "images";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);
    public static final String FILEPATH = "filepath";
}
