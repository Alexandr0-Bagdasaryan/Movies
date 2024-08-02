package com.example.movies.api;

import com.example.movies.model.MovieResponse;
import com.example.movies.model.ReviewResponse;
import com.example.movies.model.TrailerResponse;
import com.example.movies.utils.Utils;


import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("movie?rating.kp=7-10&sortField=votes.kp&sortType=-1&limit=30")
    @Headers("X-API-KEY:" + Utils.TOKEN)
    Single<MovieResponse> loadMovies(@Query("page") int page);

    @GET("movie/{id}")
    @Headers("X-API-KEY:" + Utils.TOKEN)
    Single<TrailerResponse> loadTrailers(@Path("id") int id);

    @GET("review")
    @Headers("X-API-KEY:" + Utils.TOKEN)
    Single<ReviewResponse> loadReviews(@Query("movieId") int movieID);

}
