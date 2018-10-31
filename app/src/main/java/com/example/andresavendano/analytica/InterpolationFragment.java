package com.example.andresavendano.analytica;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.AppComponentFactory;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import static com.example.andresavendano.analytica.R.id.buttonIncrementales;

public class InterpolationFragment extends Fragment{



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_interpolation, container, false);
        Button buttonIncrementales=view.findViewById(R.id.buttonNewton);
        buttonIncrementales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Main4Activity.class);
                int i=0;
                intent.putExtra("cual",i);
                startActivity(intent);
            }
        });
        Button buttonBisection=view.findViewById(R.id.buttonLagrange);
        buttonBisection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Main4Activity.class);
                int i=1;
                intent.putExtra("cual",i);
                startActivity(intent);
            }
        });
        Button buttonPuntoFijo=view.findViewById(R.id.buttonLinearSpline);
        buttonPuntoFijo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Main4Activity.class);
                int i=2;
                intent.putExtra("cual",i);
                startActivity(intent);
            }
        });
        Button buttonRaicez=view.findViewById(R.id.buttonCuadraticSpline);
        buttonRaicez.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Main4Activity.class);
                int i=3;
                intent.putExtra("cual",i);
                startActivity(intent);
            }
        });
        Button buttonReglaFalsa=view.findViewById(R.id.buttonCubicSpline);
        buttonReglaFalsa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Main4Activity.class);
                int i=4;
                intent.putExtra("cual",i);
                startActivity(intent);
            }
        });

        return view;


    }

}
