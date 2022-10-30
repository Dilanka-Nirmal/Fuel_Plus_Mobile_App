package com.example.fuel_plus_frontend;

import static java.lang.Thread.sleep;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread thread = new Thread((Runnable)()->{
            try{
                sleep(2000);
                Intent i = new Intent(SplashScreen.this, SignIn.class);
                startActivity(i);
                finish();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        });
        thread.start();
    }
}