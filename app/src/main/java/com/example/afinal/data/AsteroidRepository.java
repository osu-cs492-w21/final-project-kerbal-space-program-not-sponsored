package com.example.afinal.data;

import android.text.TextUtils;
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
    private static final String BASE_URL = "https://www.neowsapp.com/";

    private MutableLiveData<NeoFeed> neoFeed;
    private MutableLiveData<LoadingStatus> loadingStatus;

    private String currentStartDate;
    private String currentEndDate;

    private NeoFeedService neoFeedService;

    public AsteroidRepository(){
        this.neoFeed = new MutableLiveData<>();
        this.neoFeed.setValue(null);

        this.loadingStatus = new MutableLiveData<>();
        this.loadingStatus.setValue(LoadingStatus.SUCCESS);

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

    public LiveData<LoadingStatus> getLoadingStatus(){
        return this.loadingStatus;
    }

    public void loadNeoFeed(String startDate, String endDate, String apiKey){
        if(shouldFetchNeoFeed(startDate, endDate)) {
            this.currentStartDate = startDate;
            this.currentEndDate = endDate;
            this.neoFeed.setValue(null);
            this.loadingStatus.setValue(LoadingStatus.LOADING);
            Log.d(TAG, "getting new feed for this query: " + startDate);
            Call<NeoFeed> req = this.neoFeedService.fetchNeoFeed(startDate, endDate, apiKey);
//            Log.d(TAG, "getting new feed for this call: " + req);
            req.enqueue(new Callback<NeoFeed>() {
                @Override
                public void onResponse(Call<NeoFeed> call, Response<NeoFeed> response) {
                    if(response.code() == 200) {
                        neoFeed.setValue(response.body());
                        loadingStatus.setValue(LoadingStatus.SUCCESS);
                    }
                    else {
                        loadingStatus.setValue(LoadingStatus.ERROR);
                        Log.d(TAG, "unsuccessful API request(code!=200): " + call.request().url());
                        Log.d(TAG, "----response status code: " + response.code());
                        Log.d(TAG, "----response: " + response.toString());
                    }
                }

                @Override
                public void onFailure(Call<NeoFeed> call, Throwable t) {
                    Log.d(TAG, "unsuccessful API request(failure): " + call.request().url());
                    t.printStackTrace();
                    loadingStatus.setValue(LoadingStatus.ERROR);
                }
            });
        }
        else {
            Log.d(TAG, "using cached results for these parameters. startDate: " + startDate + "  endDate: " + endDate);
        }



    }

    private boolean shouldFetchNeoFeed(String startDate, String endDate) {
        /*
         * Fetch NeoFeed if there isn't currently one stored.
         */
        NeoFeed currentNeoFeed = this.neoFeed.getValue();
        if (currentNeoFeed == null) {
            return true;
        }

        /*
         * Fetch NeoFeed if there was an error fetching the last one.
         */
        if (this.loadingStatus.getValue() == LoadingStatus.ERROR) {
            return true;
        }

        /*
         * Fetch NeoFeed if either of the dates have changed
         */
        if (!TextUtils.equals(startDate, this.currentStartDate) || !TextUtils.equals(endDate, this.currentEndDate)) {
            return true;
        }

        return false;
    }

}
