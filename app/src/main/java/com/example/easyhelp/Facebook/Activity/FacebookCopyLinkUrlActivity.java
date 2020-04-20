package com.example.easyhelp.Facebook.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.easyhelp.Facebook.Fragment.FragmentFacebookImage;
import com.example.easyhelp.R;

public class FacebookCopyLinkUrlActivity extends AppCompatActivity
{
    /*
    * This is activity for facebook link copy*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_copy_link_url);

        if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.facebook_fragment_holder, new FragmentFacebookImage()).commit();
        }
    }
}
