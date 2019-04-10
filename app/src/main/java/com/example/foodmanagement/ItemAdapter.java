package com.example.foodmanagement;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemAdapter extends BaseAdapter {

    LayoutInflater mInflater;
    String[] names;
    Integer[] amounts;
    String[] units;
    String[] descriptions;
    String[] storages;

    public ItemAdapter(Context c, String[] n, Integer[] a, String[] d, String[] s, String[] u){
        names = n;
        amounts = a;
        descriptions = d;
        storages = s;
        units = u;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = mInflater.inflate(R.layout.inventory_item_detail,null);
        TextView nameTextView = (TextView) v.findViewById(R.id.nameTV);
        TextView amountTextView = (TextView) v.findViewById(R.id.amountTV);
        TextView descriptionTextView = (TextView) v.findViewById(R.id.descriptionTV);
        TextView storageTextView = (TextView) v.findViewById(R.id.storageTV);
        ImageView foodTypeImg = (ImageView) v.findViewById(R.id.foodTypeImg);


        String name = names[position];
        String amount = Integer.toString(amounts[position]);
        String desc = descriptions[position];
        String storage = storages[position];
        String unit = units[position];

        nameTextView.setText(name);
        amountTextView.setText(amount + " " + unit);
        descriptionTextView.setText(desc);
        storageTextView.setText(storage);

        return v;
    }
}
