package com.example.fuel_plus_frontend;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fuel_plus_frontend.api.RetrofitClient;
import com.example.fuel_plus_frontend.models.LoginResponse;
import com.example.fuel_plus_frontend.user_sessions.GasStation;
import com.example.fuel_plus_frontend.user_sessions.UserProfile;
import com.example.fuel_plus_frontend.user_sessions.UserSessionFragments;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignIn extends AppCompatActivity {

    private EditText vehicleNoEdt, passwordEdt;
    private Button signInBtn1, forgotPasswordBtn, signUpBtn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //Import edit text fields on SignIn screen
        vehicleNoEdt = (EditText) findViewById(R.id.vehicleNoEdt);
        passwordEdt = (EditText) findViewById(R.id.passwordEdt);

        //Import buttons on SignIn screen
        signInBtn1 = (Button) findViewById(R.id.signInBtn1);
        forgotPasswordBtn = (Button) findViewById(R.id.forgotPasswordBtn);
        signUpBtn2 = (Button) findViewById(R.id.signUpBtn2);


        //SignUp Button Activate
        signUpBtn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignIn.this, SignUp.class);
                startActivity(i);
            }
        });


        //Forgot Password Button Activate
        forgotPasswordBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignIn.this, ForgotPassword.class);
                startActivity(i);
            }
        });

        //Sign In Button Activate
        signInBtn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (!validVehicleNo() | !validPassword()) {
                    return;
                }

                //Assign all edit text field's values to variables
                String vehicleNo = vehicleNoEdt.getText().toString().trim();
                String password = passwordEdt.getText().toString().trim();

                //Call the SignIn API
                Call<LoginResponse> call = RetrofitClient
                        .getInstance()
                        .getAPI()
                        .signIn(vehicleNo, password);

                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(SignIn.this, "You're signed in, Successfully!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(SignIn.this, UserSessionFragments.class);
                            startActivity(i);
                        }else{
                            Toast.makeText(SignIn.this, "Your credentials are incorrect, Please try again!", Toast.LENGTH_SHORT).show();
                        }

                    }
                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(SignIn.this, "Something went wrong, Try again!", Toast.LENGTH_SHORT).show();

                    }
                });

//                //Pass vehicleNo value to the UserProfile fragment
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                Bundle bundle = new Bundle();
//                bundle.putString("message", vehicleNoEdt.getText().toString());
//                UserProfile userProfile = new UserProfile();
//                userProfile.setArguments(bundle);
//                fragmentTransaction.replace(R.id.vehicleNoEdt, userProfile).commit();
            }
        });

    }


    //Form Validations [signInBtn]---------------------------------------------------------------------------------------------
    boolean validVehicleNo() {
        String val = vehicleNoEdt.getText().toString();

        if (val.isEmpty()) {
            vehicleNoEdt.setError("This field cannot be Empty");
            return false;
        } else {
            vehicleNoEdt.setError(null);
            return true;
        }
    }

    boolean validPassword() {
        String val = passwordEdt.getText().toString();

        if (val.isEmpty()) {
            passwordEdt.setError("This field cannot be Empty");
            return false;
        } else {
            passwordEdt.setError(null);
            return true;
        }
    }
    //---------------------------------------------------------------------------------------------------------------------------------
}