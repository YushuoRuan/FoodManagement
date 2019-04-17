package com.example.foodmanagement;

import com.example.foodmanagement.Ingredient;

import java.util.ArrayList;
import java.util.Date;

public class FoodStorage {

    private ArrayList<Ingredient> storedIngredientList;

    public FoodStorage(){
        //initialize FoodStorage
        //read ingredient data from either database or csv.

    }

    public void addIngredient(String type, String name, double amount, String unit, Date exp, String[] tags){
        Ingredient newIngredient = new Ingredient(type, name, amount, unit);

        if (exp!=null){
            newIngredient.setExpiredDate(exp);
        }

        for(int i = 0; i < tags.length; i++){
            newIngredient.addTag(tags[i]);
        }

        storedIngredientList.add(newIngredient);

    }

    public ArrayList<Ingredient> showIngredients(){
        return storedIngredientList;
    }
}