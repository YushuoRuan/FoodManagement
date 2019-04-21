package com.example.foodmanagement;

import java.util.ArrayList;

public class Recipe {

    Integer ID;
    String name;
    String type;
    String cuisine;
    String[] ingredients;
    Double[] amounts;
    String[] units;

    public Recipe(Integer id, String n, String t, String c, String[] i, Double[] a, String[] u){
        ID = id;
        name = n;
        type = t;
        cuisine = c;
        ingredients = i;
        amounts = a;
        units = u;
    }

}
