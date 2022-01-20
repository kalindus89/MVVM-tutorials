package com.mvvm_tutorials.mvvm_movie_retrofit.api_request;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mvvm_tutorials.mvvm_movie_retrofit.model.MovieModel;
import com.mvvm_tutorials.mvvm_movie_retrofit.repositories.MovieRepository;

import java.util.List;

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
    };


    //pretend to get data from webservice or database
    public LiveData<List<MovieModel>> getMovies(){

        return mMovies;
    }

}
