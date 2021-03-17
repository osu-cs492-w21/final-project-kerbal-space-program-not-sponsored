package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class button1 extends AppCompatActivity implements View.OnClickListener, AsyncResponse {
    Button back;
    private static final String TAG;

    static {
        TAG = MainActivity.class.getSimpleName();
    }
    PictureSearchTask myAsyncTask = new PictureSearchTask();

    public PictureOfTheDay picture;
    private ImageView imageView;
    private TextView title;
    private TextView copyright;
    private TextView date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button1);

        back = (Button)findViewById(R.id.back);
        Log.d(TAG, "oncreate");
        back.setOnClickListener(this);

        myAsyncTask.delegate =  this;
        myAsyncTask.execute("https://api.nasa.gov/planetary/apod?api_key=wNhNmYGWNzRmvr4vJYsx6Z4HXDZVvDaZ3QnXTKFx");

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.back:
                Intent intent1 = new Intent(this, buttonPage.class);
                startActivity(intent1);
        }
    }

    @Override
    public void processFinish(PictureOfTheDay output) {
        this.picture = output;
        Log.d(TAG, "process finish url: " + picture.url);
        String url = picture.url;
        //String url = "https://www.youtube.com/watch?v=WJua8eXLX9o";
        if(!url.contains("youtube")) {
            Log.d(TAG, "not a video");
            new DownloadImageTask((ImageView) findViewById(R.id.picture_of_the_day))
                    .execute(picture.url);

            imageView = findViewById(R.id.picture_of_the_day);
            imageView.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    Log.d(TAG, "click");
                }
            });
        }
        else{
            Log.d(TAG, " video");

            String videoId = getVideoId(url);
            Log.d(TAG, "video id: " + videoId);
            new DownloadImageTask((ImageView) findViewById(R.id.picture_of_the_day))
                    .execute("https://img.youtube.com/vi/" + videoId +"/maxresdefault.jpg");

            TextView videoNotif = findViewById(R.id.video_notification);
            videoNotif.setVisibility(View.VISIBLE);

            imageView = findViewById(R.id.picture_of_the_day);
            imageView.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    Log.d(TAG, "click");
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    Log.i("Video", "Video Playing....");
                }
            });

        }

        title = findViewById(R.id.picture_title);
        date = findViewById(R.id.picture_date);
        copyright = findViewById(R.id.picture_copyright);
        title.setText(picture.title);
        date.setText(picture.date);
        copyright.setText(picture.copyright);
    }

    public class PictureSearchTask extends AsyncTask<String, Void, String>{
        public AsyncResponse delegate = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            Log.d(TAG, "pulling data from "+url);
            String results = "test do in background";
            try {
                results = NetworkUtils.doHTTPGet(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return results;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //loadingIndicatorPB.setVisibility(View.INVISIBLE);
            Log.d(TAG, "in post execute" + s);
            if (s != null) {
                Log.d(TAG, "in post execute, s!=null");
                PictureOfTheDay test = PotdUtils.parsePicture(s);
                delegate.processFinish(test);
            }
            else {
                Log.d(TAG, "in post execute, s==null");
//                errorMessageTV.setVisibility(View.VISIBLE);
//                forecastListRV.setVisibility(View.INVISIBLE);
            }
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    public String getVideoId(String url){
        String pattern = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";

        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(url); //url is youtube url for which you want to extract the id.
        if (matcher.find()) {
            return matcher.group();
        }
        else{
            return null;
        }
    }

}