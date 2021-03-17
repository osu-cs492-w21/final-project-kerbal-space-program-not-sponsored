package com.example.afinal.MARSS;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.afinal.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import static android.view.View.VISIBLE;

public class BottomSheet extends BottomSheetDialogFragment{
    private BottomSheetBehavior bottomSheetBSB;
    private CompositeDisposable disposableContainerCD;
    private LinearLayout listLayoutLL;
    private ProgressBar roverPB;
    private MarsNetworkCall marsNetworkMNC;
    private Retrofit retrofitR;
    private String rover;
    String appiKey = "dQF0HhzI3vZCwc9MmGIc4eZ2SeaOluSi4Nmfm2ht";

    public BottomSheet(String rover){
        this.rover = rover;
        disposableContainerCD = new CompositeDisposable();
        retrofitR = RetrofitClient.getInstance();
        marsNetworkMNC = retrofitR.create(MarsNetworkCall.class);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        TextView roverName;
        TextView dayOfLaunch;
        TextView maxSolDay;
        TextView maxDate;
        TextView totalNumberPhotos;
        TextView DateofLanding;
        TextView activeOrNot;

        BottomSheetDialog text;
        text = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view;
        view = LayoutInflater.from(getContext()).inflate(R.layout.info_list, null,false);
        text.setContentView(view);

        //get information on rover
        roverPB = text.findViewById(R.id.roverProgressBar);
        AppBarLayout appBarLayout = text.findViewById(R.id.infoLayout);
        roverName = text.findViewById(R.id.roverName);
        maxDate = text.findViewById(R.id.maxDate);
        dayOfLaunch = text.findViewById(R.id.LaunchDate);
        DateofLanding = text.findViewById(R.id.landingDate);
        maxSolDay = text.findViewById(R.id.maxSolDay);
        totalNumberPhotos = text.findViewById(R.id.totalPhotos);
        activeOrNot = text.findViewById(R.id.status);
        listLayoutLL = text.findViewById(R.id.LinearLayoutView);
        ImageView ivPhoto  = text.findViewById(R.id.roverImage);
        bottomSheetBSB = BottomSheetBehavior.from((View) (view.getParent()));
        bottomSheetBSB.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);

        view.setMinimumHeight((Resources.getSystem().getDisplayMetrics().heightPixels)/ 2);
        assert appBarLayout != null;

        bottomSheetBSB.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback(){
            @Override
            //depending on if the 'i' button is clicked either show the rover information or
            //dont show the rover information
            public void onStateChanged(@NonNull View view, int x){
                if(BottomSheetBehavior.STATE_EXPANDED == x){
                    showView(appBarLayout, getActionBarSize());
                }
                else if(BottomSheetBehavior.STATE_COLLAPSED == x){
                    hideAppBar(appBarLayout);
                }
                else if(BottomSheetBehavior.STATE_HIDDEN == x){
                    dismiss();
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v){ }
        });

        ImageButton imageButton;
        imageButton = text.findViewById(R.id.closeIV);
        assert imageButton != null;
        imageButton.setOnClickListener(v-> dismiss());
        assert ivPhoto != null;

        //depending on the rover show the picture to match the rover
        if(rover == "perseverance"){
            Glide.with(requireContext()).load(getResources().getDrawable(R.mipmap.perseverance)).into(ivPhoto);
        }
        else if(rover == "curiosity"){
            Glide.with(requireContext()).load(getResources().getDrawable(R.mipmap.curiosity)).into(ivPhoto);
        }
        else if(rover == "opportunity"){
            Glide.with(requireContext()).load(getResources().getDrawable(R.mipmap.opportunity)).into(ivPhoto);
        }
        else if(rover == "spirit"){
            Glide.with(requireContext()).load(getResources().getDrawable(R.mipmap.spirit)).into(ivPhoto);
        }
        getData(roverName, dayOfLaunch, maxSolDay, maxDate, totalNumberPhotos, DateofLanding, activeOrNot);
        return text;
    }

    @Override
    public void onStart(){
        super.onStart();
        bottomSheetBSB.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    private void showView(View view, int size){
        ViewGroup.LayoutParams layoutPa;
        layoutPa = view.getLayoutParams();
        layoutPa.height = size;
        view.setLayoutParams(layoutPa);
    }

    private void hideAppBar(View view){
        ViewGroup.LayoutParams layoutPa;
        layoutPa = view.getLayoutParams();
        layoutPa.height = 0;
        view.setLayoutParams(layoutPa);
    }

    private int getActionBarSize(){
        final TypedArray array;
        int sz;
        array = requireContext().getTheme().obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        sz = (int)array.getDimension(0, 0);
        return sz;
    }

    @SuppressLint("SetTextI18n")
    private void getData(TextView RoverName, TextView landing_Date, TextView launch_Date, TextView Status, TextView max_sol, TextView max_Date, TextView total_Photos){
        disposableContainerCD.add(marsNetworkMNC.getMarsRoverManifest(rover, appiKey).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<MarsManifest>() {
            @Override
            public void accept(MarsManifest marsManifest) throws Exception{
                roverPB.setVisibility(View.INVISIBLE);
                listLayoutLL.setVisibility(VISIBLE);

                //have the rover info printed out using setText
                RoverName.setText(getResources().getString(R.string.MARSrover_name) + marsManifest.getManifest().getName());
                launch_Date.setText(getResources().getString(R.string.MARSlaunch_date) + marsManifest.getManifest().getLaunch_date());
                landing_Date.setText(getResources().getString(R.string.MARSlanding_date) + marsManifest.getManifest().getLanding_date());
                max_sol.setText(getResources().getString(R.string.MARSmax_sol) + (marsManifest.getManifest().getMax_sol()));
                max_Date.setText(getResources().getString(R.string.MARSmax_earth_date) + marsManifest.getManifest().getMax_date());
                total_Photos.setText(getResources().getString(R.string.MARStotal_photos) + marsManifest.getManifest().getTotal_photos());
                Status.setText(getResources().getString(R.string.MARSstatus) + marsManifest.getManifest().getStatus());
            }
        }, new Consumer<Throwable>(){
            @Override
            public void accept(Throwable throwable) throws Exception {
                Toast.makeText(getContext(), throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }));
    }
}
