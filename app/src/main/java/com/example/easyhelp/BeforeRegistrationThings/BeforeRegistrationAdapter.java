package com.example.easyhelp.BeforeRegistrationThings;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyhelp.OtherActivities.RegistrationActivity;
import com.example.easyhelp.R;

import java.util.ArrayList;

public class BeforeRegistrationAdapter extends RecyclerView.Adapter<BeforeRegistrationAdapter.BeforeRegistrationViewHolder>
{
    ArrayList<BeforeRegistrationItems> beforeRegistrationItems;
    Context context;
    OnItemClickListener onItemClickListener;

    public static final String PROFESSION_NAME = "profession_name";
    public static final String PROFESSION_ICON = "profession_icon";

    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }

    public BeforeRegistrationAdapter(ArrayList<BeforeRegistrationItems> beforeRegistrationItems, Context context)
    {
        this.beforeRegistrationItems = beforeRegistrationItems;
        this.context = context;
    }

    @NonNull
    @Override
    public BeforeRegistrationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_before_registration, parent, false);

        return new BeforeRegistrationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BeforeRegistrationViewHolder holder, int position)
    {
        final BeforeRegistrationItems selectedItems = beforeRegistrationItems.get(position);

        holder.imageViewIcon.setImageResource(selectedItems.getIconId());
        holder.textViewProfession.setText(selectedItems.getProfessionName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context, RegistrationActivity.class);

                intent.putExtra(PROFESSION_NAME, selectedItems.getProfessionName());
                intent.putExtra(PROFESSION_ICON, selectedItems.getIconId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return beforeRegistrationItems.size();
    }

    public class BeforeRegistrationViewHolder extends RecyclerView.ViewHolder
    {
        TextView textViewProfession;
        ImageView imageViewIcon;

        public BeforeRegistrationViewHolder(@NonNull View itemView)
        {
            super(itemView);

            textViewProfession = itemView.findViewById(R.id.before_registration_textView);
            imageViewIcon = itemView.findViewById(R.id.before_registration_profession_icon);

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
