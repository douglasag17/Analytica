package com.example.andresavendano.analytica;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class FragBisection extends Fragment {

    private EditText function;
    private EditText xinf;
    private EditText xupp;
    private EditText iterations;
    private Button calculate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.frag_bisection, container, false);

        //Spinner
        String [] values =  {"0.5e-3","1e-3","0.5e-4","1e-4", "0.5e-5","1e-5", "0.5e-6","1e-6", "0.5e-7","1e-7"};
        Spinner tol = v.findViewById(R.id.tolerance);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        tol.setAdapter(adapter);
        int iSelection = tol.getSelectedItemPosition();
        double tolerance;
        tolerance = Double.parseDouble(values[iSelection]);
        System.out.println(tolerance);

        //Write fields declaration
        function = v.findViewById(R.id.function);
        xinf = v.findViewById(R.id.xi);
        xupp = v.findViewById(R.id.xu);
        iterations = v.findViewById(R.id.niter);
        calculate = v.findViewById(R.id.calculate);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fx = function.getText().toString().trim();
                Double xi = Double.parseDouble(xinf.getText().toString());
                System.out.println(fx);
                System.out.println(xi);
            }
        });



        //Read values of write fields
        /*String fx = function.getText().toString();
        System.out.println("FUNCTION ----------------" + fx);
        Double xi = Double.parseDouble(xinf.getText().toString());
        System.out.println("XI ----------------" + xi);
        String xu = xupp.getText().toString();
        System.out.println("XU ----------------" + xu);
        String niter = iterations.getText().toString();
        System.out.println("NITER ----------------" + niter);*/

        return v;
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }


}