package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//Find asteroids near you
public class button4 extends AppCompatActivity implements View.OnClickListener {
    public static String TAG = button4.class.getSimpleName();

    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button4);

        back = (Button)findViewById(R.id.back);

        back.setOnClickListener(this);

        //loading icon
        //error message




    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.back:
                Intent intent1 = new Intent(this, buttonPage.class);
                startActivity(intent1);
        }
    }
}