package com.example.foodmanagement;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;

import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class HistoryRecipeActivity extends AppCompatActivity {

    ListView hisRecipeListView;

    DatabaseHelper recipeDB;

    ArrayList<Recipe> recipes;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_recipe);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setSelectedItemId(R.id.navigation_recipe);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        Button newRecipeBtn = (Button) findViewById(R.id.newRecipeBtn);
        newRecipeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newRecipeActivity = new Intent(getApplicationContext(), NewRecipe.class);
                startActivity(newRecipeActivity);
            }
        });

        Button currentRecipeBtn = (Button) findViewById(R.id.currentRecipeBtn);
        currentRecipeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent currentRecipeActivity = new Intent(getApplicationContext(), RecipeActivity.class);
                startActivity(currentRecipeActivity);
            }
        });

        recipeDB = new DatabaseHelper(this);
        Cursor res = recipeDB.getHistRecipeData();
        if(res.getCount()>0){
            ArrayList<Recipe> recipes = new ArrayList<>();
            while(res.moveToNext())
            {
                Integer id = res.getInt(0);
                String current = res.getString(1);
                String name = res.getString(2);
                String type = res.getString(3);
                String cuisine = res.getString(4);
                String[] IDs = res.getString(5).split(",");
                String [] Names = res.getString(6).split(",");
                String[] amountsS = res.getString(7).split(",");
                Double []amounts = new Double[amountsS.length];
                if(amountsS[0]=="" || IDs[0]=="")
                    continue;
                for(int i = 0; i<amountsS.length; i++)
                {
                    if(amountsS[i]==""){
                        break;}
                    amounts[i] = Double.parseDouble(amountsS[i]);
                }
                if(amountsS.length!=IDs.length)
                    continue;
                IngredientList ingList = new IngredientList(IDs, Names, amounts);
                Recipe newRecipe = new Recipe(id, name, cuisine, type, ingList);
                recipes.add(newRecipe);
            }

            hisRecipeListView = (ListView) findViewById(R.id.hisRecipeLV);

            if (recipes.size() > 0) {
                HistoryRecipeItemAdapter historyRecipeItemAdapter = new HistoryRecipeItemAdapter(this, recipes);
                hisRecipeListView.setAdapter(historyRecipeItemAdapter);
            }
        }
    }
}