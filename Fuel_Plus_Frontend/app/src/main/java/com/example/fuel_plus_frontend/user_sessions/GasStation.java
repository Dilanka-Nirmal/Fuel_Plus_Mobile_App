package com.example.fuel_plus_frontend.user_sessions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.fuel_plus_frontend.R;
import com.example.fuel_plus_frontend.SignUp;


public class GasStation extends Fragment {

    private TextView fuelStatus, stationName, stationCode, petrolQueue, dieselQueue;
    private Button goInsideBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gas_station, container, false);

        fuelStatus = view.findViewById(R.id.fuelStatus);
        stationName = view.findViewById(R.id.stationName);
        stationCode = view.findViewById(R.id.stationCode);
        petrolQueue = view.findViewById(R.id.petrolQueue);
        dieselQueue = view.findViewById(R.id.dieselQueue);

        goInsideBtn = view.findViewById(R.id.goInsideBtn);

        return  view;
    }

}