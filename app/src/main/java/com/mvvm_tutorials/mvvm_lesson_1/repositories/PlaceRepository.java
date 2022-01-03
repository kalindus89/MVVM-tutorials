package com.mvvm_tutorials.mvvm_lesson_1.repositories;

import androidx.lifecycle.MutableLiveData;

import com.mvvm_tutorials.mvvm_lesson_1.model.PlacesModel;

import java.util.ArrayList;
import java.util.List;

/*
* Singleton pattern
* */

public class PlaceRepository {

    // repository used to access Database or webservice

    // here we just hardcode values

    private static PlaceRepository instance;
    private List<PlacesModel> dataSet= new ArrayList<>();

    public static PlaceRepository getInstance(){
        if(instance==null){
            instance = new PlaceRepository();
        }
        return instance;
    }

    //pretend to get data from webservice or database
    public MutableLiveData<List<PlacesModel>> getPlaces(){
        setPlaces();

        MutableLiveData<List<PlacesModel>> newData = new MutableLiveData<>();
        newData.setValue(dataSet);
        return newData;
    }

    private void setPlaces(){

        dataSet.add(new PlacesModel("Canada","https://i.redd.it/tpsnoz5bzo501.jpg"));
        dataSet.add(new PlacesModel("Australia","https://i.redd.it/obx4zydshg601.jpg"));
        dataSet.add(new PlacesModel("Sri Lanka","https://i.redd.it/tpsnoz5bzo501.jpg"));
        dataSet.add(new PlacesModel("England","https://i.redd.it/obx4zydshg601.jpg"));
        dataSet.add(new PlacesModel("United State","https://i.redd.it/tpsnoz5bzo501.jpg"));
        dataSet.add(new PlacesModel("Greenland","https://i.redd.it/obx4zydshg601.jpg"));
        dataSet.add(new PlacesModel("India","https://i.redd.it/tpsnoz5bzo501.jpg"));
    }


}
