package com.example.foodmanagement;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class RecipeActivity extends AppCompatActivity {

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
    }
}
