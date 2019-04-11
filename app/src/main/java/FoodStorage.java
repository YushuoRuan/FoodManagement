import com.example.foodmanagement.Ingredient;

import java.util.ArrayList;
import java.util.Date;

public class FoodStorage {

    private ArrayList<Ingredient> storedIngredientList;

    public FoodStorage(){
        //initialize FoodStorage
        //read ingredient data from either database or csv.

    }

    public void addIngredient(String name, double amount, String unit, Date add, Date exp, String[] tags){
        Ingredient newIngredient = new Ingredient(name, amount, unit);
        if (add!=null){
            newIngredient.setAddDate(add);
        }
        if (exp!=null){
            newIngredient.setExpiredDate(exp);
        }

        for(int i = 0; i < tags.length; i++){
            newIngredient.addTag(tags[i]);
        }

        storedIngredientList.add(newIngredient);

    }

    public ArrayList<Ingredient> showIngredients(){
        return storedIngredientList;
    }
}
