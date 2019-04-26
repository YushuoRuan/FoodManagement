/*
 * Shopping cart class.
 * Authors: Ziying Zhang, Tianshu Pang, Peng Yan, Yushuo Ruan
 */
package com.example.foodmanagement;
import android.database.Cursor;

import com.example.foodmanagement.DatabaseHelper;
import com.example.foodmanagement.Ingredient;

import java.util.ArrayList;

public class ShoppingCart {
    //shopping list contains many ingredients.
    private IngredientList shoppingList;

    public ShoppingCart(DatabaseHelper myDb){
        //initialize shopping list
        shoppingList = new IngredientList();

        //read ingredient data from DB shopping list table.
        Cursor res = myDb.getShoppingData();
        if(res.getCount() > 0)
        {
            while(res.moveToNext()){//get detail information

                Integer ID = res.getInt(0);
                String type= res.getString(1);
                String name = res.getString(2);
                double amount= res.getFloat(3);
                String unit= res.getString(4);
                String storage= res.getString(5);
                String expired= res.getString(6);
                String tags= res.getString(7);

                shoppingItem(ID,type,name,amount,unit,storage,expired,tags);

            }
        }

    }

    //helper function that stores ingredient in to shopping list arraylist.
    public void shoppingItem(Integer I, String type, String name, double amount, String unit, String storage, String exp, String tags){

        //construct a ingredient object.
        Ingredient newIngredient = new Ingredient(I, type, name, amount, unit, storage);
        shoppingList.add(newIngredient);

    }
    //ingredient arraylist getter in shopping list
    public IngredientList getShoppingIngredients(){
        return shoppingList;
    }

}