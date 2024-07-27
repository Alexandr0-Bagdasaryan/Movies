package com.example.movies.api;

import com.example.movies.model.MovieResponse;
import com.example.movies.model.ReviewResponse;
import com.example.movies.model.TrailerResponse;


import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    final String TOKEN = "5CNH9YP-SB24EAK-GDMYDG5-X8FZN52";
    final String RESERVE_TOKEN = "GBKGD9T-CWK49HW-QS5WDW6-GBQW6JB";

    @GET("movie?rating.kp=7-10&sortField=votes.kp&sortType=-1&limit=30")
    @Headers("X-API-KEY:" + TOKEN)
    Single<MovieResponse> loadMovies(@Query("page") int page);

    @GET("movie/{id}")
    @Headers("X-API-KEY:" + TOKEN)
    Single<TrailerResponse> loadTrailers(@Path("id") int id);

    @GET("review")
    @Headers("X-API-KEY:" + TOKEN)
    Single<ReviewResponse> loadReviews(@Query("movieId") int movieID);

}
