package com.example.easyhelp.RegistrationAndLogin.Login.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.example.easyhelp.API.BaseUrl;
import com.example.easyhelp.API.PlaceHolderAPI;
import com.example.easyhelp.RegistrationAndLogin.Login.Item.LoginAPIElements;
import com.example.easyhelp.MainActivity;
import com.example.easyhelp.R;
import com.example.easyhelp.RegistrationAndLogin.Registration.Activity.GoToLoginRegisterActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoadingActivity extends AppCompatActivity {

    Retrofit retrofit;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    PlaceHolderAPI placeHolderAPI;
    String baseUrl = new BaseUrl().baseUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        String userName = preferences.getString("user_name", null);
        String password = preferences.getString("password",null);

        retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        placeHolderAPI = retrofit.create(PlaceHolderAPI.class);

        Call<LoginAPIElements> call = placeHolderAPI.getLoginInfo(userName, password, "");
        call.enqueue(new Callback<LoginAPIElements>() {
            @Override
            public void onResponse(Call<LoginAPIElements> call, Response<LoginAPIElements> response)
            {
                if (response.isSuccessful())
                {
                    LoginAPIElements loginAPIElements = response.body();
                    int errorCode = loginAPIElements.getError();

                    if (errorCode == 0)
                    {
                        preferences = PreferenceManager.getDefaultSharedPreferences(LoadingActivity.this);
                        editor = preferences.edit();

                        editor.putString("user_name", userName);
                        editor.putString("password", password);
                        editor.putString("id", loginAPIElements.getId());
                        editor.putString("name", loginAPIElements.getName());
                        editor.putString("user_catagory", loginAPIElements.getUser_catagory());
                        editor.putString("catagory_type", loginAPIElements.getCatagory_type());
                        editor.putString("mobile", loginAPIElements.getMobile());
                        editor.putString("address", loginAPIElements.getAddress());
                        editor.putString("image_url", loginAPIElements.getImage_url());
                        editor.putString("institute_name", loginAPIElements.getInstitute_name());
                        editor.putString("facebook_url", null);
                        editor.putBoolean("facebook_done", false);
                        editor.apply();
                        startActivity(new Intent(LoadingActivity.this, MainActivity.class));
                        finish();
                    }
                    else if (errorCode == 1)
                    {
                        startActivity(new Intent(LoadingActivity.this, GoToLoginRegisterActivity.class));
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginAPIElements> call, Throwable t) {

            }
        });
    }
}
