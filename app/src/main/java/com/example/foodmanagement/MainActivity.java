package com.example.foodmanagement;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
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

    ListView myListView;

    ArrayList<Ingredient> ingredients;

    ArrayList<Ingredient> ingredientsTBD;

    public DatabaseHelper myDb;

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

    private BottomNavigationView.OnNavigationItemSelectedListener tOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_all:
                    ingredientsTBD = ingredients;
                    return true;
                case R.id.navigation_fridge:
                    ingredientsTBD = ingredients;
                    return true;
                case R.id.navigation_frozen:
                    //display frozen inventory
                    return true;
                case R.id.navigation_room:
                    //display room temperature inventory
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);
        FoodStorage foodStorage = new FoodStorage(myDb);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//        Resources res = getResources();
//        names = res.getStringArray(R.array.Names);
//        amounts = res.getIntArray(R.array.Amount);
//        units = res.getStringArray(R.array.Units);
//        descriptions = res.getStringArray(R.array.Dates);
//        storages = res.getStringArray(R.array.Storages);

        ingredients = foodStorage.getIngredients();

        myListView = (ListView) findViewById(R.id.invenListView);
        if(ingredients.size()>0) {
            ItemAdapter itemAdapter = new ItemAdapter(this, ingredients);
            myListView.setAdapter(itemAdapter);
        }
//
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent showFoodEdit = new Intent(getApplicationContext(), FoodEdit.class);
                showFoodEdit.putExtra("position", position);
                showFoodEdit.putExtra("ID", ingredients.get(position).ID);


                String dateS = ingredients.get(position).getExpiredDate();
                Date date1 = null;
                try {
                    date1 = new SimpleDateFormat("MM/dd/yyyy").parse(dateS);
                    showFoodEdit.putExtra("expireDate", date1.getTime());
                }catch (Exception e)
                {
                    Date today = Calendar.getInstance().getTime();
                    showFoodEdit.putExtra("expireDate", today.getTime());
                }

                startActivity(showFoodEdit);
            }
        });

        Button addInBtn = (Button) findViewById(R.id.addInventoryBtn);

        addInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showAddInventory = new Intent(getApplicationContext(), addInventoryActivity.class);
                startActivity(showAddInventory);
            }
        });


    }



}
