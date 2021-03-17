package com.example.afinal;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.afinal.data.AsteroidRepository;
import com.example.afinal.data.LoadingStatus;
import com.example.afinal.data.NeoFeed;

public class NeoFeedViewModel extends ViewModel {
    private AsteroidRepository repository;
    private LiveData<NeoFeed> neoFeed;
    private LiveData<LoadingStatus> loadingStatus;

    public NeoFeedViewModel() {
        this.repository = new AsteroidRepository();
        this.neoFeed = repository.getNeoFeed();
        this.loadingStatus = repository.getLoadingStatus();
    }

    public LiveData<NeoFeed> getNeoFeed() {
        return this.neoFeed;
    }

    public LiveData<LoadingStatus> getLoadingStatus() {
        return this.loadingStatus;
    }

    public void loadNeoFeed(String date, String apiKey) {
        this.repository.loadNeoFeed(date, date, apiKey);
    }
}
