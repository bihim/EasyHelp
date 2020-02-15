package com.example.easyhelp.News;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyhelp.OtherActivities.NewsClickedActivity;
import com.example.easyhelp.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder>
{
    ArrayList<NewsItems> newsItems;
    Context context;
    OnItemClickListener onItemClickListener;

    public static final String IMAGE = "newsImage";
    public static final String HEADLINE = "newsHeadline";
    public static final String DESCRIPTION = "newsDescription";
    public static final String TIMEANDDATE = "newsTimeAndDate";

    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }

    public NewsAdapter(ArrayList<NewsItems> newsItems, Context context)
    {
        this.newsItems = newsItems;
        this.context = context;

    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);

        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position)
    {
        final NewsItems selectedItems = newsItems.get(position);

        holder.newsImage.setImageResource(selectedItems.getImageID());
        holder.newsHeader.setText(selectedItems.getHeadline());
        holder.newsDescription.setText(selectedItems.getDescription());
        holder.newsDate.setText(selectedItems.getTimeStamp());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent clickToSend = new Intent(context, NewsClickedActivity.class);

                clickToSend.putExtra(IMAGE, selectedItems.getImageID());
                clickToSend.putExtra(HEADLINE, selectedItems.getHeadline());
                clickToSend.putExtra(DESCRIPTION, selectedItems.getDescription());
                clickToSend.putExtra(TIMEANDDATE, selectedItems.getTimeStamp());
                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
                context.startActivity(clickToSend);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsItems.size();
    }

    public class NewsHolder extends RecyclerView.ViewHolder
    {

        TextView newsHeader, newsDescription, newsDate;
        CircleImageView newsImage;

        public NewsHolder(@NonNull View itemView)
        {
            super(itemView);

            newsHeader = itemView.findViewById(R.id.news_headline);
            newsDescription = itemView.findViewById(R.id.news_description);
            newsImage = itemView.findViewById(R.id.news_image);
            newsDate = itemView.findViewById(R.id.news_date);

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
