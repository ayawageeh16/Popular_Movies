package com.example.android.popularmovies.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.ReviewsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 12/03/2018.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder> {

    List<ReviewsModel> reviews = new ArrayList<>();

    public ReviewsAdapter (List<ReviewsModel> reviews){
        this.reviews=reviews;
    }

    @Override
    public ReviewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_row,parent,false);
        ReviewsViewHolder holder = new ReviewsViewHolder(row);
        return holder;
    }

    @Override
    public void onBindViewHolder(ReviewsViewHolder holder, int position) {
      holder.bind(reviews.get(position));
    }

    @Override
    public int getItemCount() {return reviews.size();
    }

    class ReviewsViewHolder extends RecyclerView.ViewHolder{
        TextView content_tv;
        TextView author_tv;
        public ReviewsViewHolder(View itemView) {
            super(itemView);
           content_tv =itemView.findViewById(R.id.content_tv);
           author_tv =itemView.findViewById(R.id.author_tv);
        }
       public void bind (final ReviewsModel review){
                    author_tv.setText(review.author);
                    content_tv.setText(review.content);
        }
    }
}
