package com.example.flickster;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;


import com.example.flickster.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

import static com.loopj.android.http.AsyncHttpClient.log;

public class DetailActivity extends YouTubeBaseActivity {

    private static final String YOUTUBE_KEY = "AIzaSyCWNbH2iTue775UJk3ckfpgd1ABkebufYQ";
    private static final String TRALIERS  = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    TextView tvTitle;
    TextView tvOverview;
    RatingBar ratingBar;
    YouTubePlayerView playerView;


    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        tvTitle = findViewById(R.id.tvTitle);
        tvOverview = findViewById(R.id.tvOverview);
        ratingBar = findViewById(R.id.ratingBar);
        playerView = findViewById(R.id.player);

        String title = getIntent().getStringExtra("title");

        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        tvOverview.setText(movie.getOverview());
        ratingBar.setRating((float) movie.getRatings());
        tvTitle.setText(title);



        AsyncHttpClient client = new AsyncHttpClient();

        //making a get resqust with a call back function, overriding two classes
        client.get(String.format(TRALIERS, movie.getMovieId()), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try{
                    JSONArray results = response.getJSONArray("results");

                    if(results.length() == 0) {
                        return;
                    }
                    JSONObject trailer = results.getJSONObject(0);
                    String youtubeVideoKey = trailer.getString("key");
                    inititalizeVideo(youtubeVideoKey);
                }catch(JSONException i ){
                    i.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }

    //this method starts the movie trailer thats passed form the api
    private void inititalizeVideo(final String youtubeVideoKey) {
        //once you have  a reference to the video ui you have to initialize  by passing a key and callback function
        playerView.initialize(YOUTUBE_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d("smile","success");
                if(movie.getRatings() > 5) {
                    youTubePlayer.loadVideo(youtubeVideoKey);
                }else youTubePlayer.cueVideo(youtubeVideoKey);

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d("smile","we have failed");
            }
        });
    }

}
