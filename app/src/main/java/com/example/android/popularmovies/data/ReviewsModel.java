package com.example.android.popularmovies.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lenovo on 12/03/2018.
 */

public class ReviewsModel implements Parcelable{

    String author, content;

    public ReviewsModel (String author , String content){
        this.author=author;
        this.content=content;
    }

    protected ReviewsModel(Parcel in) {
        author = in.readString();
        content = in.readString();
    }

    public static final Creator<ReviewsModel> CREATOR = new Creator<ReviewsModel>() {
        @Override
        public ReviewsModel createFromParcel(Parcel in) {
            return new ReviewsModel(in);
        }

        @Override
        public ReviewsModel[] newArray(int size) {
            return new ReviewsModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(author);
        parcel.writeString(content);
    }
}
