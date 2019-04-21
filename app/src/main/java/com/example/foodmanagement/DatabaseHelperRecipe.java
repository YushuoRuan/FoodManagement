package com.example.foodmanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;


public class DatabaseHelperRecipe extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "FoodManagement.db";
    public static final String RECIPE_TABLE_NAME = "recipe_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "Current"; //1:current 0:history
    public static final String COL_3 = "Name";
    public static final String COL_4 = "Type"; //main dish, appetizer
    public static final String COL_5 = "Cuisine"; //asian, american
    public static final String COL_6 = "Ingredients";//"tomato,egg,ketchup"
    public static final String COL_7 = "Amounts";//"1, 2, 0.3"
    public static final String COL_8 = "Units";//"individual, individual, oz"


    //call DatabaseHelperShop(this) from another activity if you want to use this table
    public DatabaseHelperRecipe(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + RECIPE_TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, Current INTEGER, Name TEXT, Type TEXT, Cuisine TEXT, Ingredients TEXT, Amounts REAL, Units TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+RECIPE_TABLE_NAME);
        onCreate(db);
    }

    //insert one ingredient to shopping table
    //return false: unsuccessful
    //return true: successful
    public boolean insertRecipe(String curr, String n, String t, String cuis, String ingreds, String amounts, String units){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, curr);
        contentValues.put(COL_3, n);
        contentValues.put(COL_4, t);
        contentValues.put(COL_5, cuis);
        contentValues.put(COL_6, ingreds);
        contentValues.put(COL_7, amounts);
        contentValues.put(COL_8, units);

        long result = db.insert(RECIPE_TABLE_NAME, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;

    }
    //get all data in Recipe table
    //return multiple lines dataframe
    public Cursor getAllRecipeData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+RECIPE_TABLE_NAME, null);
        return res;
    }

    //get one recipe from recipe table
    //return one line dataframe
    public Cursor getRecipe(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+RECIPE_TABLE_NAME + " where ID = " + Integer.toString(id), null);
        res.moveToNext();
        return res;
    }
    //delete one recipe from Recipe table
    //return 0: unsuccessful
    //return 1: successful
    public Boolean toHistoryRecipe (Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update "+RECIPE_TABLE_NAME+" set Current = 1 where ID = " + Integer.toString(id));
        return true;
    }


}
