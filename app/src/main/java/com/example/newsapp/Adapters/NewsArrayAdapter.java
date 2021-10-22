package com.example.newsapp.Adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.example.newsapp.Models.NewsSearchResponse;
import com.example.newsapp.NewsActivity;

import java.util.List;

public class NewsArrayAdapter extends ArrayAdapter {
private Context context;
private String[] headlines;

    public NewsArrayAdapter(@NonNull Context context, int resource,String[] headlines) {
        super(context, resource);
        this.context = context;
        this.headlines = headlines;
    }
    @Override
    public Object getItem(int position){
        String headline = headlines[position];

        return String.format( headline+" Was rescued");
    }
    @Override
    public int getCount(){
        return headlines.length;
    }
}
