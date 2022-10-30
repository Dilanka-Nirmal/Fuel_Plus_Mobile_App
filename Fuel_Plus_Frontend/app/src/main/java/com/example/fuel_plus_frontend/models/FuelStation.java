package com.example.fuel_plus_frontend.models;

public class FuelStation {
    private String id, stationName, stationCode, fuelStatus, fuelArrivalTime, fuelFinishTime;
    private Integer petrolQueue, dieselQueue;

    public FuelStation(String id, String stationName, String stationCode, String fuelStatus, String fuelArrivalTime, String fuelFinishTime, Integer petrolQueue, Integer dieselQueue) {
        this.id = id;
        this.stationName = stationName;
        this.stationCode = stationCode;
        this.fuelStatus = fuelStatus;
        this.fuelArrivalTime = fuelArrivalTime;
        this.fuelFinishTime = fuelFinishTime;
        this.petrolQueue = petrolQueue;
        this.dieselQueue = dieselQueue;
    }

    public String getId() {
        return id;
    }

    public String getStationName() {
        return stationName;
    }

    public String getStationCode() {
        return stationCode;
    }

    public String getFuelStatus() {
        return fuelStatus;
    }

    public String getFuelArrivalTime() {
        return fuelArrivalTime;
    }

    public String getFuelFinishTime() {
        return fuelFinishTime;
    }

    public Integer getPetrolQueue() {
        return petrolQueue;
    }

    public Integer getDieselQueue() {
        return dieselQueue;
    }
}
