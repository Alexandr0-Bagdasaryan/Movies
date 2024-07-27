package com.example.movies;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.adapter.MoviesAdapter;
import com.example.movies.model.Movie;
import com.example.movies.viewModel.MainViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG="MainActivity";

    private MainViewModel mainViewModel;

    private ProgressBar progressBarLoading;

    private RecyclerView recyclerViewMovies;
    private MoviesAdapter moviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewMovies=findViewById(R.id.recyclerViewMovies);
        progressBarLoading=findViewById(R.id.progressBarLoading);
        moviesAdapter=new MoviesAdapter();
        recyclerViewMovies.setAdapter(moviesAdapter);
        recyclerViewMovies.setLayoutManager(new GridLayoutManager(this,2));
        mainViewModel= new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                moviesAdapter.setMovies(movies);
            }
        });
        mainViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if(isLoading){
                    progressBarLoading.setVisibility(View.VISIBLE);
                }
                else{
                    progressBarLoading.setVisibility(View.GONE);
                }
            }
        });
        moviesAdapter.setOnReachEndListener(new MoviesAdapter.onReachEndListener() {
            @Override
            public void onReachEnd() {
                mainViewModel.loadMovies();
            }
        });
        moviesAdapter.setOnMovieClickListener(new MoviesAdapter.OnMovieClickListner() {
            @Override
            public void onMovieClick(Movie movie) {
               Intent intent= MovieDetailActivity.newIntent(MainActivity.this,movie);
               startActivity(intent);
            }
        });
    }
}