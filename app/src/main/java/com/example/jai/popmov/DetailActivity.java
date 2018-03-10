package com.example.jai.popmov;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

public class DetailActivity extends AppCompatActivity {

    String movieTitle;
    TextView title;
    ImageView poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent incomingIntent = getIntent();

        movieTitle = incomingIntent.getStringExtra("title");
        title = findViewById(R.id.title);
        title.setText(movieTitle);

        poster = findViewById(R.id.poster);
        Picasso.get().load(incomingIntent.getStringExtra("picture")).into(poster);
    }
}
