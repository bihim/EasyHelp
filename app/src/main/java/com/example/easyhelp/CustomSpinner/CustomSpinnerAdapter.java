package com.example.easyhelp.CustomSpinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easyhelp.R;

public class CustomSpinnerAdapter extends BaseAdapter
{
    Context context;
    int icons[];
    String[] professionName;
    LayoutInflater inflter;

    public CustomSpinnerAdapter(Context applicationContext, int[] icons, String[] professionName) {
        this.context = applicationContext;
        this.icons = icons;
        this.professionName = professionName;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return icons.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_spinner_login_category, null);
        ImageView icon =  view.findViewById(R.id.spinner_profession_icon);
        TextView names =  view.findViewById(R.id.spinner_profession_text);
        icon.setImageResource(icons[i]);
        names.setText(professionName[i]);
        return view;
    }
}
