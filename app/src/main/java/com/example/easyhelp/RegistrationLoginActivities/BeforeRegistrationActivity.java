package com.example.easyhelp.RegistrationLoginActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.example.easyhelp.BeforeRegistrationThings.BeforeRegistrationAdapter;
import com.example.easyhelp.BeforeRegistrationThings.BeforeRegistrationItems;
import com.example.easyhelp.R;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class BeforeRegistrationActivity extends AppCompatActivity
{

    /*This activity is for profession choosing. It is complete but registration is incomplete.*/

    RecyclerView beforeRegistrationRecyclerView;
    ArrayList<BeforeRegistrationItems> beforeRegistrationItems;
    BeforeRegistrationAdapter beforeRegistrationAdapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_registration);

        toolBarMethod(R.id.toolbar_before_registration, "Choose your profession");
        recyclerViewThings();
    }

    private void toolBarMethod(int toolbarId, String toolbarText)
    {
        toolbar = findViewById(toolbarId);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(toolbarText);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void recyclerViewThings()
    {
        Context context = this;
        beforeRegistrationRecyclerView = findViewById(R.id.before_registration_recyclerview);
        beforeRegistrationRecyclerView.setHasFixedSize(true);
        beforeRegistrationRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        beforeRegistrationItems = new ArrayList<>();
        beforeRegistrationItems.add(new BeforeRegistrationItems(R.drawable.ic_general, "Helper/General People","0"));
        beforeRegistrationItems.add(new BeforeRegistrationItems(R.drawable.ic_lawyer, "Lawyer","1"));
        beforeRegistrationItems.add(new BeforeRegistrationItems(R.drawable.ic_journalist, "Journalist","2"));
        beforeRegistrationItems.add(new BeforeRegistrationItems(R.drawable.ic_policeman, "Police","3"));

        beforeRegistrationAdapter = new BeforeRegistrationAdapter(beforeRegistrationItems, context);
        beforeRegistrationRecyclerView.setAdapter(beforeRegistrationAdapter);

    }
}
