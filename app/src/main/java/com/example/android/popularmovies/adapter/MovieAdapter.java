package com.example.android.popularmovies.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.MovieModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 21/02/2018.
 */

public class MovieAdapter extends RecyclerView.Adapter <MovieAdapter.MovieViewHolder>{

    List<MovieModel>movies= new ArrayList<>();
    OnItemClickedListener listener ;

    public interface OnItemClickedListener{
        void onItemClicked (MovieModel movie);
    }
    public MovieAdapter (List<MovieModel> movies, OnItemClickedListener listener){
        this.movies =movies;
        this.listener =listener;
    }
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_poster,parent,false);
        MovieViewHolder holder = new MovieViewHolder(row);
        return holder;
    }
    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        ImageView moviePoster;
        public MovieViewHolder(View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.img_poster);
        }
        void bind (final MovieModel movie)
        {
            Picasso.with(moviePoster.getContext())
                    .load(movie.poster)
                    .into(moviePoster);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClicked(movie);
                }
            });
        }
}
}
