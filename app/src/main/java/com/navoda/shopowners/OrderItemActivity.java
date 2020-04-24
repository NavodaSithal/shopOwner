package com.navoda.shopowners;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.navoda.shopowners.Adaptor.OrederItemAdptor;
import com.navoda.shopowners.Modle.MainOrder;
import com.navoda.shopowners.Modle.Merchant;

import org.json.JSONException;
import org.json.JSONObject;

public class OrderItemActivity extends AppCompatActivity {

    TextView shopName , orderId , refNo , status;
    ListView list;
    MainOrder obj;
    ProgressDialog progressDialog;
    Button cancle , confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_item);

        orderId = findViewById(R.id.txt_order_id);
        refNo = findViewById(R.id.txt_order_ref);
        status = findViewById(R.id.txt_status_order);
        list = findViewById(R.id.order_products_list);

        cancle = findViewById(R.id.btncancle);
        confirm = findViewById(R.id.btnaccept);

        Intent i = getIntent();
        String shop = i.getStringExtra("ORDER");
        Gson gson = new Gson();
        obj = gson.fromJson(shop, MainOrder.class);

        orderId.setText(Integer.valueOf(obj.getOrderID()).toString());
//        status.setText(obj.getStatus());

        OrederItemAdptor adapter = new OrederItemAdptor(this, obj.getOrderList(),1);
        list.setAdapter(adapter);
    }

    public void onClickCancle(View view) {
        orderPickup("CANCEL");
    }

    public void ontapAccept(View view) {
        if (obj.getStatus() == "MERCHANT_CONFIRMED"){

        }else if (obj.getStatus() == "INITIATED"){
            orderPickup("CONFIRM");
        }
    }

    public void orderFinish(String status){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("....");
        progressDialog.show();

        String url = "http://"+SubUrl.subUrl+".ngrok.io/grocery-core/api/merchant/"+getCustomerId()+"/customer-pickup/"+orderId;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
//                goToNext();
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(OrderItemActivity.this, "112121212" , Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    public void orderPickup(String status){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("....");
        progressDialog.show();

        JSONObject jsonBody = new JSONObject();

        try {
            jsonBody.put("orderID", obj.getOrderID());
            jsonBody.put("status", status);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = "http://"+SubUrl.subUrl+".ngrok.io/grocery-core/api/merchant/"+getCustomerId()+"/confirm-order";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
//                goToNext();
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(OrderItemActivity.this, "112121212" , Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    public int getCustomerId(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = preferences.getString("MERCHANT", "");
        Merchant obj = gson.fromJson(json, Merchant.class);

        int id = obj.getId();
        return id;
    }

}
