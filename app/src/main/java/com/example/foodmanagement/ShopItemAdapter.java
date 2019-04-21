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


import java.util.HashMap;
import android.util.Log


public class ShopItemAdapter extends BaseAdapter {

    LayoutInflater shopInflater;
    String[] names;
    int[] amounts;
    String[] units;


    private static HashMap<Integer,Boolean> checked=null;
    public HashMap<Integer, Boolean> getCheckeds() {
        return checkeds;
    }


    public void setCheckeds(HashMap<Integer, Boolean> checkeds) {
        ShopItemAdapter.checkeds = checkeds;
    }


    public ShopItemAdapter(Context c, String[] n, int[] a, String[] u){
        names = n;
        amounts = a;
        units = u;
        shopInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        checkeds=new HashMap<Integer,Boolean>();
        for (int i=0;i<names.size();i++){
            getCheckeds().put(i, false);
        }


    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int position) {
        return names[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
   /* public View getView(int position, View convertView, ViewGroup parent) {*/
    public View getView(int position, View v, ViewGroup parent) {

         View v = shopInflater.inflate(R.layout.shopping_item_detail, null);

        final TextView nameTV = (TextView) v.findViewById(R.id.shopDetailNameTV);
        final TextView amountTV = (TextView) v.findViewById(R.id.shopDetailAmountTV);
        TextView unitTV = (TextView) v.findViewById(R.id.shopDetailUnitTV);
        Button deleteBtn = (Button) v.findViewById(R.id.shopDetailDeleteBtn);


        nameTV.setText(names[position]);
        amountTV.setText(Integer.toString(amounts[position]));
        unitTV.setText(units[position]);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Delete item from shopping list table
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
