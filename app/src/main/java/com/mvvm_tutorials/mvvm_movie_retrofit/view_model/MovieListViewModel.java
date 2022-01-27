package com.mvvm_tutorials.mvvm_movie_retrofit.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mvvm_tutorials.mvvm_lesson_1.repositories.PlaceRepository;
import com.mvvm_tutorials.mvvm_movie_retrofit.model.MovieModel;
import com.mvvm_tutorials.mvvm_movie_retrofit.repositories.MovieRepository;

import java.util.List;

public class MovieListViewModel extends ViewModel {


    private MovieRepository mRepository;

    public MovieListViewModel() {
        mRepository = MovieRepository.getInstance();
    }


    //------------------------------for search api calling-----------------------------------------
    //--------------------------------------------------------------------------------------------

    //calling the method to call api search movies
    public void searchMovieApi(String searchQuery, int pageNUmber) {
        mRepository.searchMovieApi(searchQuery, pageNUmber);
    }

    //calling the method to call api search movies in next pages
    public void searchMovieInNextPageApi() {
        mRepository.searchMovieInNextPageApi();
    }

    //getters of mutableLivedata.
    public LiveData<List<MovieModel>> getMovies() {

        return mRepository.getMovies();
    }


    //------------------------------for popular api calling-----------------------------------------
    // --------------------------------------------------------------------------------------------

    //calling the method to call api popular movies
    public void popularMovieApi(int pageNUmber) {
        mRepository.popularMovieApi(pageNUmber);
    }

    //calling the method to call api popular movies in next pages
    public void popularMovieInNextPageApi() {
        mRepository.popularMovieInNextPageApi();
    }

    //getters of mutableLivedata.
    public LiveData<List<MovieModel>> getPopularMovies() {

        return mRepository.getPopularMovies();
    }

}
