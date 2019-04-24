package com.example.foodmanagement;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class HistRecipeActivity extends AppCompatActivity {


    ListView hisRecipeListView;

    DatabaseHelper recipeDB;

    ArrayList<Recipe> recipes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_recipe);


        recipeDB = new DatabaseHelper(this);
        Cursor res = recipeDB.getHistRecipeData();
        if(res.getCount()>0){
            ArrayList<Recipe> recipes = new ArrayList<>();
            while(res.moveToNext())
            {
                Integer id = res.getInt(0);
                String current = res.getString(1);
                String name = res.getString(2);
                String type = res.getString(3);
                String cuisine = res.getString(4);
                String[] IDs = res.getString(5).split(",");
                String [] Names = res.getString(6).split(",");
                String[] amountsS = res.getString(7).split(",");
                Double []amounts = new Double[amountsS.length];
                if(amountsS[0]=="" || IDs[0]=="")
                    continue;
                for(int i = 0; i<amountsS.length; i++)
                {
                    if(amountsS[i]==""){
                        break;}
                    amounts[i] = Double.parseDouble(amountsS[i]);
                }
                if(amountsS.length!=IDs.length)
                    continue;
                IngredientList ingList = new IngredientList(IDs, Names, amounts);
                Recipe newRecipe = new Recipe(id, name, cuisine, type, ingList);
                recipes.add(newRecipe);
            }

            hisRecipeListView = (ListView) findViewById(R.id.hisRecipeLV);

            if (recipes.size() > 0) {
                HistoryRecipeItemAdapter historyRecipeItemAdapter = new HistoryRecipeItemAdapter(this, recipes);
                hisRecipeListView.setAdapter(historyRecipeItemAdapter);
            }
        }
    }
}
