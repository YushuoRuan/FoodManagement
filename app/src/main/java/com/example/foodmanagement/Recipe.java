package com.example.foodmanagement;

import java.util.ArrayList;
import java.util.List;

public class Recipe {

    private Integer ID;
    private String name;
    private String type;
    private String cuisine;
    private String[] ingredients;
    private Double[] amounts;
    private String[] units;
    private IngredientList ingList;

    public Recipe(Integer id, String n, String t, String c, String[] i, Double[] a, String[] u){
        ID = id;
        name = n;
        type = t;
        cuisine = c;
        ingredients = i;
        amounts = a;
        units = u;
        ingList = new IngredientList();
    }

    public Recipe (String n, String t, String c, String[] i, Double[] a, String[] u){
        this(0, n, t, c, i, a, u);
    }

    public Recipe () {

    }

    public Integer getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getCuisine() {
        return cuisine;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public Double[] getAmounts() {
        return amounts;
    }

    public String[] getUnits() {
        return units;
    }
}
