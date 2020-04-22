package com.example.easyhelp.RegistrationAndLogin.Registration.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.easyhelp.API.BaseUrl;
import com.example.easyhelp.API.PlaceHolderAPI;
import com.example.easyhelp.CustomDialog.CustomDialog;
import com.example.easyhelp.R;
import com.example.easyhelp.RegistrationAndLogin.Login.Activity.LoadingActivity;
import com.example.easyhelp.RegistrationAndLogin.Login.Activity.LoginActivity;
import com.example.easyhelp.RegistrationAndLogin.Registration.Item.RegistrationItems;

import es.dmoral.toasty.Toasty;
import life.sabujak.roundedbutton.RoundedButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationActivity extends AppCompatActivity {

    EditText editTextPassword, editTextConfirmPassword, editTextName, editTextEmail, editTextBirthCertificate, editTextAddress, editTextReferralId;
    Spinner spinnerBloodGroup;
    RoundedButton roundedButtonRegistration;
    Button buttonRegistration;
    PlaceHolderAPI placeHolderAPI;
    Retrofit retrofit;
    String baseurl = new BaseUrl().baseUrl;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    CustomDialog customDialog;
    String countryID, divisionID, districtID, thanaID;
    String[] bloodGroup = {"A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        retrofit = new Retrofit.Builder().baseUrl(baseurl).addConverterFactory(GsonConverterFactory.create()).build();
        placeHolderAPI = retrofit.create(PlaceHolderAPI.class);
        findViewByAll();
        bloodGroup();
        forSharedPreference();
        registration();
    }


    private void registration()
    {
        roundedButtonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                preferences = PreferenceManager.getDefaultSharedPreferences(RegistrationActivity.this);
                String mobile = preferences.getString("user_name",null);
                String category = preferences.getString("category", null);
                String refferalID = editTextReferralId.getText().toString();
                String password = editTextPassword.getText().toString();
                String confirmPassword = editTextConfirmPassword.getText().toString();
                String name = editTextName.getText().toString();
                String email = editTextEmail.getText().toString();
                String birthCertificate = editTextBirthCertificate.getText().toString();
                String country = preferences.getString("country", null);
                String division = preferences.getString("division", null);
                String district = preferences.getString("district", null);
                String thana = preferences.getString("thana", null);

                String blood = spinnerBloodGroup.getSelectedItem().toString();
                String address = editTextAddress.getText().toString();
                customDialog.showDialog();
                if (isNetWorkConnected() && !isEditTextsAreNull())
                {
                    Log.d("BAALERREG", "onClick: "+mobile);
                    Log.d("CATEGORY", "onClick: "+category);
                    Log.d("thana", "onClick: "+thana);
                    Log.d("BLOODYBLOOD", "onClick: "+blood);

                    Call<RegistrationItems> call = placeHolderAPI.getRegistration(mobile, category, refferalID, password, confirmPassword, name, email, birthCertificate, country, division, district, thana, blood, address);
                    call.enqueue(new Callback<RegistrationItems>() {
                        @Override
                        public void onResponse(Call<RegistrationItems> call, Response<RegistrationItems> response)
                        {
                            customDialog.hideDialog();
                            if (response.isSuccessful())
                            {
                                RegistrationItems registrationItems = response.body();

                                int errorCode = registrationItems.getError();
                                if (errorCode == 0)
                                {
                                    Toasty.success(RegistrationActivity.this, "Successfully registered. Logging in to app", Toasty.LENGTH_SHORT, true).show();
                                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(RegistrationActivity.this);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("user_name", mobile);
                                    editor.putString("password", password);
                                    editor.putString("name", name);
                                    editor.putBoolean("user_register", true);
                                    editor.apply();
                                    startActivity(new Intent(RegistrationActivity.this, LoadingActivity.class));
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<RegistrationItems> call, Throwable t)
                        {

                        }
                    });
                }
            }
        });

        buttonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                finish();
            }
        });

    }

    private boolean isEditTextsAreNull()
    {
        if (editTextName.getText().toString().isEmpty())
        {
            Toasty.error(this, "Name is empty", Toasty.LENGTH_SHORT,true).show();
        }
        else if (editTextName.getText().toString().isEmpty())
        {
            Toasty.error(this, "Email is empty", Toasty.LENGTH_SHORT,true).show();
        }
        else if (editTextPassword.getText().toString().isEmpty())
        {
            Toasty.error(this, "Password is empty", Toasty.LENGTH_SHORT,true).show();
        }
        else if (editTextConfirmPassword.getText().toString().isEmpty())
        {
            Toasty.error(this, "Input confirm password is empty", Toasty.LENGTH_SHORT,true).show();
        }
        else if (editTextBirthCertificate.getText().toString().isEmpty())
        {
            Toasty.error(this, "NID/Birth Certificate is empty", Toasty.LENGTH_SHORT,true).show();
        }
        else if (editTextAddress.getText().toString().isEmpty())
        {
            Toasty.error(this, "Address is empty", Toasty.LENGTH_SHORT,true).show();
        }
        else if (!editTextPassword.getText().toString().equals(editTextConfirmPassword.getText().toString()))
        {
            Toasty.error(this, "Password doesn't matches", Toasty.LENGTH_SHORT,true).show();
        }

        return false;
    }

    /*private void registrationCall()
    {
        buttonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                finish();
            }
        });

        retrofit = new Retrofit.Builder().baseUrl(baseurl).addConverterFactory(GsonConverterFactory.create()).build();
        placeHolderAPI = retrofit.create(PlaceHolderAPI.class);

        roundedButtonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                registrationAPICall();
            }
        });
    }*/

  /*  private void registrationAPICall()
    {


        String number = editTextNumber.getText().toString();
        String password = editTextPassword.getText().toString();
        String confirmPassword = editTextConfirmPassword.getText().toString();
        String name = editTextName.getText().toString();
        String email = editTextEmail.getText().toString();
        String birthCertificate = editTextBirthCertificate.getText().toString();
        String country = editTextCountry.getText().toString();
        String division = editTextDivision.getText().toString();
        String district = editTextDistrict.getText().toString();
        String ps = editTextPs.getText().toString();
        String blood = editTextBlood.getText().toString();
        String address = editTextAddress.getText().toString();
        String referral = editTextReferralId.getText().toString();

        if (number.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || name.isEmpty() || email.isEmpty() || birthCertificate.isEmpty() || country.isEmpty() || division.isEmpty() || district.isEmpty() || ps.isEmpty() || blood.isEmpty() || address.isEmpty())
        {
            Toasty.error(RegistrationActivity.this, "Fields must not be empty", Toasty.LENGTH_SHORT, true).show();
        }

        else if (!password.equals(confirmPassword))
        {
            Toasty.error(RegistrationActivity.this, "Password does not matches", Toasty.LENGTH_SHORT, true).show();
        }

        else
        {
            customDialog.showDialog();
            Call<RegistrationItems> call = placeHolderAPI.getRegistration(number, getCategoryNumber(categoryName), referral, password, confirmPassword, name, email, birthCertificate, country, division, district, ps, blood, address);
            call.enqueue(new Callback<RegistrationItems>() {
                @Override
                public void onResponse(Call<RegistrationItems> call, Response<RegistrationItems> response)
                {
                    if (!response.isSuccessful())
                    {
                        customDialog.hideDialog();
                        Toasty.error(RegistrationActivity.this, "Error Code: " +response.code(),Toasty.LENGTH_SHORT,true).show();
                    }

                    RegistrationItems registrationItems = response.body();

                    int errorCode = registrationItems.getError();
                    String errorReport = registrationItems.getError_report();

                    if (errorCode == 1 && errorReport.equals("This Mobile number is already exist. Try another."))
                    {
                        customDialog.hideDialog();
                        Toasty.error(RegistrationActivity.this, "Password or UserName not matched",Toasty.LENGTH_SHORT,true).show();
                    }

                    else if (errorCode == 1)
                    {
                        customDialog.hideDialog();
                        Toasty.error(RegistrationActivity.this, errorReport,Toasty.LENGTH_SHORT,true).show();
                    }

                    else if (errorCode == 0)
                    {

                        Toasty.success(RegistrationActivity.this, "Registration successful. Please login to continue",Toasty.LENGTH_LONG,true).show();
                        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                        intent.putExtra("login","LOGIN\nto continue");
                        startActivity(intent);
                        finish();
                    }


                }

                @Override
                public void onFailure(Call<RegistrationItems> call, Throwable t)
                {
                    Toasty.error(RegistrationActivity.this, "Error: "+t.getMessage(),Toasty.LENGTH_SHORT,true).show();
                }
            });
        }


    }*/
    private void findViewByAll()
    {
        editTextPassword = findViewById(R.id.registration_user_first_password);
        editTextConfirmPassword = findViewById(R.id.registration_user_re_password);
        editTextName = findViewById(R.id.registration_name);
        editTextEmail = findViewById(R.id.registration_email);
        editTextBirthCertificate = findViewById(R.id.registration_nid_birthcertificate);
        editTextAddress = findViewById(R.id.registration_address);
        editTextReferralId = findViewById(R.id.registration_referral_id);
        roundedButtonRegistration = findViewById(R.id.registration_button);
        buttonRegistration = findViewById(R.id.go_to_login_activity);
        spinnerBloodGroup = findViewById(R.id.registration_spinner_blood_group);
        customDialog = new CustomDialog(this);
    }

    private void bloodGroup()
    {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bloodGroup);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBloodGroup.setAdapter(arrayAdapter);
    }



    private String getCategoryNumber(String category)
    {
        switch (category)
        {
            case "Helper/General People":
                return "0";


            case "Lawyer":
                return "1";


            case "Journalist":
                return "2";


            case "Police":
                return "3";

            default:
                return "0";
        }
    }

    private void forSharedPreference()
    {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        /*editor.putString("id", null);
        editor.putString("name", null);
        editor.putString("user_catagory", null);
        editor.putString("catagory_type", null);
        editor.putString("mobile", null);
        editor.putString("address", null);
        editor.putString("image_url", null);
        editor.putString("institute_name", null);*/
        editor.putString("facebook_url", null);
        editor.putBoolean("facebook_done", false);
        editor.apply();
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




}
