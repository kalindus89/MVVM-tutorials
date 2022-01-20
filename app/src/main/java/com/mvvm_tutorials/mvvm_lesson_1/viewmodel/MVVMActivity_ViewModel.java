package com.mvvm_tutorials.mvvm_lesson_1.viewmodel;

import android.os.AsyncTask;

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
    private MutableLiveData<Boolean> mIsUpdatingList = new MutableLiveData<>(); // eg: show progress bar until data is retrieve

    //getters of mutableLivedata.
    public LiveData<List<PlacesModel>> getPlaces(){
        return listPlaceModel;
    }

    //getters of mutableLivedata.
    public LiveData<Boolean> getIsUpdating(){
        return mIsUpdatingList;
    }

    private PlaceRepository mRepository;

    public void init(){

        if(listPlaceModel !=null){
            return; // if there is a value or already retrieved the data
        }
        mRepository=  PlaceRepository.getInstance();
        listPlaceModel= mRepository.getPlaces();

    }

    public void addNewDataToList(final PlacesModel placesModel){

        mIsUpdatingList.setValue(true);
        new MyTask(placesModel).execute();


    }

    private class MyTask extends AsyncTask<Void, Void, Void> {

        PlacesModel placesModel;

        MyTask( PlacesModel placesModel) {
            this.placesModel=placesModel;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(200); // sleep for 2s until updating
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            List<PlacesModel> currentPlaces = listPlaceModel.getValue();
            currentPlaces.add(placesModel);
            listPlaceModel.postValue(currentPlaces); // new updated values of LiveData list
            mIsUpdatingList.postValue(false);
        }
    }



}
