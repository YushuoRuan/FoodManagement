package com.example.foodmanagement;

import java.util.ArrayList;

public class Recipe {

    private Integer ID;
    private String name;
    private String type;
    private String cuisine;
    private String[] ingredients;
    private Double[] amounts;
    private String[] units;

    public Recipe(Integer id, String n, String t, String c, String[] i, Double[] a, String[] u){
        ID = id;
        name = n;
        type = t;
        cuisine = c;
        ingredients = i;
        amounts = a;
        units = u;
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
