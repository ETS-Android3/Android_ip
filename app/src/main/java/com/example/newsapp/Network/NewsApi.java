package com.example.newsapp.Network;

import com.example.newsapp.Models.NewsSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {
    @GET("sources")
    Call<NewsSearchResponse> callHeadlines(
            @Query("sources") String sources

    );
}
