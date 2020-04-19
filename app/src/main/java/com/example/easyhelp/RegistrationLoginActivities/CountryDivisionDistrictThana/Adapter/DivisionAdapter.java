package com.example.easyhelp.RegistrationLoginActivities.CountryDivisionDistrictThana.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyhelp.R;
import com.example.easyhelp.RegistrationLoginActivities.CountryDivisionDistrictThana.Activity.DistrictActivity;
import com.example.easyhelp.RegistrationLoginActivities.CountryDivisionDistrictThana.Activity.DivisionActivity;
import com.example.easyhelp.RegistrationLoginActivities.CountryDivisionDistrictThana.Items.CountryItems;

import java.util.ArrayList;

public class DivisionAdapter extends RecyclerView.Adapter<DivisionAdapter.CountryViewHolder>
{

    ArrayList<CountryItems> arrayList;
    Context context;
    OnItemClickListener onItemClickListener;

    public DivisionAdapter(ArrayList<CountryItems> arrayList, Context context)
    {
        this.arrayList = arrayList;
        this.context = context;
    }

    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_country_division_district_thana, parent, false);

        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position)
    {
        CountryItems selectedItems = arrayList.get(position);

        holder.textView.setText(selectedItems.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("division", selectedItems.getId());
                editor.apply();
                context.startActivity(new Intent(context, DistrictActivity.class));
                ((Activity)context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CountryViewHolder extends RecyclerView.ViewHolder
    {
        TextView textView;

        public CountryViewHolder(@NonNull View itemView)
        {
            super(itemView);

            textView = itemView.findViewById(R.id.recycler_view_country_etc_text_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    if(onItemClickListener!=null)
                    {
                        int position = getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION)
                        {
                            onItemClickListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
