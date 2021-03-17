package com.example.afinal;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.afinal.data.AsteroidData;
import com.example.afinal.data.LoadingStatus;
import com.example.afinal.data.NeoFeed;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

//Find asteroids near you
public class button4 extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = button4.class.getSimpleName();

    private AsteroidAdapter asteroidAdapter;
    private NeoFeedViewModel neoFeedViewModel;
    private Map<String, List<AsteroidData>> nearEarthObjects;
    private String queryDate;
//    private String displayDate;

    private TextView dateBannerTV;
    private RecyclerView asteroidListRV;
    private TextView errorMessageTV;
    //loading indicator
    //error message

//    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Asteroids Near You");
        setContentView(R.layout.activity_button4);

//        back = (Button)findViewById(R.id.back);


//        back.setOnClickListener(this);
        this.queryDate = getTodaysDate();
        //loading icon
        //error message
        this.dateBannerTV = findViewById(R.id.tv_date_banner);
        dateBannerTV.setText("TODAY");
        this.errorMessageTV = findViewById(R.id.tv_error_message);
        this.asteroidListRV = findViewById(R.id.rv_asteroid_list);
        this.asteroidListRV.setLayoutManager(new LinearLayoutManager(this));
        this.asteroidListRV.setHasFixedSize(true);
        //layout manager?
        //fixed size?

        this.asteroidAdapter = new AsteroidAdapter();//add clicklistener
        this.asteroidListRV.setAdapter(this.asteroidAdapter);


        this.neoFeedViewModel = new ViewModelProvider(this)
                .get(NeoFeedViewModel.class);
        this.loadNeoFeed();

        /*
         * Update UI to reflect newly fetched asteroid data.
         */
        Log.d(TAG, "NeoFeed update time");
        this.neoFeedViewModel.getNeoFeed().observe(
                this,
                new Observer<NeoFeed>() {
                    @Override
                    public void onChanged(NeoFeed neoFeed) {
                        Log.d(TAG, "neoFeed is null?: " + neoFeed);
                        if(neoFeed != null) {
                            nearEarthObjects = neoFeed.getNearEarthObjects();
                            asteroidAdapter.updateAsteroidList((ArrayList<AsteroidData>) nearEarthObjects.get(queryDate));
                            Log.d(TAG, "nearEarthObjects has been updated: " + nearEarthObjects.keySet());
//                            Log.d(TAG, "Is the current date in the map?: " + nearEarthObjects.get(queryDate));
                            dateBannerTV.setText(queryDate);
                        }
                    }
                }

        );
        this.neoFeedViewModel.getLoadingStatus().observe(
                this,
                new Observer<LoadingStatus>() {
                    @Override
                    public void onChanged(LoadingStatus loadingStatus) {
                        if (loadingStatus == LoadingStatus.LOADING) {
//                            loadingIndicatorPB.setVisibility(View.VISIBLE);
                        } else if (loadingStatus == LoadingStatus.SUCCESS) {
//                            loadingIndicatorPB.setVisibility(View.INVISIBLE);
                            asteroidListRV.setVisibility(View.VISIBLE);
                            errorMessageTV.setVisibility(View.INVISIBLE);
                        } else {
//                            loadingIndicatorPB.setVisibility(View.INVISIBLE);
                            asteroidListRV.setVisibility(View.INVISIBLE);
                            errorMessageTV.setVisibility(View.VISIBLE);
                            errorMessageTV.setText(getString(
                                    R.string.button4_loading_error,
                                    "NEO(near earth object) data",
                                    "ヽ(。_°)ノ"
                            ));
                        }
                    }
                }
        );


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.back:
                Intent intent1 = new Intent(this, buttonPage.class);
                startActivity(intent1);
        }
    }

    private void loadNeoFeed() {
        this.neoFeedViewModel.loadNeoFeed(
                this.queryDate,
                "DEMO_KEY"
        );
    }
    private String getTodaysDate(){
        Date date = new Date();
        String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
        return dateStr;
    }
}