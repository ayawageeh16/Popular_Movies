package com.example.android.popularmovies.data;

import android.provider.BaseColumns;

/**
 * Created by Lenovo on 12/03/2018.
 */

public class FavouritesContract {

    public static final class FavouritesEntry implements BaseColumns{
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
