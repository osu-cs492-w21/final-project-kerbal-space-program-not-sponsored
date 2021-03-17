package com.example.afinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.afinal.data.AsteroidData;

import java.util.ArrayList;

public class AsteroidAdapter extends RecyclerView.Adapter<AsteroidAdapter.AsteroidViewHolder> {
    private ArrayList<AsteroidData> asteroidList;
    //click listener

    public AsteroidAdapter(){
        asteroidList = new ArrayList<>();
    }

    public void updateAsteroidList(ArrayList<AsteroidData> asteroidList) {
        this.asteroidList = asteroidList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.asteroidList.size();
    }

    @NonNull
    @Override
    public AsteroidViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.asteroid_list_item, parent, false);
        return new AsteroidViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AsteroidViewHolder holder, int position) {
        holder.bind(this.asteroidList.get(position));
    }

    class AsteroidViewHolder extends RecyclerView.ViewHolder{
        Context ctx = this.itemView.getContext();
        final private TextView nameTV;
        final private TextView diameterTV;
        final private TextView distanceTV;
        final private TextView velocityTV;
        final private TextView hazardTV;
        public AsteroidViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.tv_name);
            diameterTV = itemView.findViewById(R.id.tv_diameter);
            distanceTV = itemView.findViewById(R.id.tv_distance);
            velocityTV = itemView.findViewById(R.id.tv_velocity);
            hazardTV = itemView.findViewById(R.id.tv_hazard);
        }
        public void bind(AsteroidData asteroid){
            nameTV.setText(asteroid.getName());
            diameterTV.setText(ctx.getString(
                    R.string.asteroid_diameter_str,
                    asteroid.getDiameterMin(),
                    asteroid.getDiameterMax()
            ));
            distanceTV.setText(ctx.getString(
                    R.string.asteroid_distance_str,
                    asteroid.getDistance()
            ));
            velocityTV.setText(ctx.getString(
                    R.string.asteroid_velocity_str,
                    asteroid.getVelocity()
            ));
            hazardTV.setText(String.valueOf(asteroid.isHazardous()));
        }
    }

}
