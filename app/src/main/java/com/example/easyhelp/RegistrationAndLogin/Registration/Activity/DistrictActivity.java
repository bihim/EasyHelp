package com.example.easyhelp.RegistrationAndLogin.Registration.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.easyhelp.API.BaseUrl;
import com.example.easyhelp.API.PlaceHolderAPI;
import com.example.easyhelp.CustomDialog.CustomDialog;
import com.example.easyhelp.R;
import com.example.easyhelp.RegistrationAndLogin.Registration.Adapter.DistrictAdapter;
import com.example.easyhelp.RegistrationAndLogin.Registration.Item.CountryItems;
import com.example.easyhelp.RegistrationAndLogin.Registration.Item.CountryDivisionDistrictThana;
import com.example.easyhelp.RegistrationAndLogin.Registration.Item.Report;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DistrictActivity extends AppCompatActivity
{
    RecyclerView recyclerView;
    ArrayList<CountryItems> arrayList;
    Retrofit retrofit;
    PlaceHolderAPI placeHolderAPI;
    SharedPreferences preferences;
    String baseUrl = new BaseUrl().baseUrl;
    CustomDialog customDialog;

    DistrictAdapter districtAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district);

        recyclerView = findViewById(R.id.country_division_district_recycler);
        customDialog = new CustomDialog(this);
        customDialog.showDialog();

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String countryId = preferences.getString("country","947");
        String divisionId = preferences.getString("division", "1");

        retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        placeHolderAPI = retrofit.create(PlaceHolderAPI.class);

        Call<CountryDivisionDistrictThana> call = placeHolderAPI.getDistrict("", countryId, divisionId);
        call.enqueue(new Callback<CountryDivisionDistrictThana>() {
            @Override
            public void onResponse(Call<CountryDivisionDistrictThana> call, Response<CountryDivisionDistrictThana> response)
            {
                if (response.isSuccessful())
                {
                    customDialog.hideDialog();
                    CountryDivisionDistrictThana countryDivisionDistrictThana = response.body();

                    int errorCode = countryDivisionDistrictThana.getError();

                    if(errorCode == 0)
                    {
                        arrayList = new ArrayList<>();
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(DistrictActivity.this));
                        List<Report> reports = countryDivisionDistrictThana.getReport();

                        for (Report report : reports)
                        {
                            Log.d("BAAAALAL", "onResponse: "+report.getName());
                            arrayList.add(new CountryItems(report.getName(), report.getDistrictId()));
                        }

                        districtAdapter = new DistrictAdapter(arrayList, DistrictActivity.this);
                        recyclerView.setAdapter(districtAdapter);

                    }
                }
            }

            @Override
            public void onFailure(Call<CountryDivisionDistrictThana> call, Throwable t) {

            }
        });


    }
}
