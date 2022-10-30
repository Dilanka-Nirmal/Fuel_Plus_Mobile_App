package com.example.fuel_plus_frontend.user_sessions;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fuel_plus_frontend.R;
import com.example.fuel_plus_frontend.SignUp;
import com.example.fuel_plus_frontend.models.FuelStation;

import java.util.List;

public class GasStationAdapter extends RecyclerView.Adapter<GasStationAdapter.ViewHolder> {

    private List<FuelStation> fuelStationList;

    public GasStationAdapter(List<FuelStation> fuelStationList) {
        this.fuelStationList = fuelStationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_gas_station, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") int i) {

        viewHolder.fuelStatus.setText(fuelStationList.get(i).getFuelStatus());
        viewHolder.stationName.setText(fuelStationList.get(i).getStationName());
        viewHolder.stationCode.setText(fuelStationList.get(i).getStationCode());
        viewHolder.petrolQueue.setText(Integer.toString(fuelStationList.get(i).getPetrolQueue()));
        viewHolder.dieselQueue.setText(Integer.toString(fuelStationList.get(i).getDieselQueue()));

        viewHolder.goInsideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Queue frag = new Queue();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.gasStationView,frag).addToBackStack(null).commit();
                Toast.makeText(view.getContext(), fuelStationList.get(i).getStationName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return fuelStationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView fuelStatus, stationName, stationCode, petrolQueue, dieselQueue;
        private Button goInsideBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fuelStatus = itemView.findViewById(R.id.fuelStatus);
            stationName = itemView.findViewById(R.id.stationName);
            stationCode = itemView.findViewById(R.id.stationCode);
            petrolQueue = itemView.findViewById(R.id.petrolQueue);
            dieselQueue = itemView.findViewById(R.id.dieselQueue);

            goInsideBtn = itemView.findViewById(R.id.goInsideBtn);

        }
    }
}
