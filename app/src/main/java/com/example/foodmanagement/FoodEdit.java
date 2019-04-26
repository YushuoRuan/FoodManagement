/*
 * source code of Edit food page activity in inventory page
 * Authors: Ziying Zhang, Tianshu Pang, Peng Yan, Yushuo Ruan
 */
package com.example.foodmanagement;

import android.content.Intent;
import android.database.Cursor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Date;

public class FoodEdit extends AppCompatActivity {

    DatabaseHelper tmpDB; /*facade database helper*/
    EditText subtractAmountET; /*subtract amount edit text*/
    int ID; /*FoodEdit on Item with ID*/
    Date expireDate;/*subtract amount edit text*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_edit);

        //get the information from last activity.
        Intent in = getIntent();
        int index = in.getIntExtra("position", -1);
        ID = in.getIntExtra("ID", -1);
        long currExpire = in.getLongExtra("expireDate",0);
        //String storage = in.getStringExtra("Storage");

        //instantiate database helper
        tmpDB = new DatabaseHelper(this);

        //get ingredient data with ID, extract needed information.
        Cursor res = tmpDB.getIngredient(ID);
        String name = res.getString(2);
        String unit = res.getString(4);
        String storage = res.getString(5);

        //get the textviews in layout and display information
        TextView idTV = (TextView) findViewById(R.id.editIDTV);
        idTV.setText(Integer.toString(ID));
        TextView nameTV = (TextView) findViewById(R.id.editNameTV);
        nameTV.setText(name);
        TextView unitTV = (TextView) findViewById(R.id.editUnitTV);
        unitTV.setText(unit);
        TextView storageTV = (TextView) findViewById(R.id.editStorageTV);
        storageTV.setText(storage);

        //change storage icon base on storage type
        ImageView strImg = (ImageView) findViewById(R.id.StorageIV);
        switch (storage) {
            case "Frozen":
                strImg.setImageResource(R.drawable.ic_frozen);
                break;
            case "Fridge":
                strImg.setImageResource(R.drawable.ic_refrigerator);
                break;
            case "Room":
                strImg.setImageResource(R.drawable.ic_temperature_inside_595b40b85ba036ed117db483);
                break;
        }

        subtractAmountET = (EditText) findViewById(R.id.subtractAmountET);

        //find buttons in layout
        Button subtractBtn = (Button) findViewById(R.id.subtractButton);
        Button deleteBtn = (Button) findViewById(R.id.deleteButton);

        //set event when click on subtract button
        subtractBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //subtract amount from the database
                Double outAmount = Double.parseDouble(subtractAmountET.getText().toString());
                boolean subtracted = tmpDB.subtractDataInventory(ID, outAmount);
                if(subtracted){
                    //jump back to inventory activity.
                    Toast.makeText(FoodEdit.this, "Subtracted from Inventory", Toast.LENGTH_LONG).show();
                    finish();
                    Intent showInventory = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(showInventory);
                }
                else{
                    Toast.makeText(FoodEdit.this, "Failed: insufficient amount", Toast.LENGTH_LONG).show();
                }
            }
        });

        //set event when click on delete button
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deleted = tmpDB.deleteInventoryData(ID);
                if(deleted>0){
                    //show message and jump back to inventory activity.
                    Toast.makeText(FoodEdit.this, "Deleted from Inventory", Toast.LENGTH_LONG).show();
                    finish();
                    Intent showInventory = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(showInventory);
                }
                else{
                    Toast.makeText(FoodEdit.this, "Failed: item not found", Toast.LENGTH_LONG).show();
                }
            }
        });

        //change expire date functionality
        //store date chosen by user
        CalendarView expireCV = (CalendarView) findViewById(R.id.editExpireCV);
        expireCV.setDate(currExpire);
        Button updateExpireBtn = (Button) findViewById(R.id.updateExpireBtn);
        expireDate = null;
        expireCV.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                expireDate = new Date(year-1900, month, dayOfMonth);
            }
        });

        //update expire date and return to inventory activity when user click update button
        updateExpireBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean updated = tmpDB.updateExpireInventory(ID, expireDate);
                if(updated){
                    Toast.makeText(FoodEdit.this, "Updated Expire Date", Toast.LENGTH_LONG).show();
                    finish();
                    Intent showInventory = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(showInventory);
                }
                else{
                    Toast.makeText(FoodEdit.this, "Fail, please choose date", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
