package com.mvvm_tutorials.mvvm_movie_retrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mvvm_tutorials.R;
import com.mvvm_tutorials.mvvm_lesson_1.viewmodel.MVVMActivity_ViewModel;
import com.mvvm_tutorials.mvvm_movie_retrofit.model.MovieModel;
import com.mvvm_tutorials.mvvm_movie_retrofit.view_model.MovieListViewModel;
import com.mvvm_tutorials.mvvm_movie_retrofit.web_single_pattern.Credentials;
import com.mvvm_tutorials.mvvm_movie_retrofit.web_single_pattern.MovieApiInterface;
import com.mvvm_tutorials.mvvm_movie_retrofit.web_single_pattern.MoviesSearchResponse;
import com.mvvm_tutorials.mvvm_movie_retrofit.web_single_pattern.ServiceClass;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityMovieMain extends AppCompatActivity {


    /* We have added the network security config that makesure sub domain URls are working
     (filename xml folder: network-security-config) and manifest file*/

    Button searchByName,searchId;
    private MovieListViewModel movieListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_main);


        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        //calling the observers
        observeAnyDataChange();

        searchByName=findViewById(R.id.searchByName);
        searchByName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchMovieApi("Fast",1);
                // getRetrofitResponse();
            }
        });

        searchId=findViewById(R.id.searchId);
        searchId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchMovieApi("Fast",2);
                // getASingleMovieDetails();
            }
        });

    }

    //Observing any data change
    private void observeAnyDataChange(){

        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                //trigger/listening any data changes

                if(movieModels !=null){
                    for(MovieModel movieModel : movieModels){
                        System.out.println("aaaaaaaaaaa "+movieModel.getRelease_date()+" "+movieModel.getTitle());
                    }
                }
            }
        });
    }

    //calling the method to call api
    public void searchMovieApi(String searchQuery, int pageNUmber){
        movieListViewModel.searchMovieApi(searchQuery,pageNUmber);
    }


    private void getRetrofitResponse() {

        MovieApiInterface movieApiInterface = ServiceClass.getMovieApiInterface();
        //two ways. with or without response call.. check next method without it
        Call<MoviesSearchResponse> responseCall=movieApiInterface.getSearchMovies(Credentials.API_KEY,"Jack Reacher",1);

        responseCall.enqueue(new Callback<MoviesSearchResponse>() {
            @Override
            public void onResponse(Call<MoviesSearchResponse> call, Response<MoviesSearchResponse> response) {

                if(response.code() == 200){

                    List<MovieModel> movieModelList = new ArrayList<>(response.body().getResults());
                    int i=0;
                    for(MovieModel movieModel : movieModelList){
                        i++;
                        System.out.println("aaaaaa  "+i+ " "+movieModel.getTitle()+" "+movieModel.getRelease_date());
                    }

                }else{
                    System.out.println("aaaaaa  error");

                }

            }

            @Override
            public void onFailure(Call<MoviesSearchResponse> call, Throwable t) {
                System.out.println("aaaaaa "+t.getMessage());

            }
        });



    }

    private void getASingleMovieDetails(){

        MovieApiInterface movieApiInterface = ServiceClass.getMovieApiInterface();
        movieApiInterface.getMovieFromId(500,Credentials.API_KEY).enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                if(response.code() == 200){

                    MovieModel movieModel = response.body();
                    System.out.println("aaaaaa  "+movieModel.getTitle()+" "+movieModel.getRelease_date());

                }else{
                    System.out.println("aaaaaa  error");

                }
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {
                System.out.println("aaaaaa "+t.getMessage());
            }
        });

    }
}