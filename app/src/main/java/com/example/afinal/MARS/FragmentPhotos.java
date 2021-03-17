package com.example.afinal.MARS;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.afinal.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class FragmentPhotos extends Fragment implements View.OnClickListener{
    private CompositeDisposable disposableCD;
    private FloatingActionButton roverFAB;
    private int sol;
    private List<MarsPhotos> marsPhoto;
    private MarsNetworkCall marsCallMNC;
    private ProgressBar loadingPM;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapterRVA;
    private String marsRover;
    private String appiKey = "dQF0HhzI3vZCwc9MmGIc4eZ2SeaOluSi4Nmfm2ht";

    public FragmentPhotos(int solDay, String rover){
        Retrofit retrofit = RetrofitClient.getInstance();
        marsCallMNC = retrofit.create(MarsNetworkCall.class);
        this.marsRover = rover;
        this.sol = solDay;
        disposableCD = new CompositeDisposable();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View inflateView;
        inflateView = inflater.inflate(R.layout.fragment_photos, container, false);

        recyclerView = inflateView.findViewById(R.id.roverPhotos);
        roverFAB = inflateView.findViewById(R.id.roverSetting);
        loadingPM = inflateView.findViewById(R.id.progressBar);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadingPM.setVisibility(VISIBLE);
        adapterRVA = new RecyclerViewAdapter(getContext());
        marsPhoto = new ArrayList<>();
        roverFAB.setOnClickListener(this);

        getData();
        return inflateView;
    }

    private void getData(){
        disposableCD.add(marsCallMNC.getAllMarsPhotosBySol(marsRover, sol, 1, appiKey).subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread()).subscribe(marsPhotos ->{
                    loadingPM.setVisibility(GONE);

                    recyclerView.setVisibility(VISIBLE);
                    roverFAB.setVisibility(VISIBLE);

                    adapterRVA.setUpMarsList(marsPhotos.getPhotos());
                    recyclerView.setAdapter(adapterRVA);
                }, throwable ->{
                    Toast.makeText(getContext(), throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                })
        );
    }

    //if user presses on button to view rover info show everything for the selected rover
    @Override
    public void onClick(View v) {
        BottomSheet bottomSheet = new BottomSheet(marsRover);
        String TAG = bottomSheet.getTag();
        bottomSheet.show(getFragmentManager(), TAG);
    }
}