package com.example.foodmanagement;

import android.content.Context;
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

public class RecipeItemAdapter extends BaseAdapter {

    LayoutInflater mInflater;

    ArrayList<Recipe> recipes;

    IngredientList ingredients;

    DatabaseHelper myDb;

    public RecipeItemAdapter(Context c, ArrayList<Recipe> r){

        recipes = r;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        myDb = new DatabaseHelper(c);
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        ingredients = recipes.get(position).getIngList();

        View v = mInflater.inflate(R.layout.curr_recipe_item,null);
        TextView nameTextView = (TextView) v.findViewById(R.id.currRecipeNameTV);
        TextView cuisineTextView = (TextView) v.findViewById(R.id.currRecipeCuisineTV);
        TextView typeTextView = (TextView) v.findViewById(R.id.currRecipeTypeTV);
        TextView availableTV = (TextView) v.findViewById(R.id.currRecipeFracTV);
        TextView  ingredientListTV = (TextView) v.findViewById(R.id.currRecipeIngredListTV);

        final Button finishBtn = (Button) v.findViewById(R.id.currRecipeFinishBtn);
        final Button cancelBtn = (Button) v.findViewById(R.id.currRecipeCancelBtn);

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i < ingredients.length(); i++) {
                    myDb.subtractDataInventory(ingredients.getIngredientList().get(i).ID,
                            ingredients.getIngredientList().get(i).getAmount());
                }
                myDb.toHistoryRecipe(recipes.get(position).getID());
                finishBtn.setText("Enjoy!!");
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDb.toHistoryRecipe(recipes.get(position).getID());
                cancelBtn.setText("Removed!");
            }
        });

        nameTextView.setText(recipes.get(position).getName());
        cuisineTextView.setText(recipes.get(position).getCuisine());
        typeTextView.setText(recipes.get(position).getType());
        availableTV.setText(getIngredientFraction(position));
        ingredientListTV.setText(getIngredientListText(ingredients));


        return v;
    }

    private String getIngredientFraction(int position){

        return "0/"+ingredients.length();
    }

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
