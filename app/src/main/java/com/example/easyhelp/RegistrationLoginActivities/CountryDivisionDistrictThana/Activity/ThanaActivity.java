package com.example.easyhelp.RegistrationLoginActivities.CountryDivisionDistrictThana.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.example.easyhelp.API.BaseUrl;
import com.example.easyhelp.API.PlaceHolderAPI;
import com.example.easyhelp.CustomDialog.CustomDialog;
import com.example.easyhelp.R;
import com.example.easyhelp.RegistrationLoginActivities.CountryDivisionDistrictThana.Adapter.ThanaAdapter;
import com.example.easyhelp.RegistrationLoginActivities.CountryDivisionDistrictThana.Items.CountryItems;
import com.example.easyhelp.RegistrationThings.CountryDivisionDistrictThana;
import com.example.easyhelp.RegistrationThings.Report;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ThanaActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<CountryItems> arrayList;
    Retrofit retrofit;
    PlaceHolderAPI placeHolderAPI;
    SharedPreferences preferences;
    String baseUrl = new BaseUrl().baseUrl;
    CustomDialog customDialog;

    ThanaAdapter thanaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thana);

        recyclerView = findViewById(R.id.country_division_district_thana_recycler);
        customDialog = new CustomDialog(this);
        customDialog.showDialog();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String countryId = preferences.getString("country","947");
        String divisionId = preferences.getString("division", "1");
        String districtId = preferences.getString("district", "27");

        retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        placeHolderAPI = retrofit.create(PlaceHolderAPI.class);

        Call<CountryDivisionDistrictThana> call = placeHolderAPI.getThana("", countryId, divisionId, districtId);
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
                        recyclerView.setLayoutManager(new LinearLayoutManager(ThanaActivity.this));
                        List<Report> reports = countryDivisionDistrictThana.getReport();

                        for (Report report : reports)
                        {
                            arrayList.add(new CountryItems(report.getName(), report.getThanaId()));
                        }

                        thanaAdapter = new ThanaAdapter(arrayList, ThanaActivity.this);
                        recyclerView.setAdapter(thanaAdapter);

                    }
                }
            }

            @Override
            public void onFailure(Call<CountryDivisionDistrictThana> call, Throwable t) {

            }
        });
    }
}
