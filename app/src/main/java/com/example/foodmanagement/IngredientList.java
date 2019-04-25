/*
 * ingredient list class (mostly used in recipe)
 * Authors: Ziying Zhang, Tianshu Pang, Peng Yan, Yushuo Ruan
 */
package com.example.foodmanagement;

import java.util.ArrayList;
import java.util.List;

public class IngredientList {

    // delegate an array list to maintain the ingredients
    private ArrayList<Ingredient> ingredientList;
//    private Storage storageType;

    // constructors
    IngredientList() {
        ingredientList = new ArrayList<>();
    }

    IngredientList(String[] IDs, String[] names, Double[] amounts) {
        ingredientList = new ArrayList<>();
        for (int i = 0; i < IDs.length; i++) {
            Ingredient ing = new Ingredient(Integer.parseInt(IDs[i]), names[i], amounts[i]);
            ingredientList.add(ing);
        }
    }

    // methods for maintaining the array list of ingredients

    // add new ingredient to the list
    public boolean add (Ingredient ing) {
        ingredientList.add(ing);
        return true;
    }

    public Ingredient get (int i) {
        return ingredientList.get(i);
    }

    //return the total num of ingredients
    int length() {
        return ingredientList.size();
    }

    // show the information of ingredients
    List<String> showIngredients() {
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


    List<String> getInfo() {
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


    ArrayList<Ingredient> getIngredientList(){
        return ingredientList;
    }

    int size() {
        return ingredientList.size();
    }

    public IngredientList subListStorage(String storage) {
        if (storage.equals("All")) {
            return this;
        } else {IngredientList subList = new IngredientList();
            for (Ingredient ing :
                    ingredientList) {
                if (ing.getStorage().equals(storage)) {
                    subList.add(ing);
                }
            }
            return subList;
        }

    }
}
