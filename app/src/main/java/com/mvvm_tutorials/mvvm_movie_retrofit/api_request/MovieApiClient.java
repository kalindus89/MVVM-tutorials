package com.mvvm_tutorials.mvvm_movie_retrofit.api_request;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mvvm_tutorials.mvvm_movie_retrofit.backgoud_api_run.AppExecutors;
import com.mvvm_tutorials.mvvm_movie_retrofit.model.MovieModel;
import com.mvvm_tutorials.mvvm_movie_retrofit.repositories.MovieRepository;
import com.mvvm_tutorials.mvvm_movie_retrofit.web_single_pattern.Credentials;
import com.mvvm_tutorials.mvvm_movie_retrofit.web_single_pattern.MovieApiInterface;
import com.mvvm_tutorials.mvvm_movie_retrofit.web_single_pattern.MoviesPopularResponse;
import com.mvvm_tutorials.mvvm_movie_retrofit.web_single_pattern.MoviesSearchResponse;
import com.mvvm_tutorials.mvvm_movie_retrofit.web_single_pattern.ServiceClass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieApiClient {

    private static MovieApiClient instance;

    public static MovieApiClient getInstance() {
        if (instance == null) {
            instance = new MovieApiClient();
        }
        return instance;
    }

   //------------------------------for search api calling-----------------------------------------
    //--------------------------------------------------------------------------------------------

    private MutableLiveData<List<MovieModel>> mMovies= new MutableLiveData<>();
    private RetrieveSearchMovieRunnable retrieveSearchMovieRunnable;

    //pretend to get data from webservice or database
    public LiveData<List<MovieModel>> getMovies() {

        return mMovies;
    }

    //This method that we are going to call through the classes(Actiity, ViewModel, Repository, thisClass)
    public void searchMovieApi(String searchQuery, int pageNumber) {

        if(retrieveSearchMovieRunnable!=null){
            retrieveSearchMovieRunnable=null;
        }

        retrieveSearchMovieRunnable= new RetrieveSearchMovieRunnable(searchQuery,pageNumber);

        // Created two threads. One to Retrieve data from API and otherCancelling the API call if no data retrieve with in 25 seconds
        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retrieveSearchMovieRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the API call if no data retrieve with in 25 seconds
                myHandler.cancel(true);

            }
        }, 10000, TimeUnit.MILLISECONDS);

    }

    private class RetrieveSearchMovieRunnable implements Runnable {

        private String searchQuery;
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveSearchMovieRunnable(String searchQuery, int pageNumber) {
            this.searchQuery = searchQuery;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {

            try {
                Response searchResponse = getSearchMovies(searchQuery,pageNumber).execute();
                if (cancelRequest) {
                    return;
                }

                if(searchResponse.code()==200){
                    List<MovieModel> movieModelList = new ArrayList<>(((MoviesSearchResponse)searchResponse.body()).getResults());

                    if(pageNumber==1){
                        //sending data to live data

                        //postValue: used for background thread;
                        //setValue: used for not background thread;
                        mMovies.postValue(movieModelList);
                    }else {

                        List<MovieModel> currentMovies = mMovies.getValue(); // get previous data
                        currentMovies.addAll(movieModelList); // add new data
                        mMovies.postValue(currentMovies);

                    }

                    /*int i=0;
                    for(MovieModel movieModel : movieModelList){
                        i++;
                        System.out.println("aaaaaa  "+i+ " "+movieModel.getTitle()+" "+movieModel.getRelease_date());
                    }*/
                }else {
                    String error =searchResponse.errorBody().string();
                    System.out.println("aaaaaa  "+error);
                    mMovies.postValue(null);

                }

            } catch (IOException e) {
                e.printStackTrace();
                mMovies.postValue(null);
            }

            if (cancelRequest) {
                return;
            }
        }

        private Call<MoviesSearchResponse> getSearchMovies(String searchQuery, int pageNumber) {
            return ServiceClass.getMovieApiInterface().getSearchMovies(
                    Credentials.API_KEY,
                    searchQuery,
                    pageNumber
            );
        }

        private void setCancelRequest() {
            cancelRequest = true;
        }


    }



    //------------------------------for popular api calling-----------------------------------------
    //--------------------------------------------------------------------------------------------


    private MutableLiveData<List<MovieModel>> mPopularMovies= new MutableLiveData<>();
    private RetrievePopularMovieRunnable retrievePopularMovieRunnable;

    //pretend to get data from webservice or database
    public LiveData<List<MovieModel>> getPopularMovies() {

        return mPopularMovies;
    }

    //This method that we are going to call through the classes(Actiity, ViewModel, Repository, thisClass)
    public void popularMovieApi(int pageNumber) {

        if(retrievePopularMovieRunnable!=null){
            retrievePopularMovieRunnable=null;
        }

        retrievePopularMovieRunnable= new RetrievePopularMovieRunnable(pageNumber);

        // Created two threads. One to Retrieve data from API and otherCancelling the API call if no data retrieve with in 25 seconds
        final Future myHandler2 = AppExecutors.getInstance().networkIO().submit(retrievePopularMovieRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the API call if no data retrieve with in 25 seconds
                myHandler2.cancel(true);

            }
        }, 10000, TimeUnit.MILLISECONDS);

    }

    private class RetrievePopularMovieRunnable implements Runnable {

        private int pageNumber_popular;
        boolean cancelRequest;

        public RetrievePopularMovieRunnable(int pageNumber_popular) {
            this.pageNumber_popular = pageNumber_popular;
            cancelRequest = false;
        }

        @Override
        public void run() {

            try {
                Response popularResponse = getPopularMovies(pageNumber_popular).execute();
                if (cancelRequest) {
                    return;
                }

                if(popularResponse.code()==200){
                    List<MovieModel> movieModelList = new ArrayList<>(((MoviesPopularResponse)popularResponse.body()).getResults());

                    if(pageNumber_popular==1){
                        //sending data to live data

                        //postValue: used for background thread;
                        //setValue: used for not background thread;
                        mPopularMovies.postValue(movieModelList);
                    }else {

                        List<MovieModel> currentMovies = mPopularMovies.getValue(); // get previous data
                        currentMovies.addAll(movieModelList); // add new data
                        mPopularMovies.postValue(currentMovies);

                    }

                }else {
                    String error =popularResponse.errorBody().string();
                    System.out.println("aaaaaa  "+error);
                    mPopularMovies.postValue(null);

                }

            } catch (IOException e) {
                e.printStackTrace();
                mPopularMovies.postValue(null);
            }

            if (cancelRequest) {
                return;
            }
        }

        private Call<MoviesPopularResponse> getPopularMovies(int pageNumber) {
            return ServiceClass.getMovieApiInterface().getPopularMovies(
                    Credentials.API_KEY,
                    pageNumber
            );
        }

        private void setCancelRequest() {
            cancelRequest = true;
        }


    }

}
