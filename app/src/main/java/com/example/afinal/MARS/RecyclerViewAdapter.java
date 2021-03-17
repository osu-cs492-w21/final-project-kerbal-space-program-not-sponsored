package com.example.afinal.MARS;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.afinal.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<com.example.afinal.MARS.RecyclerViewAdapter.ViewHolder>{
    private Context contextC;
    private List<RoverPhotos> roverPhoto = new ArrayList<>();
    public RecyclerViewAdapter(Context context){
        this.contextC = context;
    }

    //create layout for picture and information
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View layoutView;
        layoutView = LayoutInflater.from(contextC).inflate(R.layout.rv_photos, parent,false);
        return new ViewHolder(layoutView);
    }

    //get the total number of rover pictures
    @Override
    public int getItemCount(){
        return roverPhoto.size();
    }

    //use info from VeiwHolder and print out the result
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        Glide.with(contextC).load(roverPhoto.get(position).getImg_src()).into(holder.pictureView);

        //store text information in TextView and then print out info
        String photoNum = holder.itemView.getResources().getString(R.string.MARSphoto_id) + roverPhoto.get(position).getId();
        holder.numberforPhoto.setText(photoNum);
        String cameraName = holder.itemView.getResources().getString(R.string.MARScamera) + roverPhoto.get(position).getCamera().getFull_name() + "(" + roverPhoto.get(position).getCamera().getName()+")";
        holder.nameOfCamera.setText(cameraName);
        String dayOnEarth = holder.itemView.getResources().getString(R.string.MARSearth_date) + roverPhoto.get(position).getEarth_date();
        holder.dayOnEarth.setText(dayOnEarth);
        String marsSolDay = holder.itemView.getResources().getString(R.string.MARSmartin_sol) + roverPhoto.get(position).getSol();
        holder.marsSolDay.setText(marsSolDay);
    }

    public void setUpMarsList(List<RoverPhotos> marsRoverPhotos){
        roverPhoto = marsRoverPhotos;
        notifyDataSetChanged();
    }

    //holds info for picture info
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView pictureView;
        TextView marsSolDay, dayOnEarth, nameOfCamera, numberforPhoto;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            pictureView = itemView.findViewById(R.id.roverPhoto);
            numberforPhoto = itemView.findViewById(R.id.roverPhotoId);
            nameOfCamera = itemView.findViewById(R.id.roverCameraName);
            dayOnEarth = itemView.findViewById(R.id.roverEarthDay);
            marsSolDay = itemView.findViewById(R.id.roverPhotoSol);
        }
    }
}