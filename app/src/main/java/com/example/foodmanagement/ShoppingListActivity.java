/*
 * Shopping list activity (Launcher activity) source code.
 * Authors: Ziying Zhang, Tianshu Pang, Peng Yan, Yushuo Ruan
 */
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

import android.widget.AdapterView;
import java.util.ArrayList;

public class ShoppingListActivity extends AppCompatActivity {

    ListView myListView; /*list view for ingredients in shoppinglist*/
    String[] names;
    int[] amounts;
    String[] units;

    String[] descriptions;
    String[] storages;


    private CheckBox checkBox;
    private int checkedNum =0;/*initial the checkbox number */
    private TextView nameTV;
    private TextView amountTV;
    private TextView unitTV;

    DatabaseHelper DBhelper; /*facade database helper*/


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
                    return true;
                case R.id.navigation_recipe:
                    Intent showRecipe = new Intent(getApplicationContext(), RecipeActivity.class);
                    startActivity(showRecipe);
                    return true;
            }
            return false;
        }
    };

    //first to run when starting this activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list); //address the layout

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setSelectedItemId(R.id.navigation_cart);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //find and set add add new shoppinglist item button
        Button addShopBtn = (Button) findViewById(R.id.addShoppingBtn);
        //get and enter add shoppinglist item activity.
        addShopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showAddShopping = new Intent(getApplicationContext(), AddShopingListActivity.class);//indicate the class after click button
                startActivity(showAddShopping);
            }
        });

        //define the elements
        checkBox = (CheckBox)findViewById(R.id.checkBox);
        nameTV = (TextView) findViewById(R.id.shopDetailNameTV);
        amountTV = (TextView) findViewById(R.id.shopDetailAmountTV);
        unitTV = (TextView) findViewById(R.id.shopDetailUnitTV);
        myListView = (ListView) findViewById(R.id.shoppingListView);




        /*instantiate database helper*/
        DBhelper = new DatabaseHelper(this);
        // Cursor res = DBhelper.getShoppingData();
        ShoppingCart shoppingCart = new ShoppingCart(DBhelper);

        ArrayList<Ingredient> shoppingIngredients = shoppingCart.getShoppingIngredients();//get shopping list items



        //make a judgement of shopping list size
        if(shoppingIngredients.size()>0) {
            final ShopItemAdapter shopItemAdapter = new ShopItemAdapter(this, shoppingIngredients);//define related adapter
            myListView.setAdapter(shopItemAdapter);

            //when a item in listview is clicked, checkbox status change
            myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    ShopItemAdapter.ViewHolder holder = (ShopItemAdapter.ViewHolder) view.getTag();
                    holder.ckb.toggle();
                    shopItemAdapter.getChecked().put(position, holder.ckb.isChecked());
                    shopItemAdapter.notifyDataSetChanged();
                }
            });
        }
    }
}

