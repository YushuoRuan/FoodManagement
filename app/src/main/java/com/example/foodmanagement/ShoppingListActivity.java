package com.example.foodmanagement;

import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class ShoppingListActivity extends AppCompatActivity {

    ListView myListView;
    String[] names;
    int[] amounts;
    String[] units;

    String[] descriptions;
    String[] storages;

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
                    return true;
                case R.id.navigation_recipe:
                    Intent showRecipe = new Intent(getApplicationContext(), RecipeActivity.class);
                    startActivity(showRecipe);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setSelectedItemId(R.id.navigation_cart);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Button addShopBtn = (Button) findViewById(R.id.addShoppingBtn);

        addShopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showAddShopping = new Intent(getApplicationContext(), AddShopingListActivity.class);
                startActivity(showAddShopping);
            }
        });

        Resources res = getResources();
        myListView = (ListView) findViewById(R.id.shoppingListView);
        names = res.getStringArray(R.array.Names);
        amounts = res.getIntArray(R.array.Amount);
        units = res.getStringArray(R.array.Units);



        ShopItemAdapter shopItemAdapter = new ShopItemAdapter(this, names, amounts, units);
        myListView.setAdapter(shopItemAdapter);


    }
}
