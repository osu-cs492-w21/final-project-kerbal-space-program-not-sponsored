package com.example.afinal;

import android.graphics.Picture;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;

public class PotdUtils {
    private static final String TAG = MainActivity.class.getSimpleName();


    public static PictureOfTheDay parsePicture(String json){
        Gson gson = new Gson();
        PictureOfTheDay results = gson.fromJson(json, PictureOfTheDay.class);
        if(results !=null ){
            //Log.d(TAG, " size of results: " + results.pictureOfTheDay.size());
            Log.d(TAG, " link: " + results.getUrl());
            return results;
        }
        else{
            Log.d(TAG, "returning null");
            return null;
        }
    }
}
