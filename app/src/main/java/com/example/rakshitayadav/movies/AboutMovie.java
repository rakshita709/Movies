package com.example.rakshitayadav.movies;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class AboutMovie extends AppCompatActivity {

    TextView movie_name,movie_overview,date,rating;
    ImageView movie_icon;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_about);

        movie_name = findViewById(R.id.movie_name);
        movie_icon = findViewById(R.id.ThumbnailImage);
        movie_overview = findViewById(R.id.movieOverview);
        date = findViewById(R.id.movie_release_date);
        rating = findViewById(R.id.movie_rating);

        Long movieId=getIntent().getLongExtra("MOVIE_DETAILS",-1);
        if(movieId!=-1){
            new AsyncTask<Long, Void, String>() {
                @Override
                protected String doInBackground(Long... longs) {
                    Long id=longs[0];
                    URL url = null;
                    try {
                        url = new URL("https://api.themoviedb.org/3/movie/"+id+"?api_key=8865d55dc8ba55909f3dec9e6ab79d2f&language=en-US");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                    try {
                        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                        InputStream inputStream;
                        if(urlConnection.getResponseCode()!=404){
                            inputStream = urlConnection.getInputStream();
                        }
                        else {
                            inputStream = urlConnection.getErrorStream();
                            Log.e("RESPONSE", "doInBackground: "+urlConnection.getResponseMessage() );
                        }
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        String s = bufferedReader.readLine();
                        bufferedReader.close();

                        return s;
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("Error: ",e.getMessage(),e);
                    }
                    return  null;
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    MovieDetails movieDetails=null;
                    JSONObject obj = null;
                    try {
                        movieDetails=new MovieDetails();
                        obj = new JSONObject(s);
                        movieDetails.setTitle(obj.getString("title"));
                        movieDetails.setOverview(obj.getString("overview"));
                        movieDetails.setPoster_path(obj.getString("poster_path"));
                        movieDetails.setRelease_date(obj.getString("release_date"));
                        movieDetails.setVote_average(obj.getString("vote_average"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if(movieDetails!=null)
                    {
                        Glide.with(AboutMovie.this).load("https://image.tmdb.org/t/p/w500/"+movieDetails.getPoster_path()).into(movie_icon);
                        movie_name.setText(movieDetails.getTitle());
                        movie_overview.setText(movieDetails.getOverview());
                        date.setText(movieDetails.getRelease_date());
                        rating.setText(movieDetails.getVote_average());
                    }
                }
            }.execute(movieId);
        }


    }
}
