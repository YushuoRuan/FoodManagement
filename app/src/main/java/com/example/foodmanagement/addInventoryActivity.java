/*
 * Source code for the "add new ingredient to inventory" page
 * Authors: Ziying Zhang, Tianshu Pang, Peng Yan, Yushuo Ruan
 */
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

public class addInventoryActivity extends AppCompatActivity {

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


    CalendarView expireCalender;
    Date expireDate;

    @Override
    //trigger onCreate function when starting this activity.
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inventory);

        myDb = new DatabaseHelper(this); /*instanciate Database Helper*/

        //Set candidate values to spinners
        typeSpinner = (Spinner) findViewById(R.id.foodTypeSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.FoodCategories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);

        unitSpinner = (Spinner) findViewById(R.id.addUnitSpinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.UnitsType, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinner.setAdapter(adapter2);

        storeSpinner = (Spinner) findViewById(R.id.addStorageSpinner);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.StoreType, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        storeSpinner.setAdapter(adapter3);


        //Get other layout items.
        addNameET = (EditText) findViewById(R.id.addNameET);
        addAmountET = (EditText) findViewById(R.id.addAmountET);
        expireCalender = (CalendarView) findViewById(R.id.expireCV);

        //expireDate = new Date(expireCalender.getDate());
        expireDate = null; /*initialize expire date to null*/

        //set expire date when choose date
        expireCalender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                expireDate = new Date(year-1900, month, dayOfMonth);
            }
        });

        //set event when the "add to inventory" button is clicked
        Button finalAdd = (Button) findViewById(R.id.finalAddInventoryBtn);
        finalAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                boolean added = addToInventory();
                if(added){
                    finish();
                    Intent showInventory = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(showInventory);
                }
            }
        });

    }
    /*
     * take values from the Spinner and EditText and put then into database.
     */
    private boolean addToInventory(){
        String type = typeSpinner.getSelectedItem().toString();
        String name = addNameET.getText().toString();
        String amount = addAmountET.getText().toString();
        String unit = unitSpinner.getSelectedItem().toString();
        String storage = storeSpinner.getSelectedItem().toString();


        // make sure we have all the information
        if(type.equals("") || name.equals("") || amount.equals("") || unit.equals("") || storage.equals("")){
            Toast.makeText(addInventoryActivity.this, "More Info Required", Toast.LENGTH_LONG).show();
            return false;
        }
        // convert date to string and store to database
        String date2 = "null";
        if(expireDate!=null)
            date2 = (expireDate.getMonth()+1)+"/"+expireDate.getDate()+"/"+(expireDate.getYear()+1900);
        boolean inserted = myDb.insertDataInventory(type, name, amount, unit, storage, date2, "none");
        // print status
        if(inserted){
            Toast.makeText(addInventoryActivity.this, "Added to inventory", Toast.LENGTH_LONG).show();
            return true;
        }
        else{
            Toast.makeText(addInventoryActivity.this, "Fail", Toast.LENGTH_LONG).show();
            return false;
        }

    }

}
