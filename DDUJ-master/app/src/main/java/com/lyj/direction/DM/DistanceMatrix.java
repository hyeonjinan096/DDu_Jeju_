package com.lyj.direction.DM;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DistanceMatrix {
    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/distancematrix/";
    private static final String API_KEY = "AIzaSyByRMe8nJ5cp0bKSKcpQLM5URiu07U1DvQ";

    private static DistanceMatrixService service;

    public DistanceMatrix() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(DistanceMatrixService.class);
    }

    public static void getDistanceAndDuration(String origin, String destination, Callback<DistanceMatrixResponse> callback) {
        Call<DistanceMatrixResponse> call = service.getDistanceMatrix(origin, destination, API_KEY,"transit");
        call.enqueue(callback);
    }
}