package com.example.afinal;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.afinal.data.AsteroidData;

import java.util.ArrayList;

public class AsteroidAdapter extends RecyclerView.Adapter<AsteroidAdapter.AsteroidViewHolder> {
    private ArrayList<AsteroidData> asteroidList;
    private AsteroidAdapter.OnAsteroidItemClickListener onAsteroidItemClickListener;

    public interface OnAsteroidItemClickListener {
        void onAsteroidItemClick(AsteroidData asteroidData);
    }
    public AsteroidAdapter(OnAsteroidItemClickListener onAsteroidItemClickListener){
        asteroidList = new ArrayList<>();
        this.onAsteroidItemClickListener = onAsteroidItemClickListener;
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
//        Context ctx = this.itemView.getContext();
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAsteroidItemClickListener.onAsteroidItemClick(
                            asteroidList.get(getAdapterPosition())
                    );
                }
            });

        }
        public void bind(AsteroidData asteroid){
            Context ctx = this.itemView.getContext();
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
            String diameterUnitsPref = preferences.getString(
                    ctx.getString(R.string.pref_diameter_units_key),
                    ctx.getString(R.string.pref_diameter_unit_default)
            );

            nameTV.setText(asteroid.getName());
            diameterTV.setText(ctx.getString(
                    R.string.asteroid_diameter_str,
                    asteroid.getDiameterMin(diameterUnitsPref),
                    asteroid.getDiameterMax(diameterUnitsPref),
                    diameterUnitsPref
            ));

            distanceTV.setText(ctx.getString(
                    R.string.asteroid_distance_str,
                    asteroid.getDistanceKilometers()
            ));
            velocityTV.setText(ctx.getString(
                    R.string.asteroid_velocity_str,
                    asteroid.getVelocityKmph()
            ));
            hazardTV.setText(String.valueOf(asteroid.isHazardous()));
        }
    }

    //true = metric
    //false = imperial
    public boolean unitPrefMetric(String pref){
        //change this when unit preferences have been set up
        if(false){
            return false;
        }
        return true;
    }

}
