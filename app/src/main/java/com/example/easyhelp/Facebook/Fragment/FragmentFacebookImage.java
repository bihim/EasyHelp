package com.example.easyhelp.Facebook.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.easyhelp.R;

public class FragmentFacebookImage extends Fragment
{
    int count = 0;
    Button buttonNext, buttonPrevious;
    ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        /*
        * This is for facebook url copy tutorial. It will appear once on facebook activity.
        * This contains screenshot. It has two buttons, one is for next and one is previous.
        * Check buttonNext for more.
        * */


        View view = inflater.inflate(R.layout.fragment_image_view, container, false);

        imageView = view.findViewById(R.id.facebook_image_view_fragment);
        buttonNext = view.findViewById(R.id.facebook_iamge_view_button_next);
        buttonPrevious = view.findViewById(R.id.facebook_iamge_view_button_previous);

        buttonPrevious.setVisibility(View.GONE);


            imageView.setImageResource(R.drawable.facebook_login);
            buttonNext.setText("Next");


        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (count<=4)
                {
                    count++;
                }

                if (count == 1)
                {
                    imageView.setImageResource(R.drawable.facebook_home);
                    buttonNext.setText("Next");
                    buttonPrevious.setVisibility(View.VISIBLE);
                }

                else if (count == 2)
                {
                    imageView.setImageResource(R.drawable.facebook_menu);
                    buttonNext.setText("Next");
                    buttonPrevious.setVisibility(View.VISIBLE);
                }

                else if (count == 3)
                {
                    imageView.setImageResource(R.drawable.facebook_profile);
                    buttonNext.setText("Finish");
                    buttonPrevious.setVisibility(View.VISIBLE);
                }
                else if (count == 4)
                {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.facebook_fragment_holder, new FragmentFacebookWebView()).commit();
                }
            }
        });

        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (count>=0)
                {
                    count--;
                }

                if (count == 1)
                {
                    imageView.setImageResource(R.drawable.facebook_home);
                    buttonNext.setText("Next");
                    buttonPrevious.setVisibility(View.VISIBLE);
                }

                else if (count == 2)
                {
                    imageView.setImageResource(R.drawable.facebook_menu);
                    buttonNext.setText("Next");
                    buttonPrevious.setVisibility(View.VISIBLE);
                }

                else if (count == 3)
                {
                    imageView.setImageResource(R.drawable.facebook_profile);
                    buttonNext.setText("Finish");
                    buttonPrevious.setVisibility(View.VISIBLE);
                }

                else if (count == 0)
                {
                    imageView.setImageResource(R.drawable.facebook_login);
                    buttonNext.setText("Next");
                    buttonPrevious.setVisibility(View.GONE);
                }

            }
        });


        return view;
    }
}
