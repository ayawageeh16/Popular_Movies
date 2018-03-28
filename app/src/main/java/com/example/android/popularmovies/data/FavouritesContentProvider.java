package com.example.android.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Lenovo on 18/03/2018.
 */

public class FavouritesContentProvider extends ContentProvider {
    FavouritesDBHelper dbHelper;
    Context context;
    public final static int FAVOURITES=100;
    public final static int MOVIE_WITH_ID =101;
    public final static UriMatcher sUriMATCHER = buildUriMatcher ();

    private static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(FavouritesContract.AUTHORITY,FavouritesContract.PATH_FAVOURITES, FAVOURITES);
        uriMatcher.addURI(FavouritesContract.AUTHORITY,FavouritesContract.PATH_FAVOURITES +"/#", MOVIE_WITH_ID);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        context= getContext();
        dbHelper=new FavouritesDBHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
         final SQLiteDatabase db = dbHelper.getReadableDatabase();
         int matcher= sUriMATCHER.match(uri);
         Cursor returnCursor;

         switch (matcher){
             case FAVOURITES:
                   returnCursor=db.query(FavouritesContract.FavouritesEntry.TABLE_NAME,
                           null,
                           null,
                           null,
                           null,
                           null,
                           null);
                 break;
             case MOVIE_WITH_ID :
                 String id = uri.getPathSegments().get(1);
                 String mSelection = "id=?";
                 String[] mSelectionArgs = new String[]{id};
                 returnCursor =db.query(FavouritesContract.FavouritesEntry.TABLE_NAME,
                         null,
                          mSelection,
                          mSelectionArgs,
                         null,
                         null,
                         null );
                 break;
             default:
                 throw  new UnsupportedOperationException("unknown uri"+uri);
         }
        returnCursor.setNotificationUri(getContext().getContentResolver(),uri);
        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int match = sUriMATCHER.match(uri);
        Uri returnUri ;
        switch (match) {
            case FAVOURITES:
                    long id = db.insert(FavouritesContract.FavouritesEntry.TABLE_NAME, null, contentValues);
                    if (id > 0) {
                        returnUri = ContentUris.withAppendedId(FavouritesContract.FavouritesEntry.CONTENT_URI, id);
                    } else {
                        throw new SQLException("failed to insert new row into" + uri);}
                break;
            default:
                throw new UnsupportedOperationException("unknown Uri"+uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int match = sUriMATCHER.match(uri);
        int movieDeleted;

        switch (match){
            case MOVIE_WITH_ID:
                String id = uri.getPathSegments().get(1);
                movieDeleted =db.delete(FavouritesContract.FavouritesEntry.TABLE_NAME,"id=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if(movieDeleted !=0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return movieDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
