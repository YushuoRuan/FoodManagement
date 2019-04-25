/*
 * Inventory activity (Launcher activity) source code.
 * Authors: Ziying Zhang, Tianshu Pang, Peng Yan, Yushuo Ruan
 */
package com.example.foodmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    ListView myListView; /*list view for ingredients in inventory*/

//    ArrayList<Ingredient> ingredients;
    IngredientList ingredients;
    IngredientList ingredientsTBD;

//    ArrayList<Ingredient> ingredientsTBD; /*ingredients to be displayed*/

    public DatabaseHelper myDb; /*facade database helper*/

    //set bottom navigation view functionality.
    //navigating between inventory, shopping list, and recipe pages.
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_inventory:
                    return true;
                case R.id.navigation_cart:
                    Intent showShoppingList = new Intent(getApplicationContext(), ShoppingListActivity.class);
                    startActivity(showShoppingList);
                    return true;
                case R.id.navigation_recipe:
                    Intent showRecipe = new Intent(getApplicationContext(), RecipeActivity.class);
                    startActivity(showRecipe);
                    return true;
            }
            return false;
        }
    };

    //top navigation that chose categories to display in the list view.
    private BottomNavigationView.OnNavigationItemSelectedListener tOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_all:
                    ingredientsTBD = ingredients.subListStorage("All");
                    showInventory(ingredientsTBD);
                    return true;
                case R.id.navigation_fridge:
                    ingredientsTBD = ingredients.subListStorage("Fridge");
                    showInventory(ingredientsTBD);
                    return true;
                case R.id.navigation_frozen:
                    ingredientsTBD = ingredients.subListStorage("Frozen");
                    showInventory(ingredientsTBD);
                    //display frozen inventory
                    return true;
                case R.id.navigation_room:
                    ingredientsTBD = ingredients.subListStorage("Room");
                    showInventory(ingredientsTBD);
                    //display room temperature inventory
                    return true;
            }
            return false;
        }
    };

    //first to run when starting this activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this); /*instantiate database helper*/
        FoodStorage foodStorage = new FoodStorage(myDb); /*construct food storage with ingredients in database*/

        //set up bottom navigator
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        BottomNavigationView topNavigation = (BottomNavigationView) findViewById(R.id.top_navigation);
        topNavigation.setOnNavigationItemSelectedListener(tOnNavigationItemSelectedListener);

        ingredients = foodStorage.getIngredients(); /*get ingredients from food storage*/
        mTextMessage = (TextView) findViewById(R.id.message);

        //inflate listview with adapter.
        myListView = (ListView) findViewById(R.id.invenListView);
        ingredientsTBD = ingredients.subListStorage("All");
        showInventory(ingredientsTBD);
//
        //when a item in listview is clicked, go to edit page
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //get activity
                Intent showFoodEdit = new Intent(getApplicationContext(), FoodEdit.class);
                //pass information to next activity.
                showFoodEdit.putExtra("position", position);
                showFoodEdit.putExtra("ID", ingredientsTBD.get(position).ID);

                //process date and pass to next activity.
                String dateS = ingredientsTBD.get(position).getExpiredDate();
                Date date1 = null;
                try {
                    date1 = new SimpleDateFormat("MM/dd/yyyy").parse(dateS);
                    showFoodEdit.putExtra("expireDate", date1.getTime());
                }catch (Exception e)
                {
                    Date today = Calendar.getInstance().getTime();
                    showFoodEdit.putExtra("expireDate", today.getTime());
                }
                //go to food edit activity.
                startActivity(showFoodEdit);
            }
        });

        //find and set add button in inventory page.
        Button addInBtn = (Button) findViewById(R.id.addInventoryBtn);
        addInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get and enter add ingredient activity.
                Intent showAddInventory = new Intent(getApplicationContext(), addInventoryActivity.class);
                startActivity(showAddInventory);
            }
        });


    }

    private void showInventory(IngredientList ingredients) {
        if(ingredients.size() >= 0) {
            ItemAdapter itemAdapter = new ItemAdapter(this, ingredients);
            myListView.setAdapter(itemAdapter);
        }

    }


}
