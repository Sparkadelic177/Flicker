package com.example.flickster.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.flickster.DetailActivity;
import com.example.flickster.R;
import com.example.flickster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

import static android.support.v7.content.res.AppCompatResources.getDrawable;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    Context context;
    List<Movie> movies;
    Movie movie;

    //constructor //brings in the context from the main activity
    public MoviesAdapter(Context context, List<Movie> movie) {
        this.context = context;
        this.movies = movie;
    }

    //next three methods come from abstract class RecyclerView.Adapter that we have to override
    //onCreateViewHolder passes the xml layout to create a view
    @NonNull
    @Override //this on create sends back to main activity the infalted xml and the binded information for the viewgroup
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view = LayoutInflater.from(context).inflate(R.layout.item_movie, viewGroup,false);
       return new ViewHolder(view);//this is returned to the class ViewHolder
    }

    @Override//for every section is binds them dynamically
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Movie movie = movies.get(i);
        viewHolder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    //inner class, holds all the references to the layout and actions in the viewHolder
    class ViewHolder extends RecyclerView.ViewHolder{

        //variables to the UI components
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
        ImageView playButton;
        RelativeLayout container;

        //constructor
        public ViewHolder(View itemView) {
            super(itemView);
            //getting the reference of the three objects on the UI
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.movieBox);
            playButton = itemView.findViewById(R.id.playButton);

        }

        //this methods puts data into the variables
        //movie is final since its a var outside of the inner class
        //inner class can not modify outside vars, so we have to place the final keyword
        //bind class is used to take what was parsed and place it in the widgets
        public void bind(final Movie movie) {

            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String movieUrl = movie.getPosterPath();

            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                movieUrl = movie.getBackdropPathath();
            }
            Glide.with(context).load(movieUrl).into(ivPoster);

            if(movie.getRatings() > 5){playButton.setImageDrawable(getDrawable(context, R.drawable.ic_play_button));}
            else{playButton.setImageDrawable(getDrawable(context, R.drawable.ic_play_black));}

            //when title is clicked, a new view pops up
            container.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("title",movie.getTitle());
                    i.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(i);
                }
            });
        }
    }
}
