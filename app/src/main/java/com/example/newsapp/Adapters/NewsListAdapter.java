package com.example.newsapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.Models.Article;
import com.example.newsapp.Models.NewsSearchResponse;
import com.example.newsapp.R;
import com.example.newsapp.Models.Source;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsViewHolder> {

    private List<Article> newsList;
    private Context context;

    public NewsListAdapter(Context context,List<Article> newsList) {
        this.context = context;
        this.newsList = newsList;
    }
        @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_item, parent, false);
        NewsViewHolder viewHolder = new NewsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        holder.bindNewsList(newsList.get(position));
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.ImageView) ImageView imageView;
        @BindView(R.id.titleTextView) TextView titleTextView;
        @BindView(R.id.authorTextView) TextView authorTextView;
        @BindView(R.id.sourceTextView) TextView sourceTextView;
        @BindView(R.id.urlTextView) TextView urlTextview;

        private Context context;
        public NewsViewHolder( View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            context = itemView.getContext();
        }
        public void bindNewsList(Article newsList){
            Picasso.get().load(newsList.getUrlToImage()).into(imageView);
            authorTextView.setText(newsList.getAuthor());
            sourceTextView.setText(newsList.getSource().getName());
            titleTextView.setText(newsList.getTitle());
            urlTextview.setText(newsList.getUrl());
        }

    }
}



