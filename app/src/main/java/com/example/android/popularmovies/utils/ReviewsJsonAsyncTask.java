package com.example.android.popularmovies.utils;

import android.content.Context;
import android.os.AsyncTask;

import com.example.android.popularmovies.data.ReviewsModel;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 12/03/2018.
 */

public class ReviewsJsonAsyncTask extends AsyncTask<URL, Void, String> {
    List<ReviewsModel> reviews = new ArrayList<>();
    AsyncTaskCompleteListener listener ;
    Context context;

    public  ReviewsJsonAsyncTask (Context context, AsyncTaskCompleteListener listener){
        this.context=context;
        this.listener=listener;
    }

    @Override
    protected String doInBackground(URL... urls) {

        URL searchUrl = urls[0];
        String reviewsJson="";
        try {
            reviewsJson =NetworkUtils.getResponseFromHttpUrl(searchUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reviewsJson;
    }

    @Override
    protected void onPostExecute(String reviewsJson) {
        super.onPostExecute(reviewsJson);
        if(reviewsJson != null && reviewsJson !="")
        {
            try {
                reviews = NetworkUtils.readReviewsJsonData(reviewsJson);
                listener.onReviewsJsonTaskComplete(reviews);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
