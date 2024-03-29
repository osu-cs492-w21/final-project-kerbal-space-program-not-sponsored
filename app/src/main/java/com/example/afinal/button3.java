package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.afinal.buttonPage;
import com.example.afinal.MARS;

public class button3 extends AppCompatActivity implements View.OnClickListener {
    Button back;
    Button marsPhotoB;
    TextView directionsTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button3);

        //buttons used
        marsPhotoB = (Button)findViewById(R.id.searching);
        marsPhotoB.setOnClickListener(this);
        setTitle("Mars Rover Photos");
        //back button in the header
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Instruction on how to use MARS info
        directionsTV = (TextView)findViewById(R.id.directions);
        directionsTV.setText("How to run this program:\n" +
                "\nFirst click on a RoverInfo (Perserverance, Curiosity, Opportunity, and Spirit), they are located at the top right corner.\n" +
                "\nSecond enter in a number for sol (duration of a solar day on Mars) in the search bar.\n" +
                "\nThird click search button to see image taken on that day, and its information.\n" +
                "\nFourth, click the circle 'i' button if you want to learn more information about the mars rover.\n");
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.searching:
                Intent intent3 = new Intent(this, MARS.class);
                startActivity(intent3);
                break;
        }
    }
}