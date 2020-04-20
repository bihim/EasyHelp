package com.example.easyhelp.Bloods.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.easyhelp.API.BaseUrl;
import com.example.easyhelp.API.PlaceHolderAPI;
import com.example.easyhelp.Bloods.Item.BloodRequestAPI;
import com.example.easyhelp.MainActivity;
import com.example.easyhelp.R;
import com.google.android.material.appbar.MaterialToolbar;

import es.dmoral.toasty.Toasty;
import life.sabujak.roundedbutton.RoundedButton;
import me.ibrahimsn.lib.CirclesLoadingView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BloodRequestActivity extends AppCompatActivity {

    TextView textViewUserName;
    Spinner spinnerBloodList;
    RoundedButton roundedButtonSubmit;
    MaterialToolbar toolbar;
    String[] bloodGroupList = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
    String bloodGroupSelected = null;
    CirclesLoadingView circlesLoadingView;
    SharedPreferences preferences;

    Retrofit retrofit;
    PlaceHolderAPI placeHolderAPI;
    String baseUrl = new BaseUrl().baseUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_request);
        findViewByAll();
        toolBarMethod(R.id.toolbar_blood_request, "Blood Request");

        roundedButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (bloodGroupSelected == null)
                {
                    Toasty.error(BloodRequestActivity.this, "Select a blood group",Toasty.LENGTH_SHORT,true).show();
                }

                else
                {
                    bloodRequest();
                }
            }
        });

    }


    private void bloodRequest()
    {
        circlesLoadingView.setVisibility(View.VISIBLE);
        retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        placeHolderAPI = retrofit.create(PlaceHolderAPI.class);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Call<BloodRequestAPI> call = placeHolderAPI.getRequestBlood(preferences.getString("user_name", null), bloodGroupSelected);
        call.enqueue(new Callback<BloodRequestAPI>() {
            @Override
            public void onResponse(Call<BloodRequestAPI> call, Response<BloodRequestAPI> response)
            {
                if (!response.isSuccessful())
                {
                    circlesLoadingView.setVisibility(View.INVISIBLE);
                    Toasty.error(BloodRequestActivity.this, "Error Code: " +response.code(),Toasty.LENGTH_SHORT,true).show();
                }

                BloodRequestAPI bloodRequestAPI = response.body();

                int errorCode = bloodRequestAPI.getError();
                String error_string = bloodRequestAPI.getError_report();

                if (errorCode == 0)
                {
                    circlesLoadingView.setVisibility(View.INVISIBLE);
                    Toasty.success(BloodRequestActivity.this, "Blood Request has been granted",Toasty.LENGTH_SHORT,true).show();
                }

                else if (errorCode == 1 || error_string.equals("Do not try more in a month."))
                {
                    circlesLoadingView.setVisibility(View.INVISIBLE);
                    Toasty.error(BloodRequestActivity.this, error_string,Toasty.LENGTH_SHORT,true).show();
                }
            }

            @Override
            public void onFailure(Call<BloodRequestAPI> call, Throwable t)
            {

            }
        });
    }

    private void findViewByAll()
    {
        textViewUserName = findViewById(R.id.blood_request_user_name);
        spinnerBloodList = findViewById(R.id.blood_request_blood_list_spinner);
        roundedButtonSubmit = findViewById(R.id.blood_request_submit);
        circlesLoadingView = findViewById(R.id.blood_request_circle_loading_view);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        textViewUserName.setText(preferences.getString("user_name",null));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bloodGroupList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBloodList.setAdapter(adapter);

        spinnerBloodList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                bloodGroupSelected = bloodGroupList[parent.getSelectedItemPosition()];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                bloodGroupSelected = null;
            }
        });
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

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(BloodRequestActivity.this, MainActivity.class);
                intent.putExtra("id","4");
                startActivity(intent);
                finish();
            }
        });
    }
}
