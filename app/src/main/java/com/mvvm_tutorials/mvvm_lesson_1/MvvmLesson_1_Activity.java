package com.mvvm_tutorials.mvvm_lesson_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mvvm_tutorials.R;
import com.mvvm_tutorials.mvvm_lesson_1.adapter.PlacesAdapter;
import com.mvvm_tutorials.mvvm_lesson_1.model.PlacesModel;
import com.mvvm_tutorials.mvvm_lesson_1.viewmodel.MVVMActivity_ViewModel;

import java.util.List;

public class MvvmLesson_1_Activity extends AppCompatActivity {

    private RecyclerView recycler_view;
    private FloatingActionButton fab;
    private ProgressBar progress_bar;
    private PlacesAdapter placesAdapter;

    private MVVMActivity_ViewModel mvvmActivity_viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvvm_lesson1);

        recycler_view=findViewById(R.id.recycler_view);
        fab=findViewById(R.id.fab);
        progress_bar=findViewById(R.id.progress_bar);

        mvvmActivity_viewModel = new ViewModelProvider(this).get(MVVMActivity_ViewModel.class  );
        mvvmActivity_viewModel.init(); // retrieved the data from repository
        mvvmActivity_viewModel.getPlaces().observe(this, new Observer<List<PlacesModel>>() {
            @Override
            public void onChanged(List<PlacesModel> placesModels) {
                /*trigger what is changing to the MutableLiveData objects(listPlaceModel)
                LiveData only be observed
                and also In LiveData user change screen rotation or lock the phone and back data will not changed*/
                placesAdapter.notifyDataSetChanged();
            }
        });

        initRecyclerView();
    }

    private void initRecyclerView(){
        placesAdapter= new PlacesAdapter(mvvmActivity_viewModel.getPlaces().getValue(),this); //mvvmActivity_viewModel.getPlaces().getValue() return the list
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        recycler_view.setAdapter(placesAdapter);
    }

    public void showProgressBar(){
        progress_bar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar(){
        progress_bar.setVisibility(View.GONE);
    }
}