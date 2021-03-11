package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class buttonPage extends AppCompatActivity implements View.OnClickListener {
    Button button1;
    Button button2;
    Button button3;
    Button button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_page);

        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        button4 = (Button)findViewById(R.id.button4);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button1:
                Toast.makeText(this,"Button 1 clicked",Toast.LENGTH_LONG);
                Intent intent1 = new Intent(this, button1.class);
                startActivity(intent1);
                break;

            case R.id.button2:
                Toast.makeText(this,"Button 2 clicked",Toast.LENGTH_SHORT);
                Intent intent2 = new Intent(this, button2.class);
                startActivity(intent2);
                break;

            case R.id.button3:
                Toast.makeText(this,"Button 3 clicked",Toast.LENGTH_SHORT);
                Intent intent3 = new Intent(this, button3.class);
                startActivity(intent3);
                break;

            case R.id.button4:
                Toast.makeText(this,"Button 4 clicked",Toast.LENGTH_SHORT);
                Intent intent4 = new Intent(this, button4.class);
                startActivity(intent4);
                break;
        }
    }
}