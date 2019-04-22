package com.example.foodmanagement;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import static com.example.foodmanagement.DatabaseHelper.INVENTORY_TABLE_NAME;

public class FoodEdit extends AppCompatActivity {

    DatabaseHelper tmpDB;
    EditText subtractAmountET;
    int ID;
    Date expireDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_edit);

        Intent in = getIntent();
        int index = in.getIntExtra("position", -1);
        ID = in.getIntExtra("ID", -1);
        long currExpire = in.getLongExtra("expireDate",0);


        tmpDB = new DatabaseHelper(this);
        Cursor res = tmpDB.getIngredient(ID);

//        Resources res = getResources();
//        String[] names = res.getStringArray(R.array.Names);
//        String[] units = res.getStringArray(R.array.Units);
//        String[] storages = res.getStringArray(R.array.Storages);

        String name = res.getString(2);
        String unit = res.getString(4);
        String storage = res.getString(5);

        TextView idTV = (TextView) findViewById(R.id.editIDTV);
        idTV.setText(Integer.toString(ID));

        TextView nameTV = (TextView) findViewById(R.id.editNameTV);
        nameTV.setText(name);

        TextView unitTV = (TextView) findViewById(R.id.editUnitTV);
        unitTV.setText(unit);

        TextView storageTV = (TextView) findViewById(R.id.editStorageTV);
        storageTV.setText(storage);

        subtractAmountET = (EditText) findViewById(R.id.subtractAmountET);

        Button subtractBtn = (Button) findViewById(R.id.subtractButton);
        Button deleteBtn = (Button) findViewById(R.id.deleteButton);

        subtractBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //subtract amount from the database
                Double outAmount = Double.parseDouble(subtractAmountET.getText().toString());
                boolean subtracted = tmpDB.subtractDataInventory(ID, outAmount);
                if(subtracted){
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

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deleted = tmpDB.deleteInventoryData(ID);
                if(deleted>0){
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
