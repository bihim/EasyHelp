package com.example.easyhelp.OtherActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.easyhelp.API.BaseUrl;
import com.example.easyhelp.API.PlaceHolderAPI;
import com.example.easyhelp.CustomDialog.CustomDialog;
import com.example.easyhelp.R;
import com.example.easyhelp.RegistrationThings.RegistrationItems;
import com.google.android.material.appbar.MaterialToolbar;

import es.dmoral.toasty.Toasty;
import life.sabujak.roundedbutton.RoundedButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.easyhelp.BeforeRegistrationThings.BeforeRegistrationAdapter.PROFESSION_ICON;
import static com.example.easyhelp.BeforeRegistrationThings.BeforeRegistrationAdapter.PROFESSION_NAME;

public class RegistrationActivity extends AppCompatActivity {

    EditText editTextNumber, editTextPassword, editTextConfirmPassword, editTextName, editTextEmail, editTextBirthCertificate, editTextCountry, editTextDivision, editTextDistrict, editTextPs, editTextBlood, editTextAddress, editTextReferralId;
    TextView textViewCategoryText;
    ImageView imageViewCategoryIcon;
    RoundedButton roundedButtonRegistration;
    Button buttonRegistration;
    String categoryName;
    int categoryIcon;
    LinearLayout categoryLayout;
    PlaceHolderAPI placeHolderAPI;
    Retrofit retrofit;
    String baseurl = new BaseUrl().baseUrl;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    CustomDialog customDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        findViewByAll();
        registrationCall();

    }


    private void registrationCall()
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
    }

    private void registrationAPICall()
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

                    if (errorCode == 1)
                    {
                        customDialog.hideDialog();
                        Toasty.error(RegistrationActivity.this, "Password or UserName not matched",Toasty.LENGTH_SHORT,true).show();
                    }

                    else if (errorCode == 0)
                    {

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

        Log.d("BAALERCATEGORY", "registrationAPICall: "+getCategoryNumber(categoryName));

    }

    private void findViewByAll()
    {
        editTextNumber = findViewById(R.id.registration_user_number);
        editTextPassword = findViewById(R.id.registration_user_first_password);
        editTextConfirmPassword = findViewById(R.id.registration_user_re_password);
        editTextName = findViewById(R.id.registration_name);
        editTextEmail = findViewById(R.id.registration_email);
        editTextBirthCertificate = findViewById(R.id.registration_nid_birthcertificate);
        editTextCountry = findViewById(R.id.registration_country);
        editTextDivision = findViewById(R.id.registration_division);
        editTextDistrict = findViewById(R.id.registration_district);
        editTextPs = findViewById(R.id.registration_ps);
        editTextBlood = findViewById(R.id.registration_blood);
        editTextAddress = findViewById(R.id.registration_address);
        editTextReferralId = findViewById(R.id.registration_referral_id);
        textViewCategoryText = findViewById(R.id.registration_category_text);
        imageViewCategoryIcon = findViewById(R.id.registration_category_icon);
        roundedButtonRegistration = findViewById(R.id.registration_button);
        buttonRegistration = findViewById(R.id.go_to_login_activity);
        categoryLayout = findViewById(R.id.layout_category);

        customDialog = new CustomDialog(this);

        //These codes might nog get changed
        Intent intent = getIntent();
        categoryName = intent.getStringExtra(PROFESSION_NAME);
        categoryIcon = intent.getIntExtra(PROFESSION_ICON, R.drawable.ic_general);

        //Test
        textViewCategoryText.setText(categoryName);
        imageViewCategoryIcon.setImageResource(categoryIcon);
        categoryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(RegistrationActivity.this, BeforeRegistrationActivity.class));
                finish();
            }
        });
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

}
