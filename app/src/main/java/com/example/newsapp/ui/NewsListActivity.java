package com.example.newsapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

//import com.example.newsapp.Adapters.NewsListAdapter;
import com.example.newsapp.Adapters.NewsListAdapter;
import com.example.newsapp.Constants;
import com.example.newsapp.Models.Article;
import com.example.newsapp.Models.NewsSearchResponse;
import com.example.newsapp.Network.NewsApi;
import com.example.newsapp.Network.NewsClient;
import com.example.newsapp.R;
import com.example.newsapp.util.ItemTouchHelperAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsListActivity extends AppCompatActivity {
    private SharedPreferences mSharedPreferences;
    private String mRecentSource;
    private static final String TAG = NewsListActivity.class.getSimpleName();
    @BindView(R.id.sourceTextView) TextView mSourceTextView;
    @BindView(R.id.errorTextView) TextView mErrorTextView;
    @BindView(R.id.progressBar) ProgressBar mProgressBar;
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    private NewsListAdapter mAdapter;
    public List<Article> newsList;
//    String deleteNews = null;

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

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mRecentSource = mSharedPreferences.getString(Constants.PREFERENCES_SOURCE_KEY,null);
//        String source = mRecentSource;
        Log.d("Shared Pref Location",""+ mRecentSource);
        Intent intent = getIntent();
        String source = intent.getStringExtra("source");
        mSourceTextView.setText("Here news from this :" + source);

        NewsApi client = NewsClient.getClient();
        Call<NewsSearchResponse> call = client.callHeadlines(source, Constants.API_KEY);
        call.enqueue(new Callback<NewsSearchResponse>() {
            @Override
            public void onResponse(Call<NewsSearchResponse> call, Response<NewsSearchResponse> response) {

                hideProgressBar();
                if (response.isSuccessful()){
                  newsList = response.body().getArticles();
                  mAdapter = new NewsListAdapter(NewsListActivity.this,newsList);
                  mRecyclerView.setAdapter(mAdapter);
                  RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(NewsListActivity.this);
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
                ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
                itemTouchHelper.attachToRecyclerView(mRecyclerView);

            }



            ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.START |ItemTouchHelper.END, ItemTouchHelper.LEFT| ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

                    int fromPosition = viewHolder.getAdapterPosition();
                    int toPosition =  target.getAdapterPosition();
                    Collections.swap(newsList,fromPosition,toPosition);
                    mRecyclerView.getAdapter().notifyItemMoved(fromPosition,toPosition);
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                    int position = viewHolder.getAdapterPosition();
                    switch (direction){
                        case ItemTouchHelper.LEFT:
//                            deleteNews = newsList.get(position);
                            newsList.remove(position);
                            mAdapter.notifyItemRemoved(position);
//                            Snackbar.make(mRecyclerView, deleteNews, Snackbar.LENGTH_LONG)
//                                    .setAction("Undo", new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View view) {
//                                            newsList.add(position, deleteNews);
//                                            mAdapter.notifyItemInserted(position);
//                                        }
//                                    }).show();

                            break;
                        case ItemTouchHelper.RIGHT:


                    }
                }
            };

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

