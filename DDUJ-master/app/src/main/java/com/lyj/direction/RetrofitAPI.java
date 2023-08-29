package com.lyj.direction;
import com.lyj.direction.Model.DirectionResponseModel;
import com.lyj.direction.Model.GoogleResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RetrofitAPI {
    @GET
    Call<GoogleResponseModel>getNearByPlaces(@Url String url);
    @GET
    Call<DirectionResponseModel>getDirection(@Url String url);

}
