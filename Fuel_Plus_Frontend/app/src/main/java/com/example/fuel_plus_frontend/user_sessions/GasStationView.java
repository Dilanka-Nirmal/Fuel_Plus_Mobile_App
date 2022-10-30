package com.example.fuel_plus_frontend.user_sessions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.fuel_plus_frontend.R;
import com.example.fuel_plus_frontend.api.RetrofitClient;
import com.example.fuel_plus_frontend.models.FuelStation;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GasStationView extends Fragment {

    RecyclerView recyclerView;
    ProgressBar progressBar;
    LinearLayoutManager layoutManager;
    GasStationAdapter adapter;
    List<FuelStation> fuelStationList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gas_station_view, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new GasStationAdapter(fuelStationList);
        recyclerView.setAdapter(adapter);

        //Retrieve all fuel stations data
        fetchFuelStationsData();

        return view;
    }

    //Retrieve all fuel stations data
    public void fetchFuelStationsData(){

        Call<List<FuelStation>> call = RetrofitClient
                .getInstance()
                .getAPI()
                .getFuelStations();

        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<List<FuelStation>>() {
            @Override
            public void onResponse(Call<List<FuelStation>> call, Response<List<FuelStation>> response) {
                if(response.isSuccessful() && response.body() != null){
                    fuelStationList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);

                }else{
                    Toast.makeText(getActivity(), "Fuel stations data not loaded, Try again!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<FuelStation>> call, Throwable t) {
                Toast.makeText(getActivity(), "Something went wrong, Try again!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}