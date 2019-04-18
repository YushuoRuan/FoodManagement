package com.example.foodmanagement;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemAdapter extends BaseAdapter {

    LayoutInflater mInflater;

    String[] names;
    String[] amounts;
    String[] expires;
    String[] storages;
    String[] units;

    public ItemAdapter(Context c, ArrayList<Ingredient> ingredients){
        names = new String[ingredients.size()];
        amounts = new String[ingredients.size()];
        expires = new String[ingredients.size()];
        storages = new String[ingredients.size()];
        units = new String[ingredients.size()];
        for(int i = 0; i<ingredients.size(); i++){
            names[i] = ingredients.get(i).getMaterial();
            amounts[i] = Double.toString(ingredients.get(i).getAmount());
            expires[i] = ingredients.get(i).getExpiredDate();
            storages[i] = ingredients.get(i).getStorage();
            units[i] = ingredients.get(i).getUnit();
        }
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
        //return ingredients.get(position).ID;
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
        String amount =amounts[position];
        String expire = expires[position];
        String storage = storages[position];
        String unit = units[position];

        nameTextView.setText(name);
        amountTextView.setText(amount + " " + unit);
        descriptionTextView.setText(expire);
        storageTextView.setText(storage);

        return v;
    }
}
