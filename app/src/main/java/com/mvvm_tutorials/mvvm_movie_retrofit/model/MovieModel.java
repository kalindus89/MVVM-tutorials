package com.mvvm_tutorials.mvvm_movie_retrofit.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


//Model class for movies. keys of Api
//https://api.themoviedb.org/3/movie/popular?api_key=459351d1311a26f26c93016a0d788db7

public class MovieModel implements Parcelable {

//Parcelable helps to pass datato activity fast.
    //intent.putExtra("MovieModel", new MovieModel("1","",""));

    //Bundle data = getIntent().getExtras();
    //Student student = (Student) data.getParcelable("student");

    String title;
    String poster_path;
    String release_date;
    int id;
    float vote_average;
    String overview;
    String original_language;
    String backdrop_path;
    int runtime;


    public MovieModel(String title, String poster_path, String release_date, int id, float vote_average, String overview, String original_language, String backdrop_path, int runtime) {
        this.title = title;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.id = id;
        this.vote_average = vote_average;
        this.overview = overview;
        this.original_language = original_language;
        this.backdrop_path = backdrop_path;
        this.runtime = runtime;
    }

    protected MovieModel(Parcel in) {
        title = in.readString();
        poster_path = in.readString();
        release_date = in.readString();
        id = in.readInt();
        vote_average = in.readFloat();
        overview = in.readString();
        original_language = in.readString();
        backdrop_path = in.readString();
        runtime = in.readInt();
    }

    public String getTitle() {
        return title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public int getId() {
        return id;
    }

    public float getVote_average() {
        return vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public int getRuntime() {
        return runtime;
    }

    //Parcelable helps to pass datato activity fast.
    //intent.putExtra("MovieModel", new MovieModel("1","",""));

    //Bundle data = getIntent().getExtras();
    //Student student = (Student) data.getParcelable("student");

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(poster_path);
        parcel.writeString(release_date);
        parcel.writeInt(id);
        parcel.writeFloat(vote_average);
        parcel.writeString(overview);
        parcel.writeString(original_language);
        parcel.writeString(backdrop_path);
        parcel.writeInt(runtime);
    }


    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };
}
