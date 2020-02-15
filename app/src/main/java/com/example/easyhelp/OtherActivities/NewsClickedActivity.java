package com.example.easyhelp.OtherActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easyhelp.News.NewsAdapter;
import com.example.easyhelp.R;
import com.google.android.material.appbar.MaterialToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.easyhelp.News.NewsAdapter.DESCRIPTION;
import static com.example.easyhelp.News.NewsAdapter.HEADLINE;
import static com.example.easyhelp.News.NewsAdapter.IMAGE;

public class NewsClickedActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_news)
    MaterialToolbar toolbar;

    @BindView(R.id.news_image_clicked)
    ImageView imageViewNewsImage;

    @BindView(R.id.news_headline_clicked)
    TextView textViewNewsHeadline;

    @BindView(R.id.news_description_clicked)
    TextView textViewNewsDescription;

    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_clicked);
        unbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();

        int imageResource = intent.getIntExtra(IMAGE, R.drawable.android);
        String headline = intent.getStringExtra(HEADLINE);
        String description = intent.getStringExtra(DESCRIPTION);


        imageViewNewsImage.setImageResource(imageResource);
        textViewNewsHeadline.setText(headline);
        textViewNewsDescription.setText(description);

    }
}
