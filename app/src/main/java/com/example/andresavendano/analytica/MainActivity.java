package com.example.andresavendano.analytica;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation =  findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        navigation.getMenu().getItem(2).setChecked(true);
        loadfragment(new HomeFragment());
    }

    private boolean loadfragment(Fragment fragment){
        if(fragment != null){

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,fragment)
                    .commit();

            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment= null;

        switch(menuItem.getItemId()){
            case R.id.navigation_home:
                fragment=new HomeFragment();
                break;
            case R.id.navigation_Graph:
                fragment=new GraphFragment();
                break;
            case R.id.navigation_Interpolation:
                fragment=new InterpolationFragment();
                break;
            case R.id.navigation_SystemofEquations:
                fragment=new SystemofEquFragment();
                break;
            case R.id.navigation_OneVariable:
                fragment=new OneVariableFragment();
                break;
        }

        return loadfragment(fragment);

    }
}
