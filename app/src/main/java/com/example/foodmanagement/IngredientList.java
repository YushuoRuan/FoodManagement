/*
 * ingredient list class (recipe)
 * Authors: Ziying Zhang, Tianshu Pang, Peng Yan, Yushuo Ruan
 */
package com.example.foodmanagement;

import java.util.ArrayList;
import java.util.List;

public class IngredientList {
    private ArrayList<Ingredient> ingredientList;

    public IngredientList () {
        ingredientList = new ArrayList<>();
    }

    public boolean add (Ingredient ing) {
        ingredientList.add(ing);
        return true;
    }

    public IngredientList (String[] IDs, Double[] amounts) {
        ingredientList = new ArrayList<>();
        for (int i = 0; i < IDs.length; i++) {
            Ingredient ing = new Ingredient(Integer.parseInt(IDs[i]), amounts[i]);
            ingredientList.add(ing);
        }
    }

    public IngredientList (String[] IDs, String[] names, Double[] amounts) {
        ingredientList = new ArrayList<>();
        for (int i = 0; i < IDs.length; i++) {
            Ingredient ing = new Ingredient(Integer.parseInt(IDs[i]), names[i], amounts[i]);
            ingredientList.add(ing);
        }
    }



    public void show () {
        for (Ingredient ing :
                ingredientList) {
            System.out.print(ing);
        }
    }
    public int length() {
        return ingredientList.size();
    }

    public List<String> showIngredients () {
        List<String> info = new ArrayList<>();
        StringBuilder names = new StringBuilder();
        StringBuilder amounts = new StringBuilder();
        for (Ingredient ing :
                ingredientList) {
            names.append(ing.getMaterial()).append(',');
            amounts.append(Double.toString(ing.getAmount())).append(',');
        }
        info.add(names.toString());
        info.add(amounts.toString());
        return info;
    }

    public List<String> getInfo () {
        List<String> info = new ArrayList<>();
        StringBuilder IDs = new StringBuilder();
        StringBuilder names = new StringBuilder();
        StringBuilder amounts = new StringBuilder();
        for (Ingredient ing :
                ingredientList) {
            IDs.append(Integer.toString(ing.ID)).append(',');
            names.append(ing.getMaterial()).append(',');
            amounts.append(Double.toString(ing.getAmount())).append(',');
        }
        info.add(IDs.toString());
        info.add(names.toString());
        info.add(amounts.toString());
        return info;
    }
    
    public ArrayList<Ingredient> getIngredientList(){
        return ingredientList;
    }
}
