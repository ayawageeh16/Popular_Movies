package com.example.android.popularmovies.view;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.adapter.FavouritesAdapter;
import com.example.android.popularmovies.data.FavouritesContract;
import com.example.android.popularmovies.data.FavouritesDBHelper;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

public class Favourites extends AppCompatActivity {

    RecyclerView recyclerView ;
    SQLiteDatabase mDB;
    FavouritesAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        recyclerView = findViewById(R.id.favourites_rv);
        setRecyclerView(recyclerView);
        FavouritesDBHelper dbHelper = new FavouritesDBHelper(this);
        mDB =dbHelper.getReadableDatabase();
        Cursor cursor = getAllFavourites();
        adapter=new FavouritesAdapter(cursor);
        recyclerView.setAdapter(adapter);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
               long movieId = (long) viewHolder.itemView.getTag();
               deleteFavourite(movieId);
               adapter.swapCursor(getAllFavourites());
            }
        }).attachToRecyclerView(recyclerView);


    }
    private void setRecyclerView (RecyclerView recyclerView){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,VERTICAL,true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }
    private Cursor getAllFavourites () {
        return mDB.query(FavouritesContract.FavouritesEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
    }
    private boolean deleteFavourite(long movieId){
       return mDB.delete(FavouritesContract.FavouritesEntry.TABLE_NAME,
                FavouritesContract.FavouritesEntry.COLUMN_MOVIE_ID+ "=" +movieId,null) >0;
    }

}
