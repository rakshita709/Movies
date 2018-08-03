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

import static com.example.rakshitayadav.movies.MainActivity.PAGE_NUMBER;

public class TVShows_main extends AppCompatActivity {

    float x1,x2,y1,y2;


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

                if(x1<x2){
                    Intent intent = new Intent();
                    intent.setClass(this,MainActivity.class);
                    startActivity(intent);
                }
                else  if(x1>x2){
                    Intent intent = new Intent();
                    intent.setClass(this,MainActivity.class);
                    startActivity(intent);
                }
                break;
        }

        return false;
    }

}
