package com.mvvm_tutorials.mvvm_lesson_1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mvvm_tutorials.R;
import com.mvvm_tutorials.mvvm_lesson_1.model.PlacesModel;

import java.util.ArrayList;
import java.util.List;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.ViewHolder> {

    private List<PlacesModel> placesModelsList= new ArrayList<>();
    private Context context;

    public PlacesAdapter(List<PlacesModel> placesModelsList, Context context) {
        this.placesModelsList = placesModelsList;
        this.context = context;
    }

    @NonNull
    @Override
    public PlacesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mvvm_lesson_item1, parent, false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlacesAdapter.ViewHolder holder, int position) {

        holder.place_Name.setText(placesModelsList.get(position).getTitle());
        Glide.with(context).load(placesModelsList.get(position).getImageUrl()).placeholder(R.drawable.loading_image).into(holder.place_image);
    }

    @Override
    public int getItemCount() {
        return placesModelsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView place_Name;
        ImageView place_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            place_image = itemView.findViewById(R.id.place_image);
            place_Name = itemView.findViewById(R.id.place_Name);
        }
    }
}
