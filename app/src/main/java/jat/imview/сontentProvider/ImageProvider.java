package jat.imview.сontentProvider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import static android.provider.BaseColumns._ID;
import static jat.imview.сontentProvider.ImageConstants.AUTHORITY;
import static jat.imview.сontentProvider.ImageConstants.CONTENT_URI;
import static jat.imview.сontentProvider.ImageConstants.TABLE_NAME;


/**
 * Created by bulat on 07.12.15.
 */
public class ImageProvider extends ContentProvider {
    private UriMatcher uriMatcher;
    private DBHelper dbHelper;
    private static final String CONTENT_TYPE = "jat.imview.cursor.dir/profile";
    private static final String CONTENT_ITEM_TYPE = "jat.imview.cursor.item/profile";
    private static final int IMAGES = 1;
    private static final int IMAGE_ID = 2;

    @Override
    public boolean onCreate() {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "images", IMAGES);
        uriMatcher.addURI(AUTHORITY, "images/#", IMAGE_ID);
        dbHelper = new DBHelper(this.getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (uriMatcher.match(uri) == IMAGE_ID) {
            int id = Integer.parseInt(uri.getPathSegments().get(1));
            selection = appendRowId(selection, id);
        }
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String[] getStreamTypes(Uri uri, String mimeTypeFilter) {
        return super.getStreamTypes(uri, mimeTypeFilter);
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case IMAGES:
                return CONTENT_TYPE;
            case IMAGE_ID:
                return CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (uriMatcher.match(uri) != IMAGES) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
        long id = db.insertOrThrow(TABLE_NAME, null, values);

        Uri newUri = ContentUris.withAppendedId(CONTENT_URI, id);
        getContext().getContentResolver().notifyChange(newUri, null);
        return newUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Not implemented");
    }

    // Чтобы проверяло и по id
    private String appendRowId(String selection, long id) {
        return _ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : "");
    }
}
