package com.example.afinal.MARS;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MarsPhotos {
    @SerializedName("photos")
    @Expose
    private List<RoverPhotos> photos;

    public void setPhotos(List<RoverPhotos> photos){
        this.photos = photos;
    }

    public List<RoverPhotos> getPhotos(){
        return photos;
    }
}
