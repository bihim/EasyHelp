package com.example.easyhelp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.easyhelp.Fragment.AdvertisementFragment;
import com.example.easyhelp.Fragment.BloodFragment;
import com.example.easyhelp.Fragment.HomeFragment;
import com.example.easyhelp.Fragment.MessageFragment;
import com.example.easyhelp.Fragment.NewsFragment;
import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.google.android.material.appbar.MaterialToolbar;

public class MainActivity extends AppCompatActivity {

    BubbleNavigationLinearView bubbleNavigationLinearView;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //default fragment which is home
        if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeFragment()).commit();
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
                        break;

                    case 1:
                        selectFragment = new MessageFragment();
                        break;

                    case 2:
                        selectFragment = new AdvertisementFragment();
                        break;

                    case 3:
                        selectFragment = new NewsFragment();
                        break;

                    case 4:
                        selectFragment = new BloodFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, selectFragment).commit();
            }
        });

        bubbleNavigationLinearView.setBadgeValue(3,"200");

    }
}
