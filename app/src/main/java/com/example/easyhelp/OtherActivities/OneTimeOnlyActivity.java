package com.example.easyhelp.OtherActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import com.example.easyhelp.MainActivity;
import com.example.easyhelp.R;

public class OneTimeOnlyActivity extends AppCompatActivity {

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_time_only);

        Button button = findViewById(R.id.facebook_button_start);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean facebookAvailable = preferences.getBoolean("facebook_done", false);
        if (facebookAvailable)
        {
            startActivity(new Intent(OneTimeOnlyActivity.this, MainActivity.class));
            finish();
        }

        else
        {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    startActivity(new Intent(OneTimeOnlyActivity.this, FacebookCopyLinkUrlActivity.class));
                    finish();
                }
            });
        }
    }
}
