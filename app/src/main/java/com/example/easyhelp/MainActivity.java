package com.example.easyhelp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import com.example.easyhelp.API.BaseUrl;
import com.example.easyhelp.API.PlaceHolderAPI;
import com.example.easyhelp.Bloods.Item.BloodList;
import com.example.easyhelp.Bloods.Item.BloodReportListAPI;
import com.example.easyhelp.Construction.Item.ConstructionItems;
import com.example.easyhelp.Fragment.AdvertisementFragment;
import com.example.easyhelp.Bloods.Activity.BloodFragment;
import com.example.easyhelp.Fragment.HomeFragment;
import com.example.easyhelp.Fragment.MessageFragment;
import com.example.easyhelp.NewsThings.NewsFragment;
import com.example.easyhelp.Construction.Activity.ConstructionActivity;
import com.example.easyhelp.RegistrationAndLogin.Registration.Activity.GoToLoginRegisterActivity;
import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    BubbleNavigationLinearView bubbleNavigationLinearView;
    PlaceHolderAPI placeHolderAPI;
    Retrofit retrofit;
    String baseUrl = new BaseUrl().baseUrl;
    int constructionCode;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    MaterialToolbar toolbar;
    MediaPlayer mediaPlayer;
    int bloodCount = 0;
    boolean onBackPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        threadToast(this);

        if (construction() == 1)
        {
            startActivity(new Intent(this, ConstructionActivity.class));
        }

        //default fragment which is home
        if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeFragment(), "0").commit();
        }



        Context context= MainActivity.this;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        if (preferences.getBoolean("media", true))
        {
            /*Welcome to easy helper creepy sound*/
            mediaPlayer = MediaPlayer.create(this, R.raw.welcome);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp)
                {
                    editor = preferences.edit();
                    editor.putBoolean("media", false);
                    editor.apply();
                }
            });
            /*Welcome to easy helper creepy sound*/
        }



        //view id
        bubbleNavigationLinearView = findViewById(R.id.bottom_navigation_view_linear);

        bubbleNavigationLinearView.setNavigationChangeListener(new BubbleNavigationChangeListener()
        {
            @Override
            public void onNavigationChanged(View view, int position)
            {
                Fragment selectFragment;

                selectFragment = null;

                switch (position)
                {
                    case 0:
                        selectFragment = new HomeFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, selectFragment, "0").commit();
                        break;

                    case 1:
                        selectFragment = new MessageFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, selectFragment, "1").commit();
                        break;

                    case 2:
                        selectFragment = new AdvertisementFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, selectFragment, "2").commit();
                        break;

                    case 3:
                        selectFragment = new NewsFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, selectFragment, "3").commit();
                        break;

                    case 4:
                        selectFragment = new BloodFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, selectFragment, "4").commit();
                        break;
                }

            }
        });

        //bubbleNavigationLinearView.setBadgeValue(4,String.valueOf(getBloodCount()));
        getBloodCount();

        Intent intent = getIntent();

        String getFragment = intent.getStringExtra("id");

        if (getFragment!=null)
        {
            if (getFragment.equals("4"))
            {
                bubbleNavigationLinearView.setCurrentActiveItem(4);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new BloodFragment()).commit();
            }
        }
    }

    private int construction()
    {
        baseUrl = new BaseUrl().baseUrl;
        retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        placeHolderAPI = retrofit.create(PlaceHolderAPI.class);

        Call<ConstructionItems> constructionCall = placeHolderAPI.getConstructionInfo(1);
        constructionCall.enqueue(new Callback<ConstructionItems>() {
            @Override
            public void onResponse(Call<ConstructionItems> call, Response<ConstructionItems> response)
            {
                if (!response.isSuccessful())
                {
                    Toasty.error(MainActivity.this, "Error Code: " +response.code(),Toasty.LENGTH_SHORT,true).show();
                    startActivity(new Intent(MainActivity.this, GoToLoginRegisterActivity.class));
                }

                ConstructionItems constructionItems = response.body();

                constructionCode = constructionItems.getError();
            }

            @Override
            public void onFailure(Call<ConstructionItems> call, Throwable t)
            {

            }
        });

        return constructionCode;
    }

    private void signout()
    {
        preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        editor = preferences.edit();

        editor.putString("user_name", null);
        editor.putString("password", null);
        editor.putString("category", null);
        editor.putString("id", null);
        editor.putString("name", null);
        editor.putString("user_catagory", null);
        editor.putString("catagory_type", null);
        editor.putString("mobile", null);
        editor.putString("address", null);
        editor.putString("image_url", null);
        editor.putString("institute_name", null);
        editor.commit();
        startActivity(new Intent(this, GoToLoginRegisterActivity.class));
        finish();
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.change_password:
                startActivity(new Intent(this, ChangePasswordActivity.class));
                return true;

            case R.id.signout:
                signout();
                startActivity(new Intent(this, GoToLoginRegisterActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }*/

    private boolean isNetWorkConnectedd()
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

    private void threadToast(Context context)
    {
        if (!isNetWorkConnectedd())
        {
            Thread t = new Thread() {

                @Override
                public void run() {
                    try {
                        while (!isInterrupted()) {
                            Thread.sleep(600000);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run()
                                {
                                    Toasty.error(context,"No net connection is available", Toasty.LENGTH_SHORT, true).show();
                                }
                            });
                        }
                    } catch (InterruptedException e) {
                    }
                }
            };


            t.start();
        }
    }

    private void getBloodCount()
    {
        baseUrl = new BaseUrl().baseUrl;
        retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        placeHolderAPI = retrofit.create(PlaceHolderAPI.class);

        Call<BloodReportListAPI> call = placeHolderAPI.getBloodList(1);

        call.enqueue(new Callback<BloodReportListAPI>() {
            @Override
            public void onResponse(Call<BloodReportListAPI> call, Response<BloodReportListAPI> response)
            {
                BloodReportListAPI bloodReportListAPI = response.body();

                int errorCode = bloodReportListAPI.getError();

                if(errorCode == 0)
                {
                    List<BloodList> bloodReportLists = bloodReportListAPI.getBlood_list();
                    bloodCount = bloodReportLists.size();
                    bubbleNavigationLinearView.setBadgeValue(4,String.valueOf(bloodCount));
                    Log.d("BAALERBLOODCOUNT", "onResponse: "+bloodCount);
                }

            }

            @Override
            public void onFailure(Call<BloodReportListAPI> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed()
    {
        /*if(onBackPressedOnce)
        {
            super.onBackPressed();
            bubbleNavigationLinearView.setCurrentActiveItem(0);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeFragment()).commit();
            return;
        }

        this.onBackPressedOnce = true;
        //onBackPressedOnce = false;

        new Handler().postDelayed(() -> onBackPressedOnce = false,50);*/
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_layout);
        if (fragment.getTag().equals("0"))
        {
            super.onBackPressed();
        }
        else
        {
            bubbleNavigationLinearView.setCurrentActiveItem(0);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeFragment(),"0").commit();
        }

        Context context= MainActivity.this;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
        editor.putBoolean("media", true);
        editor.apply();

    }
}
