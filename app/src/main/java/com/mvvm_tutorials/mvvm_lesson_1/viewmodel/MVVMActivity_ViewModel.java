package com.mvvm_tutorials.mvvm_lesson_1.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mvvm_tutorials.mvvm_lesson_1.model.PlacesModel;
import com.mvvm_tutorials.mvvm_lesson_1.repositories.PlaceRepository;

import java.util.ArrayList;
import java.util.List;

// or you can name this class  as PlacesModel_ViewModel
public class MVVMActivity_ViewModel extends ViewModel {

    /* MutableLiveData is a subclass of LiveData.
     MutableLiveData can be changed and LiveData can't be changed(immutable)
     LiveData only be observed
     and also In LiveData user change screen rotation or lock the phone and back data will not changed*/

    private MutableLiveData<List<PlacesModel>> listPlaceModel;

    public LiveData<List<PlacesModel>> getPlaces(){
        return listPlaceModel;
    }

    private PlaceRepository mRepository;

    public void init(){

        if(listPlaceModel !=null){
            return; // if there is a value or already retrieved the data
        }
        mRepository=  PlaceRepository.getInstance();
        listPlaceModel= mRepository.getPlaces();

    }


}
