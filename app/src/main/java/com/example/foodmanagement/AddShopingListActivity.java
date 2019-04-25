/*
 * Source code for the "add new ingredient to shopping list" page
 * Authors: Ziying Zhang, Tianshu Pang, Peng Yan, Yushuo Ruan
 */

package com.example.foodmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class AddShopingListActivity extends AppCompatActivity {

    DatabaseHelper myDb; /*facade database helper*/

    //Spinners in the layout
    Spinner typeSpinner;
    Spinner unitSpinner;
    Spinner storeSpinner;
    Spinner tagSpinner;

    //Edit Texts in the layout, will be used in inner classes
    EditText addNameET;
    EditText addAmountET;
    EditText addTagET;

    //trigger onCreate function when starting this activity.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shoping_list);

        myDb = new DatabaseHelper(this);/*instanciate Database Helper*/

        //Set candidate values to spinners
        typeSpinner = (Spinner) findViewById(R.id.shopTypeSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.FoodCategories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);

        unitSpinner = (Spinner) findViewById(R.id.shopUnitSpinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.UnitsType, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinner.setAdapter(adapter2);

        storeSpinner = (Spinner) findViewById(R.id.shopStorageSpinner);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.StoreType, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        storeSpinner.setAdapter(adapter3);

        tagSpinner = (Spinner) findViewById(R.id.shopTagSpinner);
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this, R.array.Tags, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tagSpinner.setAdapter(adapter4);

        //Get Edit Texts from layout.
        addNameET = (EditText) findViewById(R.id.shopNameET);
        addAmountET = (EditText) findViewById(R.id.shopAmountET);
        addTagET = (EditText) findViewById(R.id.shopTagET);

        //set event when the "add to inventory" button is clicked
        Button finalAdd = (Button) findViewById(R.id.shopFinalAddBtn);
        finalAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                boolean added = addToShopping();
                if(added){
                    finish();
                    Intent showShoppingList = new Intent(getApplicationContext(), ShoppingListActivity.class);
                    startActivity(showShoppingList);
                }
            }
        });


    }

    /*
     * take values from the Spinner and EditText and put then into database.
     */
    private boolean addToShopping(){
        String type = typeSpinner.getSelectedItem().toString();
        String name = addNameET.getText().toString();
        String amount = addAmountET.getText().toString();
        String unit = unitSpinner.getSelectedItem().toString();
        String storage = storeSpinner.getSelectedItem().toString();

        //make sure we have all the information
        if(type.equals("") || name.equals("") || amount.equals("") || unit.equals("") || storage.equals("")){
            Toast.makeText(AddShopingListActivity.this, "More Info Required", Toast.LENGTH_LONG).show();
            return false;
        }

        //print status
        boolean inserted = myDb.insertDataShopping(type, name, amount, unit, storage);
        if(inserted){
            Toast.makeText(AddShopingListActivity.this, "Added to Shopping List", Toast.LENGTH_LONG).show();
            return true;
        }
        else{
            Toast.makeText(AddShopingListActivity.this, "Fail", Toast.LENGTH_LONG).show();
            return false;
        }

    }
}
