package com.mvvm_tutorials.mvvm_movie_retrofit.web_single_pattern;

import com.mvvm_tutorials.mvvm_movie_retrofit.model.MovieModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApiInterface {

    @GET("search/movie")
    Call<MoviesSearchResponse> getSearchMovies(
            @Query("api_key") String api_key,
            @Query("query") String searchQuery,
            @Query("page") int pageNumber
    );

    //https://api.themoviedb.org/3/movie/550?api_key=459351d1311a26f26c93016a0d788db7
    @GET("movie/{movie_id}?")
    Call<MovieModel> getMovieFromId(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key
    );

    @GET("movie/popular")
    Call<MoviesPopularResponse> getPopularMovies(
            @Query("api_key") String api_key,
            @Query("page") int pageNumber
    );

}
