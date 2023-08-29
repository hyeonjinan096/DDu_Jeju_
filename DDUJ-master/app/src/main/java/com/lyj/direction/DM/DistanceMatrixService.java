package com.lyj.direction.DM;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DistanceMatrixService {
    @GET("https://maps.googleapis.com/maps/api/distancematrix/json")
    Call<DistanceMatrixResponse> getDistanceMatrix(
            @Query("origins") String origins,
            @Query("destinations") String destinations,
            @Query("key") String apiKey,
            @Query("mode") String mode
    );
}