package com.mvvm_tutorials.mvvm_movie_retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mvvm_tutorials.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityMovieMain extends AppCompatActivity {
    Button searchByName,searchId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_main);

        searchByName=findViewById(R.id.searchByName);
        searchByName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRetrofitResponse();
            }
        });

        searchId=findViewById(R.id.searchId);
        searchId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getASingleMovieDetails();
            }
        });

    }

    private void getRetrofitResponse() {

        MovieApiInterface movieApiInterface = ServiceClass.getMovieApiInterface();
        movieApiInterface.getSearchMovies(Credentials.API_KEY,"Jack Reacher",1).enqueue(new Callback<MoviesSearchResponse>() {
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