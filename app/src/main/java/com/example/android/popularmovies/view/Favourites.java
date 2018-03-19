package com.example.android.popularmovies.view;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.adapter.FavouritesAdapter;
import com.example.android.popularmovies.data.FavouritesContract;
import com.example.android.popularmovies.data.MovieModel;
import com.example.android.popularmovies.data.ReviewsModel;
import com.example.android.popularmovies.data.TrailerModel;
import com.example.android.popularmovies.utils.AsyncTaskCompleteListener;
import com.example.android.popularmovies.utils.FavouritesQueryAsyncTask;

import java.util.List;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

public class Favourites extends AppCompatActivity {

    RecyclerView recyclerView ;
    TextView error_tv;
    FavouritesAdapter adapter;

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        error_tv =findViewById(R.id.favourites_error_message);
        recyclerView = findViewById(R.id.favourites_rv);
        setRecyclerView(recyclerView);
        queryFavourites();
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
               long movieId = (long) viewHolder.itemView.getTag();
               deleteFavourite(movieId);
               queryFavourites();
            }
        }).attachToRecyclerView(recyclerView);
    }
    private void setRecyclerView (RecyclerView recyclerView){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,VERTICAL,true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }
    private void deleteFavourite(long movieId){
        String id = Long.toString(movieId);
        Uri uri = FavouritesContract.FavouritesEntry.CONTENT_URI;
        uri= uri.buildUpon().appendPath(id).build();
        getContentResolver().delete(uri,null,null);
    }
    private void queryFavourites(){
        Uri querySearch = FavouritesContract.FavouritesEntry.CONTENT_URI;
        new FavouritesQueryAsyncTask(this, new FetchData()).execute(querySearch);
    }
   public class FetchData implements AsyncTaskCompleteListener{

       @Override
       public void onMovieJsonTaskComplete(List<MovieModel> movies) {}

       @Override
       public void onTrailerJsonTaskComplete(List<TrailerModel> trailers) {}

       @Override
       public void onReviewsJsonTaskComplete(List<ReviewsModel> reviews) {}

       @Override
       public void onFavouritesTaskComplete(Cursor cursor) {
           adapter= new FavouritesAdapter(cursor);
           recyclerView.setAdapter(adapter);
       }
       @Override
       public void errorMessage(String errorMessage) {}
   }
}
