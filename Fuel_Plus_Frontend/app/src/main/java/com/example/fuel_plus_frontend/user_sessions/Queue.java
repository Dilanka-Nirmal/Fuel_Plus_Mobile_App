package com.example.fuel_plus_frontend.user_sessions;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fuel_plus_frontend.R;
import com.example.fuel_plus_frontend.api.RetrofitClient;
import com.example.fuel_plus_frontend.models.FuelStation;
import com.example.fuel_plus_frontend.models.User;

import java.text.DateFormat;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Queue extends Fragment {

    private TextView fuelStatus, stationName, stationCode, petrolQueue, dieselQueue;
    private Button petrolQueueJoinBtn, dieselQueueJoinBtn, outBtn, exitBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_queue, container, false);

        fuelStatus = view.findViewById(R.id.fuelStatus);
        stationName = view.findViewById(R.id.stationName);
        stationCode = view.findViewById(R.id.stationCode);
        petrolQueue = view.findViewById(R.id.petrolQueue);
        dieselQueue = view.findViewById(R.id.dieselQueue);

        petrolQueueJoinBtn = view.findViewById(R.id.petrolQueueJoinBtn);
        dieselQueueJoinBtn = view.findViewById(R.id.dieselQueueJoinBtn);
        outBtn = view.findViewById(R.id.outBtn);
        exitBtn = view.findViewById(R.id.exitBtn);


        final String[] vehicleFuelType = new String[1];

        //Retrieve fuelType of the user
        Call<User> fuelType = RetrofitClient
                .getInstance()
                .getAPI()
                .getUserDetails("63583ca305db39c20dd23f5a");

        fuelType.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    vehicleFuelType[0] = response.body().getFuelType();
                } else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(), "Fuel type data not loaded, Try again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getActivity(), "Something went wrong, Try again!", Toast.LENGTH_SHORT).show();
            }
        });

        //Retrieve fuel station data
        Call<FuelStation> station = RetrofitClient
                .getInstance()
                .getAPI()
                .getFuelStationDetails("63596a02dbbf8c2d2bbce09f");

        station.enqueue(new Callback<FuelStation>() {
            @Override
            public void onResponse(Call<FuelStation> call, Response<FuelStation> response) {
                if (response.isSuccessful()) {
                    fuelStatus.setText(response.body().getFuelStatus());
                    stationName.setText(response.body().getStationName());
                    stationCode.setText(response.body().getStationCode());
                    petrolQueue.setText(Integer.toString(response.body().getPetrolQueue()));
                    dieselQueue.setText(Integer.toString(response.body().getDieselQueue()));
                } else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(), "Fuel station data not loaded, Try again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FuelStation> call, Throwable t) {
                Toast.makeText(getActivity(), "Something went wrong, Try again!", Toast.LENGTH_SHORT).show();
            }
        });

        //petrolQueueJoin button Implementation-------------------------------------------------------------------------------------------
        petrolQueueJoinBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (vehicleFuelType[0].equalsIgnoreCase("Petrol")) {
                    //Assign all edit text field's values to variables
                    Integer petrolQueueCount = Integer.parseInt(petrolQueue.getText().toString());
                    petrolQueueCount++;

                    Call<ResponseBody> call = RetrofitClient
                            .getInstance()
                            .getAPI()
                            .handlePetrolQueue("63596a02dbbf8c2d2bbce09f", petrolQueueCount);

                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.body() == null) {
                                Toast.makeText(getActivity(), "Please try again.", Toast.LENGTH_SHORT).show();
                            } else {
                                //Refresh the current fragment
                                Fragment frag = new Queue();
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.fragment_queue, frag).commit();
                                //Display a toast message
                                Toast.makeText(getActivity(), "You have been added to the petrol queue, Successfully!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getActivity(), "Something went wrong, Try again!", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Toast.makeText(getActivity(), "You vehicle cannot enter to the petrol queue", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //dieselQueueJoin button Implementation-------------------------------------------------------------------------------------------
        dieselQueueJoinBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (vehicleFuelType[0].equalsIgnoreCase("Diesel")) {
                    //Assign all edit text field's values to variables
                    Integer dieselQueueCount = Integer.parseInt(dieselQueue.getText().toString());
                    dieselQueueCount++;
                    Log.d(TAG, "onClick: " + dieselQueueCount);
                    Call<ResponseBody> call = RetrofitClient
                            .getInstance()
                            .getAPI()
                            .handleDieselQueue("63596a02dbbf8c2d2bbce09f", dieselQueueCount);

                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.body() == null) {
                                Toast.makeText(getActivity(), "Please try again.", Toast.LENGTH_SHORT).show();
                            } else {
                                //Refresh the current fragment
                                Fragment frag = new Queue();
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.fragment_queue, frag).commit();
                                //Display a toast message
                                Toast.makeText(getActivity(), vehicleFuelType[0], Toast.LENGTH_SHORT).show();
                                Toast.makeText(getActivity(), "You have been added to the diesel queue, Successfully!", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getActivity(), "Something went wrong, Try again!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "You vehicle cannot enter to the diesel queue", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //OutBtn implementation-------------------------------------------------------------------------------------------
        outBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (vehicleFuelType[0].equalsIgnoreCase("Petrol")) {
                    //Assign all edit text field's values to variables
                    Integer petrolQueueCount = Integer.parseInt(petrolQueue.getText().toString());
                    if(petrolQueueCount != 0){
                        petrolQueueCount--;
                    }
                    Call<ResponseBody> call = RetrofitClient
                            .getInstance()
                            .getAPI()
                            .handlePetrolQueue("63596a02dbbf8c2d2bbce09f", petrolQueueCount);

                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.body() == null) {
                                Toast.makeText(getActivity(), "Please try again.", Toast.LENGTH_SHORT).show();
                            } else {
                                //Refresh the current fragment
                                Fragment frag = new Queue();
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.fragment_queue, frag).commit();
                                //Display a toast message
                                Toast.makeText(getActivity(), "You left the queue, Successfully!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getActivity(), "Something went wrong, Try again!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else if(vehicleFuelType[0].equalsIgnoreCase("Diesel")){
                    //Assign all edit text field's values to variables
                    Integer dieselQueueCount = Integer.parseInt(dieselQueue.getText().toString());
                    if(dieselQueueCount != 0){
                        dieselQueueCount--;
                    }
                    Log.d(TAG, "onClick: " + dieselQueueCount);
                    Call<ResponseBody> call = RetrofitClient
                            .getInstance()
                            .getAPI()
                            .handleDieselQueue("63596a02dbbf8c2d2bbce09f", dieselQueueCount);

                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.body() == null) {
                                Toast.makeText(getActivity(), "Please try again.", Toast.LENGTH_SHORT).show();
                            } else {
                                //Refresh the current fragment
                                Fragment frag = new Queue();
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.fragment_queue, frag).commit();
                                //Display a toast message
                                Toast.makeText(getActivity(), "You left the queue, Successfully!", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getActivity(), "Something went wrong, Try again!", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else {
                    Toast.makeText(getActivity(), "You cannot leave the queue", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //ExitBtn implementation-------------------------------------------------------------------------------------------
        exitBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (vehicleFuelType[0].equalsIgnoreCase("Petrol")) {
                    //Assign all edit text field's values to variables
                    Integer petrolQueueCount = Integer.parseInt(petrolQueue.getText().toString());
                    if(petrolQueueCount != 0){
                    petrolQueueCount--;
                    }
                    Call<ResponseBody> call = RetrofitClient
                            .getInstance()
                            .getAPI()
                            .handlePetrolQueue("63596a02dbbf8c2d2bbce09f", petrolQueueCount);

                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.body() == null) {
                                Toast.makeText(getActivity(), "Please try again.", Toast.LENGTH_SHORT).show();
                            } else {
                                //Refresh the current fragment
                                Fragment frag = new Queue();
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.fragment_queue, frag).commit();
                                //Display a toast message
                                Toast.makeText(getActivity(), "You left the queue, Successfully!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getActivity(), "Something went wrong, Try again!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else if(vehicleFuelType[0].equalsIgnoreCase("Diesel")){
                    //Assign all edit text field's values to variables
                    Integer dieselQueueCount = Integer.parseInt(dieselQueue.getText().toString());
                    if(dieselQueueCount != 0){
                        dieselQueueCount--;
                    }
                    Log.d(TAG, "onClick: " + dieselQueueCount);
                    Call<ResponseBody> call = RetrofitClient
                            .getInstance()
                            .getAPI()
                            .handleDieselQueue("63596a02dbbf8c2d2bbce09f", dieselQueueCount);

                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.body() == null) {
                                Toast.makeText(getActivity(), "Please try again.", Toast.LENGTH_SHORT).show();
                            } else {
                                //Refresh the current fragment
                                Fragment frag = new Queue();
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.fragment_queue, frag).commit();
                                //Display a toast message
                                Toast.makeText(getActivity(), vehicleFuelType[0], Toast.LENGTH_SHORT).show();
                                Toast.makeText(getActivity(), "You left the queue, Successfully!", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getActivity(), "Something went wrong, Try again!", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else {
                    Toast.makeText(getActivity(), "You cannot leave the queue", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

}