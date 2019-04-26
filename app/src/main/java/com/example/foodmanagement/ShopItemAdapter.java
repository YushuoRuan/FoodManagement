/*
 * ItemAdapter class for shopping list list view
 * Authors: Ziying Zhang, Tianshu Pang, Peng Yan, Yushuo Ruan
 */
package com.example.foodmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


public class ShopItemAdapter extends BaseAdapter {

    LayoutInflater shopInflater; /*declare inflater for list view. */
    IngredientList ingredients;
    DatabaseHelper myDb;/*declare database helper to accesses data in inventory table*/

    //initial the checkbox status
    private static HashMap<Integer,Boolean> checked=null;
    //write a function to get and set the checkbox status
    public HashMap<Integer, Boolean> getChecked() {
        return checked;
    }
    public void setCheckeds(HashMap<Integer, Boolean> checkeds) {
        ShopItemAdapter.checked= checkeds;
    }


    public ShopItemAdapter(Context c, IngredientList i){

        shopInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//initialize inflater
        ingredients = i;

        myDb = new DatabaseHelper(c);/*instantiate database helper*/


    //initialize all checkbox to be false in the beginning
        checked=new HashMap<Integer,Boolean>();
        for (int j=0;j<i.size();j++){
            getChecked().put(j, false);
        }


    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public Object getItem(int position) {
        return ingredients.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ingredients.get(position).ID;
    }


    @Override
   /* public View getView(int position, View convertView, ViewGroup parent) {*/
    public View getView(final int position, View view, ViewGroup parent) {
        //initialize listview with inflater.
         View v = shopInflater.inflate(R.layout.shopping_item_detail, null);

        //get TextViews from layout
        final TextView nameTV = (TextView) v.findViewById(R.id.shopDetailNameTV);
        final TextView amountTV = (TextView) v.findViewById(R.id.shopDetailAmountTV);
        final TextView unitTV = (TextView) v.findViewById(R.id.shopDetailUnitTV);
        final Button deleteBtn = (Button) v.findViewById(R.id.shopDetailDeleteBtn);
        final CheckBox ckb = (CheckBox) v.findViewById(R.id.checkBox);

        //get detail information of each item
        nameTV.setText(ingredients.get(position).getMaterial());
        amountTV.setText(Double.toString(ingredients.get(position).getAmount()));
        unitTV.setText(ingredients.get(position).getUnit());

        //set delete item button event
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete shopping item from shopping list and database
                deleteBtn.setText("Deleted!");
                myDb.deleteShoppingData(ingredients.get(position).ID);
            }
        });

        //use viewholder method to set checkbox event
        ViewHolder holder = null;
        //if ViewHolder is null to do the initialization
        if(holder ==  null){
            holder =new ViewHolder();

            //implement CheckBox initial mapping
            holder.ckb= (CheckBox) v.findViewById(R.id.checkBox);
            v.setTag(holder);
        }else{
            holder = (ViewHolder) v.getTag();
        }

        //set Checkbox status
        holder.ckb.setChecked(getChecked().get(position));

        //after click the check box, delete the item from shopping list DB table and add it to the inventory DB table
        ckb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDb.deleteShoppingData(ingredients.get(position).ID);
                myDb.insertDataInventory(ingredients.get(position).getType(), ingredients.get(position).getMaterial(),
                        Double.toString(ingredients.get(position).getAmount()), ingredients.get(position).getUnit(),
                        ingredients.get(position).getStorage(), "null", "null");//get the current position item detail information
            }
        });



        return v;
    }
    //define viewholder class for checkbox
    class ViewHolder{

        public CheckBox ckb;
    }

}





