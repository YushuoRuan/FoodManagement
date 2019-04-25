/*
 * Activity class for history recipe list view
 * Authors: Ziying Zhang, Tianshu Pang, Peng Yan, Yushuo Ruan
 */


package com.example.foodmanagement;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class HistRecipeActivity extends AppCompatActivity {


    ListView hisRecipeListView; /*list view for recipes*/;

    DatabaseHelper recipeDB; /*facade database helper*/;

    ArrayList<Recipe> recipes;

    //first to run when starting this activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_recipe);

        /*instantiate database helper*/
        recipeDB = new DatabaseHelper(this);
        //get recipe marked as current to display on the list view.
        Cursor res = recipeDB.getHistRecipeData();
        if(res.getCount()>0){
            ArrayList<Recipe> recipes = new ArrayList<>();
            while(res.moveToNext())
            {
                //get info from each row of table
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
                //convert string array to double array
                for(int i = 0; i<amountsS.length; i++)
                {
                    if(amountsS[i]==""){
                        break;}
                    amounts[i] = Double.parseDouble(amountsS[i]);
                }
                //check number of ingredient and amount match
                if(amountsS.length!=IDs.length)
                    continue;
                IngredientList ingList = new IngredientList(IDs, Names, amounts);
                Recipe newRecipe = new Recipe(id, name, cuisine, type, ingList);
                recipes.add(newRecipe);
            }
            //find listview and set its adapter using the recipe list.
            hisRecipeListView = (ListView) findViewById(R.id.hisRecipeLV);

            if (recipes.size() > 0) {
                HistoryRecipeItemAdapter historyRecipeItemAdapter = new HistoryRecipeItemAdapter(this, recipes);
                hisRecipeListView.setAdapter(historyRecipeItemAdapter);
            }
        }
    }
}
