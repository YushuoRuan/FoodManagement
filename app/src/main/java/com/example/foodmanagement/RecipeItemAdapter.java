package com.example.foodmanagement;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class RecipeItemAdapter extends BaseAdapter {

    LayoutInflater mInflater;

    ArrayList<Recipe> recipes;

    DatabaseHelperRecipe recipeDB;

    public RecipeItemAdapter(Context c, ArrayList<Recipe> r){

        recipes = r;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return recipes.size();
    }

    @Override
    public Object getItem(int position) {
        return recipes.get(position);
    }

    @Override
    public long getItemId(int position) {
        //return ingredients.get(position).ID;
        return recipes.get(position).getID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = mInflater.inflate(R.layout.inventory_item_detail,null);
        TextView nameTextView = (TextView) v.findViewById(R.id.nameTV);
        TextView cuisineTextView = (TextView) v.findViewById(R.id.cuisineTV);
        TextView typeTextView = (TextView) v.findViewById(R.id.cuisineTypeTV);
        TextView availableTV = (TextView) v.findViewById(R.id.availableTV);


        nameTextView.setText(recipes.get(position).getName());
        cuisineTextView.setText(recipes.get(position).getCuisine());
        typeTextView.setText(recipes.get(position).getType());
        availableTV.setText(getIngredientFraction(position));

        return v;
    }

    private String getIngredientFraction(int position){

        String[] ingredients = recipes.get(position).getIngredients();
        return "0/"+ingredients.length;
    }
}
