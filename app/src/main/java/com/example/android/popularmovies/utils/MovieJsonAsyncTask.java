package com.example.android.popularmovies.utils;

import android.content.Context;
import android.os.AsyncTask;

import com.example.android.popularmovies.data.MovieModel;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 05/03/2018.
 */

public class MovieJsonAsyncTask extends AsyncTask<URL, Void, String> {

    List<MovieModel> movies = new ArrayList<MovieModel>();
    AsyncTaskCompleteListener listener;
    Context context;

    public MovieJsonAsyncTask(Context context, AsyncTaskCompleteListener listener) {
        this.listener=listener;
        this.context=context;
    }

    @Override
    protected String doInBackground(URL... urls) {
        URL SearchUrl = urls[0];
        String movieJson = "";
        try {
            movieJson = NetworkUtils.getResponseFromHttpUrl(SearchUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movieJson;
    }

    @Override
    protected void onPostExecute(String movieJson) {
        if (movieJson != null && !movieJson.equals("")) {
            try {
                movies = NetworkUtils.readMovieJsonData(movieJson);
                listener.onMovieJsonTaskComplete(movies);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            listener.errorMessage("Json file is empty");
        }
    }
}

