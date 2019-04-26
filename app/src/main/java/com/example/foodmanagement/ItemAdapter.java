/*
 * ItemAdapter class for list view
 * Authors: Ziying Zhang, Tianshu Pang, Peng Yan, Yushuo Ruan
 */
package com.example.foodmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ItemAdapter extends BaseAdapter {

    LayoutInflater mInflater;

    //avariables that can be accessed from all functions.
    IngredientList ingredients;


    //put ingredient information into arrays.
    public ItemAdapter(Context c, IngredientList i){
        ingredients = i;
        //initialize inflater
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return ingredients.length();
    }

    @Override
    public Object getItem(int position) {
        return ingredients.get(position);
    }

    @Override
    public long getItemId(int position) {
        //return ingredients.get(position).ID;
        return ingredients.get(position).ID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //initialize listview with inflater.
        View v = mInflater.inflate(R.layout.inventory_item_detail,null);

        //get TextViews from layout
        TextView nameTextView = (TextView) v.findViewById(R.id.nameTV);
        TextView amountTextView = (TextView) v.findViewById(R.id.amountTV);
        TextView descriptionTextView = (TextView) v.findViewById(R.id.descriptionTV);
        TextView storageTextView = (TextView) v.findViewById(R.id.storageTV);
        ImageView foodTypeImg = (ImageView) v.findViewById(R.id.foodTypeImg);

        //set image depend on food type
        switch (ingredients.get(position).getType()) {
            case "Meat":
                foodTypeImg.setImageResource(R.drawable.ic_meat);
                break;
            case "Vegetable":
                foodTypeImg.setImageResource(R.drawable.ic_vegetable);
                break;
            case "Fruit":
                foodTypeImg.setImageResource(R.drawable.ic_fruit);
                break;
            case "Beverage":
                foodTypeImg.setImageResource(R.drawable.ic_beverage);
                break;
            case "Seafood":
                foodTypeImg.setImageResource(R.drawable.ic_seafood);
                break;
            case "Dessert":
                foodTypeImg.setImageResource(R.drawable.ic_dessert);
                break;
            case "Prepared":
                foodTypeImg.setImageResource(R.drawable.ic_prepared);
                break;
            case "Spice":
                foodTypeImg.setImageResource(R.drawable.ic_spicy);
                break;
            case "Sauce":
                foodTypeImg.setImageResource(R.drawable.ic_sauce);
                break;
            case "Other":
                foodTypeImg.setImageResource(R.drawable.ic_launcher_foreground);
        }
        //foodTypeImg.setImageResource(R.mipmap.meatIm);
        //extract information at position
        String name = ingredients.get(position).getMaterial();
        String amount = Double.toString(ingredients.get(position).getAmount());
        String expire = ingredients.get(position).getExpiredDate();
        String storage = ingredients.get(position).getStorage();
        String unit = ingredients.get(position).getUnit();

        //display information in the TextViews
        DecimalFormat df = new DecimalFormat("#.##");
        nameTextView.setText(name);
        amountTextView.setText(df.format(Double.parseDouble(amount)) + " " + unit);
        descriptionTextView.setText(expire);
        storageTextView.setText(storage);

        return v;
    }
}
