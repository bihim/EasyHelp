package com.example.easyhelp.OtherActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.easyhelp.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import life.sabujak.roundedbutton.RoundedButton;

public class GoToLoginRegisterActivity extends AppCompatActivity
{

    /*
    * This triggers when a user is not logged in.
    * This one has lottie animation, youtube video
    */

    RoundedButton registration, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_to_login_register);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        registration = findViewById(R.id.go_to_register);
        login = findViewById(R.id.go_to_login);

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(GoToLoginRegisterActivity.this, BeforeRegistrationActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(GoToLoginRegisterActivity.this, LoginActivity.class));
            }
        });


    }
}
