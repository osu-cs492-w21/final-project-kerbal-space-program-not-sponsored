package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.afinal.data.Btn2_inputData;
import com.example.afinal.utils.Button_2_utils;
import com.example.afinal.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Calendar;

public class button2 extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = button2.class.getSimpleName();
    private Btn2_inputData btn2_inputData;
    private Button_2_utils button_2_utils;
    private String EarthUrl;

    EditText Lat_et;
    EditText Lon_et;
    EditText Date_et;
    Button back;
    Button Submit_button;
    DatePickerDialog picker;
    ImageView imageView;
    URL url;
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button2);
        back = (Button)findViewById(R.id.back);
        Date_et = findViewById(R.id.date_et);
        Lat_et = findViewById((R.id.lat_et));
        Lon_et = findViewById(R.id.lon_et);
        Submit_button = findViewById((R.id.submit_button));
        imageView = (ImageView) findViewById((R.id.iv));
        //this.todoEntryET = findViewById(R.id.et_todo_entry_box);
        back.setOnClickListener(this);
        Date_et.setOnClickListener(this);
        Submit_button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.back:
                Intent intent1 = new Intent(this, buttonPage.class);
                startActivity(intent1);
                break;

            case R.id.date_et:

                    final Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);

                    picker = new DatePickerDialog(button2.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOF, int dayOfMonth) {
                                    int month = monthOF + 1;
                                    Date_et.setText(year + "-" + month + "-" + dayOfMonth);
                                }
                            }, year, month, day);

                    picker.show();
                    break;

            case R.id.submit_button:

                if (Lat_et.getText() != null){
                   // Log.d(TAG , "Lat iss " + Date_et.getText());
                    this.btn2_inputData = new Btn2_inputData(Lat_et.getText() , Lon_et.getText(), Date_et.getText());
                    //Log.d(TAG, "LAT is "+ this.btn2_inputData.getLongitude());
                    btn2_url();
                }
        }

    }

    public void btn2_url(){
        String EarthURL = button_2_utils.buildEarthURL(
                btn2_inputData.getLongitude() ,
                btn2_inputData.getLatitude() ,
                btn2_inputData.getDate());
        new Btn_urlTask().execute(EarthURL);
    }


    public class Btn_urlTask extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String EarthURL = params[0];
            Log.d(TAG , "URL IS " + EarthURL);

            String results = null;
            try {
                results = NetworkUtils.doHttpGet(EarthURL);
            } catch (IOException e){
                e.printStackTrace();
            }

            Log.d(TAG, "query result is "+ results);
            return results;
        }

        @Override
        protected void onPostExecute(String results) {

            if (results != null){
               // ArrayList<String> searchResultList = new ArrayList<>();
                //searchResultList.add(results);
                String searchResults = button_2_utils.parseEarthData(results);
                EarthUrl = searchResults;
                Log.d(TAG , "URL is "+ EarthUrl);
                Picasso.get().load(EarthUrl).into(imageView);

            }
        }
    }


}
