package com.example.foodmanagement;

import android.database.Cursor;

import com.example.foodmanagement.Ingredient;

import java.util.ArrayList;
import java.util.Date;

public class FoodStorage {

    private ArrayList<Ingredient> storedIngredientList;

    public FoodStorage(DatabaseHelper myDb){
        storedIngredientList = new ArrayList<>();
        //initialize FoodStorage
        //read ingredient data from either database or csv.
        Cursor res = myDb.getInventoryData();
        if(res.getCount() > 0)
        {
            while(res.moveToNext()){

                Integer ID = res.getInt(0);
                String type= res.getString(1);
                String material = res.getString(2);
                double amount= res.getFloat(3);
                String unit= res.getString(4);
                String storage= res.getString(5);
                String expired= res.getString(6);
                String tags= res.getString(7);

                storeIngredient(ID,type,material,amount,unit,storage,expired,tags);

            }
        }

    }

    public void storeIngredient(Integer I, String type, String name, double amount, String unit, String storage, String exp, String tags){
        Ingredient newIngredient = new Ingredient(I, type, name, amount, unit, storage);

        if(exp!="null"){
            newIngredient.setExpiredDate(exp);
        }
        String[] tagArray = tags.split(",");
        for(int i = 0; i < tagArray.length; i++){
            newIngredient.addTag(tagArray[i]);
        }

        storedIngredientList.add(newIngredient);

    }

    public ArrayList<Ingredient> getIngredients(){
        return storedIngredientList;
    }

}
