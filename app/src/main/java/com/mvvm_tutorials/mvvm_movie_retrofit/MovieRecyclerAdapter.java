package com.mvvm_tutorials.mvvm_movie_retrofit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mvvm_tutorials.R;
import com.mvvm_tutorials.mvvm_lesson_1.model.PlacesModel;
import com.mvvm_tutorials.mvvm_movie_retrofit.model.MovieModel;

import java.util.ArrayList;
import java.util.List;

public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.ViewHolder>{

    List<MovieModel> mMovies;
    private  MovieItemOnClickListener movieOnClickListener;

    public void setmMovies(List<MovieModel> mMovies) {
        this.mMovies = mMovies;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MovieRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        ViewHolder viewHolder=new ViewHolder(view,movieOnClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieRecyclerAdapter.ViewHolder holder, int position) {

        holder.movie_title.setText(mMovies.get(position).getTitle());
        holder.movie_runtime.setText(String.valueOf(mMovies.get(position).getRuntime()));
        holder.movie_release_date.setText(mMovies.get(position).getRelease_date());
        holder.rating_bar.setRating( (mMovies.get(position).getVote_average()) /2 );

        Glide.with(holder.itemView.getContext()).load(mMovies.get(position).getPoster_path()).placeholder(R.drawable.loading_image).into(holder.movie_img);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView movie_title,movie_runtime,movie_release_date;
        ImageView movie_img;
        RatingBar rating_bar;

        //Click listeners
        MovieItemOnClickListener onMovieItemClickListener;

        public ViewHolder(@NonNull View itemView, MovieItemOnClickListener onMovieItemClickListener) {
            super(itemView);

            movie_title = itemView.findViewById(R.id.movie_title);
            movie_runtime = itemView.findViewById(R.id.movie_runtime);
            movie_release_date = itemView.findViewById(R.id.movie_release_date);
            movie_img = itemView.findViewById(R.id.movie_img);
            rating_bar = itemView.findViewById(R.id.rating_bar);
            this.onMovieItemClickListener=onMovieItemClickListener;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {

            onMovieItemClickListener.onMovieClick(getAdapterPosition());
        }
    }
}
