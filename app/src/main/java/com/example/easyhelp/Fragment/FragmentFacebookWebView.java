package com.example.easyhelp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.easyhelp.MainActivity;
import com.example.easyhelp.R;

public class FragmentFacebookWebView extends Fragment
{
    WebView webView;
    Button buttonOk;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        /*This one is for URL copy. It has an webview and a button.
        * User follows the tutorial and after that clicks on finish button.
        * Then the link is copied to the sharedpreference because API has not provided yet.
        * This link goes to ProfilesActivity*/

        View view = inflater.inflate(R.layout.fragment_webview, container, false);

        webView = view.findViewById(R.id.facebook_webview_fragment);
        buttonOk = view.findViewById(R.id.facebook_webview_fragment_button);
        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                buttonOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                        editor = preferences.edit();
                        editor.putString("facebook_url", webView.getUrl());
                        editor.putBoolean("facebook_done",true);
                        editor.apply();
                        startActivity(new Intent(getActivity(), MainActivity.class));
                        getActivity().finish();
                    }
                });

            }
        });
        webView.loadUrl("http://m.facebook.com");

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        return view;
    }
}
