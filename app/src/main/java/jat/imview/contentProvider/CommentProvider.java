package jat.imview.contentProvider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import jat.imview.contentProvider.db.DBHelper;
import jat.imview.contentProvider.db.table.CommentTable;
import jat.imview.contentProvider.db.table.ImageTable;

/**
 * Created by bulat on 10.01.16.
 */
public class CommentProvider extends ContentProvider {
    public static final UriMatcher uriMatcher;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    private static final int URI_COMMENTS = 1;
    private static final int URI_COMMENT_ID = 2;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(CommentTable.AUTHORITY, CommentTable.URI_PATH, URI_COMMENTS);
        uriMatcher.addURI(CommentTable.AUTHORITY, CommentTable.URI_PATH + "/#", URI_COMMENT_ID);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(this.getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        switch (uriMatcher.match(uri)) {
            case URI_COMMENTS:

                break;
            case URI_COMMENT_ID:
                int id = Integer.parseInt(uri.getLastPathSegment());
                selection = DBHelper.appendRowId(selection, id);
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(ImageTable.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case URI_COMMENTS:
                return CommentTable.CONTENT_TYPE;
            case URI_COMMENT_ID:
                return CommentTable.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        if (uriMatcher.match(uri) != URI_COMMENT_ID) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
        db = dbHelper.getWritableDatabase();
        long id = db.insertWithOnConflict(CommentTable.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        Uri newUri = ContentUris.withAppendedId(CommentTable.CONTENT_URI, id);
        getContext().getContentResolver().notifyChange(newUri, null);
        return newUri;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
