package com.example.foodmanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "FoodManagement.db";
    public static final String INVENTORY_TABLE_NAME = "inventory_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "Type";
    public static final String COL_3 = "Name";
    public static final String COL_4 = "Amount";
    public static final String COL_5 = "Unit";
    public static final String COL_6 = "Storage";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + INVENTORY_TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, Type Text, Name Text, Amount INTEGER, Unit TEXT, Storage TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+INVENTORY_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertDataInventory(String type, String name, String amount, String unit, String storage){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, type);
        contentValues.put(COL_3, name);
        contentValues.put(COL_4, amount);
        contentValues.put(COL_5, unit);
        contentValues.put(COL_6, storage);
        long result = db.insert(INVENTORY_TABLE_NAME, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;

    }
    public Cursor getInventoryData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+INVENTORY_TABLE_NAME, null);
        return res;
    }
}
