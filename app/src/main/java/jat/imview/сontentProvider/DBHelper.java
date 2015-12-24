package jat.imview.сontentProvider;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by FRAGnat on 23.12.15.
 */
public class DBHelper extends SQLiteOpenHelper {

    final String LOG_TAG = "myLogs";

    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "--- onCreate database ---");
        // создаем таблицу с полями
        db.execSQL("create table mytable ("
                + "id integer primary key autoincrement,"
                + "isLike text" + ");");
        db.execSQL("create table comment ("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "usertext text" + ");");
//        String isLike = "true";
//        SQLiteDatabase dbNew = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put("name", isLike);
//        cv.put("usertext", "zabrComment");
//        long rowID = dbNew.insert("comment", null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

