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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_about);

        movie_name = findViewById(R.id.movie_name);
        movie_icon = findViewById(R.id.ThumbnailImage);
        movie_overview = findViewById(R.id.movieOverview);

        MovieDetails details = (MovieDetails) getIntent().getExtras().getSerializable("MOVIE_DETAILS");

        if(details!=null)
        {
            Glide.with(this).load("https://image.tmdb.org/t/p/w500/"+details.getPoster_path()).into(movie_icon);
            movie_name.setText(details.getTitle());
            movie_overview.setText(details.getOverview());

            finish();
        }
    }
}
