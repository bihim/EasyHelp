package com.example.easyhelp.Profile.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.easyhelp.ChangePassword.Activity.ChangePasswordActivity;
import com.example.easyhelp.R;

import life.sabujak.roundedbutton.RoundedButton;

public class ProfileEditActivity extends AppCompatActivity
{
    RoundedButton changePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        changePassword = findViewById(R.id.change_password_button_profile);

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(ProfileEditActivity.this, ChangePasswordActivity.class));
            }
        });
    }
}
