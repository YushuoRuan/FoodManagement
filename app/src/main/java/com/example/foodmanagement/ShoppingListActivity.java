package com.example.foodmanagement;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;


import android.widget.TextView;
import android.util.Log;

import java.util.ArrayList;

public class ShoppingListActivity extends AppCompatActivity {

    ListView myListView;
    String[] names;
    int[] amounts;
    String[] units;

    String[] descriptions;
    String[] storages;


    private CheckBox checkBox;
    private int checkedNum =0;
    private TextView nameTV;
    private TextView amountTV;
    private TextView unitTV;

    DatabaseHelper DBhelper;



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

        checkBox = (CheckBox)findViewById(R.id.checkBox);
        nameTV = (TextView) findViewById(R.id.shopDetailNameTV);
        amountTV = (TextView) findViewById(R.id.shopDetailAmountTV);
        unitTV = (TextView) findViewById(R.id.shopDetailUnitTV);
        myListView = (ListView) findViewById(R.id.shoppingListView);

        DBhelper = new DatabaseHelper(this);
        // Cursor res = DBhelper.getShoppingData();
        ShoppingCart shoppingCart = new ShoppingCart(DBhelper);

        ArrayList<Ingredient> shoppingIngredients= shoppingCart.getShoppingIngredients();





        // Resources res = getResources();
        // myListView = (ListView) findViewById(R.id.shoppingListView);
        // names = res.getStringArray(R.array.Names);
        // amounts = res.getIntArray(R.array.Amount);
        // units = res.getStringArray(R.array.Units);



        // final ArrayList<String> names = initNames();
        // final ArrayList<String> amounts = initAmounts();
        // final ArrayList<String> units = initUnits();




        ShopItemAdapter shopItemAdapter = new ShopItemAdapter(this, shoppingIngredients);
        myListView.setAdapter(shopItemAdapter);



        // public void checkboxClick(ShopItemAdapter shopItemAdapter, int position){


        //     String s = names.get(position);
        //     Log.d("String","s");
        //     if (shopItemAdapter.getCheckeds().get(position)==true) {
        //         shopItemAdapter.getCheckeds().put(position, false);

        //     }else{

        //         shopItemAdapter.getCheckeds().put(position, true);
        //     }
        //     shopItemAdapter.notifyDataSetChanged();


        // }

    }
}

