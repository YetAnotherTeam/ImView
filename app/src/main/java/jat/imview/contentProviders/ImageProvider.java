package jat.imview.contentProviders;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by bulat on 07.12.15.
 */
public class ImageProvider extends ContentProvider {
    static final String DB_NAME = "ImViewDB";
    static final int DB_VERSION = 1;

    static final String IMAGE_TABLE = "image";

    static final String IMAGE_ID = "_id";
    static final String IMAGE_PATH = "path";

    static final String TABLE_CREATE = String.format("create table %s(%s integer primary key autoincrement, %s text);", IMAGE_TABLE, IMAGE_ID, IMAGE_PATH);

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String[] getStreamTypes(Uri uri, String mimeTypeFilter) {
        return super.getStreamTypes(uri, mimeTypeFilter);
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
