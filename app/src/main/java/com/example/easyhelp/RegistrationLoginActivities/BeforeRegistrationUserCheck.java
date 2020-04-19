package com.example.easyhelp.RegistrationLoginActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.easyhelp.API.BaseUrl;
import com.example.easyhelp.API.PlaceHolderAPI;
import com.example.easyhelp.BeforeRegistrationThings.BeforeRegistrationUserCheckAPI;
import com.example.easyhelp.R;

import es.dmoral.toasty.Toasty;
import life.sabujak.roundedbutton.RoundedButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BeforeRegistrationUserCheck extends AppCompatActivity {

    EditText editTextUserNumber;
    RoundedButton buttonUserNext;
    Retrofit retrofit;
    PlaceHolderAPI placeHolderAPI;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String baseUrl = new BaseUrl().baseUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_registration_user_check);
        forSharedPreference();

        editTextUserNumber = findViewById(R.id.before_registration_user_check_number);
        buttonUserNext = findViewById(R.id.before_registration_user_check_button);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        placeHolderAPI = retrofit.create(PlaceHolderAPI.class);

        buttonUserNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (!editTextUserNumber.getText().toString().isEmpty())
                {
                    Call<BeforeRegistrationUserCheckAPI> call = placeHolderAPI.getUserCheck(editTextUserNumber.getText().toString());
                    call.enqueue(new Callback<BeforeRegistrationUserCheckAPI>() {
                        @Override
                        public void onResponse(Call<BeforeRegistrationUserCheckAPI> call, Response<BeforeRegistrationUserCheckAPI> response)
                        {
                            BeforeRegistrationUserCheckAPI beforeRegistrationUserCheckAPI = response.body();

                            int errorCode = beforeRegistrationUserCheckAPI.getError();
                            Log.d("ERRORCODE", "onResponse: "+errorCode);

                            if (errorCode == 1)
                            {
                                editor.putString("user_name", editTextUserNumber.getText().toString());
                                editor.apply();
                                startActivity(new Intent(BeforeRegistrationUserCheck.this, BeforeRegistrationActivity.class));
                                finish();
                            }
                            else if (errorCode == 0)
                            {
                                Toasty.error(BeforeRegistrationUserCheck.this, "This mobile number exists", Toasty.LENGTH_SHORT, true).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<BeforeRegistrationUserCheckAPI> call, Throwable t) {

                        }
                    });
                }
            }
        });


    }
    private void forSharedPreference()
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
    }
}
