package com.mvvm_tutorials.mvvm_movie_retrofit.api_request;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mvvm_tutorials.mvvm_movie_retrofit.backgoud_api_run.AppExecutors;
import com.mvvm_tutorials.mvvm_movie_retrofit.model.MovieModel;
import com.mvvm_tutorials.mvvm_movie_retrofit.repositories.MovieRepository;
import com.mvvm_tutorials.mvvm_movie_retrofit.web_single_pattern.Credentials;
import com.mvvm_tutorials.mvvm_movie_retrofit.web_single_pattern.MovieApiInterface;
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
    private MutableLiveData<List<MovieModel>> mMovies;
    private RetrieveSearchMovieRunnable retrieveSearchMovieRunnable;

    public MovieApiClient() {
        mMovies = new MutableLiveData<>();
    }

    public static MovieApiClient getInstance() {
        if (instance == null) {
            instance = new MovieApiClient();
        }
        return instance;
    }

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
        }, 25000, TimeUnit.MILLISECONDS);

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


}
