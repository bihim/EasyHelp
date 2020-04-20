package com.example.easyhelp.Bloods.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyhelp.API.BaseUrl;
import com.example.easyhelp.API.PlaceHolderAPI;
import com.example.easyhelp.Bloods.Item.BloodList;
import com.example.easyhelp.Bloods.Item.BloodReportListAPI;
import com.example.easyhelp.Bloods.Adapter.BloodReportListAdapter;
import com.example.easyhelp.Bloods.Item.BloodReportListItem;
import com.example.easyhelp.R;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import life.sabujak.roundedbutton.RoundedButton;
import me.ibrahimsn.lib.CirclesLoadingView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BloodFragment extends Fragment
{
    RecyclerView recyclerViewBloodList;
    ArrayList<BloodReportListItem> bloodReportListItems;
    BloodReportListAdapter bloodReportListAdapter;
    CirclesLoadingView circlesLoadingView;
    Retrofit retrofit;
    PlaceHolderAPI placeHolderAPI;
    String baseurl = new BaseUrl().baseUrl;
    View view;
    RoundedButton roundedButtonBloodRequest, roundedButtonBloodReport;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_blood, container, false);

        bloodListApi(view);
        buttonCall(view);

        return view;
    }


    private void bloodListApi(View view)
    {
        circlesLoadingView = view.findViewById(R.id.recycler_view_blood_report_progressbar);
        recyclerViewBloodList = view.findViewById(R.id.blood_recycler_view);
        circlesLoadingView.setVisibility(View.VISIBLE);
        recyclerViewBloodList.setVisibility(View.GONE);

        retrofit = new Retrofit.Builder().baseUrl(baseurl).addConverterFactory(GsonConverterFactory.create()).build();
        placeHolderAPI = retrofit.create(PlaceHolderAPI.class);

        Call<BloodReportListAPI> call = placeHolderAPI.getBloodList(1);

        call.enqueue(new Callback<BloodReportListAPI>() {
            @Override
            public void onResponse(Call<BloodReportListAPI> call, Response<BloodReportListAPI> response)
            {
                circlesLoadingView.setVisibility(View.GONE);
                recyclerViewBloodList.setVisibility(View.VISIBLE);
                if (!response.isSuccessful())
                {
                    Toasty.error(getActivity(), "Error Code: "+response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                BloodReportListAPI bloodReportListAPI = response.body();

                int errorCode = bloodReportListAPI.getError();
                Log.d("BLOODYHELL", "onResponse: "+errorCode);

                if (errorCode == 0)
                {
                    bloodReportListItems = new ArrayList<>();
                    recyclerViewBloodList.setVisibility(View.VISIBLE);
                    recyclerViewBloodList.setHasFixedSize(true);
                    recyclerViewBloodList.setLayoutManager(new LinearLayoutManager(getActivity()));

                    List<BloodList> bloodReportLists = bloodReportListAPI.getBlood_list();

                    for (BloodList bloodList: bloodReportLists)
                    {
                        bloodReportListItems.add(new BloodReportListItem(bloodList.getId(), bloodList.getCategory(), bloodList.getTitle(), bloodList.getName(), bloodList.getMobile()));
                    }

                    bloodReportListAdapter = new BloodReportListAdapter(bloodReportListItems, getActivity());
                    recyclerViewBloodList.setAdapter(bloodReportListAdapter);


                }
                else
                {
                    Toasty.error(getActivity(), "Something went wrong. Please check your internet connection.", Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailure(Call<BloodReportListAPI> call, Throwable t)
            {

            }
        });
    }

    private void buttonCall(View view)
    {
        roundedButtonBloodRequest = view.findViewById(R.id.blood_fragment_blood_request);
        roundedButtonBloodReport = view.findViewById(R.id.blood_fragment_blood_report);

        roundedButtonBloodRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getActivity(), BloodRequestActivity.class));
            }
        });
    }

}
