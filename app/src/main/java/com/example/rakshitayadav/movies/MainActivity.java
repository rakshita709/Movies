package com.example.rakshitayadav.movies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    float x1,x2,y1,y2;
    ProgressBar progressBar;
    GridView gridView;
    TextView page;
    static int PAGE_NUMBER = 1;
    MovieAdapter movieAdapter;
    ArrayList<MovieDetails> movieList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        page=findViewById(R.id.change_page);

        gridView = findViewById(R.id.gridView);
        movieAdapter = new MovieAdapter(MainActivity.this, R.layout.view_movie_list, movieList);
        gridView.setAdapter(movieAdapter);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        gridView.setVisibility(View.GONE);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, About_Movie.class);
                intent.putExtra("MOVIE_DETAILS", movieList.get(position).getId());
                startActivity(intent);
            }
        });

        new CheckStatus()
                .execute("https://api.themoviedb.org/3/movie/popular?api_key=8865d55dc8ba55909f3dec9e6ab79d2f&language=en-US&page="
                        + PAGE_NUMBER);

    }

    public void page_Change(View view )
    {
        PAGE_NUMBER++;
        new CheckStatus().execute("https://api.themoviedb.org/3/movie/popular?api_key=8865d55dc8ba55909f3dec9e6ab79d2f&language=en-US&page=" + PAGE_NUMBER);
    }

    class CheckStatus extends AsyncTask<String,Void,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... strings) {
            URL url = null;
            try {
                url = new URL(strings[0]);
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


            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(s);

                JSONArray jsonArray = jsonObject.getJSONArray("results");

                for (int i=0;i<jsonArray.length();i++)
                {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    MovieDetails movieDetails = new MovieDetails();
                    movieDetails.setId(obj.getLong("id"));
                    movieDetails.setTitle(obj.getString("title"));
                    movieDetails.setOverview(obj.getString("overview"));
                    movieDetails.setPoster_path(obj.getString("poster_path"));
                    movieList.add(movieDetails);


                }
                movieAdapter.notifyDataSetChanged();


            } catch (JSONException e) {
                e.printStackTrace();
            }
            progressBar.setVisibility(View.GONE);
            gridView.setVisibility(View.VISIBLE);

        }
    }

    public boolean onTouchEvent(MotionEvent touchEvent)
    {
        switch ((touchEvent.getAction()))
        {
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;

            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();

                if(x1>x2){
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this,TVShows_main.class);
                    startActivity(intent);
                }
                else  if(x1<x2){
                    Intent intent = new Intent();
                    intent.setClass(this,TVShows_main.class);
                    startActivity(intent);
                }
                break;
        }

        return false;
    }

}
