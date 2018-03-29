package com.example.android.popularmovies.utils;

import android.net.Uri;
import android.text.TextUtils;

import com.example.android.popularmovies.data.MovieModel;
import com.example.android.popularmovies.data.ReviewsModel;
import com.example.android.popularmovies.data.TrailerModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Lenovo on 21/02/2018.
 */

public class NetworkUtils {

    //CONSTANTS
    final static String MOVIE_BASE_URL = "https://api.themoviedb.org/3/movie/";
    final static String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";
    final static String IMAGE_SIZE = "w185";
    final static String COVER_SIZE = "w200";

    //build URL
    public static URL buildURL(String sortBy) throws MalformedURLException {
        Uri builtURI = Uri.parse(MOVIE_BASE_URL + sortBy).buildUpon()
                .appendQueryParameter("api_key", "074d499d5f8f7cfc23a16f8d4936f80c")
                .build();
        URL url = new URL(builtURI.toString());
        return url;
    }

    //get HttpConnection
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream inputStream = httpURLConnection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");
            boolean hasNext = scanner.hasNext();
            if (hasNext) {
                return scanner.next();
            } else
                return null;
        } finally {
            httpURLConnection.disconnect();
        }
    }

    //read MovieJSON
    public static List<MovieModel> readMovieJsonData(String moviesJson) throws JSONException {
        List<MovieModel> movies = new ArrayList<>();
        if (TextUtils.isEmpty(moviesJson)) {
            return null;
        } else {
            JSONObject reader = new JSONObject(moviesJson);
            JSONArray resultArray = reader.getJSONArray("results");
            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject movie = resultArray.getJSONObject(i);
                String movieName = movie.getString("title");
                String year = movie.getString("release_date");
                String poster = IMAGE_BASE_URL + IMAGE_SIZE + movie.getString("poster_path");
                Double rate_double = movie.getDouble("vote_average");
                String rate = String.valueOf(rate_double);
                String description = movie.getString("overview");
                String cover = IMAGE_BASE_URL + COVER_SIZE + movie.getString("backdrop_path");
                long id = movie.getLong("id");
                movies.add(new MovieModel(movieName, year, rate, description, poster, cover, id));
            }
        }
        return movies;
    }

    public static List<TrailerModel> readTrailerJsonData(String trailerJson) throws JSONException {
        List<TrailerModel> trailers = new ArrayList<>();
        if (TextUtils.isEmpty(trailerJson)) {
            return null;
        } else {

            JSONObject reader = new JSONObject(trailerJson);
            JSONArray resultArray = reader.getJSONArray("results");
            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject trailer = resultArray.getJSONObject(i);
                String id = trailer.getString("id");
                String name = trailer.getString("name");
                String key = trailer.getString("key");
                String site = trailer.getString("site");
                String size = trailer.getString("size");
                String type = trailer.getString("type");
                trailers.add(new TrailerModel(id, name, key, site, size, type));
            }
            return trailers;
        }
    }

    public static List<ReviewsModel> readReviewsJsonData(String reviewsJson) throws JSONException {
        List<ReviewsModel> reviews = new ArrayList<>();
        if (TextUtils.isEmpty(reviewsJson)){
            return null;
        }else{
            JSONObject reader = new JSONObject(reviewsJson);
            JSONArray resultArray = reader.getJSONArray("results");
            for (int i=0; i<resultArray.length() ; i++) {
                JSONObject review = resultArray.getJSONObject(i);
                String author = review.getString("author");
                String content = review.getString("content");
                reviews.add(new ReviewsModel(author,content));
            }
            return reviews;
        }
    }
}
