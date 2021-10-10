package com.example.newsapp;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;

public class NewsHighlightsArrayAdapter extends ArrayAdapter {
    private Context mContext;
    private String[] mChannels;
    private String[] mHighlights;

    public NewsHighlightsArrayAdapter(Context mContext, int resource, String[] mChannels, String[] mHighlights) {
        super(mContext, resource);
        this.mContext = mContext;
        this.mChannels = mChannels;
        this.mHighlights = mHighlights;
    }

    @Override
    public int getCount() {
        return mChannels.length;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
       String channels = mChannels[position];
       String highlights = mHighlights[position];
       return String.format("%s \nBetter Information:%s", channels, highlights);
    }
}
