package com.example.foodmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Date;

public class AddShopingListActivity extends AppCompatActivity {

    DatabaseHelper myDb;

    Spinner typeSpinner;
    Spinner unitSpinner;
    Spinner storeSpinner;
    Spinner tagSpinner;

    EditText addNameET;
    EditText addAmountET;
    EditText addTagET;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shoping_list);

        myDb = new DatabaseHelper(this);

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

        addNameET = (EditText) findViewById(R.id.shopNameET);
        addAmountET = (EditText) findViewById(R.id.shopAmountET);
        addTagET = (EditText) findViewById(R.id.shopTagET);

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

    private boolean addToShopping(){
        String type = typeSpinner.getSelectedItem().toString();
        String name = addNameET.getText().toString();
        String amount = addAmountET.getText().toString();
        String unit = unitSpinner.getSelectedItem().toString();
        String storage = storeSpinner.getSelectedItem().toString();


        if(type==""|| name==""||amount==""||unit==""||storage==""){
            Toast.makeText(AddShopingListActivity.this, "More Info Required", Toast.LENGTH_LONG).show();
            return false;
        }


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
