package com.mvvm_tutorials.mvvm_movie_retrofit.backgoud_api_run;

import androidx.lifecycle.MutableLiveData;


import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppExecutors {

    private static AppExecutors instance;

    public static AppExecutors getInstance() {
        if(instance==null){
            instance = new AppExecutors();
        }
        return instance;
    }

    //run api in background without knowing to user. making background threads Executors.newScheduledThreadPool(3);
    // (3) number of threads we are going to use
    private final ScheduledExecutorService mNetworkIO = Executors.newScheduledThreadPool(3);

    public ScheduledExecutorService networkIO(){
        return mNetworkIO;
    }




}
