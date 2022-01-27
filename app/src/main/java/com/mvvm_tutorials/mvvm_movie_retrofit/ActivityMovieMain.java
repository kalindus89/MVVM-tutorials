package com.mvvm_tutorials.mvvm_movie_retrofit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mvvm_tutorials.R;
import com.mvvm_tutorials.mvvm_lesson_1.adapter.PlacesAdapter;
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

public class ActivityMovieMain extends AppCompatActivity implements MovieItemOnClickListener {


    /* We have added the network security config that makesure sub domain URls are working
     (filename xml folder: network-security-config) and manifest file*/

    RecyclerView recycler_view;
    Toolbar toolBar;
    SearchView search_view;
    private MovieListViewModel movieListViewModel;
    private MovieRecyclerAdapter movieRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_main);


        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        recycler_view = findViewById(R.id.recycler_view);
        search_view = findViewById(R.id.search_view);

        toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        configureRecyclerView();
        setupSearchView();

        //calling the observers
        observeAnyDataChange();

      /*  searchByName=findViewById(R.id.searchByName);
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
        });*/

    }

    //Observing any data change
    private void observeAnyDataChange() {

        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                //trigger/listening any data changes

                if (movieModels != null) {
                    /*for(MovieModel movieModel : movieModels){
                        System.out.println("aaaaaaaaaaa "+movieModel.getRelease_date()+" "+movieModel.getTitle());
                    }*/
                    movieRecyclerAdapter.setmMovies(movieModels);
                }
            }
        });
    }


    private void setupSearchView() {

        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                //calling the method to call api
                movieListViewModel.searchMovieApi(query, 1); // if pageNumber is 1 new list. else adding to current list. check searchMovieApi in MovieApiClient class
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void configureRecyclerView() {

        movieRecyclerAdapter = new MovieRecyclerAdapter(this); //mvvmActivity_viewModel.getPlaces().getValue() return the list
// passing data inside the observeAnyDataChange() method
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        recycler_view.setAdapter(movieRecyclerAdapter);


        //loading next page of Api response while scrolling
        recycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {

                    //calling the method to call api search movies
                    movieListViewModel.searchMovieInNextPageApi();
                }


            }
        });

    }

    //get listiew item click listeners
    @Override
    public void onMovieClick(int position) {
        Toast.makeText(this, "Click item position: " + String.valueOf(position + 1), Toast.LENGTH_SHORT).show();

    }


    private void getRetrofitResponse() {

        MovieApiInterface movieApiInterface = ServiceClass.getMovieApiInterface();
        //two ways. with or without response call.. check next method without it
        Call<MoviesSearchResponse> responseCall = movieApiInterface.getSearchMovies(Credentials.API_KEY, "Jack Reacher", 1);

        responseCall.enqueue(new Callback<MoviesSearchResponse>() {
            @Override
            public void onResponse(Call<MoviesSearchResponse> call, Response<MoviesSearchResponse> response) {

                if (response.code() == 200) {

                    List<MovieModel> movieModelList = new ArrayList<>(response.body().getResults());
                    int i = 0;
                    for (MovieModel movieModel : movieModelList) {
                        i++;
                        System.out.println("aaaaaa  " + i + " " + movieModel.getTitle() + " " + movieModel.getRelease_date());
                    }

                } else {
                    System.out.println("aaaaaa  error");

                }

            }

            @Override
            public void onFailure(Call<MoviesSearchResponse> call, Throwable t) {
                System.out.println("aaaaaa " + t.getMessage());

            }
        });


    }

    private void getASingleMovieDetails() {

        MovieApiInterface movieApiInterface = ServiceClass.getMovieApiInterface();
        movieApiInterface.getMovieFromId(500, Credentials.API_KEY).enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                if (response.code() == 200) {

                    MovieModel movieModel = response.body();
                    System.out.println("aaaaaa  " + movieModel.getTitle() + " " + movieModel.getRelease_date());

                } else {
                    System.out.println("aaaaaa  error");

                }
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {
                System.out.println("aaaaaa " + t.getMessage());
            }
        });

    }


}