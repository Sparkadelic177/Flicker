package com.example.flickster.Adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.flickster.R;
import com.example.flickster.models.Movie;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    Context context;
    List<Movie> movies;

    public MoviesAdapter(Context context, List<Movie> movie) {
        this.context = context;
        this.movies = movie;
    }

    //next three methods come from abstract class RecyclerView.Adapter
    //onCreateViewHolder passes the xml layout to create a view
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view = LayoutInflater.from(context).inflate(R.layout.item_movie, viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Movie movie = movies.get(i);
        viewHolder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    //inner class
    class ViewHolder extends RecyclerView.ViewHolder{

        //variables to the UI components
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        //constructor
        public ViewHolder(View itemView) {
            super(itemView);
            //getting the reference of the three objects on the UI
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
        }

        //this methods puts data into the variables
        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String movieUrl = movie.getPosterPath();
            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                movieUrl = movie.getBackdropPathath();
            }
            Glide.with(context).load(movieUrl).into(ivPoster);
        }
    }
}
