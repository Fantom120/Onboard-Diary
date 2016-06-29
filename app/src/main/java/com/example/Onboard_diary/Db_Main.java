package com.example.Onboard_diary;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class Db_Main extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "diary";
    private static final String TABLE_DATA = "data";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_THEME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_PATH = "path";
    private static final String COLUMN_DATE = "date";


    public Db_Main(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public Db_Main(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DATA_TABLE = "CREATE TABLE "
                + TABLE_DATA + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_THEME + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_DATE + " LONG,"
                + COLUMN_PATH + " TEXT" + ")";
        db.execSQL(CREATE_DATA_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);
//        db.execSQL("ALTER TABLE  phone_number RENAME TO description " );
//        db.execSQL("ALTER TABLE  id RENAME TO _id " );
        onCreate(db);
    }


    public synchronized void  addItem(DataItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_THEME, item.getTheme());
        values.put(COLUMN_DESCRIPTION, item.getDescription());
        values.put(COLUMN_DATE, item.getDate());
        values.put(COLUMN_PATH,item.getAudioPathKey());

        db.insert(TABLE_DATA, null, values);
        db.close();
    }

    public void addItems(List<DataItem> items) {
        if (items != null && items.size() > 0) {
            // obtain a readable database
            SQLiteDatabase db = getWritableDatabase();

            for (DataItem item : items) {
                addItem(item);
            }
            // close the database connection
            db.close();
        }
    }

    public synchronized void deleteItem(DataItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DATA, COLUMN_ID + " = ?", new String[]{String.valueOf(item.get_id())});
        db.close();

    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DATA, null, null);
        db.close();
    }

    public synchronized void updateItem(DataItem item) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_THEME, item.getTheme());
        values.put(COLUMN_DESCRIPTION, item.getDescription());
        values.put(COLUMN_DATE, item.getDate());
        values.put(COLUMN_PATH, item.getAudioPathKey());
         db.update(TABLE_DATA, values, COLUMN_ID + " = ?",
                 new String[]{String.valueOf(item.get_id())});
        db.close();
    }

    public DataItem getItemDetails(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        DataItem item = new DataItem();
        Cursor cursor = db.query(TABLE_DATA, new String[]{COLUMN_THEME, COLUMN_DESCRIPTION, COLUMN_DATE},
                COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        //  Cursor cursor  =  db.rawQuery("SELECT * FROM "+ TABLE_DATA + " WHERE _id = ?", new String[]{String.valueOf(id)});
        cursor.moveToFirst();

        item.setTheme(cursor.getString(cursor.getColumnIndex(COLUMN_THEME)));
        item.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
        item.setDate(cursor.getLong(cursor.getColumnIndex(COLUMN_DATE)));


        cursor.close();
        db.close();
        return item;
    }


    public int getDataCount() {
        String countQuery = "SELECT * FROM " + TABLE_DATA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }


    public List<DataItem> getAllData() {

        List<DataItem> itemList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_DATA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    DataItem item = new DataItem();
                    item.set_id(Integer.parseInt(cursor.getString(0)));
                    item.setTheme(cursor.getString(1));
                    item.setDescription(cursor.getString(2));
                    item.setDate(cursor.getLong(3));
                    item.setAudioPathKey(cursor.getString(4));
                    itemList.add(item);
                } while (cursor.moveToNext());
            }
        }
        db.close();
        return itemList;
    }


}
