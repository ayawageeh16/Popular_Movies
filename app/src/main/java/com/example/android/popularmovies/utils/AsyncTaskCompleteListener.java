package com.example.android.popularmovies.utils;

import com.example.android.popularmovies.data.MovieModel;
import com.example.android.popularmovies.data.TrailerModel;

import java.util.List;

/**
 * Created by Lenovo on 05/03/2018.
 */

public interface AsyncTaskCompleteListener{
    void onMovieJsonTaskComplete(List<MovieModel> movies);
    void onTrailerJsonTaskComplete(List<TrailerModel> trailers);
    void errorMessage(String errorMessage);
}
