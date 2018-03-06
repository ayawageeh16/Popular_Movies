package com.example.android.popularmovies.view;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewStructure;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.adapter.TrailerAdapter;
import com.example.android.popularmovies.data.MovieModel;
import com.example.android.popularmovies.data.TrailerModel;
import com.example.android.popularmovies.utils.AsyncTaskCompleteListener;
import com.example.android.popularmovies.utils.MovieJsonAsyncTask;
import com.example.android.popularmovies.utils.NetworkUtils;
import com.example.android.popularmovies.utils.TrailerJsonAsyncTask;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;
import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

public class MovieDetails extends AppCompatActivity {

    ImageView coverImg;
    ImageView posterImg;
    TextView movieTitle;
    TextView movieYear;
    TextView movieRate;
    TextView movieOverview;
    TextView errorMessageTv;
    RecyclerView recyclerView ;
    MovieModel movie;
    String errorMessageDisplay;
    List<TrailerModel> trailerslist = new ArrayList<>();

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
        errorMessageTv =findViewById(R.id.error_tv);
        recyclerView =findViewById(R.id.trailers_rv);
        setRecyclerView(recyclerView);

        Bundle bundle = getIntent().getExtras();
        movie = bundle.getParcelable("movie");

        Picasso.with(coverImg.getContext())
                .load(movie.cover)
                .into(coverImg);
        Picasso.with(posterImg.getContext())
                .load(movie.poster)
                .into(posterImg);
        movieTitle.setText(movie.movieName);
        movieYear.setText(movie.movieYear);
        movieRate.setText(movie.movieRate);
        movieOverview.setText(movie.movieDescription);

        try {
            searchQueryExecute();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    void setRecyclerView (RecyclerView recyclerView){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,VERTICAL,true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }

    public void searchQueryExecute () throws MalformedURLException {
        if (isConnected()) {
            //call JSON Async task
            URL TrailerSearchQuery = NetworkUtils.buildURL(String.valueOf(movie.id)+"/"+ "videos");
            new TrailerJsonAsyncTask(this, new FetchTrailerData()).execute(TrailerSearchQuery);
        } else {
            showErrorMessage();
        }
    }
    private Boolean isConnected (){
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        Boolean isConnected = activeNetwork !=null&& activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
    public void showErrorMessage (){
        errorMessageTv.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        errorMessageTv.setText(errorMessageDisplay);
    }
    public class FetchTrailerData implements AsyncTaskCompleteListener{
        @Override
        public void onMovieJsonTaskComplete(List<MovieModel> movies) {}
        @Override
        public void onTrailerJsonTaskComplete(List<TrailerModel> trailers) {
            trailerslist =trailers;
          TrailerAdapter adapter= new TrailerAdapter(trailerslist, new TrailerAdapter.OnItemClickedListerner() {
                @Override
                public void onItemClicked(TrailerModel trailer) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.youtube.com/watch?v="+trailer.key));
                    startActivity(intent);
                     }
            });
            recyclerView.setAdapter(adapter);
        }
        @Override
        public void errorMessage(String errorMessage) {
            errorMessageDisplay =errorMessage;
        }
    }
}
