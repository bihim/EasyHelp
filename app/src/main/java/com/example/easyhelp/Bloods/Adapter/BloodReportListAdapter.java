package com.example.easyhelp.Bloods.Adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyhelp.Bloods.Item.BloodReportListItem;
import com.example.easyhelp.Message.Activity.MessageActivity;
import com.example.easyhelp.OtherActivities.SplashScreenActivity;
import com.example.easyhelp.R;
import com.intentfilter.androidpermissions.PermissionManager;
import com.intentfilter.androidpermissions.models.DeniedPermission;
import com.intentfilter.androidpermissions.models.DeniedPermissions;

import java.util.ArrayList;
import java.util.Arrays;

import static java.util.Collections.singleton;

public class BloodReportListAdapter extends RecyclerView.Adapter<BloodReportListAdapter.BloodReportListViewHolder> {
    ArrayList<BloodReportListItem> bloodReportListItems;
    Context context;
    OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public BloodReportListAdapter(ArrayList<BloodReportListItem> bloodReportListItems, Context context) {
        this.bloodReportListItems = bloodReportListItems;
        this.context = context;
    }

    @NonNull
    @Override
    public BloodReportListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_blood_list, parent, false);

        return new BloodReportListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BloodReportListViewHolder holder, int position) {
        BloodReportListItem selectedItem = bloodReportListItems.get(position);

        holder.textViewMobile.setText(selectedItem.getMobile());
        holder.textViewName.setText(selectedItem.getName());
        holder.textViewTitle.setText(selectedItem.getTitle());
        holder.textViewCategory.setText(selectedItem.getCategory());

        holder.imageViewCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+selectedItem.getMobile()));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    PermissionManager permissionManager = PermissionManager.getInstance(context);
                    permissionManager.checkPermissions(singleton(Manifest.permission.CALL_PHONE), new PermissionManager.PermissionRequestListener() {
                        @Override
                        public void onPermissionGranted() {
                            Toast.makeText(context, "Permissions Granted", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onPermissionDenied(DeniedPermissions deniedPermissions) {
                            String deniedPermissionsText = "Denied: " + Arrays.toString(deniedPermissions.toArray());
                            Toast.makeText(context, deniedPermissionsText, Toast.LENGTH_SHORT).show();

                            for (DeniedPermission deniedPermission : deniedPermissions) {
                                if(deniedPermission.shouldShowRationale()) {
                                    // Display a rationale about why this permission is required
                                }
                            }
                        }
                    });
                    return;
                }
                context.startActivity(callIntent);
            }
        });

        holder.imageViewMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                context.startActivity(new Intent(context, MessageActivity.class));
            }
        });

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

        TextView textViewCategory, textViewTitle, textViewName, textViewMobile;
        ImageView imageViewCall, imageViewMessage;

        public BloodReportListViewHolder(@NonNull View itemView)
        {
            super(itemView);

            imageViewCall = itemView.findViewById(R.id.recycler_view_blood_report_call);
            imageViewMessage = itemView.findViewById(R.id.recycler_view_blood_report_message);
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
