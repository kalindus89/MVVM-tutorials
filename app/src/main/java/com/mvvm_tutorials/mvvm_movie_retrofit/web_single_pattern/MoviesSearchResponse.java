package com.mvvm_tutorials.mvvm_movie_retrofit.web_single_pattern;


import com.google.gson.annotations.SerializedName;
import com.mvvm_tutorials.mvvm_movie_retrofit.model.MovieModel;

import java.util.List;

//this class is for getting multiple movies in results array
//https://api.themoviedb.org/3/movie/popular?api_key=459351d1311a26f26c93016a0d788db7

public class MoviesSearchResponse {

    @SerializedName("total_results")
    private int total_results;

    @SerializedName("results")
    //@Expose() then you can uses different name for object
    private List<MovieModel> results;

    public int getTotal_results() {
        return total_results;
    }

    public List<MovieModel> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return "MoviesResponse{" +
                "total_results=" + total_results +
                ", results=" + results +
                '}';
    }
}
