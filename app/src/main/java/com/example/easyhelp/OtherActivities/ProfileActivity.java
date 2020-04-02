package com.example.easyhelp.OtherActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.example.easyhelp.Profilethings.ProfileAdapter;
import com.example.easyhelp.Profilethings.ProfileItem;
import com.example.easyhelp.R;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    MaterialToolbar toolbar;
    SharedPreferences preferences;
    RecyclerView profileRecyclerView;
    ArrayList<ProfileItem> profileItems;
    ProfileAdapter profileAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        toolbar = findViewById(R.id.toolbar_profile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Profile");

        profileDetails();
    }

    private void profileDetails()
    {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        profileRecyclerView = findViewById(R.id.profile_details_recyclerview);
        profileRecyclerView.setHasFixedSize(true);
        profileRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        profileItems = new ArrayList<>();

        profileItems.add(new ProfileItem("ID", preferences.getString("id",null)));
        profileItems.add(new ProfileItem("Name", preferences.getString("name",null)));
        profileItems.add(new ProfileItem("User Category", preferences.getString("user_catagory",null)));
        profileItems.add(new ProfileItem("Category Type", preferences.getString("catagory_type",null)));
        profileItems.add(new ProfileItem("Mobile", preferences.getString("mobile",null)));
        profileItems.add(new ProfileItem("Address", preferences.getString("address",null)));
        profileItems.add(new ProfileItem("Institute Name", preferences.getString("institute_name",null)));

        profileAdapter = new ProfileAdapter(profileItems, this);
        profileRecyclerView.setAdapter(profileAdapter);
    }
}
