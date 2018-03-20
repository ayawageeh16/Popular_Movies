package com.example.android.popularmovies.view;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.adapter.ReviewsAdapter;
import com.example.android.popularmovies.adapter.TrailerAdapter;
import com.example.android.popularmovies.data.FavouritesContract;
import com.example.android.popularmovies.data.FavouritesDBHelper;
import com.example.android.popularmovies.data.MovieModel;
import com.example.android.popularmovies.data.ReviewsModel;
import com.example.android.popularmovies.data.TrailerModel;
import com.example.android.popularmovies.utils.AsyncTaskCompleteListener;
import com.example.android.popularmovies.utils.NetworkUtils;
import com.example.android.popularmovies.utils.ReviewsJsonAsyncTask;
import com.example.android.popularmovies.utils.TrailerJsonAsyncTask;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

public class MovieDetails extends AppCompatActivity {

    ImageView coverImg;
    ImageView posterImg;
    TextView movieTitle;
    TextView movieYear;
    TextView movieRate;
    TextView movieOverview;
    TextView trailersErrorMessageTv;
    TextView reviewsErrorMeassageTv;
    RecyclerView trailerRecyclerView ;
    RecyclerView reviewRecyclerView;
    ImageButton favouriteButton;
    MovieModel movie;
    String errorMessageDisplay;
    List<TrailerModel> trailersList = new ArrayList<>();
    List<ReviewsModel> reviewsList = new ArrayList<>();
    SQLiteDatabase mDB ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        movieTitle =findViewById(R.id.tv_title);
        coverImg = findViewById(R.id.img_cover);
        posterImg = findViewById(R.id.img_pos);
        movieYear = findViewById(R.id.tv_year);
        movieRate = findViewById(R.id.tv_rate);
        movieOverview =findViewById(R.id.tv_overview);
        favouriteButton=findViewById(R.id.favourite_btn);
        trailersErrorMessageTv =findViewById(R.id.trailer_error_tv);
        reviewsErrorMeassageTv = findViewById(R.id.reviews_error_tv);
        trailerRecyclerView =findViewById(R.id.trailers_rv);
        reviewRecyclerView =findViewById(R.id.reviews_rv);
        setTrailersRecyclerView(trailerRecyclerView);
        setReviewsRecyclerView(reviewRecyclerView);
        FavouritesDBHelper dbHelper = new FavouritesDBHelper(this);
        mDB = dbHelper.getWritableDatabase();

        Bundle bundle = getIntent().getExtras();
        movie = bundle.getParcelable("movie");

        Picasso.with(coverImg.getContext())
                .load(movie.cover)
                .into(coverImg);
        Picasso.with(posterImg.getContext())
                .load(movie.poster)
                .into(posterImg);
        movieTitle.setText(movie.title);
        movieYear.setText(movie.releaseDate);
        movieRate.setText(movie.rate);
        movieOverview.setText(movie.overview);


        try {
            trailersSearchQueryExecute();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            reviewsSearchQueryExecute();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    void setTrailersRecyclerView (RecyclerView recyclerView){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,VERTICAL,true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }
    void setReviewsRecyclerView (RecyclerView recyclerView){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,VERTICAL,true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }

    public void trailersSearchQueryExecute () throws MalformedURLException {
        if (isConnected()) {
            //call JSON Async task
            URL trailerSearchQuery = NetworkUtils.buildURL(String.valueOf(movie.id)+"/"+ "videos");
            new TrailerJsonAsyncTask(this, new FetchData()).execute(trailerSearchQuery);
        } else {
            showTrailerErrorMessage();
        }
    }
    public  void reviewsSearchQueryExecute () throws MalformedURLException{
        if (isConnected()){
            URL reviewSearchQuery = NetworkUtils.buildURL(String.valueOf(movie.id)+"/"+"reviews");
            new ReviewsJsonAsyncTask(this,new FetchData()).execute(reviewSearchQuery);
        } else {
            showReviewErrorMessage();
        }
    }
    private Boolean isConnected (){
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        Boolean isConnected = activeNetwork !=null&& activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
    public void showTrailerErrorMessage (){
        trailersErrorMessageTv.setVisibility(View.VISIBLE);
        trailerRecyclerView.setVisibility(View.INVISIBLE);
        trailersErrorMessageTv.setText(errorMessageDisplay);
    }
    public void showReviewErrorMessage (){
        reviewsErrorMeassageTv.setVisibility(View.VISIBLE);
        reviewRecyclerView.setVisibility(View.INVISIBLE);
        reviewsErrorMeassageTv.setText(errorMessageDisplay);
    }

    public void addToFavourites(View view) {
        ContentValues values = new ContentValues();
        values.put(FavouritesContract.FavouritesEntry.COLUMN_MOVIE_ID, movie.id);
        values.put(FavouritesContract.FavouritesEntry.COLUMN_MOVIE_TITLE, movie.title);
        values.put(FavouritesContract.FavouritesEntry.COLUMN_MOVIE_RELEASE_DATE, movie.releaseDate);
        values.put(FavouritesContract.FavouritesEntry.COLUMN_MOVIE_OVERVIEW, movie.overview);
        values.put(FavouritesContract.FavouritesEntry.COLUMN_MOVIE_RATE, movie.rate);
        values.put(FavouritesContract.FavouritesEntry.COLUMN_MOVIE_POSTER, movie.poster);
        values.put(FavouritesContract.FavouritesEntry.COLUMN_MOVIE_COVER, movie.cover);
        Uri uri= getContentResolver().insert(FavouritesContract.FavouritesEntry.CONTENT_URI,values);
        if (uri !=null){
            Toast.makeText(this,movie.title+" added to your favourites successfully!",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this,"failed",Toast.LENGTH_LONG).show();
        }

    }

    public class FetchData implements AsyncTaskCompleteListener{
        @Override
        public void onMovieJsonTaskComplete(List<MovieModel> movies) {}
        @Override
        public void onTrailerJsonTaskComplete(List<TrailerModel> trailers) {
            trailersList =trailers;
          TrailerAdapter adapter= new TrailerAdapter(trailersList, new TrailerAdapter.OnItemClickedListerner() {
                @Override
                public void onItemClicked(TrailerModel trailer) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.youtube.com/watch?v="+trailer.key));
                    startActivity(intent);
                     }
            });
            trailerRecyclerView.setAdapter(adapter);
        }
        @Override
        public void onReviewsJsonTaskComplete(List<ReviewsModel> reviews) {
            reviewsList =reviews;
            ReviewsAdapter adapter = new ReviewsAdapter(reviewsList);
            reviewRecyclerView.setAdapter(adapter);
        }

        @Override
        public void onFavouritesTaskComplete(Cursor cursor) {}

        @Override
        public void errorMessage(String errorMessage) {
            errorMessageDisplay =errorMessage;
        }
    }
}
