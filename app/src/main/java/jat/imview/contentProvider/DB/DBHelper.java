package jat.imview.contentProvider.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import jat.imview.contentProvider.DB.Table.FeaturedTable;
import jat.imview.contentProvider.DB.Table.ImageTable;

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
        StringBuilder sqlBuilder = new StringBuilder();
        String sqlQuery;

        // IMAGE TABLE
        sqlBuilder.append("CREATE TABLE ").append(ImageTable.TABLE_NAME).append(" (");
        sqlBuilder.append(ImageTable.ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append(ImageTable.NETPATH).append(" TEXT NOT NULL, ");
        sqlBuilder.append(ImageTable.RATING).append(" INTEGER NOT NULL DEFAULT 0, ");
        sqlBuilder.append(ImageTable.PUBLISH_DATE).append(" DATETIME NOT NULL, ");
        sqlBuilder.append(ImageTable.FILEPATH).append(" TEXT, ");
        sqlBuilder.append(ImageTable.STATE).append(" INTEGER NOT NULL DEFAULT ")
                .append(RequestState.WAITING.ordinal());
        sqlBuilder.append(");");
        sqlQuery = sqlBuilder.toString();
        db.execSQL(sqlQuery);

        // FEATURED TABLE
        sqlBuilder.setLength(0);
        sqlBuilder.append("CREATE TABLE ").append(FeaturedTable.TABLE_NAME).append(" (");
        sqlBuilder.append(FeaturedTable.ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append(FeaturedTable.IMAGE_ID).append(" INTEGER, ");
        sqlBuilder.append("FOREIGN KEY(").append(FeaturedTable.IMAGE_ID).append(") REFERENCES ")
                .append(ImageTable.TABLE_NAME).append("(").append(ImageTable.ID).append(")");
        sqlBuilder.append(");");
        sqlQuery = sqlBuilder.toString();
        db.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public static String getTruncateSqlQuery(String tableName) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("DELETE FROM ").append(tableName).append(";");
        sqlBuilder.append("VACUUM;");
        return sqlBuilder.toString();
    }
}
