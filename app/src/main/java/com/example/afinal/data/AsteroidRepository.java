package com.example.afinal.data;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AsteroidRepository {
    private static final String TAG = AsteroidRepository.class.getSimpleName();
    private static final String BASE_URL = "http://www.neowsapp.com/";

    private MutableLiveData<NeoFeed> neoFeed;
//    private MutableLiveData<LoadingStatus> loadingStatus;

    private String currentDate;

    private NeoFeedService neoFeedService;

    public AsteroidRepository(){
        this.neoFeed = new MutableLiveData<>();
        this.neoFeed.setValue(null);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(AsteroidData.class, new AsteroidData.JsonDeserializer())
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        this.neoFeedService = retrofit.create(NeoFeedService.class);
    }

    public LiveData<NeoFeed> getNeoFeed() {
        return this.neoFeed;
    }

    public void loadNeoFeed(String startDate, String endDate, String apiKey){
        this.neoFeed.setValue(null);
        Log.d(TAG, "getting new feed for this query: " + startDate + apiKey);

        Call<NeoFeed> req = this.neoFeedService.fetchNeoFeed(startDate, endDate, apiKey);
        Log.d(TAG, "getting new feed for this call: " + req);
        req.enqueue(new Callback<NeoFeed>() {
            @Override
            public void onResponse(Call<NeoFeed> call, Response<NeoFeed> response) {
                if(response.code() == 200) {
                    neoFeed.setValue(response.body());
                    //loading status value set success
                }
                else {
                    //loading status value set error
                    Log.d(TAG, "unsuccessful API request: " + call.request().url());
                    Log.d(TAG, "  -- response status code: " + response.code());
                    Log.d(TAG, "  -- response: " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<NeoFeed> call, Throwable t) {
                //loading status value set error
                Log.d(TAG, "unsuccessful API request: " + call.request().url());
                t.printStackTrace();
            }
        });
    }

}
