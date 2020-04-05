package com.example.easyhelp.OtherActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.example.easyhelp.API.BaseUrl;
import com.example.easyhelp.API.PlaceHolderAPI;
import com.example.easyhelp.CustomDialog.CustomDialog;
import com.example.easyhelp.Profilethings.ProfileAdapter;
import com.example.easyhelp.Profilethings.ProfileItem;
import com.example.easyhelp.Profilethings.ProfileItemAPI;
import com.example.easyhelp.Profilethings.Profiles;
import com.example.easyhelp.R;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileActivity extends AppCompatActivity {

    MaterialToolbar toolbar;
    RecyclerView profileRecyclerView;
    ArrayList<ProfileItem> profileItems;
    ProfileAdapter profileAdapter;
    String baseurl = new BaseUrl().baseUrl;
    Retrofit retrofit;
    PlaceHolderAPI placeHolderAPI;
    SharedPreferences preferences;
    CustomDialog customDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        toolbar = findViewById(R.id.toolbar_profile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Profile");
        threadToast(this);
        if (!isNetWorkConnected())
        {
            Toasty.error(this,"No internet connection is available", Toasty.LENGTH_SHORT, true).show();
        }
        else
        {
            profileJson();
        }
    }


    private void profileJson()
    {
        customDialog = new CustomDialog(this);
        retrofit = new Retrofit.Builder().baseUrl(baseurl).addConverterFactory(GsonConverterFactory.create()).build();
        placeHolderAPI = retrofit.create(PlaceHolderAPI.class);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int userId = Integer.parseInt(preferences.getString("id",null));

        customDialog.showDialog();
        Call<ProfileItemAPI> call = placeHolderAPI.getProfileDetails(userId);
        call.enqueue(new Callback<ProfileItemAPI>() {
            @Override
            public void onResponse(Call<ProfileItemAPI> call, Response<ProfileItemAPI> response)
            {
                if (!response.isSuccessful())
                {
                    Toast.makeText(ProfileActivity.this, "Error Code: "+response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                ProfileItemAPI profileItemAPI = response.body();
                //
                profileItems = new ArrayList<>();
                preferences = PreferenceManager.getDefaultSharedPreferences(ProfileActivity.this);
                profileRecyclerView = findViewById(R.id.profile_details_recyclerview);
                profileRecyclerView.setHasFixedSize(true);
                profileRecyclerView.setLayoutManager(new LinearLayoutManager(ProfileActivity.this));
                //
                /*
                * from here json array part starts
                */
                List<Profiles> profiles = profileItemAPI.getProfiles();
                for (Profiles items: profiles)
                {
                    if (items.getTitle().equals("User"))
                    {
                        profileItems.add(new ProfileItem("User", items.getDetails()));
                        Log.d("PROFILE_API", "onResponse: "+items.getDetails());
                    }

                    if (items.getTitle().equals("Name"))
                    {
                        profileItems.add(new ProfileItem("Name", items.getDetails()));
                    }

                    if (items.getTitle().equals("Mobile"))
                    {
                        profileItems.add(new ProfileItem("Mobile", items.getDetails()));
                    }

                    if (items.getTitle().equals("Account Type"))
                    {
                        profileItems.add(new ProfileItem("Account Type", items.getDetails()));
                    }

                    if (items.getTitle().equals("Email"))
                    {
                        profileItems.add(new ProfileItem("Email", items.getDetails()));
                    }

                    if (items.getTitle().equals("Rank"))
                    {
                        profileItems.add(new ProfileItem("Rank", items.getDetails()));
                    }

                    if (items.getTitle().equals("Gender"))
                    {
                        profileItems.add(new ProfileItem("Gender", items.getDetails()));
                    }

                    if (items.getTitle().equals("NID\\/B.Certificate"))
                    {
                        profileItems.add(new ProfileItem("NID or Birth Certificate", items.getDetails()));
                    }

                    if (items.getTitle().equals("Date of Birth"))
                    {
                        profileItems.add(new ProfileItem("Date of Birth", items.getDetails()));
                    }

                    if (items.getTitle().equals("Facebook"))
                    {
                        profileItems.add(new ProfileItem("Facebook", items.getDetails()));
                    }

                    if (items.getTitle().equals("Blood Group"))
                    {
                        profileItems.add(new ProfileItem("Blood Group", items.getDetails()));
                    }

                    if (items.getTitle().equals("Father Name"))
                    {
                        profileItems.add(new ProfileItem("Father Name", items.getDetails()));
                    }

                    if (items.getTitle().equals("Mother Name"))
                    {
                        profileItems.add(new ProfileItem("Mother Name", items.getDetails()));
                    }

                    if (items.getTitle().equals("My Join Age"))
                    {
                        profileItems.add(new ProfileItem("My Join Age", items.getDetails()));
                    }

                    if (items.getTitle().equals("Join"))
                    {
                        profileItems.add(new ProfileItem("Join", items.getDetails()));
                    }

                    if (items.getTitle().equals("Address"))
                    {
                        profileItems.add(new ProfileItem("Address", items.getDetails()));
                    }

                    if (items.getTitle().equals("Father Name"))
                    {
                        profileItems.add(new ProfileItem("Father Name", items.getDetails()));
                    }

                    if (items.getTitle().equals("Father Name"))
                    {
                        profileItems.add(new ProfileItem("Father Name", items.getDetails()));
                    }

                    //16 Items
                }

                /*
                 * It ends here
                 */

                profileItems.add(new ProfileItem("Company Name", profileItemAPI.getCompany_name()));
                profileItems.add(new ProfileItem("Company Phone", profileItemAPI.getCompany_phone()));
                profileItems.add(new ProfileItem("Company Address", profileItemAPI.getCompany_address()));

                profileAdapter = new ProfileAdapter(profileItems, ProfileActivity.this);
                profileRecyclerView.setAdapter(profileAdapter);
                customDialog.hideDialog();
            }

            @Override
            public void onFailure(Call<ProfileItemAPI> call, Throwable t) {

            }
        });

    }

    private boolean isNetWorkConnected()
    {
        boolean connectedOrNot = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null)
        {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
            {
                connectedOrNot = true;
            }

            else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
            {
                connectedOrNot = true;
            }

        }

        else
        {
            connectedOrNot = false;
        }

        return connectedOrNot;
    }

    private void threadToast(Context context)
    {
        if (!isNetWorkConnected())
        {
            Thread t = new Thread() {

                @Override
                public void run() {
                    try {
                        while (!isInterrupted()) {
                            Thread.sleep(600000);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run()
                                {
                                    Toasty.error(context,"No net connection is available", Toasty.LENGTH_SHORT, true).show();
                                }
                            });
                        }
                    } catch (InterruptedException e) {
                    }
                }
            };


            t.start();
        }
    }

}
