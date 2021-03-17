package com.example.afinal.utils;

import android.net.Uri;
import android.text.Editable;

import com.google.gson.Gson;

public class Button_2_utils {
    static String EARTH_URL = "https://api.nasa.gov/planetary/earth/assets";
    static String EARTH_LON = "lon";
    static String EARTH_LAT = "lat";
    static String EARTH_DATE = "date";
    static String EARTH_DIMNESS = "dim";
    static String EARTH_DIMNESS_VALUE = "0.15";
    static String EARTH_API_KEY = "api_key";

    private static class EarthSearchResults {
        public String url;
    }

    public static String buildEarthURL(Editable lon , Editable lat , Editable date){
        return  Uri.parse(EARTH_URL).buildUpon()
                .appendQueryParameter(EARTH_LON , String.valueOf(lon))
                .appendQueryParameter(EARTH_LAT, String.valueOf(lat))
                .appendQueryParameter(EARTH_DATE, String.valueOf(date))
                .appendQueryParameter(EARTH_DIMNESS,EARTH_DIMNESS_VALUE)
                .appendQueryParameter(EARTH_API_KEY, "wNhNmYGWNzRmvr4vJYsx6Z4HXDZVvDaZ3QnXTKFx")
                .build()
                .toString();
    }

    public static String parseEarthData(String json){
        Gson gson = new Gson();
        EarthSearchResults results = gson.fromJson(json,EarthSearchResults.class);
        return results !=null ? results.url :null;
    }
}
