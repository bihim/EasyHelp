package com.example.easyhelp.OtherActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.example.easyhelp.API.BaseUrl;
import com.example.easyhelp.API.PlaceHolderAPI;
import com.example.easyhelp.RegistrationAndLogin.Registration.Activity.GoToLoginRegisterActivity;
import com.example.easyhelp.Construction.Item.ConstructionItems;
import com.example.easyhelp.RegistrationAndLogin.Login.Item.LoginAPIElements;
import com.example.easyhelp.MainActivity;
import com.example.easyhelp.R;

import java.util.Timer;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashScreenActivity extends AppCompatActivity {

    Timer timer;
    Retrofit retrofit;
    PlaceHolderAPI placeHolderAPI;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String baseUrl;
    String userName;
    String password;
    String category;
    int constructionCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if (!isNetWorkConnectedd())
        {
            startActivity(new Intent(SplashScreenActivity.this, ErrorActivity.class));
            finish();
        }
        else
        {
            login();
        }
        finish();

        /*timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run()
            {


            }
        }, 5000);*/
    }


    private void login()
    {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        userName = preferences.getString("user_name", null);
        password = preferences.getString("password", null);
        category = preferences.getString("category", null);

        if (preferences.contains("user_register"))
        {
            if(!preferences.getBoolean("user_register", false))
            {
                startActivity(new Intent(SplashScreenActivity.this, GoToLoginRegisterActivity.class));
                finish();
            }

            /*if (userName == null && password == null)
            {

            }*/

            else if (preferences.getBoolean("user_register", false))
            {
                baseUrl = new BaseUrl().baseUrl;
                retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
                placeHolderAPI = retrofit.create(PlaceHolderAPI.class);

                Call<LoginAPIElements> call = placeHolderAPI.getLoginInfo(userName, password, "");
                call.enqueue(new Callback<LoginAPIElements>() {
                    @Override
                    public void onResponse(Call<LoginAPIElements> call, Response<LoginAPIElements> response)
                    {
                        if (!response.isSuccessful())
                        {
                            Toasty.error(SplashScreenActivity.this, "Error Code: " +response.code(),Toasty.LENGTH_SHORT,true).show();
                            startActivity(new Intent(SplashScreenActivity.this, GoToLoginRegisterActivity.class));
                        }

                        LoginAPIElements loginAPIElements = response.body();

                        int errorCode = loginAPIElements.getError();

                        if (errorCode == 0)
                        {
                            startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginAPIElements> call, Throwable t) {

                    }
                });
            }
        }

        else
        {
            startActivity(new Intent(SplashScreenActivity.this, GoToLoginRegisterActivity.class));
            finish();
        }

    }

    private int construction()
    {
        baseUrl = new BaseUrl().baseUrl;
        retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        placeHolderAPI = retrofit.create(PlaceHolderAPI.class);

        Call<ConstructionItems> constructionCall = placeHolderAPI.getConstructionInfo(1);
        constructionCall.enqueue(new Callback<ConstructionItems>() {
            @Override
            public void onResponse(Call<ConstructionItems> call, Response<ConstructionItems> response)
            {
                if (!response.isSuccessful())
                {
                    Toasty.error(SplashScreenActivity.this, "Error Code: " +response.code(),Toasty.LENGTH_SHORT,true).show();
                    startActivity(new Intent(SplashScreenActivity.this, GoToLoginRegisterActivity.class));
                }

                ConstructionItems constructionItems = response.body();

                constructionCode = constructionItems.getError();
            }

            @Override
            public void onFailure(Call<ConstructionItems> call, Throwable t)
            {

            }
        });

        return constructionCode;
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
}
