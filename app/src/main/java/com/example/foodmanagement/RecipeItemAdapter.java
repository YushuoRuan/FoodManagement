/*
 * ItemAdapter class for recipe list view
 * Authors: Ziying Zhang, Tianshu Pang, Peng Yan, Yushuo Ruan
 */
package com.example.foodmanagement;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;


import java.util.ArrayList;

public class RecipeItemAdapter extends BaseAdapter {

    LayoutInflater mInflater; /*declare inflater for list view. */

    /**/
    ArrayList<Recipe> recipes;
    IngredientList ingredients;

    DatabaseHelper myDb; /*declare database helper to accesses data in inventory table*/

    public RecipeItemAdapter(Context c, ArrayList<Recipe> r){

        recipes = r;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//initialize inflater
        myDb = new DatabaseHelper(c); /*instantiate database helper*/
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

        ingredients = recipes.get(position).getIngList(); /*get ingredient in recipe at position*/

        View v = mInflater.inflate(R.layout.curr_recipe_item,null); /*initialize listview with inflater.*/

        //get TextViews from layout
        TextView nameTextView = (TextView) v.findViewById(R.id.currRecipeNameTV);
        TextView cuisineTextView = (TextView) v.findViewById(R.id.currRecipeCuisineTV);
        TextView typeTextView = (TextView) v.findViewById(R.id.currRecipeTypeTV);
        TextView availableTV = (TextView) v.findViewById(R.id.currRecipeFracTV);
        final TextView  ingredientListTV = (TextView) v.findViewById(R.id.currRecipeIngredListTV);

        //get buttons from layout
        final Button finishBtn = (Button) v.findViewById(R.id.currRecipeFinishBtn);
        final Button cancelBtn = (Button) v.findViewById(R.id.currRecipeCancelBtn);

        //set finish button click event.
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishBtn.setText("finishing...");
                for (int i = 0; i < ingredients.length(); i++) {
                    // when finish button is clicked, subtract ingredients from inventory table base on name.
                    myDb.subtractDataInventoryName(ingredients.get(i).getMaterial(),
                            ingredients.get(i).getAmount());
                }
                myDb.toHistoryRecipe(recipes.get(position).getID()); /*set */
                finishBtn.setText("Enjoy!!");
            }
        });

        //set cancel button click event.
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDb.toHistoryRecipe(recipes.get(position).getID()); /*set recipe to history*/
                cancelBtn.setText("Removed!");
            }
        });

        //set display information
        nameTextView.setText(recipes.get(position).getName());
        cuisineTextView.setText(recipes.get(position).getCuisine());
        typeTextView.setText(recipes.get(position).getType());
        availableTV.setText(getIngredientFraction());
        ingredientListTV.setText(getIngredientListText(ingredients));


        return v;
    }

    //get fraction of available ingredients.
    private String getIngredientFraction(){
        return "0/"+ingredients.length();
    }

    //change ingredient array list to displayable string
    private String getIngredientListText(IngredientList ingredientList){
        String list = "";
        for(int i =0; i<ingredientList.length(); i++){
            Cursor res = myDb.getIngredient(ingredientList.get(i).ID);
            list = list + res.getString(2) +
                    ":      " +
                    ingredientList.get(i).getAmount() +
                    "  " +
                    res.getString(4) +
                    "\n";
        }
        return list;
    }
}
