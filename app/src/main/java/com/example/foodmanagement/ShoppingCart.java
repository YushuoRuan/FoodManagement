public class ShoppingCart {
    private ArrayList<Ingredient> shoppingList;

    public ShoppingCart(DatabaseHelper myDb){
        shoppingList = new ArrayList<>();
        Cursor res = myDb.getShoppingData();
        if(res.getCount() > 0)
        {
            while(res.moveToNext()){

                Integer ID = res.getInt(0);
                String type= res.getString(1);
                String name = res.getString(2);
                double amount= res.getFloat(3);
                String unit= res.getString(4);
                String storage= res.getString(5);
                String expired= res.getString(6);
                String tags= res.getString(7);

                shoppingItem(ID,type,name,amount,unit,storage,expired,tags);

            }
        }

    }

    public void shoppingItem(Integer I, String type, String name, double amount, String unit, String storage, String exp, String tags){
        Ingredient newIngredient = new Ingredient(I, type, name, amount, unit, storage);
        shoppingList.add(newIngredient);

    }

    public ArrayList<Ingredient> getShoppingIngredients(){
        return shoppingList;
    }

}