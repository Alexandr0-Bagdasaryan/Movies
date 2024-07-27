package com.example.movies.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movies.api.ApiFactory;
import com.example.movies.model.Review;
import com.example.movies.model.ReviewResponse;
import com.example.movies.model.Trailer;
import com.example.movies.model.TrailerResponse;
import com.example.movies.model.TrailersList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailViewModel extends AndroidViewModel {

    private static final String TAG = "MovieDetailViewModel";

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private MutableLiveData<List<Trailer>> trailers = new MutableLiveData<List<Trailer>>();

    private MutableLiveData<List<Review>> reviews = new MutableLiveData<>();

    public LiveData<List<Trailer>> getTrailers() {
        return trailers;
    }

    public LiveData<List<Review>> getReviews() {
        return reviews;
    }

    public MovieDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public void loadTrailers(int idFilm) {
        Disposable disposable = ApiFactory.apiService.loadTrailers(idFilm)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<TrailerResponse, List<Trailer>>() {
                    @Override
                    public List<Trailer> apply(TrailerResponse trailerResponse) throws Throwable {
                        if (trailerResponse.getTrailersList() == null) {
                            return new ArrayList<Trailer>();
                        }
                        return trailerResponse.getTrailersList().getTrailers();
                    }
                })
                .subscribe(new Consumer<List<Trailer>>() {
                    @Override
                    public void accept(List<Trailer> trailersList) throws Throwable {
                        Log.d(TAG, trailersList.toString());
                        if (trailersList.isEmpty()) {
                            Log.d(TAG, "there is no trailers for this film");
                        } else {
                            Log.d(TAG, "Movie ID " + idFilm);
                            Log.d(TAG, trailersList.toString());
                            trailers.setValue(trailersList);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d(TAG, throwable.toString());
                    }
                });
        compositeDisposable.add(disposable);
    }

    public void loadReviews(int movieID) {
        Disposable disposable =
                ApiFactory.apiService.loadReviews(movieID).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(new Function<ReviewResponse, List<Review>>() {
                            @Override
                            public List<Review> apply(ReviewResponse reviewResponse) throws Throwable {
                                return reviewResponse.getReviews();
                            }
                        })
                        .subscribe(new Consumer<List<Review>>() {
                            @Override
                            public void accept(List<Review> reviewList) throws Throwable {
                                reviews.setValue(reviewList);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Throwable {
                                Log.d(TAG, throwable.toString());
                            }
                        });
        compositeDisposable.add(disposable);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
