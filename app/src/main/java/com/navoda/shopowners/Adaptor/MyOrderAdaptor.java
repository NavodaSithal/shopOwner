package com.navoda.shopowners.Adaptor;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.navoda.shopowners.Modle.MainOrder;
import com.navoda.shopowners.R;

import java.util.List;

public class MyOrderAdaptor extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<MainOrder> data;

    private int type;
    public MyOrderAdaptor(Context context, List<MainOrder> data, int a){
        this.context = context;
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
        if (layoutInflater == null){
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.my_order_item, null);
        }

        MainOrder obj  = data.get(position);

        TextView txt = convertView.findViewById(R.id.txt_status);
        TextView price = convertView.findViewById(R.id.txt_price);
        TextView refNo = convertView.findViewById(R.id.txt_ref_no);

        Drawable background = txt.getBackground();


//        if (obj.getStatus() == ""){
//            if (background instanceof ShapeDrawable) {
//                ((ShapeDrawable)background).getPaint().setColor(ContextCompat.getColor(context,R.color.white));
//            }
//        }

        txt.setText("Pending");
        price.setText(Integer.valueOf(obj.getOrderID()).toString());
//        shop.setText(obj.getShopName());
        refNo.setText(String.valueOf(obj.getTotalValue()));

        return convertView;
    }
}
