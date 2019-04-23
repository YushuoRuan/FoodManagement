package com.example.foodmanagement;


public class Recipe {

    private Integer ID;
    private String name;
    private String type;
    private String cuisine;

    private IngredientList ingList;

    public Recipe(Integer id, String n, String t, String c, IngredientList ingList){
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
}
