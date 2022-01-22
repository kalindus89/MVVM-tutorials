package com.mvvm_tutorials.mvvm_movie_retrofit.api_request;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mvvm_tutorials.mvvm_movie_retrofit.backgoud_api_run.AppExecutors;
import com.mvvm_tutorials.mvvm_movie_retrofit.model.MovieModel;
import com.mvvm_tutorials.mvvm_movie_retrofit.repositories.MovieRepository;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class MovieApiClient {

    private static MovieApiClient instance;
    private MutableLiveData<List<MovieModel>> mMovies;

    public MovieApiClient() {
        mMovies = new MutableLiveData<>();
    }

    public static MovieApiClient getInstance() {
        if(instance==null){
            instance = new MovieApiClient();
        }
        return instance;
    }

    //pretend to get data from webservice or database
    public LiveData<List<MovieModel>> getMovies(){

        return mMovies;
    }

    public void searchMovieApi(){

        // Created two threads. One to Retrieve data from API and otherCancelling the API call if no data retrieve with in 25 seconds

        final Future myHandler = AppExecutors.getInstance().networkIO().submit(new Runnable() {
            @Override
            public void run() {
                //Retrieve data from API

            }
        });

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the API call if no data retrieve with in 25 seconds
                myHandler.cancel(true);

            }
        },25000, TimeUnit.MILLISECONDS);

    }



}
