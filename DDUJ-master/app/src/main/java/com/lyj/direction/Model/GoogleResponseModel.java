package com.lyj.direction.Model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lyj.direction.Model.GooglePlaceModel;

import java.util.List;
public class GoogleResponseModel {
   @SerializedName("result")
    @Expose
    private List<GooglePlaceModel>googlePlaceModelList;
    @SerializedName("error_message")
    @Expose
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<GooglePlaceModel>getGooglePlaceModelList(){

        return googlePlaceModelList;
   }
   public void setGooglePlaceModelList(List<GooglePlaceModel>googlePlaceModelList){
       this.googlePlaceModelList=googlePlaceModelList;
   }
}
