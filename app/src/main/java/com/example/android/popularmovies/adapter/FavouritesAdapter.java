package com.example.android.popularmovies.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.FavouritesContract;
import com.example.android.popularmovies.data.MovieModel;
import com.squareup.picasso.Picasso;

/**
 * Created by Lenovo on 13/03/2018.
 */

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.FavouritesViewHolder> {

    Cursor cursor;
    int position;
    Context context;
    OnItemClickedListener listener ;

    public interface OnItemClickedListener{
        void onItemClicked (MovieModel movie);
    }
    public FavouritesAdapter(Cursor cursor, OnItemClickedListener listener){
        this.cursor =cursor;
        this.listener =listener;
    }
    public FavouritesAdapter(Context context){
        this.context=context;
    }

    @Override
    public FavouritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_row,parent,false);
        FavouritesViewHolder holder = new FavouritesViewHolder(row);
        return holder;
    }

    @Override
    public void onBindViewHolder(FavouritesViewHolder holder, int position) {
       if (!cursor.moveToPosition(position)) {
           return;
       }else {
           holder.Bind(cursor);
           this.position=position;
           holder.itemView.setTag(cursor.getLong(cursor.getColumnIndex(FavouritesContract.FavouritesEntry.COLUMN_MOVIE_ID)));
       }
    }
    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public class FavouritesViewHolder extends RecyclerView.ViewHolder{
        ImageView poster;
        TextView title;
        TextView rate;
        TextView releasedDate;
        public FavouritesViewHolder(View itemView) {
            super(itemView);
            poster=itemView.findViewById(R.id.favourite_poster);
            title=itemView.findViewById(R.id.favourite_title);
            rate=itemView.findViewById(R.id.tv_rate);
            releasedDate=itemView.findViewById(R.id.tv_released);
        }
        public void Bind(final Cursor cursor){
            long movieId = cursor.getLong(cursor.getColumnIndex(FavouritesContract.FavouritesEntry.COLUMN_MOVIE_ID));
            String movieTitle = String.valueOf(cursor.getString(cursor.getColumnIndex(FavouritesContract.FavouritesEntry.COLUMN_MOVIE_TITLE)));
            String movieRate = String.valueOf(cursor.getString(cursor.getColumnIndex(FavouritesContract.FavouritesEntry.COLUMN_MOVIE_RATE)));
            String movieYear = String.valueOf(cursor.getString(cursor.getColumnIndex(FavouritesContract.FavouritesEntry.COLUMN_MOVIE_RELEASE_DATE)));
            String movieOverview = String.valueOf(cursor.getString(cursor.getColumnIndex(FavouritesContract.FavouritesEntry.COLUMN_MOVIE_OVERVIEW)));
            String movieCover = String.valueOf(cursor.getString(cursor.getColumnIndex(FavouritesContract.FavouritesEntry.COLUMN_MOVIE_COVER)));
            String moviePoster =String.valueOf(cursor.getString(cursor.getColumnIndex(FavouritesContract.FavouritesEntry.COLUMN_MOVIE_POSTER)));
            final MovieModel movie =new MovieModel(movieTitle,movieYear,movieRate,movieOverview,moviePoster,movieCover,movieId);
            Picasso.with(poster.getContext())
                    .load(moviePoster)
                    .into(poster);
            title.setText(movieTitle);
            rate.setText(movieRate);
            releasedDate.setText(movieYear);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClicked(movie);
                }
            });
        }
    }
}
