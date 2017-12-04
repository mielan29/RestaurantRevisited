package com.example.milan.restaurantrevisited;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Milan on 4-12-2017.
 */

public class RestoDatabase extends SQLiteOpenHelper {
    private static RestoDatabase instance;

    //Database info
    private static String DATABASE_NAME ="RestoDatabase";
    private static Integer DATABASE_VERSION = 1;
    private static String TABLE_ORDER = "orders";

    private static String KEY_SQL_ID = "_id";
    private static String KEY_SQL_NAME = "name";
    private static String KEY_SQL_PRICE = "price";
    private static String KEY_SQL_COUNT = "count";
    public Cursor cursor;
    public SQLiteDatabase db;

    private RestoDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

        @Override
        public void onCreate(SQLiteDatabase sgLiteDatabase) {
            String createTableOrder = "CREATE TABLE " + TABLE_ORDER +
                    "(" +
                    KEY_SQL_ID + " INTEGER PRIMARY KEY, " +
                KEY_SQL_NAME + " TEXT, " +
                KEY_SQL_PRICE + " INTEGER, " +
                KEY_SQL_COUNT + " INTEGER DEFAULT 0"+
                ")";
        Log.d("homo's", createTableOrder);
        sgLiteDatabase.execSQL(createTableOrder);
        onUpgrade(sgLiteDatabase, 1, 1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if ( i != i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER);
            onCreate(sqLiteDatabase);
        }
    }

    public void clear(){
        db = instance.getWritableDatabase();
        String clearcountsql =  "UPDATE " + TABLE_ORDER +
                " SET " + KEY_SQL_COUNT + "= 0 " +
                " WHERE  NOT " + KEY_SQL_COUNT + " = 0";
        Log.d("clearcount", clearcountsql);
        db.execSQL(clearcountsql);
    }

    public Cursor selectAll() {
        //String selectall = "SELECT * FROM " + TABLE_ORDER + " WHERE " + KEY_SQL_COUNT + " > 0";
        String selectall = "SELECT * FROM " + TABLE_ORDER + ';';
        SQLiteDatabase db = instance.getWritableDatabase();
        cursor = db.rawQuery(selectall, null);
        if (cursor.moveToFirst()) {
            do {
                Log.d("hallo, lul", cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));
            }while(cursor.moveToNext());
        }
        return cursor;
    }
    public static RestoDatabase getInstance (Context context) {
        if (instance == null) {
            return instance = new RestoDatabase(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        else {
            return instance;
        }
    }

    public void addItem(String item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_SQL_NAME, item);
        contentValues.put(KEY_SQL_COUNT, 1);
        SQLiteDatabase db = instance.getWritableDatabase();
        long test = db.insert(TABLE_ORDER, null, contentValues);
        Log.d("insert", String.valueOf(test));
        Log.d("Order is nu", item);
    }

    public void check(String item) {
        String[] bindingArgs = new String[] {item};
        cursor = db.rawQuery("SELECT " + KEY_SQL_NAME +
                " FROM " + TABLE_ORDER + " WHERE " + KEY_SQL_NAME + "= ?",bindingArgs, null);
        if (cursor.getColumnCount() == 1 ) {
            updatecount(item);
        }
        else {
            addItem(item);
        }
    }

    public void updatecount(String item) {
        SQLiteDatabase db = instance.getWritableDatabase();
        String valueToIncrementBy = "1";
        String[] bindingArgs = new String[] { valueToIncrementBy, KEY_SQL_NAME};
        db.execSQL("UPDATE " + TABLE_ORDER +
                " SET " + KEY_SQL_COUNT + "= " + KEY_SQL_COUNT + " + ?" +
                " WHERE " + KEY_SQL_NAME + "= ?",bindingArgs);
    }
}
