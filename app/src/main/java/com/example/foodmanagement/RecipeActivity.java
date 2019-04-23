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


public class RecipeActivity extends AppCompatActivity {

    ListView recipeListView;

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
        setContentView(R.layout.activity_recipe);

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

        Button historyRecipeBtn = (Button) findViewById(R.id.historyRecipeBtn);
        historyRecipeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historyRecipeActivity = new Intent(getApplicationContext(), HistoryRecipe.class);
                startActivity(historyRecipeActivity);
            }
        });

        recipeDB = new DatabaseHelper(this);
        Cursor res = recipeDB.getAllRecipeData();
        if(res.getCount()>0){
            ArrayList<Recipe> recipes = new ArrayList<>();
            while(res.moveToNext())
            {
                Integer id = res.getInt(0);
                String name = res.getString(1);
                String cuisine = res.getString(2);
                String type = res.getString(3);
                String[] IDs = res.getString(4).split(",");
                String[] amountsS = res.getString(5).split(",");
                Double []amounts = new Double[amountsS.length];
                for(int i = 0; i<amountsS.length; i++)
                {
                    amounts[i] = Double.parseDouble(amountsS[i]);
                }
                IngredientList ingList = new IngredientList(IDs, amounts);
                Recipe newRecipe = new Recipe(id, name, cuisine, type, ingList);
                recipes.add(newRecipe);
            }

            recipeListView = (ListView) findViewById(R.id.recipeLV);

            if(recipes.size()>0) {
                RecipeItemAdapter recipeItemAdapter = new RecipeItemAdapter(this, recipes);
                recipeListView.setAdapter(recipeItemAdapter);
            }
        }





//    public static void setListViewHeightBasedOnChildren(ListView listView) {
//        ListAdapter listAdapter = listView.getAdapter();
//        if (listAdapter == null)
//            return;
//
//        int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.UNSPECIFIED);
//        int totalHeight = 0;
//        View view = null;
//        for (int i = 0; i < listAdapter.getCount(); i++) {
//            view = listAdapter.getView(i, view, listView);
//            if (i == 0)
//                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, LayoutParams.WRAP_CONTENT));
//
//            view.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
//            totalHeight += view.getMeasuredHeight();
//        }
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//        listView.setLayoutParams(params);
//    }
    }
}


