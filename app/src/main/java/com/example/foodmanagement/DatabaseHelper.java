/*
 * Database Helper
 *      create database and table, initialize columns.
 *      contains all the function needed to access database.
 * Authors: Ziying Zhang, Tianshu Pang, Peng Yan, Yushuo Ruan
 */
package com.example.foodmanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    //database name
    public static final String DATABASE_NAME = "FoodManagement.db";

    //table name and column names for inventory table.
    public static final String INVENTORY_TABLE_NAME = "inventory_table";
    public static final String COLI_0 = "ID";
    public static final String COLI_1 = "Type";
    public static final String COLI_2 = "Name";
    public static final String COLI_3 = "Amount";
    public static final String COLI_4 = "Unit";
    public static final String COLI_5 = "Storage";
    public static final String COLI_6 = "Expire";
    public static final String COLI_7 = "Tags";

    //table name and column names for shopping list table.
    public static final String SHOPPING_TABLE_NAME = "shoppinglist_table";
    public static final String COLS_0 = "ID";
    public static final String COLS_1 = "Type";
    public static final String COLS_2 = "Name";
    public static final String COLS_3 = "Amount";
    public static final String COLS_4 = "Unit";
    public static final String COLS_5 = "Storage";
    public static final String COLS_6 = "Expire";
    public static final String COLS_7 = "Tags";

    //table name and column names for recipe table.
    public static final String RECIPE_TABLE_NAME = "recipe_table";
    public static final String COLR_0 = "ID";
    public static final String COLR_1 = "Current"; //1:current 0:history
    public static final String COLR_2 = "Name";
    public static final String COLR_3 = "Type"; //main dish, appetizer
    public static final String COLR_4 = "Cuisine"; //asian, american
    public static final String COLR_5 = "IDs";//"tomato,egg,ketchup"
    public static final String COLR_6 = "Names";//"1, 2, 0.3"
    public static final String COLR_7 = "Amounts";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    //construct the 3 tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + INVENTORY_TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, Type Text, Name Text, Amount REAL, Unit TEXT, Storage TEXT, Expire TEXT, Tags TEXT)");
        db.execSQL("create table " + SHOPPING_TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, Type Text, Name Text, Amount REAL, Unit TEXT, Storage TEXT, Expire TEXT, Tags TEXT)");
        db.execSQL("create table " + RECIPE_TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, Current INTEGER, Name TEXT, Type TEXT, Cuisine TEXT, IDs TEXT, Names TEXT, Amounts TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+INVENTORY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+SHOPPING_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+RECIPE_TABLE_NAME);
        onCreate(db);
    }

