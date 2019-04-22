package com.example.foodmanagement;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import android.util.Log;



public class ShopItemAdapter extends BaseAdapter {

    LayoutInflater shopInflater;
    ArrayList<Ingredient> ingredients;

    DatabaseHelper myDb;

    private static HashMap<Integer,Boolean> checked=null;

    public HashMap<Integer, Boolean> getChecked() {
        return checked;
    }


    public void setCheckeds(HashMap<Integer, Boolean> checkeds) {
        ShopItemAdapter.checked= checkeds;
    }


    public ShopItemAdapter(Context c, ArrayList<Ingredient> i){

        shopInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ingredients = i;

        myDb = new DatabaseHelper(c);



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

         View v = shopInflater.inflate(R.layout.shopping_item_detail, null);

        final TextView nameTV = (TextView) v.findViewById(R.id.shopDetailNameTV);
        final TextView amountTV = (TextView) v.findViewById(R.id.shopDetailAmountTV);
        final TextView unitTV = (TextView) v.findViewById(R.id.shopDetailUnitTV);
        final Button deleteBtn = (Button) v.findViewById(R.id.shopDetailDeleteBtn);


        nameTV.setText(ingredients.get(position).getMaterial());
        amountTV.setText(Double.toString(ingredients.get(position).getAmount()));
        unitTV.setText(ingredients.get(position).getUnit());


        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete shopping item from shopping list and database
                deleteBtn.setText("Deleted!");
                myDb.deleteShoppingData(ingredients.get(position).ID);

            }
        });


        ViewHolder holder = null;

        if(holder ==  null){
            holder =new ViewHolder();
//            if( v== null){
//
//                View v = shopInflater(R.layout.shopping_item_detail,null);
//            }


            holder.ckb= (CheckBox) v.findViewById(R.id.checkBox);
            v.setTag(holder);
        }else{
            holder = (ViewHolder) v.getTag();
        }

        holder.ckb.setChecked(getChecked().get(position));


        return v;
    }
    class ViewHolder{

        public CheckBox ckb;
    }

}





//     }
//     class ViewHolder{
//         private TextView nameTV;
//         private TextView amountTV;
//         private TextView unitTV;

//         public CheckBox ckb;

//     }
// }
