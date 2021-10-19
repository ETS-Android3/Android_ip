package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.newsapp.Adapters.CustomAdapter;
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
    @BindView(R.id.listView) ListView mListView;
    @BindView(R.id.errorTextView) TextView mErrorTextView;
    @BindView(R.id.progressBar) ProgressBar mProgressBar;

public List<Source> sources ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);


        Intent intent = getIntent();
        String source = intent.getStringExtra("country");

        NewsApi client = NewsClient.getClient();
        Call<NewsSearchResponse> call = client.callHeadlines(source);
        call.enqueue(new Callback<NewsSearchResponse>() {
            @Override
            public void onResponse(Call<NewsSearchResponse> call, Response<NewsSearchResponse> response) {


                if (response.isSuccessful()){
                    hideProgressBar();
//                    sources = (List<Source>) response.body().getSource();
//                    String[] news = new String[sources.size()];
//                    for (int i = 0;i< news.length;i++){
//                        news[i] = sources.get(i).getName();
//                    }
//                    CustomAdapter arrayAdapter = new CustomAdapter(NewsActivity.this, android.R.layout.simple_list_item_1,sources);
//                    mListView.setAdapter(arrayAdapter);

                    mErrorTextView.setVisibility(View.VISIBLE);
                    mErrorTextView.setText("Your api is working well");

//                    showNews();
//                    List<NewsSearchResponse> newsList = response.body().getName();
//                    String[] news = new String[newsList.size()];
//                    String[] categories = new String[newsList.size()];
//                    for (int i = 0;i<news.length;i++){
//                        news[i] = String.valueOf(newsList.get(i).getName());
//                    }
//                    for (int i = 0;i<categories.length;i++){
//                        ULocale.Category category = newsList.get(i).getCategories().get(0);
//                        categories[i] = category.getTitle();
//                    }
//                    ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,news, categories);
//    mHeadlines.setAdapter(adapter);
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

            private void showNews() {
                mListView.setVisibility(View.VISIBLE);
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