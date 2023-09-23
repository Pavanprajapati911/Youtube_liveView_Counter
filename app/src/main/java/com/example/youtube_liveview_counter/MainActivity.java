package com.example.youtube_liveview_counter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;




public class MainActivity extends AppCompatActivity {
    CardView Live_view;

    CardView Tags;
    CardView Likes;

    CardView Dislikes;

    CardView Subscribers;

    CardView Comments;
    EditText Video_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Live_view = findViewById(R.id.live_views);
        Likes = findViewById(R.id.likes);
        Video_url= findViewById(R.id.video_url);
        Dislikes = findViewById(R.id.dislikes);
        Subscribers = findViewById(R.id.subscribers);
        Tags = findViewById(R.id.tags);
        Comments = findViewById(R.id.comments);
        String API_KEY = "YOUR YOUTUBE API KEY";

        Live_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url_value,Video_id;
                int duration = Toast.LENGTH_LONG;
                url_value = Video_url.getText().toString();
                //url_value = "https://youtu.be/Pyn4f8VQMkc?si=vF8sukVLgFA7bwh3";

                if(url_value.matches("")){
                    Toast.makeText(getApplicationContext(),"enter url first \n   then click", duration).show();
                }
                else{
                    if(url_value.contains("shorts")){
                        Toast.makeText(getApplicationContext(),"shorts videos not allowed \n feature coming soon  ", duration).show();
                    }else {
                        try {
                            Seperate_VideoId seperation = new Seperate_VideoId();
                            Video_id = seperation.getVideoId(url_value);
                            String API_REQ = "https://www.googleapis.com/youtube/v3/videos?id=" + Video_id + "&key=" + API_KEY + "-v7Qas&fields=items(id,statistics(viewCount))&part=snippet,statistics";

                            Intent intent = new Intent(MainActivity.this,live_views_page.class);
                            intent.putExtra("API_REQ_URL",API_REQ);

                            startActivity(intent);

                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(),"check url",duration).show();
                        }

                    }

                }
            }
        });

        // navigating to Dislikes page --------------------------------------------------------------------------

        Dislikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"Youtube has removed this\n               feature",Toast.LENGTH_LONG).show();
            }
        });

        // navigating to Likes page -----------------------------------------------------------------------------------

        Likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url_value,Video_id;
                url_value = Video_url.getText().toString();
                //url_value = "https://youtu.be/Pyn4f8VQMkc?si=vF8sukVLgFA7bwh3";


                if(url_value.matches("")){
                    Toast.makeText(getApplicationContext(),"enter url first \n   then click", Toast.LENGTH_LONG).show();
                }
                else{
                    if(url_value.contains("shorts")){
                        Toast.makeText(getApplicationContext(),"shorts videos not allowed \n feature coming soon  ", Toast.LENGTH_LONG).show();
                    }else {
                        try {
                            Seperate_VideoId seperation = new Seperate_VideoId();
                            Video_id = seperation.getVideoId(url_value);
                            String API_LIKES_REQ_URL = "https://www.googleapis.com/youtube/v3/videos?id=" + Video_id + "&key=" + API_KEY + "-v7Qas&fields=items(id,statistics(likeCount))&part=snippet,statistics";

                            Intent intent = new Intent(MainActivity.this,live_likes_page.class);
                            intent.putExtra("API_REQ_URL",API_LIKES_REQ_URL);

                            startActivity(intent);

                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(),"check url",Toast.LENGTH_LONG).show();
                        }

                    }

                }

            }
        });
        // -------------------------------------------------------------------------------------------------------

        Comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"Feature not added yet",Toast.LENGTH_LONG).show();
            }
        });

        // navigating to Subscribers activity------------------------------------------------------------------------------------------------------

        Subscribers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"Feature not added yet",Toast.LENGTH_LONG).show();
            }
        });

        // navigating to Tags activity ----------------------------------------------------------------------------------------
        Tags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"feature not added yet",Toast.LENGTH_LONG).show();
            }
        });

        //----------------------------------------------------------------------------
    }

}

//---------------------------------------------------------------------------------------------------------------------------
class Seperate_VideoId{
    public String getVideoId(String url){
        String id;
        if(url.contains("watch")){
            String[] arr;
            arr = url.split("v=",-2);
            id = arr[1];

        }
    else {
            String[] str;
            str = url.split("e/", -2);
            id = str[1];
            str = id.split("si=", -2);
            id = str[0].replace("?", "");
        }
        return id;
    }
}
