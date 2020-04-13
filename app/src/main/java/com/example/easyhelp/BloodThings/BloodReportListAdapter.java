package com.example.easyhelp.BloodThings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyhelp.R;

import java.util.ArrayList;

public class BloodReportListAdapter extends RecyclerView.Adapter<BloodReportListAdapter.BloodReportListViewHolder>
{
    ArrayList<BloodReportListItem> bloodReportListItems;
    Context context;
    OnItemClickListener onItemClickListener;

    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }

    public BloodReportListAdapter(ArrayList<BloodReportListItem> bloodReportListItems, Context context)
    {
        this.bloodReportListItems = bloodReportListItems;
        this.context = context;
    }

    @NonNull
    @Override
    public BloodReportListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_blood_list, parent, false);

        return new BloodReportListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BloodReportListViewHolder holder, int position)
    {
        BloodReportListItem selectedItem = bloodReportListItems.get(position);

        holder.textViewMobile.setText(selectedItem.getMobile());
        holder.textViewName.setText(selectedItem.getName());
        holder.textViewTitle.setText(selectedItem.getTitle());
        holder.textViewCategory.setText(selectedItem.getCategory());
        holder.textViewId.setText(selectedItem.getId());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Apply click listener here
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return bloodReportListItems.size();
    }

    public class BloodReportListViewHolder extends RecyclerView.ViewHolder
    {

        TextView textViewId, textViewCategory, textViewTitle, textViewName, textViewMobile;

        public BloodReportListViewHolder(@NonNull View itemView)
        {
            super(itemView);

            textViewId = itemView.findViewById(R.id.recycler_view_blood_report_id);
            textViewCategory = itemView.findViewById(R.id.recycler_view_blood_report_category);
            textViewTitle = itemView.findViewById(R.id.recycler_view_blood_report_title);
            textViewName = itemView.findViewById(R.id.recycler_view_blood_report_name);
            textViewMobile = itemView.findViewById(R.id.recycler_view_blood_report_number);

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
