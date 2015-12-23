package jat.imview.сontentProvider;
import static android.provider.BaseColumns._ID;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        sqlBuilder.append("CREATE TABLE").append(ImageConstants.TABLE_NAME).append(" (");
        sqlBuilder.append(_ID).append(" INTEGER, ");
        sqlBuilder.append(ImageConstants.FILEPATH).append(" TEXT, ");
        sqlBuilder.append(");");
        String sqlQuery = sqlBuilder.toString();
        db.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
