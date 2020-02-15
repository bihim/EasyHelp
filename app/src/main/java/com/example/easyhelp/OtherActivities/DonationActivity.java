package com.example.easyhelp.OtherActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.easyhelp.R;
import com.google.android.material.appbar.MaterialToolbar;

public class DonationActivity extends AppCompatActivity {


    MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);

        toolbar = findViewById(R.id.toolbar_donation);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}
