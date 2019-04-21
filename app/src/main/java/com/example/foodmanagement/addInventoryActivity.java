package com.example.foodmanagement;

import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.support.annotation.ArrayRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Date;

public class addInventoryActivity extends AppCompatActivity {

    DatabaseHelper myDb;

    Spinner typeSpinner;
    Spinner unitSpinner;
    Spinner storeSpinner;
    Spinner tagSpinner;

    EditText addNameET;
    EditText addAmountET;
    EditText addTagET;

    CalendarView expireCalender;
    Date expireDate;

    String[] tags;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inventory);

//        Resources res = getResources();
//        foodCategories = res.getStringArray(R.array.FoodCategories);
        myDb = new DatabaseHelper(this);
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

        String[] a;


        tagSpinner = (Spinner) findViewById(R.id.addTagSpinner);
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this, R.array.Tags, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tagSpinner.setAdapter(adapter4);


        addNameET = (EditText) findViewById(R.id.addNameET);
        addAmountET = (EditText) findViewById(R.id.addAmountET);
        addTagET = (EditText) findViewById(R.id.addTagET);
        expireCalender = (CalendarView) findViewById(R.id.expireCV);

        //expireDate = new Date(expireCalender.getDate());
        expireDate = null;

        expireCalender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                expireDate = new Date(year-1900, month, dayOfMonth);
            }
        });

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


//        tagSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//        });

    }

    private boolean addToInventory(){
        String type = typeSpinner.getSelectedItem().toString();
        String name = addNameET.getText().toString();
        String amount = addAmountET.getText().toString();
        String unit = unitSpinner.getSelectedItem().toString();
        String storage = storeSpinner.getSelectedItem().toString();



        if(type==""|| name==""||amount==""||unit==""||storage==""){
            Toast.makeText(addInventoryActivity.this, "More Info Required", Toast.LENGTH_LONG).show();
            return false;
        }
        String date2 = "null";
        if(expireDate!=null)
            date2 = (expireDate.getMonth()+1)+"/"+expireDate.getDate()+"/"+(expireDate.getYear()+1900);
        boolean inserted = myDb.insertDataInventory(type, name, amount, unit, storage, date2, "none");
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
