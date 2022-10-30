package com.example.fuel_plus_frontend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fuel_plus_frontend.api.RetrofitClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassword extends AppCompatActivity {

    private EditText vehicleNoEdt2, newPasswordEdt2;
    private Button continueBtn, signInBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        //Import edit text fields on ForgotPassword screen
        vehicleNoEdt2 = findViewById(R.id.vehicleNoEdt2);
        newPasswordEdt2 = findViewById(R.id.newPasswordEdt2);

        //Import buttons on ForgotPassword screen
        continueBtn = (Button) findViewById(R.id.continueBtn);
        signInBtn = (Button) findViewById(R.id.signInBtn);

        //SignIn Button Activate
        signInBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(ForgotPassword.this, SignIn.class);
                startActivity(i);
            }
        });

        continueBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(!validVehicleNo() | !validNewPassword()){
                    return;
                }

                //Assign all edit text field's values to variables
                String vehicleNo = vehicleNoEdt2.getText().toString().trim();
                String password = newPasswordEdt2.getText().toString().trim();

                Call<ResponseBody> call = RetrofitClient
                        .getInstance()
                        .getAPI()
                        .forgotPassword(vehicleNo, password);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.body() == null){
                            Toast.makeText(ForgotPassword.this, "Please try again.", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ForgotPassword.this, "Password is changed, Successfully! ", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(ForgotPassword.this, SignIn.class);
                            startActivity(i);
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(ForgotPassword.this, "Something went wrong, Please try again!", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }
    //Form Validations------------------------------------------------------------------------------------------------------------------------
    private boolean validVehicleNo(){
        String val = vehicleNoEdt2.getText().toString();

        if(val.isEmpty()){
            vehicleNoEdt2.setError("This field cannot be Empty");
            return false;
        }else{
            vehicleNoEdt2.setError(null);
            return true;
        }
    }

    private boolean validNewPassword(){
        String val = newPasswordEdt2.getText().toString();

        if(val.isEmpty()){
            newPasswordEdt2.setError("This field cannot be Empty");
            return false;
        }else{
            newPasswordEdt2.setError(null);
            return true;
        }
    }
    //---------------------------------------------------------------------------------------------------------------------------------
}