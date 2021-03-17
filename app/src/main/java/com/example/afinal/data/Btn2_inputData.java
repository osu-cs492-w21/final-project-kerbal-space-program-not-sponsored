package com.example.afinal.data;

import android.text.Editable;

public class Btn2_inputData {

    private Editable latitude;
    private Editable longitude;
    private Editable date;

    public Btn2_inputData(Editable text, Editable text1, Editable text2) {

        this.latitude = text;
        this.longitude = text1;
        this.date = text2;

    }

    public Editable getLatitude(){
        return this.latitude;
    }
    public Editable getLongitude(){
        return this.longitude;
    }

    public Editable getDate(){
        return this.date;
    }
}
