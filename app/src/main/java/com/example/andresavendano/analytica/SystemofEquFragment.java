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

public class SystemofEquFragment extends Fragment{



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_systemofequ, container, false);
        Button buttonSimpleGaussianElimination=view.findViewById(R.id.buttonSimpleGaussian);
        buttonSimpleGaussianElimination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Main3Activity.class);
                int i=0;
                intent.putExtra("cual",i);
                startActivity(intent);
            }
        });
        Button buttonSimpleGaussianPartialPivoting=view.findViewById(R.id.buttonSimpleGaussianPartialPivoting);
        buttonSimpleGaussianPartialPivoting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Main3Activity.class);
                int i=1;
                intent.putExtra("cual",i);
                startActivity(intent);
            }
        });
        Button buttonSimpleGaussianTotalPivoting=view.findViewById(R.id.buttonSimpleGaussianTotalPivoting);
        buttonSimpleGaussianTotalPivoting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Main3Activity.class);
                int i=2;
                intent.putExtra("cual",i);
                startActivity(intent);
            }
        });
        Button buttonLUPartialPivoting=view.findViewById(R.id.buttonLUPartialPivoting);
        buttonLUPartialPivoting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Main3Activity.class);
                int i=4;
                intent.putExtra("cual",i);
                startActivity(intent);
            }
        });
        Button buttonLUGaussianElimination=view.findViewById(R.id.buttonLUGaussianElimination);
        buttonLUGaussianElimination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Main3Activity.class);
                int i=3;
                intent.putExtra("cual",i);
                startActivity(intent);
            }
        });
        Button buttonDirectFactorization=view.findViewById(R.id.buttonDirectFactorization);
        buttonDirectFactorization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Main3Activity.class);
                int i=5;
                intent.putExtra("cual",i);
                startActivity(intent);
            }
        });
        Button buttonJacobi=view.findViewById(R.id.buttonJacobi);
        buttonJacobi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Main3Activity.class);
                int i=6;
                intent.putExtra("cual",i);
                startActivity(intent);
            }
        });
        Button buttonGauss_Seidel=view.findViewById(R.id.buttonGauss_Seidel);
        buttonGauss_Seidel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Main3Activity.class);
                int i=7;
                intent.putExtra("cual",i);
                startActivity(intent);
            }
        });

        Button buttonSORGauss_Seidel=view.findViewById(R.id.buttonSORGauss_Seidel);
        buttonSORGauss_Seidel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Main3Activity.class);
                int i=8;
                intent.putExtra("cual",i);
                startActivity(intent);
            }
        });
        return view;


    }

}
