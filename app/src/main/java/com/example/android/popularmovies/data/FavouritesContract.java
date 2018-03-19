package com.example.android.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

import java.security.PublicKey;

/**
 * Created by Lenovo on 12/03/2018.
 */

public class FavouritesContract {

    public static final String AUTHORITY ="com.example.android.popularmovies";
    public static final Uri BASE_CONTENT_URI= Uri.parse("content://"+AUTHORITY);
    public static final String PATH_FAVOURITES="favourites";

    public static final class FavouritesEntry implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVOURITES).build();
        public static final String TABLE_NAME = "favourites";
        public static final String COLUMN_MOVIE_ID = "id" ;
        public static final String COLUMN_MOVIE_TITLE = "title" ;
        public static final String COLUMN_MOVIE_RELEASE_DATE = "releaseDate" ;
        public static final String COLUMN_MOVIE_OVERVIEW = "overview" ;
        public static final String COLUMN_MOVIE_RATE = "rate" ;
        public static final String COLUMN_MOVIE_POSTER = "posterPath" ;
        public static final String COLUMN_MOVIE_COVER = "coverPath" ;
    }
}
