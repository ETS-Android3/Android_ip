package com.example.newsapp.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Parcel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newsapp.Models.Article;
import com.example.newsapp.Models.Source;
import com.example.newsapp.R;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NewDetailFragment extends Fragment implements View.OnClickListener{
@BindView(R.id.ImageView) ImageView mImageLabel;
@BindView(R.id.titleTextView) TextView mTitleTextView;
@BindView(R.id.sourceTextView) TextView mSourceTextView;
@BindView(R.id.authorTextView) TextView mAuthorTextView;
@BindView(R.id.urlTextView) TextView mUrlTextView;
@BindView(R.id.contentTextView) TextView mContentTextView;
@BindView(R.id.newsButton) TextView mNewsButton;
@BindView(R.id.nameTextView) TextView mNameTextView;

private Article mNewsList;

public NewDetailFragment(){
}

public static NewDetailFragment newInstance(Article newsList){
    NewDetailFragment newDetailFragment = new NewDetailFragment();
    Bundle args = new Bundle();
    args.putParcelable("newsList", Parcels.wrap(newsList));
    newDetailFragment.setArguments(args);
    return newDetailFragment;
}

@Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    assert getArguments() != null;
    mNewsList = Parcels.unwrap(getArguments().getParcelable("newsList"));
}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_detail, container,false);
        ButterKnife.bind(this,view);

        mUrlTextView.setOnClickListener(this);

        Picasso.get().load(mNewsList.getUrlToImage()).into(mImageLabel);
//        List<String> sources = new ArrayList<>();
//        for (Source source: mNewsList.getSource()){
//            sources.add(source.getName());
//        }
        mNameTextView.setText(mNewsList.getSource().getName());
        mTitleTextView.setText(mNewsList.getTitle());
        mAuthorTextView.setText(mNewsList.getAuthor());
        mUrlTextView.setText(mNewsList.getUrl());
        mContentTextView.setText(mNewsList.getContent());
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view==mUrlTextView){
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(mNewsList.getUrl()));
            startActivity(webIntent);
        }

    }
}