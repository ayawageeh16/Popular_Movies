package com.example.android.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lenovo on 12/03/2018.
 */

public class FavouritesDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="favourites.db";
    public static final int DATABASE_VERSION = 1;

    public FavouritesDBHelper (Context context){
    super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_FAVOURITES_TABLE ="CREATE TABLE "+ FavouritesContract.FavouritesEntry.TABLE_NAME +" ("
                                                 + FavouritesContract.FavouritesEntry.COLUMN_MOVIE_ID +" INTEGER PRIMARY KEY,"
                                                 + FavouritesContract.FavouritesEntry.COLUMN_MOVIE_TITLE +" TEXT,"
                                                 + FavouritesContract.FavouritesEntry.COLUMN_MOVIE_RELEASE_DATE +" TEXT,"
                                                 + FavouritesContract.FavouritesEntry.COLUMN_MOVIE_RATE +" TEXT,"
                                                 + FavouritesContract.FavouritesEntry.COLUMN_MOVIE_POSTER +" TEXT,"
                                                 + FavouritesContract.FavouritesEntry.COLUMN_MOVIE_COVER +" TEXT,"
                                                 + FavouritesContract.FavouritesEntry.COLUMN_MOVIE_OVERVIEW +" TEXT,"
                                                 +"UNIQUE"+" ("+ FavouritesContract.FavouritesEntry.COLUMN_MOVIE_ID + ") "
                                                 + "ON CONFLICT REPLACE" +");";
        sqLiteDatabase.execSQL(SQL_CREATE_FAVOURITES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ FavouritesContract.FavouritesEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
