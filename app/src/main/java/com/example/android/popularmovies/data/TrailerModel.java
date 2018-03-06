package com.example.android.popularmovies.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lenovo on 06/03/2018.
 */

public class TrailerModel implements Parcelable {

  public String id, name, key, site, size, type ;

   public TrailerModel(String id, String name, String key,String site, String size, String type){
       this.id =id;
       this.name =name;
       this.key = key;
       this.site = site;
       this.type =type;
   }

    protected TrailerModel(Parcel in) {
        id = in.readString();
        name = in.readString();
        key = in.readString();
        site = in.readString();
        size = in.readString();
        type = in.readString();
    }

    public static final Creator<TrailerModel> CREATOR = new Creator<TrailerModel>() {
        @Override
        public TrailerModel createFromParcel(Parcel in) {
            return new TrailerModel(in);
        }

        @Override
        public TrailerModel[] newArray(int size) {
            return new TrailerModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(key);
        parcel.writeString(site);
        parcel.writeString(size);
        parcel.writeString(type);
    }
}
