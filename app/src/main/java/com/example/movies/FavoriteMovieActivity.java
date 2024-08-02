package com.example.movies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.adapter.MoviesAdapter;
import com.example.movies.model.Movie;
import com.example.movies.viewModel.FavoriteMovieViewModel;

import java.util.List;

public class FavoriteMovieActivity extends AppCompatActivity {


    private RecyclerView recyclerViewFavoriteMovies;

    private FavoriteMovieViewModel favoriteMovieViewModel;
    private MoviesAdapter moviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_movie);
        initViews();
        moviesAdapter=new MoviesAdapter();
        recyclerViewFavoriteMovies.setAdapter(moviesAdapter);
        moviesAdapter.setOnMovieClickListener(new MoviesAdapter.OnMovieClickListner() {
            @Override
            public void onMovieClick(Movie movie) {
                Intent intent = MovieDetailActivity.newIntent(FavoriteMovieActivity.this,movie);
                startActivity(intent);
            }
        });
        recyclerViewFavoriteMovies.setLayoutManager(new GridLayoutManager(this, 2));
        favoriteMovieViewModel=new ViewModelProvider(this).get(FavoriteMovieViewModel.class);
        favoriteMovieViewModel.loadFavoriteMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                moviesAdapter.setMovies(movies);
            }
        });
    }

    public static Intent newIntent(Context context){
        return new Intent(context, FavoriteMovieActivity.class);
    }

    private void initViews(){
        recyclerViewFavoriteMovies=findViewById(R.id.recyclerViewFavoriteMovies);
    }
}