package com.example.fuel_plus_frontend.user_sessions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fuel_plus_frontend.R;
import com.example.fuel_plus_frontend.SignIn;
import com.example.fuel_plus_frontend.SignUp;
import com.example.fuel_plus_frontend.api.RetrofitClient;
import com.example.fuel_plus_frontend.models.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserProfile extends Fragment {

    EditText firstNameEdt, lastNameEdt, phoneNumberEdt;
    TextView vehicleNoView, fuelTypeView;
    Button updateBtn, deleteBtn, logoutBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        firstNameEdt = view.findViewById(R.id.firstNameEdt);
        lastNameEdt = view.findViewById(R.id.lastNameEdt);
        phoneNumberEdt = view.findViewById(R.id.phoneNumberEdt);

        vehicleNoView = view.findViewById(R.id.vehicleNoView);
        fuelTypeView = view.findViewById(R.id.fuelTypeView);

        updateBtn = view.findViewById(R.id.updateBtn);
        deleteBtn = view.findViewById(R.id.deleteBtn);
        logoutBtn = view.findViewById(R.id.logoutBtn);

        //Retrieve user data
        Call<User> call = RetrofitClient
                .getInstance()
                .getAPI()
                .getUserDetails("63583ca305db39c20dd23f5a");

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    firstNameEdt.setText(response.body().getFirstName());
                    lastNameEdt.setText(response.body().getLastName());
                    phoneNumberEdt.setText(response.body().getPhoneNo());
                    vehicleNoView.setText(response.body().getVehicleNo());
                    fuelTypeView.setText(response.body().getFuelType());
                }else{
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(), "User data not loaded, Try again!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getActivity(), "Something went wrong, Try again!", Toast.LENGTH_SHORT).show();

            }
        });

        //Update button Implementation-------------------------------------------------------------------------------------------
        updateBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (!validFirstName() | !validLastName() | !validPhoneNo() ) {
                    return;
                }

                //Assign all edit text field's values to variables
                String firstName = firstNameEdt.getText().toString().trim();
                String lastName = lastNameEdt.getText().toString().trim();
                String phoneNo = phoneNumberEdt.getText().toString().trim();

                Call<ResponseBody> call = RetrofitClient
                        .getInstance()
                        .getAPI()
                        .updateUserProfile("63583ca305db39c20dd23f5a", firstName, lastName, phoneNo);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.body() == null){
                            Toast.makeText(getActivity(), "Please try again.", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getActivity(), "User account updated, Successfully!", Toast.LENGTH_SHORT).show();

                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getActivity(), "Something went wrong, Try again!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        //Delete button Implementation-------------------------------------------------------------------------------------------
        deleteBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Call<User> call = RetrofitClient
                        .getInstance()
                        .getAPI()
                        .deleteUserProfile("63583ca305db39c20dd23f5a");

                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.body() == null){
                            Toast.makeText(getActivity(), "Please try again.", Toast.LENGTH_SHORT).show();
                        }else{
                            Intent i = new Intent(getActivity(), SignUp.class);
                            startActivity(i);
                            Toast.makeText(getActivity(), "User account deleted, Successfully!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getActivity(), "User account deleted, Successfully!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getActivity(), SignUp.class);
                        startActivity(i);
                    }
                });
            }
        });

        //LogoutBtn implementation
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Logout Successfully!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(), SignIn.class);
                startActivity(i);
            }
        });

        return view;
    }

    //Form Validations-----------------------------------------------------------------------------------------------------------
    private boolean validFirstName() {
        String val = firstNameEdt.getText().toString();

        if (val.isEmpty()) {
            firstNameEdt.setError("This field cannot be Empty");
            return false;
        } else {
            firstNameEdt.setError(null);
            return true;
        }
    }

    private boolean validLastName() {
        String val = lastNameEdt.getText().toString();

        if (val.isEmpty()) {
            lastNameEdt.setError("This field cannot be Empty");
            return false;
        } else {
            lastNameEdt.setError(null);
            return true;
        }
    }

    private boolean validPhoneNo() {
        String val = phoneNumberEdt.getText().toString();

        if (val.isEmpty()) {
            phoneNumberEdt.setError("This field cannot be Empty");
            return false;
        } else if(val.length() != 9){
            phoneNumberEdt.setError("Invalid phone number");
            return false;
        }else {
            phoneNumberEdt.setError(null);
            return true;
        }
    }
    //----------------------------------------------------------------------------------------------
}