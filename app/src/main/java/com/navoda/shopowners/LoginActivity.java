package com.navoda.shopowners;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.navoda.shopowners.Modle.Merchant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    EditText username , psw;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        psw = findViewById(R.id.psw);

//        readFile();
    }


    private String readFile() {
        File fileEvents = new File(LoginActivity.this.getFilesDir()+"/text/sample");
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileEvents));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
//                text.append('\n');
            }

            br.close();
        } catch (IOException e) { }
        String result = text.toString();
        SubUrl.subUrl = result;
        return  result;
    }






    public void onTapLoginBtn(View view) {

//        String user = username.getText().toString();
//        String pass = psw.getText().toString();

//        gotoNext();


        String user = "merchant";
        String pass = "user";
//
        if (user.isEmpty()){
            username.setError("Enter username");
        }else if (pass.isEmpty()){
            psw.setError("Enter Password");
        }else if (readFile().isEmpty()){
            Toast.makeText(LoginActivity.this, "URL missing" , Toast.LENGTH_SHORT).show();
        }

        else{
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Logging....");
            progressDialog.show();

            JSONObject jsonBody = new JSONObject();

            try {
                jsonBody.put("username", user);
                jsonBody.put("password", pass);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String url = "http://"+SubUrl.subUrl+".ngrok.io/grocery-core/api/merchant/login";

//            String url = "http://lahiruat-29044.portmap.io:29044/grocery-core/api/customer/login";
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new com.android.volley.Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progressDialog.dismiss();
                    Gson gson = new Gson();
                    Merchant data = gson.fromJson(String.valueOf(response),Merchant.class);
                    saveMerchant(data);
                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "112121212" , Toast.LENGTH_SHORT).show();
                }
            });

            requestQueue.add(jsonObjectRequest);
        }




    }


    public void saveMerchant(Merchant obj){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(obj);

        editor.putString("MERCHANT", json);
        editor.apply();

        gotoNext();


    }



    public void gotoNext(){
        Intent i = new Intent(this, OrdersActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }


    public void setUrl(View view) {
        Intent i = new Intent(this, SetUrlActivity.class);
        startActivity(i);
    }
}
