package com.example.fuel_plus_frontend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.fuel_plus_frontend.api.RetrofitClient;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {

    private EditText firstNameEdt, lastNameEdt, phoneNumberEdt, vehicleNoEdt, newPasswordEdt;
    private RadioGroup fuelType;
    private RadioButton petrolEdt, dieselEdt, radioBtn;
    private Button signUpBtn1, signInBtn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Import edit text fields on SignUp screen
        firstNameEdt = (EditText) findViewById(R.id.firstNameEdt);
        lastNameEdt = (EditText) findViewById(R.id.lastNameEdt);
        phoneNumberEdt = (EditText) findViewById(R.id.phoneNumberEdt);
        vehicleNoEdt = (EditText) findViewById(R.id.vehicleNoEdt);
        newPasswordEdt = (EditText) findViewById(R.id.newPasswordEdt);

        //Import radio & radio group buttons on SignUp screen
        petrolEdt = (RadioButton) findViewById(R.id.petrolEdt);
        dieselEdt = (RadioButton) findViewById(R.id.dieselEdt);
        fuelType = (RadioGroup) findViewById(R.id.fuelTypeGroup);

        //Import buttons on SignUp screen
        signUpBtn1 = (Button) findViewById(R.id.signUpBtn1);
        signInBtn2 = (Button) findViewById(R.id.signInBtn2);

        //SignIn Button Activate
        signInBtn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUp.this, SignIn.class);
                startActivity(i);
            }
        });

        // Sign Up Implementation-------------------------------------------------------------------------------------------
        signUpBtn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (!validFirstName() | !validLastName() | !validPhoneNo() | !validVehicleNo() | !validPassword() ) {
                    return;
                }

                //Assign radio button value to variable
                int selectedId = fuelType.getCheckedRadioButtonId();
                radioBtn = (RadioButton)findViewById(selectedId);

                //Assign all edit text field's values to variables
                String firstName = firstNameEdt.getText().toString().trim();
                String lastName = lastNameEdt.getText().toString().trim();
                String phoneNo = phoneNumberEdt.getText().toString().trim();
                String vehicleNo = vehicleNoEdt.getText().toString().trim();
                String fuelType = radioBtn.getText().toString().trim();
                String password = newPasswordEdt.getText().toString().trim();

                Call<ResponseBody> call = RetrofitClient
                        .getInstance()
                        .getAPI()
                        .signUp(firstName, lastName, phoneNo, vehicleNo, fuelType, password);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.body() == null){
                            Toast.makeText(SignUp.this, "User account already existed.", Toast.LENGTH_SHORT).show();
                        }else{
                        Toast.makeText(SignUp.this, "You're registered, Successfully!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(SignUp.this, SignIn.class);
                        startActivity(i);
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(SignUp.this, "Your registration is failed, Try again!", Toast.LENGTH_SHORT).show();

                    }
                });

            }

        });
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

    private boolean validVehicleNo() {
        String val = vehicleNoEdt.getText().toString();

        if (val.isEmpty()) {
            vehicleNoEdt.setError("This field cannot be Empty");
            return false;
        } else {
            vehicleNoEdt.setError(null);
            return true;
        }
    }

    private boolean validPassword() {
        String val = newPasswordEdt.getText().toString();

        if (val.isEmpty()) {
            newPasswordEdt.setError("This field cannot be Empty");
            return false;
        } else {
            newPasswordEdt.setError(null);
            return true;
        }
    }
    //------------------------------------------------------------------------------------------------------------------
}