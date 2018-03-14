package com.example.android.popularmovies.adapter;

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
import com.squareup.picasso.Picasso;

/**
 * Created by Lenovo on 13/03/2018.
 */

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.FavouritesViewHolder> {

    Cursor cursor;
    int position;

    public FavouritesAdapter(Cursor cursor){
        this.cursor =cursor;
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

    public void swapCursor (Cursor newCursor){
       if(cursor!=null)cursor.close();
           cursor=newCursor;
       if (newCursor!=null){
           this.notifyDataSetChanged();
           this.notifyItemChanged(this.position);
       }
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public class FavouritesViewHolder extends RecyclerView.ViewHolder{
        ImageView poster;
        TextView title;
        public FavouritesViewHolder(View itemView) {
            super(itemView);
            poster=itemView.findViewById(R.id.favourite_poster);
            title=itemView.findViewById(R.id.favourite_title);
        }
        public void Bind(final Cursor cursor){
            String s =String.valueOf(cursor.getString(cursor.getColumnIndex(FavouritesContract.FavouritesEntry.COLUMN_MOVIE_POSTER)));
            Picasso.with(poster.getContext())
                    .load(s)
                    .into(poster);
            title.setText(String.valueOf(cursor.getString(cursor.getColumnIndex(FavouritesContract.FavouritesEntry.COLUMN_MOVIE_TITLE))));
        }
    }
}
