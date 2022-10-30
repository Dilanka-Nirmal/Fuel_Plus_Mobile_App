package com.example.fuel_plus_frontend.models;

import java.util.ArrayList;

public class User {
    private String id, firstName, lastName, vehicleNo, phoneNo, fuelType, password;
    private boolean userType;

    public User(String id, String firstName, String lastName, String vehicleNo, String fuelType, String password, String phoneNo, boolean userType) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.vehicleNo = vehicleNo;
        this.fuelType = fuelType;
        this.password = password;
        this.phoneNo = phoneNo;
        this.userType = userType;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public String getFuelType() {
        return fuelType;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public boolean isUserType() {
        return userType;
    }

}
