package com.example.easyhelp.OtherActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easyhelp.R;
import com.google.android.material.appbar.MaterialToolbar;

import static com.example.easyhelp.NewsThings.NewsAdapter.DESCRIPTION;
import static com.example.easyhelp.NewsThings.NewsAdapter.HEADLINE;
import static com.example.easyhelp.NewsThings.NewsAdapter.IMAGE;
import static com.example.easyhelp.NewsThings.NewsAdapter.TIMEANDDATE;

public class NewsClickedActivity extends AppCompatActivity {

    MaterialToolbar toolbar;

    ImageView imageViewNewsImage;

    TextView textViewNewsHeadline;

    TextView textViewNewsDescription;

    TextView textViewNewsTimeAndDate;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_clicked);

        toolbar = findViewById(R.id.toolbar_news);
        imageViewNewsImage = findViewById(R.id.news_image_clicked);
        textViewNewsHeadline = findViewById(R.id.news_headline_clicked);
        textViewNewsDescription = findViewById(R.id.news_description_clicked);
        textViewNewsTimeAndDate = findViewById(R.id.news_date_clicked);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        Intent intent = getIntent();

        int imageResource = intent.getIntExtra(IMAGE, R.drawable.android);
        String headline = intent.getStringExtra(HEADLINE);
        String description = intent.getStringExtra(DESCRIPTION);
        String timeanddate = intent.getStringExtra(TIMEANDDATE);


        imageViewNewsImage.setImageResource(imageResource);
        textViewNewsHeadline.setText(headline);
        textViewNewsDescription.setText(description);
        textViewNewsTimeAndDate.setText(timeanddate);

    }
}
