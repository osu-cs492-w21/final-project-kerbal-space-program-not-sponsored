package com.example.afinal.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NeoFeedService {
    @GET("rest/v1/feed?detailed=false&")
    Call<NeoFeed> fetchNeoFeed(
            @Query("start_date") String start_date,
            @Query("end_date") String end_date,
            @Query("api_key") String apiKey
    );
}
