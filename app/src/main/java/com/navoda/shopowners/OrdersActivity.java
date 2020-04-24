package com.navoda.shopowners;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.navoda.shopowners.Adaptor.MyOrderAdaptor;
import com.navoda.shopowners.Modle.MainOrder;
import com.navoda.shopowners.Modle.Merchant;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends AppCompatActivity {
    ListView orderList;
    ProgressDialog progressDialog;
    List<MainOrder> dataM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        orderList = findViewById(R.id.order_list);
        dataM = new ArrayList<>();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        orderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goToList(position);
            }
        });

        String url = "http://"+SubUrl.subUrl+".ngrok.io/grocery-core/api/customer/"+getCustomerId()+"/get-orders";
        getMyOrders(url);

    }

    public void setData(){
        MyOrderAdaptor adapter = new MyOrderAdaptor(this,dataM,0);
        orderList.setAdapter(adapter);
    }

    public void getMyOrders(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressDialog.dismiss();
                for (int i = 0; i < response.length(); i++) {
                    Gson gson = new Gson();
                    MainOrder data = null;
                    try {
                        data = gson.fromJson(String.valueOf(response.get(i)), MainOrder.class);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    dataM.add(data);
                }

                setData();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    public void goToList(int position){
        MainOrder obj = dataM.get(position);
        Gson gson = new Gson();
        String json = gson.toJson(obj);

        Intent i = new Intent(this,OrderItemActivity.class);
        i.putExtra("ORDER" , json);
        startActivity(i);
    }

    public int getCustomerId(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = preferences.getString("MERCHANT", "");
        Merchant obj = gson.fromJson(json, Merchant.class);

        int id = obj.getId();
        return id;
    }

    public void onTapPending(View view) {
        String url = "http://"+SubUrl.subUrl+".ngrok.io/grocery-core/api/customer/"+getCustomerId()+"/get-orders";
        getMyOrders(url);
    }

    public void ontapAll(View view) {
        String url = "http://"+SubUrl.subUrl+".ngrok.io/grocery-core/api/customer/"+getCustomerId()+"/get-all-orders";
        getMyOrders(url);
    }
}
