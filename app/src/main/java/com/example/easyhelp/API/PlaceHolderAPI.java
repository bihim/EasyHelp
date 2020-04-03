package com.example.easyhelp.API;

import com.example.easyhelp.ChangePasswordThings.ChangePasswordItems;
import com.example.easyhelp.ConstructionThings.ConstructionItems;
import com.example.easyhelp.LoginThings.LoginAPIElements;
import com.example.easyhelp.WelcomeSpeechThing.WelcomeSpeechAPIElements;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PlaceHolderAPI
{
    @FormUrlEncoded
    @POST("welcome_speech.php")
    Call<WelcomeSpeechAPIElements> getWelcomeSpeech(
            @Field("user_id") int user_id
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginAPIElements> getLoginInfo(
            @Field("user_name") String user_name,
            @Field("password") String password,
            @Field("category") String category
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginAPIElements> getLoginInfo1(
            @Field("user_name") String user_name,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("construction.php")
    Call<ConstructionItems> getConstructionInfo(
            @Field("user_id") int user_id
    );

    @FormUrlEncoded
    @POST("password.php")
    Call<ChangePasswordItems> getChangePasswordInfo(
            @Field("user_name") String username,
            @Field("password") String oldPassword,
            @Field("old_password") String confirmOldPassword,
            @Field("new_password") String newPassword,
            @Field("again_password") String confirmNewPassword

    );


}
