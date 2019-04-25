/*
 * ItemAdapter class for history recipe list view
 * Authors: Ziying Zhang, Tianshu Pang, Peng Yan, Yushuo Ruan
 */

package com.example.foodmanagement;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class HistoryRecipeItemAdapter extends BaseAdapter {

    LayoutInflater mInflater; /*declare inflater for list view. */

    ArrayList<Recipe> recipes;

    IngredientList ingredients;

    DatabaseHelper myDb;

    Context context; /*declare database helper to accesses data in inventory table*/

    public HistoryRecipeItemAdapter(Context c, ArrayList<Recipe> r){

        recipes = r;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//initialize inflater
        myDb = new DatabaseHelper(c); /*instantiate database helper*/
        context = c;
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
    //generate view for list view.
    public View getView(final int position, View convertView, ViewGroup parent) {

        ingredients = recipes.get(position).getIngList();/*get ingredient in recipe at position*/

        View v = mInflater.inflate(R.layout.his_recipe_item,null); /*initialize listview with inflater.*/

        //get all elements in TextViews from layout
        TextView nameTextView = (TextView) v.findViewById(R.id.hisRecipeNameTV);
        TextView cuisineTextView = (TextView) v.findViewById(R.id.hisRecipeCuisineTV);
        TextView typeTextView = (TextView) v.findViewById(R.id.hisRecipeTypeTV);
        TextView availableTV = (TextView) v.findViewById(R.id.hisRecipeFracTV);
        final TextView  ingredientListTV = (TextView) v.findViewById(R.id.hisRecipeIngredListTV);

        //get convert button from layout
        final Button hisToCurBtn = (Button) v.findViewById(R.id.hisRecipeToCurrentBtn);


        //set convert button from layout
        hisToCurBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myDb.toCurrentRecipe(recipes.get(position).getID()); /*set recipe to current*/
                hisToCurBtn.setText("Added to current!");
                Intent showRecipe = new Intent(context, RecipeActivity.class);
                context.startActivity(showRecipe);
            }
        });

        //set display information
        nameTextView.setText(recipes.get(position).getName());
        cuisineTextView.setText(recipes.get(position).getCuisine());
        typeTextView.setText(recipes.get(position).getType());
        availableTV.setText(getIngredientFraction(position));
        ingredientListTV.setText(getIngredientListText(ingredients));

        return v;
    }
    //get fraction of available ingredients.
    private String getIngredientFraction(int position){


        return "0/"+ingredients.length();
    }
    //change ingredient array list to displayable string
    private String getIngredientListText(IngredientList ingredientList){
        String list = "";
        for(int i =0; i<ingredientList.length(); i++){
            Cursor res = myDb.getIngredient(ingredientList.getIngredientList().get(i).ID);
            list = list + res.getString(2) +
                    ":      " +
                    ingredientList.getIngredientList().get(i).getAmount() +
                    "  " +
                    res.getString(4) +
                    "\n";
        }
        return list;
    }
}
