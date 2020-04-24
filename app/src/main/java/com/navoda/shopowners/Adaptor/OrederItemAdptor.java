package com.navoda.shopowners.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.navoda.shopowners.Modle.OrderItems;
import com.navoda.shopowners.R;

import java.util.List;

public class OrederItemAdptor extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<OrderItems> data;
    private int type;


    public OrederItemAdptor(Context context, List<OrderItems> data, int a) {
        this.context = context;
//        this.layoutInflater = layoutInflater;
        this.data = data;
        this.type = a;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (layoutInflater == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.cart_item, null);
        }

        TextView txt = convertView.findViewById(R.id.txtItemNum);
        TextView name = convertView.findViewById(R.id.txtName);
        TextView quen = convertView.findViewById(R.id.txtQuentity);

        OrderItems obj = data.get(position);
//
        txt.setText(Integer.toString(position + 1));
        name.setText(obj.getProductName());
//        int val =  Integer.valueOf(obj.getQuantity().intValue());
        quen.setText(obj.getQuantity());

        return convertView;
    }
}