package com.example.newsapp.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.newsapp.Models.Article;
import com.example.newsapp.ui.NewDetailFragment;

import java.util.List;

public class NewsPagerAdapter extends FragmentPagerAdapter {
    private List<Article> mNewsList;

    public NewsPagerAdapter(@NonNull FragmentManager fm, int behavior, List<Article> mNewsList) {
        super(fm, behavior);
        this.mNewsList = mNewsList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return NewDetailFragment.newInstance(mNewsList.get(position));
    }

    @Override
    public int getCount() {
        return mNewsList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mNewsList.get(position).getTitle();
    }
}
