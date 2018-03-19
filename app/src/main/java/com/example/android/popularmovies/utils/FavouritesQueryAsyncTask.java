package com.example.android.popularmovies.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.android.popularmovies.data.FavouritesContract;

import java.net.URI;

/**
 * Created by Lenovo on 19/03/2018.
 */

public class FavouritesQueryAsyncTask extends AsyncTask<Uri,Void,Cursor> {

    AsyncTaskCompleteListener listener;
    Context context;

    public FavouritesQueryAsyncTask(Context context ,AsyncTaskCompleteListener listener){
        this.context=context;
        this.listener=listener;
    }

    @Override
    protected Cursor doInBackground(Uri... uri1s) {
        Uri queryUri = uri1s[0];
        try{
            return context.getContentResolver().query(queryUri,
                    null,
                    null,
                    null,
                    null,
                    null);

        }catch (Exception e){
            Log.e("query","failed");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Cursor cursor) {
        super.onPostExecute(cursor);
        if(cursor !=null ){
            listener.onFavouritesTaskComplete(cursor);
        }else {
            listener.errorMessage("returned cursor in null");
        }
    }
}
