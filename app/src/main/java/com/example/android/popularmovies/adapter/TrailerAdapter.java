package com.example.android.popularmovies.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.TrailerModel;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 06/03/2018.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {
    List<TrailerModel> trailers = new ArrayList<>();
    OnItemClickedListerner listener;

    public interface OnItemClickedListerner{
        void onItemClicked(TrailerModel trailer);
    }
   public  TrailerAdapter(List <TrailerModel> trailers){
       this.trailers =trailers;
   }
    public TrailerAdapter (List<TrailerModel> trailers, OnItemClickedListerner listener){
        this.trailers =trailers;
        this.listener =listener;
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.youtube_video,parent,false);
        TrailerViewHolder holder = new TrailerViewHolder(row);
        return holder;
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
      holder.bind(trailers.get(position));
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder{
        //ImageButton playButton;
        //TextView trailerTV;
        ImageView ivYoutubeVideo;
        ImageView ivPlay;

        public TrailerViewHolder(View itemView) {
            super(itemView);
        //playButton = itemView.findViewById(R.id.imgButton_play);
        //trailerTV = itemView.findViewById(R.id.tv_trailer);
            ivYoutubeVideo = itemView.findViewById(R.id.img_thumnail);
            ivPlay = itemView.findViewById(R.id.iv_play_pause);

        }
        public void bind(final TrailerModel trailer){
          //trailerTV.setText(trailer.name);
         /* playButton.setOnClickListener(new View.OnClickListener(){
               @Override
               public void onClick(View view) {
                   listener.onItemClicked(trailer);
               }
           });*/
            String img_url = "http://img.youtube.com/vi/" + trailer.key + "/0.jpg";
            Picasso.with(ivYoutubeVideo.getContext())
                    .load(img_url)
                    .into(ivYoutubeVideo);
            ivPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClicked(trailer);
                }
            });

        }
    }
}
