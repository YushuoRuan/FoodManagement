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

    public void show () {
        for (Ingredient ing :
                ingredientList) {
            System.out.print(ing);
        }
    }
}
