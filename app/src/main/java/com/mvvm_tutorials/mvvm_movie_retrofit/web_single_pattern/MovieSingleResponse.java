package com.mvvm_tutorials.mvvm_movie_retrofit.web_single_pattern;


import com.mvvm_tutorials.mvvm_movie_retrofit.model.MovieModel;

//this class is for getting 1 movie data
//https://api.themoviedb.org/3/movie/550?api_key=459351d1311a26f26c93016a0d788db7
// id=550

public class MovieSingleResponse {



    private MovieModel results;

    public MovieModel getResults() {
        return results;
    }

    @Override
    public String toString() {
        return "MoviesIdResponse{" +
                "results=" + results +
                '}';
    }
}
