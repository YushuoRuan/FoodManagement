package com.example.foodmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ShopItemAdapter extends BaseAdapter {

    LayoutInflater shopInflater;
    String[] names;
    int[] amounts;
    String[] units;

    public ShopItemAdapter(Context c, String[] n, int[] a, String[] u){
        names = n;
        amounts = a;
        units = u;
        shopInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = shopInflater.inflate(R.layout.shopping_item_detail, null);

        final TextView nameTV = (TextView) v.findViewById(R.id.shopDetailNameTV);
        final TextView amountTV = (TextView) v.findViewById(R.id.shopDetailAmountTV);
        TextView unitTV = (TextView) v.findViewById(R.id.shopDetailUnitTV);
        Button deleteBtn = (Button) v.findViewById(R.id.shopDetailDeleteBtn);

        //ImageView checkImg = (ImageView) v.findViewById(R.id.checkImageView);


        nameTV.setText(names[position]);
        amountTV.setText(Integer.toString(amounts[position]));
        unitTV.setText(units[position]);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Delete item from shopping list table
            }
        });
//        deleteBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //delete shopping item from shopping list and database
//
//                nameTV.setText("clicked!");
//            }
//        });

        return v;
    }
}
