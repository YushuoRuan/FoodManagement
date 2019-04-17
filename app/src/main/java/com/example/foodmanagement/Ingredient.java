package com.example.foodmanagement;

import java.util.ArrayList;
import java.util.Date;

public class Ingredient {

    private String type;
    private String material;
    private double amount;
    private String unit;
    private Date expiredDate;
    private ArrayList<String> tags = new ArrayList<>();

    //init
    public Ingredient(String t, String m, double a, String u){
        material = m;
        amount = a;
        unit = u;
    }

    //setters

    public void setExpiredDate(Date d) {
        this.expiredDate = d;
    }

    public void addTag(String newTag){
        this.tags.add(newTag);
    }

    public void removeTag(String newTag){
        this.tags.remove(newTag);
    }

    //getters

    public Date getExpiredDate() {
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

}
