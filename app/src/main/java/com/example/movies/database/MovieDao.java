package com.example.movies.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.movies.model.Movie;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;


@Dao
public interface MovieDao {

    @Query("SELECT * FROM favouriteMovies")
    LiveData<List<Movie>> getFavoriteMovies();

    @Query("SELECT * FROM favouriteMovies WHERE id=:movieId")
    LiveData<Movie> getFavoriteMovie(int movieId);

    @Insert
    Completable addMovieToFavorite(Movie movie);

    @Query("DELETE FROM favouriteMovies WHERE id=:movieID")
    Completable removeMovieFromFavorite(int movieID);

}
