package com.example.foodmanagement;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FoodEdit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_edit);

        Intent in = getIntent();
        int index = in.getIntExtra("com.example.foodmanagement.ITEM_INDEX", -1);

        Resources res = getResources();
        String[] names = res.getStringArray(R.array.Names);
        String[] units = res.getStringArray(R.array.Units);
        String[] storages = res.getStringArray(R.array.Storages);

        TextView nameTV = (TextView) findViewById(R.id.editNameTV);
        nameTV.setText(names[index]);

        TextView unitTV = (TextView) findViewById(R.id.editUnitTV);
        unitTV.setText(units[index]);

        TextView storageTV = (TextView) findViewById(R.id.editStorageTV);
        storageTV.setText(storages[index]);

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
