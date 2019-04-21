package com.example.foodmanagement;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NewRecipe extends AppCompatActivity {

    DatabaseHelper inventoryDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);

        // select recipe type
        Spinner recipeTypeSpinner = (Spinner) findViewById(R.id.recipeTypeSpinner);
        ArrayAdapter<CharSequence> recipeTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.recipeType, android.R.layout.simple_spinner_item);
        recipeTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        recipeTypeSpinner.setAdapter(recipeTypeAdapter);

        // select recipe cuisine
        Spinner recipeCuisineSpinner = (Spinner) findViewById(R.id.recipeCuisineSpinner);
        ArrayAdapter<CharSequence> recipeCuisineAdapter = ArrayAdapter.createFromResource(this,
                R.array.recipeCuisine, android.R.layout.simple_spinner_item);
        recipeCuisineAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        recipeCuisineSpinner.setAdapter(recipeCuisineAdapter);

        // select ingredient from inventory
        Spinner selectIngredientSpinner = (Spinner) findViewById(R.id.selectIngredientSpinner);
        inventoryDB = new DatabaseHelper(this);
        Cursor inventory =  inventoryDB.getInventoryData();
        List<String> ingredientList = new ArrayList<>();
        final List<String> unitList = new ArrayList<>();
        if (inventory.getCount() == 0) {

        } else {
            while (inventory.moveToNext()) {
                String item = inventory.getString(2);
                ingredientList.add(item);
                String unit = inventory.getString(4);
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        typeSpinner = (Spinner) findViewById(R.id.foodTypeSpinner);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.FoodCategories, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        typeSpinner.setAdapter(adapter);
    }



//    private boolean addToInventory(){
//        String type = typeSpinner.getSelectedItem().toString();
//        String name = addNameET.getText().toString();
//        String amount = addAmountET.getText().toString();
//        String unit = unitSpinner.getSelectedItem().toString();
//        String storage = storeSpinner.getSelectedItem().toString();
//
//
//        if(type==""|| name==""||amount==""||unit==""||storage==""){
//            Toast.makeText(addInventoryActivity.this, "More Info Required", Toast.LENGTH_LONG).show();
//            return false;
//        }
//        String date2 = "null";
//        if(expireDate!=null)
//            date2 = (expireDate.getMonth()+1)+"/"+expireDate.getDate()+"/"+(expireDate.getYear()+1900);
//        boolean inserted = myDb.insertDataInventory(type, name, amount, unit, storage, date2, "none");
//        if(inserted){
//            Toast.makeText(addInventoryActivity.this, "Added to inventory", Toast.LENGTH_LONG).show();
//            return true;
//        }
//        else{
//            Toast.makeText(addInventoryActivity.this, "Fail", Toast.LENGTH_LONG).show();
//            return false;
//        }




}
