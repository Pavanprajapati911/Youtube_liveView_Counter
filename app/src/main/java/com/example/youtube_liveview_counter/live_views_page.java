package com.example.youtube_liveview_counter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class live_views_page extends AppCompatActivity {

    TextView views,views_1minute,views_2minute,views_3minute,views_4minute;

    TextView calculation;
    RequestQueue queue;
    StringRequest stringRequest;
    Boolean isupdating = true;
    Boolean flag = false;
    Thread updating,one_minute_updating,two_minute_updating,three_minute_updating,four_minute_updating;
    double calculating=0,previous=0,total_last_10sec_val;
    String calculated_value;

    int total_1_minute,total_2_minute,total_3_minute,total_4_minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_views_page);
        views = findViewById(R.id.views);
        views_1minute = findViewById(R.id.views_1minute);
        views_2minute = findViewById(R.id.views_2minute);
        views_3minute = findViewById(R.id.views_3minute);
        views_4minute = findViewById(R.id.views_4minute);
        calculation = findViewById(R.id.calculation);
        queue = Volley.newRequestQueue(live_views_page.this);


        Intent intent = getIntent();
        String REQ_URL = intent.getStringExtra("API_REQ_URL");

        one_minute_calculation();
        two_minute_calculation();
        three_minute_calculation();
        four_minute_calculation();

        updating = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (isupdating) {
                        try {
                            Sending_req(REQ_URL);
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            return;
                        }
                    }
                }
            });

            updating.start();

    }
    public void Sending_req(String api_key){



        stringRequest = new StringRequest(Request.Method.GET, api_key,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //Log.d("checking response", response);
                            JSONObject object = new JSONObject(response);
                            JSONArray arr = object.getJSONArray("items");
                            object = arr.getJSONObject(0);
                            object = object.getJSONObject("statistics");
                            JSONObject new_object = object;
                            calculating = new_object.getDouble("viewCount");
                            if(!flag) {
                                previous = new_object.getDouble("viewCount");
                                flag =true;
                            }

                            total_last_10sec_val = calculating-previous;
                            int added_last_10_sec =(int) total_last_10sec_val;

                            total_1_minute += added_last_10_sec;
                            total_2_minute += added_last_10_sec;
                            total_3_minute += added_last_10_sec;
                            total_4_minute += added_last_10_sec;

                            previous = calculating;

                            if(added_last_10_sec > 0){
                                calculation.setTextColor(Color.parseColor("#15A317"));
                            }else if(added_last_10_sec ==0) {
                                calculation.setTextColor(Color.parseColor("#FFFFFFFF"));
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        views.setText(new_object.getString("viewCount"));
                                        calculated_value = "+ "+added_last_10_sec;
                                        calculation.setText(calculated_value);

                                    } catch (Exception e) {
                                        views.setText("doesnt update");
                                    }
                                }
                            });

                        } catch (JSONException e) {
                            Toast.makeText(live_views_page.this,"reponse problem", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                views.setText("Try again later");
            }
        });

        queue.add(stringRequest);


    }

    public void one_minute_calculation(){
        one_minute_updating = new Thread(new Runnable() {
            @Override
            public void run() {
                while(isupdating){
                    try {
                        Thread.sleep(60000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                views_1minute.setText(String.valueOf(total_1_minute));
                                total_1_minute = 0;
                            }
                        });
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
        });
        one_minute_updating.start();
    }

    public void two_minute_calculation(){
        two_minute_updating = new Thread(new Runnable() {
            @Override
            public void run() {
                while(isupdating){
                    try {
                        Thread.sleep(120000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                views_2minute.setText(String.valueOf(total_2_minute));
                                total_2_minute = 0;
                            }
                        });
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
        });
        two_minute_updating.start();
    }

    public void three_minute_calculation(){
        three_minute_updating = new Thread(new Runnable() {
            @Override
            public void run() {
                while(isupdating){
                    try {
                        Thread.sleep(180000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                views_3minute.setText(String.valueOf(total_3_minute));
                                total_3_minute = 0;
                            }
                        });
                    } catch (Exception e) {
                        return;
                    }
                }
            }
        });
        three_minute_updating.start();
    }

    public void four_minute_calculation(){
        four_minute_updating = new Thread(new Runnable() {
            @Override
            public void run() {
                while(isupdating){
                    try {
                        Thread.sleep(240000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                views_4minute.setText(String.valueOf(total_4_minute));
                                total_4_minute = 0;
                            }
                        });
                    } catch (Exception e) {
                        return;
                    }
                }
            }
        });
        four_minute_updating.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(updating.isAlive()){
            updating.interrupt();
        }
        if(one_minute_updating.isAlive()){
            one_minute_updating.interrupt();
        }
        if(two_minute_updating.isAlive()){
            two_minute_updating.interrupt();
        }
        if(three_minute_updating.isAlive()){
            three_minute_updating.interrupt();
        }
        if(four_minute_updating.isAlive()){
            four_minute_updating.interrupt();
        }
    }

}