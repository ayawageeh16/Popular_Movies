package com.example.android.popularmovies.view;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.adapter.MovieAdapter;
import com.example.android.popularmovies.data.ReviewsModel;
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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    final static String ACTIVITY_STATE_KEY = "Activity state";
    TextView errorMessageTv;
    RecyclerView recyclerView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView ;
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
        drawerLayout = findViewById(R.id.drawer_layout);
        recyclerView = findViewById(R.id.rv_movies);
        navigationView = findViewById(R.id.navigation_view);
        setRecyclerView(recyclerView);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        setDrawerLayout(drawerLayout,toggle);
        navigationView.setNavigationItemSelectedListener(this);


        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(ACTIVITY_STATE_KEY)) {
                sortBy = savedInstanceState.getString(ACTIVITY_STATE_KEY);
                try {searchQueryExecute();
                } catch (MalformedURLException e) {
                    e.printStackTrace();}}} else {
            sortBy ="popular";
            try {searchQueryExecute();} catch (MalformedURLException e) {
                e.printStackTrace();}}
    }

    private void setDrawerLayout (DrawerLayout drawerLayout , ActionBarDrawerToggle toggle) {
        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemClicked = item.getItemId();
        if (itemClicked == R.id.popular_movies_sort) {
            sortBy = "popular";
            try {
                searchQueryExecute();
                drawerLayout.closeDrawers();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        else if (itemClicked ==R.id.top_rated_sort) {
            sortBy = "top_rated";
            try {
                searchQueryExecute();
                drawerLayout.closeDrawers();
            } catch (MalformedURLException e) {
            }
        }
        return false;
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
        public void onReviewsJsonTaskComplete(List<ReviewsModel> reviews) {}

        @Override
        public void errorMessage(String errorMessage) {
            errorMessageDisplay=errorMessage;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
