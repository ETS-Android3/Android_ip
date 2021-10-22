package com.example.newsapp.Network;

import com.example.newsapp.Models.NewsSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {
    @GET("top-headlines")
    Call<NewsSearchResponse> callHeadlines(
            @Query("sources") String source,
            @Query("apiKey") String apikey

    );
}
