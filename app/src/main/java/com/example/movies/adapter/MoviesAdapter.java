package com.example.movies.adapter;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movies.R;
import com.example.movies.model.Movie;
import com.example.movies.model.Poster;
import com.example.movies.model.Rating;


import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private static final String TAG = "MoviesAdapter";

    private List<Movie> movies = new ArrayList<>();

    private OnMovieClickListner onMovieClickListner;

    public void setOnMovieClickListener(OnMovieClickListner onMovieClickListner) {
        this.onMovieClickListner = onMovieClickListner;
    }

    public interface OnMovieClickListner {
        void onMovieClick(Movie movie);
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public interface onReachEndListener {
        void onReachEnd();
    }

    private onReachEndListener onReachEndListener;

    public void setOnReachEndListener(onReachEndListener onReachEndListener) {
        this.onReachEndListener = onReachEndListener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.movie_item,
                parent
                , false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder called times");
        Movie movie = movies.get(position);
        Rating rating = movie.getRating();
        Poster poster = movie.getPoster();
        if (poster == null || poster.getUrl() == null) {
            Glide.with(holder.itemView)
                    .load(Poster.defaultPoster)
                    .into(holder.imageViewPoster);
        } else {
            Glide.with(holder.itemView)
                    .load(poster.getUrl())
                    .into(holder.imageViewPoster);
        }
        double ratingKp = rating.getKp();
        int backgroudId;
        if (ratingKp > 7) {
            backgroudId = R.drawable.circle_green;
        } else if (ratingKp > 5) {
            backgroudId = R.drawable.circle_yellow;
        } else {
            backgroudId = R.drawable.circle_red;
        }
        Drawable backgroud = ContextCompat.getDrawable(holder.itemView.getContext(), backgroudId);
        holder.textViewRating.setBackground(backgroud);
        holder.textViewRating.setText(String.format("%.1f", ratingKp));
        if (position >= movies.size() - 10 && onReachEndListener != null) {
            onReachEndListener.onReachEnd();
        }
        holder.itemView.setOnClickListener(v -> {
            if (onMovieClickListner != null) {
                onMovieClickListner.onMovieClick(movie);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }


    static class MovieViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewRating;
        private final ImageView imageViewPoster;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            imageViewPoster = itemView.findViewById(R.id.imageViewPoster);
        }
    }

}
