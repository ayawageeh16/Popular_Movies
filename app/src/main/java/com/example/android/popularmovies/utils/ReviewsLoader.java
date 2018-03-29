package com.example.android.popularmovies.utils;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.android.popularmovies.data.ReviewsModel;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 29/03/2018.
 */

public class ReviewsLoader extends AsyncTaskLoader <List<ReviewsModel>> {

    String reviewsJson="";
    URL SearchQuery ;
    List<ReviewsModel> reviews = new ArrayList<>();
     int movieId ;
    public ReviewsLoader(Context context, int movieId) {
        super(context);
        this.movieId =movieId;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<ReviewsModel> loadInBackground() {
        try {
             SearchQuery = NetworkUtils.buildURL(String.valueOf(movieId) + "/" + "reviews");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            reviewsJson =NetworkUtils.getResponseFromHttpUrl(SearchQuery);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(reviewsJson != null && reviewsJson !="")
        {
            try {
                reviews = NetworkUtils.readReviewsJsonData(reviewsJson);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return reviews;
    }
    @Override
    public void deliverResult(List<ReviewsModel> reviews) {
        super.deliverResult(reviews);
    }
}
