package com.lyj.direction.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Geometry {

    @SerializedName("location")
    @Expose
    private LocationModel location;
    public LocationModel  getLocation() {
        return location;
    }

    public void setLocation(LocationModel  location) {
        this.location = location;
    }

}
