package com.example.movies.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.movies.database.MovieDao;
import com.example.movies.database.MovieDatabase;
import com.example.movies.model.Movie;

import java.util.List;

public class FavoriteMovieViewModel extends AndroidViewModel {

    private MovieDao movieDao;

    public FavoriteMovieViewModel(@NonNull Application application) {
        super(application);
        movieDao= MovieDatabase.getInstance(application).movieDao();
    }

    public LiveData<List<Movie>> loadFavoriteMovies(){
        return movieDao.getFavoriteMovies();
    }


}
