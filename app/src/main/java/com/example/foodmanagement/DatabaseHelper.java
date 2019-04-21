package com.example.foodmanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.Date;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "FoodManagement.db";

    public static final String INVENTORY_TABLE_NAME = "inventory_table";
    public static final String COLI_1 = "ID";
    public static final String COLI_2 = "Type";
    public static final String COLI_3 = "Name";
    public static final String COLI_4 = "Amount";
    public static final String COLI_5 = "Unit";
    public static final String COLI_6 = "Storage";
    public static final String COLI_7 = "Expire";
    public static final String COLI_8 = "Tags";

    public static final String SHOPPING_TABLE_NAME = "shoppinglist_table";
    public static final String COLS_1 = "ID";
    public static final String COLS_2 = "Type";
    public static final String COLS_3 = "Name";
    public static final String COLS_4 = "Amount";
    public static final String COLS_5 = "Unit";
    public static final String COLS_6 = "Storage";
    public static final String COLS_7 = "Expire";
    public static final String COLS_8 = "Tags";


    public static final String RECIPE_TABLE_NAME = "recipe_table";
    public static final String COLR_1 = "ID";
    public static final String COLR_2 = "Current"; //1:current 0:history
    public static final String COLR_3 = "Name";
    public static final String COLR_4 = "Type"; //main dish, appetizer
    public static final String COLR_5 = "Cuisine"; //asian, american
    public static final String COLR_6 = "Ingredients";//"tomato,egg,ketchup"
    public static final String COLR_7 = "Amounts";//"1, 2, 0.3"
    public static final String COLR_8 = "Units";//"individual, individual, oz"


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + INVENTORY_TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, Type Text, Name Text, Amount REAL, Unit TEXT, Storage TEXT, Expire TEXT, Tags TEXT)");
        db.execSQL("create table " + SHOPPING_TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, Type Text, Name Text, Amount REAL, Unit TEXT, Storage TEXT, Expire TEXT, Tags TEXT)");
        db.execSQL("create table " + RECIPE_TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, Current INTEGER, Name TEXT, Type TEXT, Cuisine TEXT, Ingredients TEXT, Amounts REAL, Units TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+INVENTORY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+SHOPPING_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+RECIPE_TABLE_NAME);
        onCreate(db);
    }


    //inventory database helper functions

    public boolean insertDataInventory(String t, String n, String a, String u, String s, String e, String tags){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLI_2, t);
        contentValues.put(COLI_3, n);
        contentValues.put(COLI_4, a);
        contentValues.put(COLI_5, u);
        contentValues.put(COLI_6, s);
        contentValues.put(COLI_7, e);
        contentValues.put(COLI_8, tags);

        long result = db.insert(INVENTORY_TABLE_NAME, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;

    }

    public boolean subtractDataInventory(Integer id, Double num){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+INVENTORY_TABLE_NAME + " where ID = " + Integer.toString(id), null);
        res.moveToNext();
        Double amount = res.getDouble(3);
        amount = amount - num;
        if(amount<=0)
        {
            return false;
        }
        db.execSQL("update "+INVENTORY_TABLE_NAME+" set Amount = " + Double.toString(amount) + " where ID = " + Integer.toString(id));
        return true;
    }
    public Cursor getInventoryData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+INVENTORY_TABLE_NAME, null);
        return res;
    }

    public Cursor getIngredient(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+INVENTORY_TABLE_NAME + " where ID = " + Integer.toString(id), null);
        res.moveToNext();
        return res;
    }
    public Integer deleteInventoryData (Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(INVENTORY_TABLE_NAME, "ID = ?", new String[] {Integer.toString(id)});
    }



    //shopping list database helper functions

    public boolean insertDataShopping(String t, String n, String a, String u, String s, String e, String tags){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLS_2, t);
        contentValues.put(COLS_3, n);
        contentValues.put(COLS_4, a);
        contentValues.put(COLS_5, u);
        contentValues.put(COLS_6, s);
        contentValues.put(COLS_7, e);
        contentValues.put(COLS_8, tags);

        long result = db.insert(SHOPPING_TABLE_NAME, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;

    }

    public Cursor getShoppingData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+SHOPPING_TABLE_NAME, null);
        return res;
    }
    public Integer deleteShoppingData (Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(SHOPPING_TABLE_NAME, "ID = ?", new String[] {Integer.toString(id)});
    }


    //recipe database helper functions


    //insert one ingredient to shopping table
    //return false: unsuccessful
    //return true: successful
    public boolean insertRecipe(String curr, String n, String t, String cuis, String ingreds, String amounts, String units){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLR_2, curr);
        contentValues.put(COLR_3, n);
        contentValues.put(COLR_4, t);
        contentValues.put(COLR_5, cuis);
        contentValues.put(COLR_6, ingreds);
        contentValues.put(COLR_7, amounts);
        contentValues.put(COLR_8, units);

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
