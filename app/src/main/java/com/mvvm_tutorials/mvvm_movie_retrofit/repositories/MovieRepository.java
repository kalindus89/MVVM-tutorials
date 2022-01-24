package com.mvvm_tutorials.mvvm_movie_retrofit.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mvvm_tutorials.mvvm_movie_retrofit.api_request.MovieApiClient;
import com.mvvm_tutorials.mvvm_movie_retrofit.model.MovieModel;

import java.util.List;

public class MovieRepository {

    private static MovieRepository instance;

    private MovieApiClient movieApiClient;

    public MovieRepository() {
        movieApiClient=  MovieApiClient.getInstance();
    }

    public static MovieRepository getInstance() {
        if(instance==null){
            instance = new MovieRepository();
        }
        return instance;
    }

    //calling the method to call api
    public void searchMovieApi(String searchQuery, int pageNUmber){
        movieApiClient.searchMovieApi(searchQuery,pageNUmber);
    }

    //pretend to get data from webservice or database
    public LiveData<List<MovieModel>> getMovies(){

        return movieApiClient.getMovies();
    }


}
