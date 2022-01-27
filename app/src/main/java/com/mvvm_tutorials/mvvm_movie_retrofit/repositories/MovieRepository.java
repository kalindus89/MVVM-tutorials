package com.mvvm_tutorials.mvvm_movie_retrofit.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mvvm_tutorials.mvvm_movie_retrofit.api_request.MovieApiClient;
import com.mvvm_tutorials.mvvm_movie_retrofit.model.MovieModel;

import java.util.List;

public class MovieRepository {

    private static MovieRepository instance;

    private MovieApiClient movieApiClient;

    private String searchQuery;
    private int pageNumber;

    public MovieRepository() {
        movieApiClient=  MovieApiClient.getInstance();
    }

    public static MovieRepository getInstance() {
        if(instance==null){
            instance = new MovieRepository();
        }
        return instance;
    }

    //calling the method to call api search movies
    public void searchMovieApi(String searchQuery, int pageNumber){

        this.searchQuery=searchQuery;
        this.pageNumber=pageNumber;

        movieApiClient.searchMovieApi(searchQuery,pageNumber);
    }

    //calling the method to call api search movies in next pages
    public void searchMovieInNextPageApi(){
        pageNumber=(pageNumber+1);
        movieApiClient.searchMovieApi(searchQuery,pageNumber);
    }

    //pretend to get data from webservice or database
    public LiveData<List<MovieModel>> getMovies(){

        return movieApiClient.getMovies();
    }


}
