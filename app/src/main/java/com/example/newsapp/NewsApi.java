package com.example.newsapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {
    @GET("news/search")
    Call<NewsSearchResponse> getNews(
            @Query("country") String country,
            @Query("name") String name
    );
}
