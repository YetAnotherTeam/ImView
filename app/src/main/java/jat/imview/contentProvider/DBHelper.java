package jat.imview.contentProvider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import jat.imview.rest.RequestState;

/**
 * Created by bulat on 23.12.15.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "imview.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // IMAGE TABLE
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE ").append(ImageTable.TABLE_NAME).append(" (");
        sqlBuilder.append(ImageTable.ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append(ImageTable.NETPATH).append(" TEXT NOT NULL, ");
        sqlBuilder.append(ImageTable.RATING).append(" INTEGER NOT NULL DEFAULT 0, ");
        sqlBuilder.append(ImageTable.PUBLISH_DATE).append(" DATETIME NOT NULL, ");
        sqlBuilder.append(ImageTable.FILEPATH).append(" TEXT, ");
        sqlBuilder.append(ImageTable.STATE).append(" INTEGER NOT NULL DEFAULT ")
                .append(RequestState.WAITING.ordinal());
        sqlBuilder.append(");");
        String sqlQuery = sqlBuilder.toString();
        db.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
