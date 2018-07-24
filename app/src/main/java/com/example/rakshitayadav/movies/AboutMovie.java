package com.example.rakshitayadav.movies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class AboutMovie extends AppCompatActivity {

    TextView movie_name,movie_overview;
    ImageView movie_icon;

    String errormsg="msg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_movie);

        movie_name = findViewById(R.id.movie_name);
        movie_icon = findViewById(R.id.movie_thumbnail);
        movie_overview = findViewById(R.id.movie_overview);

        Log.e("Error in aboutMovie: 5 ", errormsg);

        MovieDetails details = (MovieDetails) getIntent().getExtras().getSerializable("MOVIE DETAILS");

        Log.e("Error in aboutMovie: 6 ", errormsg);
        if(details!=null)
        {
            Glide.with(this).load("https://image.tmdb.org/t/p/w500/"+details.getPoster_path()).into(movie_icon);
            Log.e("Error in aboutMovie: 7 ", errormsg);
            movie_name.setText(details.getTitle());
            Log.e("Error in aboutMovie: ", errormsg);
            movie_overview.setText(details.getOverview());
            Log.e("Error in aboutMovie: ", errormsg);
            finish();
        }


    }
}
