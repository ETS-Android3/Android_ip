package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newsapp.Adapters.NewsArrayAdapter;
//import com.example.newsapp.Adapters.NewsListAdapter;
import com.example.newsapp.Adapters.NewsListAdapter;
import com.example.newsapp.Models.Article;
import com.example.newsapp.Models.NewsSearchResponse;
import com.example.newsapp.Models.Source;
import com.example.newsapp.Network.NewsApi;
import com.example.newsapp.Network.NewsClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends AppCompatActivity {
    private static final String TAG = NewsActivity.class.getSimpleName();
    @BindView(R.id.sourceTextView) TextView mSourceTextView;
    @BindView(R.id.errorTextView) TextView mErrorTextView;
    @BindView(R.id.progressBar) ProgressBar mProgressBar;
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    private NewsListAdapter mAdapter;
    public List<Article> newsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);

//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String news = ((TextView)view).getText().toString();
//                Toast.makeText(NewsActivity.this, news, Toast.LENGTH_LONG).show();
//            }
//        });

        Intent intent = getIntent();
        String source = intent.getStringExtra("source");
        mSourceTextView.setText("Here news from this :" + source);

        NewsApi client = NewsClient.getClient();
        Call<NewsSearchResponse> call = client.callHeadlines(source,Constants.API_KEY);
        call.enqueue(new Callback<NewsSearchResponse>() {
            @Override
            public void onResponse(Call<NewsSearchResponse> call, Response<NewsSearchResponse> response) {

                hideProgressBar();
                if (response.isSuccessful()){
                  newsList = response.body().getArticles();
                  mAdapter = new NewsListAdapter(NewsActivity.this,newsList);
                  mRecyclerView.setAdapter(mAdapter);
                  RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(NewsActivity.this);
                  mRecyclerView.setLayoutManager(layoutManager);
                  mRecyclerView.setHasFixedSize(true);
//                    List<Article> newsList = response.body().getArticles();
//                    String[] headlines = new String[newsList.size()];
//                    Log.d(TAG,"response is" + headlines.length);
//                    String[] sources = new String[newsList.size()];
//
//
//
//                    for (int i = 0;i < headlines.length;i++){
//                        headlines[i] = newsList.get(i).getTitle();
//                    }
//                    for (int i = 0; i < sources.length; i++) {
//                        Source source = newsList.get(i).getSource();
//                        sources[i] = source.getName();
//                    }
//                    for (int i = 0; i < sources.length; i++) {
//                        Source source = newsList.get(i).getSource();
//                        sources[i] = source.getName();
//                    }

//                   mErrorTextView.setVisibility(View.VISIBLE);
//                  mErrorTextView.setText("Your api is working well");

                    showNewsList();

                }else {
                    hideProgressBar();
                    showUnsuccessfulMessage();
                }
            }

            @Override
            public void onFailure(Call<NewsSearchResponse> call, Throwable t) {
                hideProgressBar();
                showFailureMessage();
            }

            private void showFailureMessage() {
                mErrorTextView.setText("Something went wrong. Please check your Internet connection and try again later");
                mErrorTextView.setVisibility(View.VISIBLE);
            }

            private void showUnsuccessfulMessage() {
                mErrorTextView.setText("Something went wrong. Please try again later");
                mErrorTextView.setVisibility(View.VISIBLE);
            }

            private void showNewsList() {
                mRecyclerView.setVisibility(View.VISIBLE);
            }

            private void hideProgressBar() {
                mProgressBar.setVisibility(View.GONE);
            }
        });


//        moreNewsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Toast.makeText(NewsActivity.this, "Wait for More in the next morning", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}

//  sources = (List<NewsSearchResponse>) response.body().getSource();
//        mAdapter = new NewsListAdapter( NewsActivity.this,sources);
//        mRecyclerView.setAdapter(mAdapter);
//        RecyclerView.LayoutManager layoutManager =
//        new LinearLayoutManager(NewsActivity.this);
//        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setHasFixedSize(true);
