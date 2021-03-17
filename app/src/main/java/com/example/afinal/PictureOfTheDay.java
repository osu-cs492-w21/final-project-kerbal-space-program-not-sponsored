package com.example.afinal;

public class PictureOfTheDay {
    public String url;
    public String title;
    public String copyright;
    public String date;

    public PictureOfTheDay(){
        this.url = null;
        this.title = null;
        this.copyright = null;
        this.date = null;
    }

    public PictureOfTheDay(String link, String title, String copyright, String date){
        this.url = link;
        this.title = title;
        this.copyright = copyright;
        this.date = date;
    }

    String getUrl(){
        return this.url;
    }
}


