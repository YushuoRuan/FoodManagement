package com.example.foodmanagement;


import java.util.ArrayList;
import java.util.List;

public class Recipe {

    private int current;
    private Integer ID;
    private String name;
    private String type;
    private String cuisine;

    private IngredientList ingList;

    public Recipe(Integer id, String n, String t, String c, IngredientList ingList){
        current = 1;
        ID = id;
        name = n;
        type = t;
        cuisine = c;
        this.ingList = ingList;
    }

    Recipe(String n, String t, String c, IngredientList ingList){
        this(0, n, t, c, ingList);
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

    String getCuisine() {
        return cuisine;
    }


    public IngredientList getIngList() {
        return ingList;
    }

    public void setIngList(IngredientList ingList) {
        this.ingList = ingList;
    }

    public int isCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    List<String> getInfo () {
        List<String> info = new ArrayList<>();
        info.add(Integer.toString(current));
        info.add(name);
        info.add(type);
        info.add(cuisine);
        info.addAll(ingList.getInfo());
        return info;
    }
}
