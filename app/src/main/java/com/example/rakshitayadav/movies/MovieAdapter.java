package com.example.rakshitayadav.movies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MovieAdapter extends ArrayAdapter {

    private List<MovieDetails> movieDetailsList;
    private Context context;
    private int resource;

    public MovieAdapter(@NonNull Context context, int resource, @NonNull List<MovieDetails> movieDetails) {
        super(context, resource, movieDetails);

        this.context=context;
        this.resource=resource;
        this.movieDetailsList=movieDetails;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        MovieDetails details = movieDetailsList.get(position);

        View view = LayoutInflater.from(context).inflate(resource,parent,false);

        TextView movieName = view.findViewById(R.id.movie_name);
        ImageView thumbnail = view.findViewById(R.id.movie_thumbnail);

        movieName.setText(details.getTitle());
        Glide.with(context).load("https://image.tmdb.org/t/p/w500/"+details.getPoster_path()).into(thumbnail);

        return view;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return movieDetailsList.get(position);
    }
}
