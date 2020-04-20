package com.example.easyhelp.Profile.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyhelp.API.BaseUrl;
import com.example.easyhelp.API.PlaceHolderAPI;
import com.example.easyhelp.ChangePassword.Activity.ChangePasswordActivity;
import com.example.easyhelp.OtherActivities.OneTimeOnlyActivity;
import com.example.easyhelp.RegistrationAndLogin.Registration.Activity.GoToLoginRegisterActivity;
import com.example.easyhelp.CustomDialog.CustomDialog;
import com.example.easyhelp.Profile.Adapter.ProfileAdapter;
import com.example.easyhelp.Profile.Item.ProfileItem;
import com.example.easyhelp.Profile.Item.ProfileItemAPI;
import com.example.easyhelp.Profile.Item.Profiles;
import com.example.easyhelp.R;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import life.sabujak.roundedbutton.RoundedButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
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
    Button logoutButton;
    TextView toolbarText;
    SharedPreferences.Editor editor;
    RoundedButton changePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        toolbar = findViewById(R.id.toolbar_profile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        logoutButton = findViewById(R.id.profile_toolbar_logout_button);
        toolbarText = findViewById(R.id.profile_toolbar_text);
        changePassword = findViewById(R.id.change_password_button_profile);
        toolbarText.setText("Profile");
        threadToast(this);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isfacebookUrlExists = preferences.getBoolean("facebook_done", false);

        if (!isfacebookUrlExists)
        {
            startActivity(new Intent(this, OneTimeOnlyActivity.class));
            finish();
        }

        else
        {
            if (!isNetWorkConnectedd())
            {
                Toasty.error(this,"No internet connection is available", Toasty.LENGTH_SHORT, true).show();
            }
            else
            {
                profileJson();
            }
        }
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(ProfileActivity.this, ChangePasswordActivity.class));
            }
        });
    }


    private void profileJson()
    {
        customDialog = new CustomDialog(this);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int userId = Integer.parseInt(preferences.getString("id",null));
        customDialog.showDialog();

        retrofit = new Retrofit.Builder().baseUrl(baseurl).addConverterFactory(GsonConverterFactory.create()).build();
        placeHolderAPI = retrofit.create(PlaceHolderAPI.class);
        Call<ProfileItemAPI> call = placeHolderAPI.getProfileDetails(userId);
        call.enqueue(new Callback<ProfileItemAPI>() {
            @Override
            public void onResponse(Call<ProfileItemAPI> call, Response<ProfileItemAPI> response)
            {
                if (!response.isSuccessful())
                {
                    Toasty.error(ProfileActivity.this, "Error Code: "+response.code(), Toast.LENGTH_SHORT).show();
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
                        toolbarText.setText(items.getDetails()+"'s Profile");
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
                        profileItems.add(new ProfileItem("Facebook", preferences.getString("facebook_url",null))); //Add fb url
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

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                signout();
            }
        });

    }

    private boolean isNetWorkConnectedd()
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
        if (!isNetWorkConnectedd())
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

    private void signout()
    {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        editor.putString("user_name", null);
        editor.putString("password", null);
        editor.putString("category", null);
        editor.putString("id", null);
        editor.putString("name", null);
        editor.putString("user_catagory", null);
        editor.putString("catagory_type", null);
        editor.putString("mobile", null);
        editor.putString("address", null);
        editor.putString("image_url", null);
        editor.putString("institute_name", null);
        editor.putString("facebook_url", null);
        editor.putBoolean("facebook_done", false);
        editor.apply();
        startActivity(new Intent(this, GoToLoginRegisterActivity.class));
        finish();
    }

}
