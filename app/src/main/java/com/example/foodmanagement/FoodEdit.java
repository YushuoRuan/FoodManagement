package com.example.foodmanagement;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.foodmanagement.DatabaseHelper.INVENTORY_TABLE_NAME;

public class FoodEdit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_edit);

        Intent in = getIntent();
        int index = in.getIntExtra("position", -1);
        int ID = in.getIntExtra("ID", -1);


        DatabaseHelper tmpDB = new DatabaseHelper(this);
        Cursor res = tmpDB.getIngredient(ID);

//        Resources res = getResources();
//        String[] names = res.getStringArray(R.array.Names);
//        String[] units = res.getStringArray(R.array.Units);
//        String[] storages = res.getStringArray(R.array.Storages);

        String name = res.getString(2);
        String unit = res.getString(4);
        String storage = res.getString(5);

        TextView nameTV = (TextView) findViewById(R.id.editNameTV);
        nameTV.setText(name);

        TextView unitTV = (TextView) findViewById(R.id.editUnitTV);
        unitTV.setText(unit);

        TextView storageTV = (TextView) findViewById(R.id.editStorageTV);
        storageTV.setText(storage);

        Button subtractBtn = (Button) findViewById(R.id.subtractButton);
        Button deleteBtn = (Button) findViewById(R.id.deleteButton);

        subtractBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //subtract amount from the database
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete item from the database
            }
        });

    }
}
