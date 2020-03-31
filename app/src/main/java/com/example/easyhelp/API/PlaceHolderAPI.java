package com.example.easyhelp.API;

import com.example.easyhelp.WelcomeSpeechThing.WelcomeSpeechAPIElements;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PlaceHolderAPI
{
    @FormUrlEncoded
    @POST("welcome_speech.php")
    Call<WelcomeSpeechAPIElements> createPost(
            @Field("user_id") int user_id
    );
}
