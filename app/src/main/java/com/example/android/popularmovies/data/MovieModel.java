package com.example.android.popularmovies.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lenovo on 21/02/2018.
 */

public class MovieModel implements Parcelable {

    public String title, releaseDate, rate, overview, poster, cover;
    public long id;

    public MovieModel(String movieName , String movieYear , String movieRate ,String movieDescription, String poster, String cover, long id)
    {
        this.title = movieName ;
        this.releaseDate = movieYear ;
        this.rate = movieRate ;
        this.overview = movieDescription ;
        this.poster =poster;
        this.cover=cover;
        this.id = id;
    }

    protected MovieModel(Parcel in) {
        title = in.readString();
        releaseDate = in.readString();
        rate = in.readString();
        overview = in.readString();
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
        parcel.writeString(title);
        parcel.writeString(releaseDate);
        parcel.writeString(rate);
        parcel.writeString(overview);
        parcel.writeString(poster);
        parcel.writeString(cover);
        parcel.writeLong(id);
    }
}
