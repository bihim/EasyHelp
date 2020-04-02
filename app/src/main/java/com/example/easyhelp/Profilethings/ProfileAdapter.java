package com.example.easyhelp.Profilethings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyhelp.R;

import java.util.ArrayList;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>
{
    ArrayList<ProfileItem> profileItems;
    Context context;

    public ProfileAdapter(ArrayList<ProfileItem> profileItems, Context context)
    {
        this.profileItems = profileItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_profile_view, parent, false);
        return new ProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position)
    {
        ProfileItem selectedItems = profileItems.get(position);

        holder.textViewTitle.setText(selectedItems.getTitle());
        holder.textViewDetails.setText(selectedItems.getDetails());
    }

    @Override
    public int getItemCount()
    {
        return profileItems.size();
    }

    public class ProfileViewHolder extends RecyclerView.ViewHolder
    {
        TextView textViewTitle;
        TextView textViewDetails;

        public ProfileViewHolder(@NonNull View itemView)
        {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.profile_title);
            textViewDetails = itemView.findViewById(R.id.profile_details);


        }
    }


}
