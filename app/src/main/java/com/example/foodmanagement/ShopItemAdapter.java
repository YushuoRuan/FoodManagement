package com.example.foodmanagement;

import android.content.Context;
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

//    private static HashMap<Integer,Boolean> checked=null;
//    public HashMap<Integer, Boolean> getCheckeds() {
//        return checkeds;
//    }
//
//
//    public void setCheckeds(HashMap<Integer, Boolean> checkeds) {
//        ShopItemAdapter.checkeds = checkeds;
//    }


    public ShopItemAdapter(Context c, ArrayList<Ingredient> i){

        shopInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ingredients = i;

        myDb = new DatabaseHelper(c);

//        checkeds=new HashMap<Integer,Boolean>();
//        for (int i=0;i<names.size();i++){
//            getCheckeds().put(i, false);
//        }


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
        TextView unitTV = (TextView) v.findViewById(R.id.shopDetailUnitTV);
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


        return v;
    }
}






//         ViewHolder holder = null;

//         if(holder ==  null){
//             holder =new ViewHolder();
//             if( v== null){

//                  View v = shopInflater(R.layout.shopping_item_detail,null);
//             }

//             holder.nameTV = (TextView) v.findViewById(R.id.shopDetailNameTV);
//             holder.amountTV = (TextView) v.findViewById(R.id.shopDetailAmountTV);
//             holder.unitTV = (TextView) v.findViewById(R.id.shopDetailUnitTV);

//             holder.ckb= (CheckBox) v.findViewById(R.id.checkBox);
//             v.setText(holder);
//         }else{
//             holder = (ViewHolder) v.getText();
//         }

//         holder.nameTV.setText(names.get(position));
//         holder.amountTV.setText(Integer.toString(amounts[position]));
//         holder.unitTV.setText(units.get(position));

//         holder.ckb.setChecked(getCheckeds().get(position));
//         return v;


//     }
//     class ViewHolder{
//         private TextView nameTV;
//         private TextView amountTV;
//         private TextView unitTV;

//         public CheckBox ckb;

//     }
// }
