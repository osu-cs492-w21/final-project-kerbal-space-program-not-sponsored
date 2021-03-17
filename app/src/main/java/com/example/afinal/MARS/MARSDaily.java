package com.example.afinal.MARS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.afinal.R;
import com.example.afinal.button3;
import com.example.afinal.buttonPage;

public class MARSDaily extends AppCompatActivity implements View.OnClickListener {
    Button backB;
    Button homeB;
    Button searchB;
    Button Perseverance, Curiosity, Opportunity, Spirit;
    EditText daySolET;
    private String marsRover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mars_daily);
        setTitle("Mars Rover Photos");

        //buttons to go to previous page and button page
        backB = findViewById(R.id.backdaily3);
        backB.setOnClickListener(this);
        homeB = findViewById(R.id.homedaily3);
        homeB.setOnClickListener(this);

        //Rover buttons Click to see pictures and rover info
        Perseverance = (Button)findViewById(R.id.perseveranceB);
        Perseverance.setOnClickListener(this);
        Curiosity = (Button)findViewById(R.id.curiosityB);
        Curiosity.setOnClickListener(this);
        Opportunity = (Button)findViewById(R.id.opportunityB);
        Opportunity.setOnClickListener(this);
        Spirit = (Button)findViewById(R.id.spiritB);
        Spirit.setOnClickListener(this);

        //sol entered
        daySolET = findViewById(R.id.solDay);
 
        //search button collects sol info and seaches for picture on the sol day
        searchB = findViewById(R.id.solSearch);
        searchB.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        //different buttons and what they do
        //rover buttons store string name as the rovers name
        //search button searches for info for the rover and its pictures
        switch(v.getId()) {
            case R.id.backdaily3:
                Intent intent1 = new Intent(this, button3.class);
                startActivity(intent1);
                break;
            case R.id.homedaily3:
                Intent intent2 = new Intent(this, buttonPage.class);
                startActivity(intent2);
                break;
            case R.id.perseveranceB:
                marsRover = "perseverance";
                break;
            case R.id.curiosityB:
                marsRover = "curiosity";
                break;
            case R.id.opportunityB:
                marsRover = "opportunity";
                break;
            case R.id.spiritB:
                marsRover = "spirit";
                break;
            case R.id.solSearch:
                String sol = daySolET.getText().toString();
                //if the sol search bar is empty ask user to input in number
                if(TextUtils.isEmpty(sol)){
                    daySolET.setError("Please Enter Sol", getResources().getDrawable(R.drawable.error_outline));
                    daySolET.requestFocus();
                    return;
                }
                //turn the text in the search bar into Sol number and use it to find pictures of the day the rover was on mars
                int Sol = Integer.parseInt(sol);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentPhotos(Sol, marsRover)).commit();
        }
    }
}