package com.example.foodmanagement;

import java.util.ArrayList;
import java.util.List;

public class IngredientList {
    private List<Ingredient> ingredientList;

    public IngredientList () {
        ingredientList = new ArrayList<>();
    }

    public boolean add (Ingredient ing) {
        ingredientList.add(ing);
        return true;
    }

    public IngredientList (String[] IDs, Double[] amounts) {
        for (int i = 0; i < IDs.length; i++) {
            Ingredient ing = new Ingredient(Integer.parseInt(IDs[i]), amounts[i]);
            ingredientList.add(ing);
        }
    }



    public void show () {
        for (Ingredient ing :
                ingredientList) {
            System.out.print(ing);
        }
    }
    public int length(){
        return ingredientList.size();
    }
}
