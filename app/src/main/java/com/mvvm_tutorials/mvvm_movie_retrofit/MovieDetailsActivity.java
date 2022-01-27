package com.mvvm_tutorials.mvvm_movie_retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mvvm_tutorials.R;
import com.mvvm_tutorials.mvvm_movie_retrofit.model.MovieModel;

public class MovieDetailsActivity extends AppCompatActivity {


    ImageView imageview_details, backBtn;
    TextView movie_Title, movie_overView;
    RatingBar rating_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        imageview_details=findViewById(R.id.imageview_details);
        movie_Title=findViewById(R.id.movie_Title);
        movie_overView=findViewById(R.id.movie_overView);
        rating_bar=findViewById(R.id.rating_bar);

        backBtn=findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        
        getDataFromIntent();

    }

    private void getDataFromIntent() {

        if (getIntent().hasExtra("movie")){

            MovieModel movieModel = getIntent().getParcelableExtra("movie");

            movie_Title.setText(movieModel.getTitle());
            movie_overView.setText(movieModel.getOverview());

            Glide.with(this).load("https://image.tmdb.org/t/p/w500"+movieModel.getBackdrop_path()).placeholder(R.drawable.loading_image).into(imageview_details);

            rating_bar.setRating((movieModel.getVote_average()) / 2);
            rating_bar.setIsIndicator(true);

        }


    }
}