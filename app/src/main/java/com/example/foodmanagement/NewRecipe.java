package com.example.foodmanagement;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NewRecipe extends AppCompatActivity {

    DatabaseHelper inventoryDB;
    Recipe recipe;
    String recipeName = "";
    String recipeType = "";
    String recipeCuisine = "";
    IngredientList ingList;
    TextView ingredientText;
    EditText recipeNameText;

    int ingPos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);

        init();
        ingredientText = (TextView) findViewById(R.id.ingredientText);
        recipeNameText = (EditText) findViewById(R.id.recipeNameText);
        // select recipe type
        Spinner recipeTypeSpinner = (Spinner) findViewById(R.id.recipeTypeSpinner);
        final ArrayAdapter<CharSequence> recipeTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.recipeType, android.R.layout.simple_spinner_item);
        recipeTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        recipeTypeSpinner.setAdapter(recipeTypeAdapter);
        recipeTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                recipeType = Objects.requireNonNull(recipeTypeAdapter.getItem(position)).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // select recipe cuisine
        Spinner recipeCuisineSpinner = (Spinner) findViewById(R.id.recipeCuisineSpinner);
        final ArrayAdapter<CharSequence> recipeCuisineAdapter = ArrayAdapter.createFromResource(this,
                R.array.recipeCuisine, android.R.layout.simple_spinner_item);
        recipeCuisineAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        recipeCuisineSpinner.setAdapter(recipeCuisineAdapter);
        recipeCuisineSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                recipeCuisine = Objects.requireNonNull(recipeCuisineAdapter.getItem(position)).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // read the recipe name
        final EditText recipeNameText = (EditText) findViewById(R.id.recipeNameText);
        

        // select ingredient from inventory
        Spinner selectIngredientSpinner = (Spinner) findViewById(R.id.selectIngredientSpinner);
        inventoryDB = new DatabaseHelper(this);
        Cursor inventory =  inventoryDB.getInventoryData();
        final List<Integer> idList = new ArrayList<>();
        final List<String> ingredientList = new ArrayList<>();
        final List<String> amountList = new ArrayList<>();
        final List<String> typeList = new ArrayList<>();
        final List<String> storageList = new ArrayList<>();
        final List<String> unitList = new ArrayList<>();
        if (inventory.getCount() == 0) {

        } else {
            while (inventory.moveToNext()) {
                idList.add(inventory.getInt(0));
                typeList.add(inventory.getString(1));
                String item = inventory.getString(2);
                ingredientList.add(item);
                amountList.add(inventory.getString(3));
                String unit = inventory.getString(4);
                storageList.add(inventory.getString(5));
                unitList.add(unit);
            }
        }
        ArrayAdapter<String> ingredientListAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, ingredientList);
        ingredientListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectIngredientSpinner.setAdapter(ingredientListAdapter);

        // change unit according to the ingredient selected
        final TextView unitText = (TextView) findViewById(R.id.ingredientUnitText);
        selectIngredientSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                unitText.setText(unitList.get(position));
                ingPos = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final EditText ingredientAmount = (EditText) findViewById(R.id.ingredientAmountText);


        final Button addIngredientButton = (Button) findViewById(R.id.addIngredientBtn);
        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (ingPos == -1) {
                    addIngredientButton.setText("Please select ingredient");
                } else if (ingredientAmount.getText().toString().equals("")) {
                    addIngredientButton.setText("Please input amount");
                } else {
                    addIngredientButton.setText("Added current ingredient");
                    double amountWanted = Double.parseDouble(ingredientAmount.getText().toString());
                    ingredientAmount.getText().clear();
                    Ingredient ing = new Ingredient(idList.get(ingPos), ingredientList.get(ingPos), amountWanted);
                    if (amountWanted > Double.parseDouble(amountList.get(ingPos))) {
                        double amountDiff = amountWanted - Double.parseDouble(amountList.get(ingPos));
                        inventoryDB.insertDataShopping(typeList.get(ingPos), ingredientList.get(ingPos),
                                 String.valueOf(amountDiff), unitList.get(ingPos), storageList.get(ingPos));
                    }
                    ingList.add(ing);
                    ingredientText.setText(ingList.showIngredients().get(0) + '\n' + ingList.showIngredients().get(1));
                }

            }
        });

        final Button addRecipeButton = (Button) findViewById(R.id.addRecipeBtn);
        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                recipeName = recipeNameText.getText().toString();
                recipe = new Recipe(recipeName, recipeType, recipeCuisine, ingList);
                inventoryDB.insertNewRecipe(recipe);
                addRecipeButton.setText("ADDED");
                finish();
                startActivity(new Intent(getApplicationContext(), RecipeActivity.class));
            }
        });


    }



    private void init () {
        ingList = new IngredientList();
    }


}