//inventory table helper funcitions: ========================================================================

    //take ingredient info and put in to database.
    public boolean insertDataInventory(String t, String n, String a, String u, String s, String e, String tags){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLI_1, t);
        contentValues.put(COLI_2, n);
        contentValues.put(COLI_3, a);
        contentValues.put(COLI_4, u);
        contentValues.put(COLI_5, s);
        contentValues.put(COLI_6, e);
        contentValues.put(COLI_7, tags);

        long result = db.insert(INVENTORY_TABLE_NAME, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;

    }

    //subtract ingredient amount when user modify amount in food edit page
    public boolean subtractDataInventory(Integer id, Double num){
        SQLiteDatabase db = this.getWritableDatabase();
        //execute query and get the recipe row.
        Cursor res = db.rawQuery("select * from "+INVENTORY_TABLE_NAME + " where ID = " + Integer.toString(id), null);
        res.moveToNext();
        Double amount = res.getDouble(3);
        amount = amount - num;
        if(amount < 0) {
            return false;
        }
        else if(amount == 0) {
            deleteInventoryData(id);
            return true;
        }
        else {
            db.execSQL("update " + INVENTORY_TABLE_NAME + " set Amount = " + Double.toString(amount) + " where ID = " + Integer.toString(id));
            return true;
        }
    }

    //subtract ingredient amount base on name, used when finish a recipe
    public void subtractDataInventoryName (String name, double num) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + INVENTORY_TABLE_NAME + " where Name = '" + name +"'", null);
        while (res.moveToNext()) {
            num = num - res.getDouble(3);
            if (num > 0) {
                deleteInventoryData(res.getInt(0));
            } else {
                db.execSQL("update "+INVENTORY_TABLE_NAME+" set Amount = " + Double.toString(Math.abs(num)) + " where ID = " + res.getInt(0));
                break;
            }
        }

    }
    //update expire date in food edit page.
    public boolean updateExpireInventory(Integer id, Date expireDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        if(expireDate==null){
            return false;
        }
        else{
            String idS = Integer.toString(id);
            String newDate = (expireDate.getMonth() + 1) + "/" + expireDate.getDate() + "/" + (expireDate.getYear() + 1900);
            db.execSQL("update " + INVENTORY_TABLE_NAME + " set Expire = '" + newDate + "' where ID = " + idS);
            return true;
        }

    }

    //extract all inventory data from inventory table and return as a cursor.
    public Cursor getInventoryData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+INVENTORY_TABLE_NAME, null);
        return res;
    }

    //get information of certain ingredient with id.
    public Cursor getIngredient(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+INVENTORY_TABLE_NAME + " where ID = " + Integer.toString(id), null);
        res.moveToNext();
        return res;
    }

    //delete a ingredient from inventory in food edit page.
    public Integer deleteInventoryData (Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(INVENTORY_TABLE_NAME, "ID = ?", new String[] {Integer.toString(id)});
    }


//shopping list database helper functions===================================================================

    //get shopping item info and put into shopping list table
    public boolean insertDataShopping(String t, String n, String a, String u, String s){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLS_1, t);
        contentValues.put(COLS_2, n);
        contentValues.put(COLS_3, a);
        contentValues.put(COLS_4, u);
        contentValues.put(COLS_5, s);
        contentValues.put(COLS_6, "null");
        contentValues.put(COLS_7, "null");

        long result = db.insert(SHOPPING_TABLE_NAME, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;

    }

    //get all shopping data from shopping list table and return as cursor.
    public Cursor getShoppingData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+SHOPPING_TABLE_NAME, null);
        return res;
    }

    //delete shopping item with id.
    public Integer deleteShoppingData (Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(SHOPPING_TABLE_NAME, "ID = ?", new String[] {Integer.toString(id)});
    }


//recipe database helper functions=====================================================================


    //insert one ingredient to shopping table
    //return false: unsuccessful
    //return true: successful
    public boolean insertNewRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        List<String> recipeInfo = recipe.getInfo();
        contentValues.put(COLR_1, recipeInfo.get(0));
        contentValues.put(COLR_2, recipeInfo.get(1));
        contentValues.put(COLR_3, recipeInfo.get(2));
        contentValues.put(COLR_4, recipeInfo.get(3));
        contentValues.put(COLR_5, recipeInfo.get(4));
        contentValues.put(COLR_6, recipeInfo.get(5));
        contentValues.put(COLR_7, recipeInfo.get(6));

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

    //get current data in Recipe table
    //return multiple lines dataframe
    public Cursor getCurrRecipeData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+RECIPE_TABLE_NAME + " where Current = 1", null);
        return res;
    }

    //get history data in Recipe table
    //return multiple lines dataframe
    public Cursor getHistRecipeData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+RECIPE_TABLE_NAME + " where Current = 0", null);
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

    //move recipe from current to history
    public Boolean toHistoryRecipe (Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update "+RECIPE_TABLE_NAME+" set Current = 0 where ID = " + Integer.toString(id));
        return true;
    }

    //move recipe from history to current.
    public Boolean toCurrentRecipe (Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update "+RECIPE_TABLE_NAME+" set Current = 1 where ID = " + Integer.toString(id));
        return true;
    }

}
