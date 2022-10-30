package com.example.fuel_plus_frontend.user_sessions;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.fuel_plus_frontend.R;
import com.example.fuel_plus_frontend.databinding.ActivityUserSessionFragmentsBinding;

public class UserSessionFragments extends AppCompatActivity {

    ActivityUserSessionFragmentsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserSessionFragmentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new GasStationView());

        binding.userNavigation.setOnNavigationItemSelectedListener(item ->{
            switch (item.getItemId()){
                case R.id.gasStationTab:
                    replaceFragment(new GasStationView());
                    break;
                case R.id.userProfileTab:
                    replaceFragment(new UserProfile());
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.userContainer, fragment);
        fragmentTransaction.commit();
    }
}