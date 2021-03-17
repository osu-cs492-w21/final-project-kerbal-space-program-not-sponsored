package com.example.afinal;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.afinal.data.AsteroidRepository;
import com.example.afinal.data.NeoFeed;

public class NeoFeedViewModel extends ViewModel {
    private AsteroidRepository repository;
    private LiveData<NeoFeed> neoFeed;
    //loading status

    public NeoFeedViewModel() {
        this.repository = new AsteroidRepository();
        this.neoFeed = repository.getNeoFeed();
        //loading status
    }

    public LiveData<NeoFeed> getNeoFeed() {
        return this.neoFeed;
    }

    //return loading status

    public void loadNeoFeed(String date, String apiKey) {
        this.repository.loadNeoFeed(date, date, apiKey);
    }
}
