package com.example.easyhelp.RegistrationAndLogin.ForgetPassword.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.easyhelp.API.BaseUrl;
import com.example.easyhelp.API.PlaceHolderAPI;
import com.example.easyhelp.CustomDialog.CustomDialog;
import com.example.easyhelp.CustomSpinner.CustomSpinnerAdapter;
import com.example.easyhelp.R;
import com.example.easyhelp.RegistrationAndLogin.ForgetPassword.Items.ForgetPasswordItems;
import com.example.easyhelp.RegistrationAndLogin.Login.Activity.LoginActivity;
import com.google.android.material.appbar.MaterialToolbar;

import es.dmoral.toasty.Toasty;
import life.sabujak.roundedbutton.RoundedButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForgetPasswordActivity extends AppCompatActivity
{
    String[] professionName = {"Helper/General People", "Lawyer", "Journalist", "Police"};
    int[] icons = {R.drawable.ic_general, R.drawable.ic_lawyer, R.drawable.ic_journalist, R.drawable.ic_policeman};
    MaterialToolbar toolbar;
    String baseUrl = new BaseUrl().baseUrl;
    Retrofit retrofit;
    Spinner spinner;
    PlaceHolderAPI placeHolderAPI;
    EditText editTextUserName, editTextNewPassword, editTextConfirmPassword;
    RoundedButton roundedButtonSubmit;
    CustomDialog customDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        findViewByAll();
        forgetPassword();
    }

    private void forgetPassword()
    {
        retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        placeHolderAPI = retrofit.create(PlaceHolderAPI.class);
        roundedButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (!isEdittextsAreEmpty())
                {
                    customDialog.showDialog();
                    Log.d("BAALERFORGET", "onResponse: Dhukse");
                    Call<ForgetPasswordItems> call = placeHolderAPI.getForgetPassword(editTextUserName.getText().toString(), "1", editTextNewPassword.getText().toString(), editTextConfirmPassword.getText().toString());
                    call.enqueue(new Callback<ForgetPasswordItems>() {
                        @Override
                        public void onResponse(Call<ForgetPasswordItems> call, Response<ForgetPasswordItems> response)
                        {
                            customDialog.hideDialog();
                            ForgetPasswordItems forgetPasswordItems = response.body();
                            String error_report = forgetPasswordItems.getErrorReport();
                            Log.d("BAALERFORGET", "onResponse: "+error_report);

                             if (error_report.equals("Successfully Password Change"))
                             {
                                 Toasty.success(ForgetPasswordActivity.this, "Password has changed", Toasty.LENGTH_SHORT, true).show();
                                 startActivity(new Intent(ForgetPasswordActivity.this, LoginActivity.class));
                                 finish();
                             }
                             else
                             {
                                 Toasty.error(ForgetPasswordActivity.this, error_report, Toasty.LENGTH_SHORT, true).show();
                             }
                        }

                        @Override
                        public void onFailure(Call<ForgetPasswordItems> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }

    private boolean isEdittextsAreEmpty()
    {
        if (editTextUserName.getText().toString().isEmpty())
        {
            Toasty.error(this, "Username is empty", Toasty.LENGTH_SHORT, true).show();
            return true;
        }
        else if (editTextNewPassword.getText().toString().isEmpty() || editTextConfirmPassword.getText().toString().isEmpty())
        {
            Toasty.error(this, "Password is empty", Toasty.LENGTH_SHORT, true).show();
            return true;
        }
        else if (!editTextNewPassword.getText().toString().equals(editTextConfirmPassword.getText().toString()))
        {
            Toasty.error(this, "Password does not match", Toasty.LENGTH_SHORT, true).show();
            return true;
        }
        else
        {
            return false;
        }
    }

    private void findViewByAll()
    {
        editTextUserName = findViewById(R.id.forget_password_user_name);
        editTextNewPassword = findViewById(R.id.forget_password_new_password);
        editTextConfirmPassword = findViewById(R.id.forget_password_new_confirm);
        spinner = findViewById(R.id.forget_password_spinner);
        roundedButtonSubmit = findViewById(R.id.forget_password_submit);
        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(getApplicationContext(), icons, professionName);
        spinner.setAdapter(customSpinnerAdapter);
        customDialog = new CustomDialog(this);
    }
}
