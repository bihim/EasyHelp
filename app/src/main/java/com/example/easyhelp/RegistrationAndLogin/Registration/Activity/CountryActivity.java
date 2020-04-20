package com.example.easyhelp.RegistrationAndLogin.Registration.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.easyhelp.API.BaseUrl;
import com.example.easyhelp.API.PlaceHolderAPI;
import com.example.easyhelp.CustomDialog.CustomDialog;
import com.example.easyhelp.R;
import com.example.easyhelp.RegistrationAndLogin.Registration.Adapter.CountryAdapter;
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

public class CountryActivity extends AppCompatActivity {


    CountryAdapter countryAdapter;
    RecyclerView recyclerView;
    ArrayList<CountryItems> arrayList;
    Retrofit retrofit;
    PlaceHolderAPI placeHolderAPI;
    SharedPreferences preferences;
    String baseUrl = new BaseUrl().baseUrl;
    CustomDialog customDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        recyclerView = findViewById(R.id.country_recycler);
        customDialog = new CustomDialog(this);
        customDialog.showDialog();

        retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        placeHolderAPI = retrofit.create(PlaceHolderAPI.class);

        Call<CountryDivisionDistrictThana> call = placeHolderAPI.getCountryList("");
        call.enqueue(new Callback<CountryDivisionDistrictThana>() {
            @Override
            public void onResponse(Call<CountryDivisionDistrictThana> call, Response<CountryDivisionDistrictThana> response)
            {
                if (response.isSuccessful())
                {
                    customDialog.hideDialog();
                    CountryDivisionDistrictThana countryDivisionDistrictThana = response.body();

                    int errorCode = countryDivisionDistrictThana.getError();

                    if (errorCode == 0)
                    {
                        arrayList = new ArrayList<>();
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(CountryActivity.this));
                        List<Report> reports = countryDivisionDistrictThana.getReport();

                        for (Report report: reports)
                        {
                            arrayList.add(new CountryItems(report.getName(), report.getCountryId()));
                        }

                        countryAdapter = new CountryAdapter(arrayList, CountryActivity.this);
                        recyclerView.setAdapter(countryAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<CountryDivisionDistrictThana> call, Throwable t) {

            }
        });


    }
}
