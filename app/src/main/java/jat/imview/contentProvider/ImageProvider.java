package jat.imview.contentProvider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by bulat on 07.12.15.
 */
public class ImageProvider extends ContentProvider {
    public final static UriMatcher uriMatcher;
    private DBHelper dbHelper;
    private static final int URI_IMAGES = 1;
    private static final int URI_IMAGES_ID = 2;
    SQLiteDatabase db;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(ImageTable.AUTHORITY, ImageTable.URI_PATH, URI_IMAGES);
        uriMatcher.addURI(ImageTable.AUTHORITY, ImageTable.URI_PATH + "/#", URI_IMAGES_ID);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(this.getContext());
        return true;
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
            case URI_IMAGES:
                return ImageTable.CONTENT_TYPE;
            case URI_IMAGES_ID:
                return ImageTable.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        db = dbHelper.getWritableDatabase();
        if (uriMatcher.match(uri) != URI_IMAGES_ID) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
        long id = db.insertOrThrow(ImageTable.TABLE_NAME, null, values);

        Uri newUri = ContentUris.withAppendedId(ImageTable.CONTENT_URI, id);
        getContext().getContentResolver().notifyChange(newUri, null);
        return newUri;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        switch (uriMatcher.match(uri)) {
            case URI_IMAGES:
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = ImageTable.PUBLISH_DATE + " ASC";
                }
                break;
            case URI_IMAGES_ID:
                int id = Integer.parseInt(uri.getLastPathSegment());
                selection = appendRowId(selection, id);
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(ImageTable.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case URI_IMAGES:
                throw new UnsupportedOperationException("Not implemented");
            case URI_IMAGES_ID:
                int id = Integer.parseInt(uri.getLastPathSegment());
                selection = appendRowId(selection, id);
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = dbHelper.getWritableDatabase();
        int count = db.update(ImageTable.TABLE_NAME, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Not implemented");
    }

    // Чтобы проверяло и по id
    private String appendRowId(String selection, long id) {
        return ImageTable.ID + " = " + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : "");
    }
}
