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

    //public static final String DATABASE_NAME = "FoodManagement.db";
    public static final String RECIPE_TABLE_NAME = "recipe_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "Current"; //1:current 0:history
    public static final String COL_3 = "Name";
    public static final String COL_4 = "Type"; //main dish, appetizer
    public static final String COL_5 = "Cuisine"; //asian, american
    public static final String COL_6 = "Ingredients";//"tomato,egg,ketchup"
    public static final String COL_7 = "Amounts";//"1, 2, 0.3"
    public static final String COL_8 = "Units";//"individual, individual, oz"


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + INVENTORY_TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, Type Text, Name Text, Amount REAL, Unit TEXT, Storage TEXT, Expire TEXT, Tags TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+INVENTORY_TABLE_NAME);
        onCreate(db);
    }

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

    public Cursor getShoppingData() {
        return null;
    }


}
