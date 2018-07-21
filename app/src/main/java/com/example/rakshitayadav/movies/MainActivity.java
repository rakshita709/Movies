package com.example.rakshitayadav.movies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    TextView tv,tv1,tv2,tv3;
    //ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // progressBar = findViewById(R.id.progressBar);
        tv = findViewById(R.id.tv);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);

        new CheckStatus().execute("https://api.themoviedb.org/3/movie/550?api_key=8865d55dc8ba55909f3dec9e6ab79d2f");
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

            Map<String,Integer> map = new HashMap<>();

            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(s);
                tv.setText(jsonObject.getString("original_title"));
                tv1.setText(jsonObject.getString("budget"));

                JSONArray jsonArray = jsonObject.getJSONArray("production_companies");

                for (int i=0;i<jsonArray.length();i++)
                {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    map.put(obj.getString("name"),obj.getInt("id"));

                }

                tv2.setText("Fox 2000 Pictures");
                tv3.setText(String.valueOf(map.get("Fox 2000 Pictures"))); // jo int val ayi..usse string me parse kia and fir display

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
