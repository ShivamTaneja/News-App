package com.example.shivam.newsapp1;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    private List<NewsList> newsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView webTitle, webUrl, sectionName, webPublicationDate, webPublicationTime, author;

        public MyViewHolder(View view) {
            super(view);
            webTitle = view.findViewById(R.id.webTitle);
            webUrl = view.findViewById(R.id.webUrl);
            sectionName = view.findViewById(R.id.sectionName);
            webPublicationDate = view.findViewById(R.id.webPublicationDate);
            webPublicationTime = view.findViewById(R.id.webPublicationTime);
            author = view.findViewById(R.id.author);
        }
    }

    public NewsAdapter(List<NewsList> newsList) {
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        NewsList news = newsList.get(position);
        holder.webTitle.setText(news.getWebTitle());
        holder.sectionName.setText(news.getSectionName());
        holder.webPublicationDate.setText(news.getWebPublicationDate());
        holder.webPublicationTime.setText(news.getWebPublicationTime());
        holder.webUrl.setText(news.getWebUrl());
        String str = news.getAuthor();
        if(str == null)
        {
            str = "anonymous";
        }
        holder.author.setText(str);
      }
    @Override
    public int getItemCount() {
        if(newsList == null)
            return 0;
        return newsList.size();
    }
}
