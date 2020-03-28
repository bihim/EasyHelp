package com.example.easyhelp.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyhelp.NewsThings.NewsAdapter;
import com.example.easyhelp.NewsThings.NewsItems;
import com.example.easyhelp.R;

import java.util.ArrayList;
import java.util.Random;

public class NewsFragment extends Fragment
{

    RecyclerView newsRecyclerView;

    ArrayList<NewsItems> newsItems;
    NewsAdapter newsAdapter;

    Boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems;
    LinearLayoutManager linearLayoutManager;

    ProgressBar progressBarNews;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        newsRecyclerView = view.findViewById(R.id.news_recycler_view);
        progressBarNews = view.findViewById(R.id.progress_bar_news);

        newsDummyData();

        return view;
    }


    private void newsDummyData()
    {

        Context context = getActivity();
        linearLayoutManager = new LinearLayoutManager(context);
        newsRecyclerView.setHasFixedSize(true);
        newsRecyclerView.setLayoutManager(linearLayoutManager);


        newsItems = new ArrayList<>();
        newsItems.add(new NewsItems(R.drawable.facebook,"Facebook: The leading social site", "Facebook is the main social network over the internet. It has huge amount of members. Over a billion of data are passing through facebook.","14 February, 2020"));
        newsItems.add(new NewsItems(R.drawable.android,"Android: Open Source OS","Android is one of the main os of this era. 78% of mobile devices are android. This os is based on linux kernel. Latest version of this os is android 10","14 February, 2020"));
        newsItems.add(new NewsItems(R.drawable.dell,"Dell: A leading company of desktop and laptop","Dell Inc., which operates under the brand name Dell, is an American multinational computer technology company that develops, sells, repairs, and supports computers and related products and services. Named after its founder, Michael Dell, the company is one of the largest technological corporations in the world, employing more than 145,000 people in the U.S. and around the world (Annual report 2018).","14 February, 2020"));
        newsItems.add(new NewsItems(R.drawable.google,"Google: Leading software company","Google LLC is an American multinational technology company that specializes in Internet-related services and products, which include online advertising technologies, search engine, cloud computing, software, and hardware. It is considered one of the Big Four technology companies, alongside Amazon, Apple, and Facebook.", "14 February, 2020"));
        newsItems.add(new NewsItems(R.drawable.flutter,"Flutter: Cross platform development tool", "Flutter is an open-source UI software development kit created by Google. It is used to develop applications for Android, iOS, Windows, Mac, Linux, Google Fuchsia[5] and the web.","14 February, 2020"));
        newsItems.add(new NewsItems(R.drawable.instagram,"Instagram: Social app for photo enthuasims","Instagram (also known informally as IG or Insta[11]) is an American photo and video-sharing social networking service owned by Facebook, Inc. It was created by Kevin Systrom and Mike Krieger, and launched in October 2010 exclusively on iOS. A version for Android devices was released a year and half later in April 2012, followed by a feature-limited website interface in November 2012, a Fire OS app on June 15, 2014 and an app for Windows 10 tablets and computers in October 2016.","14 February, 2020"));
        newsItems.add(new NewsItems(R.drawable.ios,"IOS: An apple os","iOS (formerly iPhone OS) is a mobile operating system created and developed by Apple Inc. exclusively for its hardware. It is the operating system that presently powers many of the company's mobile devices, including the iPhone, and iPod Touch; it also powered the iPad prior to the introduction of iPadOS in 2019.","14 February, 2020"));
        newsItems.add(new NewsItems(R.drawable.linux,"Linux: An open source OS","Linux (/ˈlɪnəks/ (About this soundlisten) LIN-əks)[9][10] is a family of open source Unix-like operating systems based on the Linux kernel,[11] an operating system kernel first released on September 17, 1991, by Linus Torvalds.[12][13][14] Linux is typically packaged in a Linux distribution.","14 February, 2020"));
        newsItems.add(new NewsItems(R.drawable.manjaro,"Manjaro: Linux distribution","Manjaro is a user-friendly Linux distribution based on the independently developed Arch operating system. Within the Linux community, Arch itself is renowned for being an exceptionally fast, powerful, and lightweight distribution that provides access to the very latest cutting edge - and bleeding edge - software. However, Arch is also aimed at more experienced or technically-minded users. As such, it is generally considered to be beyond the reach of those who lack the technical expertise (or persistence) required to use it.","14 February, 2020"));
        newsItems.add(new NewsItems(R.drawable.windows,"Windows: A shitty os","Microsoft Windows, commonly referred to as Windows, is a group of several proprietary graphical operating system families, all of which are developed and marketed by Microsoft. Each family caters to a certain sector of the computing industry.","14 February, 2020"));

        newsAdapter = new NewsAdapter(newsItems, getActivity());
        newsRecyclerView.setAdapter(newsAdapter);

        newsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState)
            {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);

                currentItems = linearLayoutManager.getChildCount();
                totalItems = linearLayoutManager.getItemCount();
                scrollOutItems = linearLayoutManager.findFirstVisibleItemPosition();

                if (isScrolling && (currentItems+scrollOutItems == totalItems))
                {
                    isScrolling = false;
                    fetchData();
                }
            }
        });


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void fetchData()
    {
        progressBarNews.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {
                for(int i = 0; i<5; i++)
                {

                    NewsItems randomItem;
                    Random random = new Random();
                    int randomNewsAdapter = random.nextInt(newsAdapter.getItemCount());
                    randomItem = newsItems.get(randomNewsAdapter);
                    newsItems.add(randomItem);
                    newsAdapter.notifyDataSetChanged();
                    progressBarNews.setVisibility(View.GONE);
                }

            }
        },2000);
    }
}
