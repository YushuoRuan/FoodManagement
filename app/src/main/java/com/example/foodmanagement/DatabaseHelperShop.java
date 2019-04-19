package com.example.foodmanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;


public class DatabaseHelperShop extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "FoodManagement.db";
    public static final String SHOPPING_TABLE_NAME = "shoppinglist_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "Type";
    public static final String COL_3 = "Name";
    public static final String COL_4 = "Amount";
    public static final String COL_5 = "Unit";
    public static final String COL_6 = "Storage";
    public static final String COL_7 = "Expire";
    public static final String COL_8 = "Tags";


    //call DatabaseHelperShop(this) from another activity if you want to use this table
    public DatabaseHelperShop(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + SHOPPING_TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, Type Text, Name Text, Amount REAL, Unit TEXT, Storage TEXT, Expire TEXT, Tags TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+SHOPPING_TABLE_NAME);
        onCreate(db);
    }

    //insert one ingredient to shopping table
    //return false: unsuccessful
    //return true: successful
    public boolean insertDataShopping(String t, String n, String a, String u, String s, String e, String tags){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, t);
        contentValues.put(COL_3, n);
        contentValues.put(COL_4, a);
        contentValues.put(COL_5, u);
        contentValues.put(COL_6, s);
        contentValues.put(COL_7, e);
        contentValues.put(COL_8, tags);

        long result = db.insert(SHOPPING_TABLE_NAME, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;

    }
    //get all data in shopping table
    //return multiple lines dataframe
    public Cursor getShoppingData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+SHOPPING_TABLE_NAME, null);
        return res;
    }

    //get one ingredient from shopping table
    //return one line dataframe
    public Cursor getShoppingIngredient(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+SHOPPING_TABLE_NAME + " where ID = " + Integer.toString(id), null);
        res.moveToNext();
        return res;
    }
    //delete one ingredient from shopping table
    //return 0: unsuccessful
    //return 1: successful
    public Integer deleteShoppingData (Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(SHOPPING_TABLE_NAME, "ID = ?", new String[] {Integer.toString(id)});
    }


}
