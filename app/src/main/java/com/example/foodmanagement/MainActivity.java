package com.example.foodmanagement;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    ListView myListView;
    String[] names;
    int[] amounts;
    String[] units;
    String[] descriptions;
    String[] storages;

//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.navigation_inventory:
//                    mTextMessage.setText(R.string.title_inventory);
//                    return true;
//                case R.id.navigation_cart:
//                    mTextMessage.setText(R.string.title_shopping_cart);
//                    return true;
//                case R.id.navigation_recipe:
//                    mTextMessage.setText(R.string.title_recipe);
//                    return true;
//            }
//            return false;
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Resources res = getResources();
        myListView = (ListView) findViewById(R.id.invenListView);
        names = res.getStringArray(R.array.Names);
        amounts = res.getIntArray(R.array.Amount);
        units = res.getStringArray(R.array.Units);;
        descriptions = res.getStringArray(R.array.Dates);
        storages = res.getStringArray(R.array.Storages);


        ItemAdapter itemAdapter = new ItemAdapter(this, names, amounts, units, descriptions, storages);
        myListView.setAdapter(itemAdapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent showFoodEdit = new Intent(getApplicationContext(), FoodEdit.class);
                showFoodEdit.putExtra("com.example.foodmanagement.ITEM_INDEX", position);
                startActivity(showFoodEdit);
            }
        });


    }

}
