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

import static com.example.andresavendano.analytica.R.id.buttonAitken;
import static com.example.andresavendano.analytica.R.id.buttonIncrementales;
import static com.example.andresavendano.analytica.R.id.buttonSteffensen;

public class OneVariableFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onevariable, container, false);
        Button buttonIncrementales=view.findViewById(R.id.buttonIncrementales);
        buttonIncrementales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Main2Activity.class);
                int i=0;
                intent.putExtra("cual",i);
                startActivity(intent);
            }
        });
        Button buttonBisection=view.findViewById(R.id.buttonBiseccion);
        buttonBisection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Main2Activity.class);
                int i=1;
                intent.putExtra("cual",i);
                startActivity(intent);
            }
        });
        Button buttonPuntoFijo=view.findViewById(R.id.buttonPuntoFijo);
        buttonPuntoFijo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Main2Activity.class);
                int i=3;
                intent.putExtra("cual",i);
                startActivity(intent);
            }
        });
        Button buttonRaicez=view.findViewById(R.id.buttonRaicez);
        buttonRaicez.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Main2Activity.class);
                int i=6;
                intent.putExtra("cual",i);
                startActivity(intent);
            }
        });
        Button buttonReglaFalsa=view.findViewById(R.id.buttonReglaFalsa);
        buttonReglaFalsa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Main2Activity.class);
                int i=2;
                intent.putExtra("cual",i);
                startActivity(intent);
            }
        });
        Button buttonSecante=view.findViewById(R.id.buttonSecante);
        buttonSecante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Main2Activity.class);
                int i=5;
                intent.putExtra("cual",i);
                startActivity(intent);
            }
        });
        Button buttonNewton=view.findViewById(R.id.buttonNewton);
        buttonNewton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Main2Activity.class);
                int i=4;
                intent.putExtra("cual",i);
                startActivity(intent);
            }
        });
        Button buttonAitken = view.findViewById(R.id.buttonAitken);
        buttonAitken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Main2Activity.class);
                int i=7;
                intent.putExtra("cual",i);
                startActivity(intent);
            }
        });
        Button buttonSteffensen = view.findViewById(R.id.buttonSteffensen);
        buttonSteffensen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Main2Activity.class);
                int i=8;
                intent.putExtra("cual",i);
                startActivity(intent);
            }
        });
        return view;
    }

}
