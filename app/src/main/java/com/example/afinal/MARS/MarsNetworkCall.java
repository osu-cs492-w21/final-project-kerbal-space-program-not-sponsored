package com.example.afinal.MARS;

import com.example.afinal.MARS.MarsManifest;
import com.example.afinal.MARS.MarsPhotos;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MarsNetworkCall {
    @GET("rovers/{rover_name}/photos")
    Observable<MarsPhotos> getAllMarsPhotosBySol(@Path("rover_name") String rover_name, @Query("sol") int sol,
        @Query("page") int page, @Query("api_key") String API_KEY);

    @GET("manifests/{rover}")
    Observable<MarsManifest> getMarsRoverManifest(@Path("rover") String rover, @Query("api_key") String API_KEY);
}
