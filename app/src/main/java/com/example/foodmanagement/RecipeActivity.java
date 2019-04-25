/*
 * Here is a block comment.
 */
package com.example.foodmanagement;
import android.content.Intent;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


public class RecipeActivity extends AppCompatActivity {

    ListView recipeListView /*list view for recipes*/;

    DatabaseHelper recipeDB /*facade database helper*/;

    ArrayList<Recipe> recipes;

    //set bottom navigation view functionality.
    //navigating between inventory, shopping list, and recipe pages.
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_inventory:
                    Intent showInventory = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(showInventory);
                    return true;
                case R.id.navigation_cart:
                    Intent showShoppingList = new Intent(getApplicationContext(), ShoppingListActivity.class);
                    startActivity(showShoppingList);
                    return true;
                case R.id.navigation_recipe:
                    return true;
            }
            return false;
        }
    };

    //first to run when starting this activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        //set up bottom navigator
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setSelectedItemId(R.id.navigation_recipe);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //find and set add recipe button in recipe page
        Button newRecipeBtn = (Button) findViewById(R.id.newRecipeBtn);
        newRecipeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get and enter new recipe activity.
                Intent newRecipeActivity = new Intent(getApplicationContext(), NewRecipe.class);
                startActivity(newRecipeActivity);
            }
        });

        //find and set history recipe button in recipe page
        Button historyRecipeBtn = (Button) findViewById(R.id.historyRecipeBtn);
        historyRecipeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get and enter history recipe activity.
                Intent historyRecipeActivity = new Intent(getApplicationContext(),HistRecipeActivity.class);
                startActivity(historyRecipeActivity);
            }
        });


        recipeDB = new DatabaseHelper(this);/*instantiate database helper*/

        //get recipe marked as current to display on the list view.
        Cursor res = recipeDB.getCurrRecipeData();
        if(res.getCount()>0){
            ArrayList<Recipe> recipes = new ArrayList<>();
            while(res.moveToNext())
            {
                //get info from each row of table
                Integer id = res.getInt(0);
                String current = res.getString(1);
                String name = res.getString(2);
                String type = res.getString(3);
                String cuisine = res.getString(4);
                String[] IDs = res.getString(5).split(",");
                String [] Names = res.getString(6).split(",");
                String a = res.getString(7);
                String[] amountsS = res.getString(7).split(",");
                Double []amounts = new Double[amountsS.length];
                if(amountsS[0]=="" || IDs[0]=="")
                    continue;
                //convert string array to double array
                for(int i = 0; i<amountsS.length; i++)
                {
                    if(amountsS[i]==""){
                        break;}
                    amounts[i] = Double.parseDouble(amountsS[i]);
                }
                //check number of ingredient and amount match
                if(amountsS.length!=IDs.length)
                    continue;


                IngredientList ingList = new IngredientList(IDs, Names, amounts); /*construct new ingredient list*/
                Recipe newRecipe = new Recipe(id, name, cuisine, type, ingList);/*construct new recipe*/
                recipes.add(newRecipe); /*add to recipe list array*/
            }

            //find listview and set adapter to it using the recipe list.
            recipeListView = (ListView) findViewById(R.id.recipeLV);
            if(recipes.size()>0) {
                RecipeItemAdapter recipeItemAdapter = new RecipeItemAdapter(this, recipes);
                recipeListView.setAdapter(recipeItemAdapter);
            }
        }
    }
}


