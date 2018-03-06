package com.example.android.popularmovies.view;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.popularmovies.adapter.MovieAdapter;
import com.example.android.popularmovies.data.TrailerModel;
import com.example.android.popularmovies.utils.AsyncTaskCompleteListener;
import com.example.android.popularmovies.utils.MovieJsonAsyncTask;
import com.example.android.popularmovies.utils.NetworkUtils;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.MovieModel;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final static String ACTIVITY_STATE_KEY = "Activity state";
    TextView errorMessageTv;
    RecyclerView recyclerView;
    String sortBy ;
    String errorMessageDisplay;
    List<MovieModel> movieDetails = new ArrayList<MovieModel>();

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ACTIVITY_STATE_KEY,sortBy);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        errorMessageTv =findViewById(R.id.Show_error_message);
        recyclerView = findViewById(R.id.rv_movies);
        setRecyclerView(recyclerView);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(ACTIVITY_STATE_KEY)) {
                sortBy = savedInstanceState.getString(ACTIVITY_STATE_KEY);
                try {searchQueryExecute();
                } catch (MalformedURLException e) {
                    e.printStackTrace();}
            }
        } else {
            sortBy ="popular";
            try {searchQueryExecute();
            } catch (MalformedURLException e) {
                e.printStackTrace();}
        }
    }
    private void  setRecyclerView (RecyclerView recyclerView){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
    }
    private Boolean isConnected (){
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        Boolean isConnected = activeNetwork !=null&& activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
    public void searchQueryExecute () throws MalformedURLException {
        //check for internet connection
        if (isConnected()){
            //call JSON Async task
            URL moviesSearchQuery = NetworkUtils.buildURL(sortBy);
            new MovieJsonAsyncTask(this, new FetchMovieData()).execute(moviesSearchQuery);}
        else {
            showErrorMessage();}
    }
    public void showErrorMessage (){
        errorMessageTv.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        errorMessageTv.setText(errorMessageDisplay);
    }
    // execute network tasks and JSON in background
    public class FetchMovieData implements AsyncTaskCompleteListener{
        @Override
        public void onMovieJsonTaskComplete(List movies) {
            movieDetails = movies;
            MovieAdapter adapter = new  MovieAdapter(movieDetails, new MovieAdapter.OnItemClickedListener() {
                @Override
                public void onItemClicked(MovieModel movie) {
                    Intent intent = new Intent(MainActivity.this,MovieDetails.class);
                    intent.putExtra("movie",movie);
                    startActivity(intent);}
            });
            recyclerView.setAdapter(adapter);}

        @Override
        public void onTrailerJsonTaskComplete(List<TrailerModel> trailers) {}

        @Override
        public void errorMessage(String errorMessage) {
            errorMessageDisplay=errorMessage;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemClicked = item.getItemId();
        if (itemClicked == R.id.sort_byPopular) {
            sortBy = "popular";
            try {
                searchQueryExecute();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        else if (itemClicked ==R.id.sort_byTopRated) {
            sortBy ="top_rated";
            try {
                searchQueryExecute();
            } catch (MalformedURLException e) {
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
