package com.example.foodmanagement;

import java.util.ArrayList;

public class Ingredient {

    public int ID;
    private String type;
    private String material;
    private double amount;
    private String unit;
    private String storage;
    private String expiredDate;
    private ArrayList<String> tags = new ArrayList<>();

    //init
    public Ingredient(int I, String t, String m, double a, String u, String s){
        ID=I;
        type = t;
        material = m;
        amount = a;
        unit = u;
        storage = s;
    }

    public Ingredient (int id, double amount) {
        ID = id;
        this.amount = amount;
    }

    public Ingredient(int id, String material, double amount) {
        ID = id;
        this.material = material;
        this.amount = amount;
    }

    @Override
    public String toString () {

        return null;
    }

    //setters

    public void setExpiredDate(String d) {
        this.expiredDate = d;
    }

    public void addTag(String newTag){
        this.tags.add(newTag);
    }

    public void removeTag(String newTag){
        this.tags.remove(newTag);
    }

    //getters

    public String getExpiredDate() {
        return this.expiredDate;
    }

    public String getUnit() {
        return this.unit;
    }

    public double getAmount() {
        return this.amount;
    }

    public String getMaterial() {
        return this.material;
    }

    public ArrayList<String> getTags() {
        return this.tags;
    }

    public String getStorage() {
        return storage;
    }

    public String getType() {
        return type;
    }
}
