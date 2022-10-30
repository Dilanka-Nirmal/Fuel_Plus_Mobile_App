package com.example.fuel_plus_frontend.api;

import com.example.fuel_plus_frontend.models.FuelStation;
import com.example.fuel_plus_frontend.models.LoginResponse;
import com.example.fuel_plus_frontend.models.User;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface API {

    //Sign Up API called
    @FormUrlEncoded
    @POST("api/user/add")
    Call<ResponseBody> signUp(
            @Field("firstName") String firstName,
            @Field("lastName") String lastName,
            @Field("phoneNo") String phoneNo,
            @Field("vehicleNo") String vehicleNo,
            @Field("fuelType") String fuelType,
            @Field("password") String password
    );

    //Sign In API called
    @FormUrlEncoded
    @POST("api/user/signIn")
    Call<LoginResponse> signIn(
            @Field("vehicleNo") String vehicleNo,
            @Field("password") String password
    );

    //Forgot password API called
    @FormUrlEncoded
    @POST("api/user/forgotPassword")
    Call<ResponseBody> forgotPassword(
            @Field("vehicleNo") String vehicleNo,
            @Field("password") String password
    );

    //User data API called
    @GET("api/user/{userId}")
    Call<User> getUserDetails(@Path("userId") String id);

    //Update user profile API called
    @FormUrlEncoded
    @PUT("api/user/edit/{userId}")
    Call<ResponseBody> updateUserProfile(
            @Path("userId") String id,
            @Field("firstName") String firstName,
            @Field("lastName") String lastName,
            @Field("phoneNo") String phoneNo
    );

    //Delete user profile API called
    @DELETE("api/user/delete/{userId}")
    Call<User> deleteUserProfile(@Path("userId") String id);

    //User's fuel history API called
    @GET("api/user/get/history/:id")
    Call<List<User>> getUserFuelHistory();

    //Fuel station list API called
    @GET("api/fuelStation/home")
    Call<List<FuelStation>> getFuelStations();

    //Specific fuel station API called
    @GET("api/fuelStation/{stationId}")
    Call<FuelStation> getFuelStationDetails(@Path("stationId") String id);

    //Petrol queue API called
    @FormUrlEncoded
    @PUT("api/fuelStation/edit/{stationId}")
    Call<ResponseBody> handlePetrolQueue(
            @Path("stationId") String id,
            @Field("petrolQueue") Integer petrolQueue
    );

    //Diesel queue API called
    @FormUrlEncoded
    @PUT("api/fuelStation/edit/{stationId}")
    Call<ResponseBody> handleDieselQueue(
            @Path("stationId") String id,
            @Field("dieselQueue") Integer dieselQueue
    );
}
