package com.example.android.popularmovies.utils;

import android.content.Context;
import android.os.AsyncTask;

import com.example.android.popularmovies.data.TrailerModel;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 06/03/2018.
 */

public class TrailerJsonAsyncTask extends AsyncTask <URL, Void, String> {
    List<TrailerModel> trailers = new ArrayList<>();
    AsyncTaskCompleteListener listener ;
    Context context;

   public TrailerJsonAsyncTask (Context context,AsyncTaskCompleteListener listener) {
       this.context=context;
       this.listener=listener;
   }
    @Override
    protected String doInBackground(URL... urls) {
       URL searchUrl = urls[0];
       String trailerJson ="";
        try {
            trailerJson = NetworkUtils.getResponseFromHttpUrl(searchUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return trailerJson;
    }
    @Override
    protected void onPostExecute(String trailerJson) {
        if (trailerJson !=null && trailerJson !=""){
            try {
                 trailers=NetworkUtils.readTrailerJsonData(trailerJson);
                 listener.onTrailerJsonTaskComplete(trailers);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            listener.errorMessage("Json file is empty");
        }
    }
}
