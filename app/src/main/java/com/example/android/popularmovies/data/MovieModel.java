package com.example.android.popularmovies.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lenovo on 21/02/2018.
 */

public class MovieModel implements Parcelable {

    public String movieName , movieYear ,movieRate, movieDescription, poster, cover;
    public long id;

    public MovieModel(String movieName , String movieYear , String movieRate ,String movieDescription, String poster, String cover, long id)
    {
        this.movieName = movieName ;
        this.movieYear = movieYear ;
        this.movieRate = movieRate ;
        this.movieDescription = movieDescription ;
        this.poster =poster;
        this.cover=cover;
        this.id = id;
    }

    protected MovieModel(Parcel in) {
        movieName = in.readString();
        movieYear = in.readString();
        movieRate = in.readString();
        movieDescription = in.readString();
        poster = in.readString();
        cover=in.readString();
        id = in.readLong();
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(movieName);
        parcel.writeString(movieYear);
        parcel.writeString(movieRate);
        parcel.writeString(movieDescription);
        parcel.writeString(poster);
        parcel.writeString(cover);
        parcel.writeLong(id);
    }
}
