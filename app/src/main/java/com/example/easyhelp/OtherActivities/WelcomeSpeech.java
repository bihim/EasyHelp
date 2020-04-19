package com.example.easyhelp.OtherActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyhelp.API.BaseUrl;
import com.example.easyhelp.API.PlaceHolderAPI;
import com.example.easyhelp.CustomDialog.CustomDialog;
import com.example.easyhelp.R;
import com.example.easyhelp.WelcomeSpeechThing.WelcomeSpeechAPIElements;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class WelcomeSpeech extends AppCompatActivity {

    MaterialToolbar toolbar;
    String baseUrl;
    Retrofit retrofit;
    PlaceHolderAPI placeHolderAPI;
    TextView welcomeSpeechTextView;
    CustomDialog customDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_speech);
        findViewByIdDeclare();
        toolBarMethod(R.id.toolbar_welcome_speech,"Welcome User");
        baseUrl = new BaseUrl().baseUrl;
        customDialog = new CustomDialog(this);
        getSpeech();
    }

    private void findViewByIdDeclare()
    {
        welcomeSpeechTextView = findViewById(R.id.welcome_speech_text_view);
    }

    private void getSpeech()
    {
        customDialog.showDialog();
        retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        placeHolderAPI = retrofit.create(PlaceHolderAPI.class);

        Call<WelcomeSpeechAPIElements> call = placeHolderAPI.getWelcomeSpeech();

        call.enqueue(new Callback<WelcomeSpeechAPIElements>() {
            @Override
            public void onResponse(Call<WelcomeSpeechAPIElements> call, Response<WelcomeSpeechAPIElements> response)
            {
                if (!response.isSuccessful())
                {
                    customDialog.hideDialog();
                    Toast.makeText(WelcomeSpeech.this, "Error Code: "+response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                customDialog.hideDialog();
                WelcomeSpeechAPIElements welcomeSpeechAPIElements = response.body();
                welcomeSpeechTextView.setText(Html.fromHtml(welcomeSpeechAPIElements.getAboutUs()));

            }

            @Override
            public void onFailure(Call<WelcomeSpeechAPIElements> call, Throwable t)
            {
                customDialog.hideDialog();
                Toast.makeText(WelcomeSpeech.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
    }
}
