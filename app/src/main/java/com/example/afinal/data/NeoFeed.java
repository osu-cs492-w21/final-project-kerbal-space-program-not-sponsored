package com.example.afinal.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class NeoFeed {
    @SerializedName("near_earth_objects")
    private Map<String, List<AsteroidData>> nearEarthObjects;

}
