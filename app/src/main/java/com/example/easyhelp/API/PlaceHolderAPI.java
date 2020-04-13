package com.example.easyhelp.API;

import com.example.easyhelp.BloodThings.BloodReportListAPI;
import com.example.easyhelp.BloodThings.BloodRequestAPI;
import com.example.easyhelp.ChangePasswordThings.ChangePasswordItems;
import com.example.easyhelp.ConstructionThings.ConstructionItems;
import com.example.easyhelp.LoginThings.LoginAPIElements;
import com.example.easyhelp.Profilethings.ProfileItemAPI;
import com.example.easyhelp.RegistrationThings.RegistrationItems;
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

    @FormUrlEncoded
    @POST("myprofile.php")
    Call<ProfileItemAPI> getProfileDetails(
            @Field("user_id") int user_id
    );

    @FormUrlEncoded
    @POST("join.php")
    Call<RegistrationItems> getRegistration(
            @Field("mobile") String mobile,
            @Field("category") String category,
            @Field("referral_id") String referral_id,
            @Field("first_password") String first_password,
            @Field("re_password") String re_password,
            @Field("name") String name,
            @Field("email") String email,
            @Field("nid") String nid,
            @Field("country") String country,
            @Field("division") String division,
            @Field("district") String district,
            @Field("ps") String ps,
            @Field("blood") String blood,
            @Field("address") String address
    );


    /*Blood Report List*/
    @FormUrlEncoded
    @POST("bloods.php")
    Call<BloodReportListAPI> getBloodList(
            @Field("user_id") int user_id
    );

    /*Blood Request API*/
    @FormUrlEncoded
    @POST("blood.php")
    Call<BloodRequestAPI> getRequestBlood(
            @Field("user_name") String user_name,
            @Field("blood") String blood
    );

}
