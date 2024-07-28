package com.example.movies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movies.adapter.ReviewAdapter;
import com.example.movies.adapter.TrailerAdapter;
import com.example.movies.api.ApiFactory;
import com.example.movies.api.ApiService;
import com.example.movies.database.MovieDao;
import com.example.movies.database.MovieDatabase;
import com.example.movies.model.Movie;
import com.example.movies.model.Poster;
import com.example.movies.model.Review;
import com.example.movies.model.ReviewResponse;
import com.example.movies.model.Trailer;
import com.example.movies.model.TrailerResponse;
import com.example.movies.viewModel.MovieDetailViewModel;

import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailActivity extends AppCompatActivity {

    private static final String TAG="MovieDetailActivity";

    private static final String EXTRA_MOVIE = "movie";

    private ImageView imageViewPoster;
    private TextView textViewTitle;
    private TextView textViewYear;
    private TextView textViewDescription;

    private RecyclerView rvTrailers;
    private RecyclerView rvReviews;

    private TrailerAdapter trailerAdapter;
    private ReviewAdapter reviewAdapter;
    private MovieDetailViewModel movieDetailViewModel;

    private MovieDatabase movieDatabase;
    private MovieDao movieDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        initViews();
        movieDatabase=MovieDatabase.getInstance(getApplication());
        movieDao=movieDatabase.movieDao();
        trailerAdapter = new TrailerAdapter();
        rvTrailers.setAdapter(trailerAdapter);
        reviewAdapter= new ReviewAdapter();
        rvReviews.setAdapter(reviewAdapter);
        movieDetailViewModel=new ViewModelProvider(this).get(MovieDetailViewModel.class);
        movieDetailViewModel.getTrailers().observe(this, new Observer<List<Trailer>>() {
            @Override
            public void onChanged(List<Trailer> trailers) {
                Log.d(TAG,"GOT TRAILERS \n"+trailers.toString());
                trailerAdapter.setTrailers(trailers);
            }
        });
        movieDetailViewModel.getReviews().observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(List<Review> reviews) {
                Log.d(TAG,""+reviews.size());
                reviewAdapter.setReviews(reviews);
            }
        });
        trailerAdapter.setOnItemClick(new TrailerAdapter.OnTrailerClickListener() {
            @Override
            public void click(Trailer trailer) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(trailer.getUrl()));
                startActivity(intent);
            }
        });
        Movie movie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);
        if (movie != null) {
            setMovieInfo(movie);
            movieDetailViewModel.loadTrailers(movie.getId());
            movieDetailViewModel.loadReviews(movie.getId());
            Handler handler = new Handler(Looper.getMainLooper());
            movieDao.addMovieToFavorite(movie).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();
        }
    }


    private void setMovieInfo(Movie movie) {
        textViewTitle.setText(movie.getName());
        textViewDescription.setText(movie.getDescription());
        textViewYear.setText(String.valueOf(movie.getYear()));
        Poster poster = movie.getPoster();
        if (poster == null || poster.getUrl().isEmpty()) {
            Glide.with(this)
                    .load(Poster.defaultPoster)
                    .into(imageViewPoster);
        } else {
            Glide.with(this)
                    .load(poster.getUrl())
                    .into(imageViewPoster);
        }
    }


    private void initViews() {
        rvReviews=findViewById(R.id.rvReviews);
        rvTrailers=findViewById(R.id.rvTrailers);
        imageViewPoster = findViewById(R.id.imageViewPoster);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewYear = findViewById(R.id.textViewYear);
        textViewDescription = findViewById(R.id.textViewDescription);
    }

    public static Intent newIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);
        return intent;
    }

}