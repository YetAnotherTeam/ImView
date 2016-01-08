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
import android.text.TextUtils;

import jat.imview.contentProvider.DB.DBHelper;
import jat.imview.contentProvider.DB.Table.AbyssTable;
import jat.imview.contentProvider.DB.Table.FeaturedTable;
import jat.imview.contentProvider.DB.Table.ImageTable;
import jat.imview.model.Image;

/**
 * Created by bulat on 07.12.15.
 */
public class ImageProvider extends ContentProvider {
    public static final UriMatcher uriMatcher;
    private DBHelper dbHelper;
    private static final int URI_IMAGES = 1;
    private static final int URI_IMAGES_ID = 2;
    private static final int URI_FEATURED_IMAGES = 3;
    private static final int URI_ABYSS_IMAGES = 4;
    private SQLiteDatabase db;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(ImageTable.AUTHORITY, ImageTable.URI_PATH, URI_IMAGES);
        uriMatcher.addURI(ImageTable.AUTHORITY, ImageTable.URI_PATH + "/#", URI_IMAGES_ID);
        uriMatcher.addURI(ImageTable.AUTHORITY, ImageTable.URI_PATH + "/" + FeaturedTable.URI_PATH, URI_FEATURED_IMAGES);
        uriMatcher.addURI(ImageTable.AUTHORITY, ImageTable.URI_PATH + "/" + AbyssTable.URI_PATH, URI_ABYSS_IMAGES);
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
    public Uri insert(@NonNull Uri uri, ContentValues values) {
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
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
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
    public int bulkInsert(@NonNull Uri uri, ContentValues[] values) {
        switch (uriMatcher.match(uri)) {
            case URI_FEATURED_IMAGES:
            case URI_ABYSS_IMAGES:
                db = dbHelper.getWritableDatabase();
                int insertCount = 0;
                try{
                    db.beginTransaction();
                    for (ContentValues valuesItem: values) {
                        db.insert(FeaturedTable.TABLE_NAME, null, valuesItem);
                    }
                    db.setTransactionSuccessful();
                    insertCount = values.length;
                } catch (Exception e){
                    e.printStackTrace();
                } finally {
                    db.endTransaction();
                }
                return insertCount;
        }
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
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
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case URI_FEATURED_IMAGES:
                db = dbHelper.getWritableDatabase();
                db.execSQL(DBHelper.getTruncateSqlQuery(FeaturedTable.TABLE_NAME));
                break;
            case URI_ABYSS_IMAGES:
                db = dbHelper.getWritableDatabase();
                db.execSQL(DBHelper.getTruncateSqlQuery(AbyssTable.TABLE_NAME));
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        return 0;
    }

    // Чтобы проверяло и по id
    private String appendRowId(String selection, long id) {
        return ImageTable.ID + " = " + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : "");
    }
}