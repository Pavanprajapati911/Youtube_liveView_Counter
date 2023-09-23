package com.example.youtube_liveview_counter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Queue;

public class live_likes_page extends AppCompatActivity {
    TextView Total_Likes;

    RequestQueue queue;

    StringRequest stringRequest;
    int total_likes_on_video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_likes_page);
        Total_Likes = findViewById(R.id.total_likes);

        queue = Volley.newRequestQueue(live_likes_page.this);

        Intent intent = getIntent();
        String REQ_URL = intent.getStringExtra("API_REQ_URL");

        stringRequest = new StringRequest(Request.Method.GET, REQ_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject object = new JSONObject(response);
                            JSONArray arr = object.getJSONArray("items");
                            object = arr.getJSONObject(0);
                            object = object.getJSONObject("statistics");
                            total_likes_on_video = object.getInt("likeCount");
                            Total_Likes.setText(String.valueOf(total_likes_on_video));

                        }catch (Exception e){
                            Toast.makeText(live_likes_page.this,"error Try again later",Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Total_Likes.setText("Try again later");
            }
        });

        queue.add(stringRequest);


    }
}












