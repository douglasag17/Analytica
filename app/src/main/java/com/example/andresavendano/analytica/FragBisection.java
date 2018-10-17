package com.example.andresavendano.analytica;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.udojava.evalex.Expression;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Scanner;
import java.util.ArrayList;

public class FragBisection extends Fragment {

    private EditText function;
    private EditText xinf;
    private EditText xupp;
    private EditText iterations;
    private Double tolerance;
    private Button calculate;
    private String errorType;

    Spinner tol;
    ArrayAdapter<String> adapter;
    ArrayList<String> values;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.frag_bisection, container, false);

        final View newTol = inflater.inflate(R.layout.new_tolerance,  (ViewGroup) getView(), false);

        /**
         * This code is for the tolerance
         */
        //Determinated Tolerance
        values = new ArrayList<String>();
        values.add("0.5e-3"); values.add("1e-3"); values.add("0.5e-4"); values.add("1e-4"); values.add("0.5e-5");
        values.add("1e-5"); values.add("0.5e-6"); values.add("1e-6"); values.add("0.5e-7"); values.add("1e-7");
        tol = v.findViewById(R.id.tolerance);
        adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        tol.setAdapter(adapter);
        //Add new tolerance
        final Button moreTol = v.findViewById(R.id.moreTolerance);
        moreTol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Type the new tolerance");
                final EditText input =  newTol.findViewById(R.id.input);
                builder.setView(newTol);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        String t = input.getText().toString();
                        if(! values.contains(t)) {
                            values.add(t);
                            int spinnerPosition = adapter.getPosition(t);
                            tol.setSelection(spinnerPosition);
                        }
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });

        /**
         * Error Type
         */


        /**
         * Calculate Bisection method
         */
        //Write fields declaration
        function = v.findViewById(R.id.function);
        xinf = v.findViewById(R.id.xi);
        xupp = v.findViewById(R.id.xu);
        iterations = v.findViewById(R.id.niter);
        calculate = v.findViewById(R.id.calculate);
        //When calculate button is clicked
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fx = function.getText().toString().trim();
                Double xi = Double.parseDouble(xinf.getText().toString());
                Double xu = Double.parseDouble(xupp.getText().toString());
                int niter = Integer.parseInt(iterations.getText().toString());
                tolerance = Double.parseDouble(values.get(tol.getSelectedItemPosition()));
                System.out.println(fx);
                System.out.println(xi);
                System.out.println(xu);
                System.out.println(niter);
                System.out.println(tolerance);
                System.out.println(errorType);
                bisection(xi, xu, tolerance, niter, fx);

            }
        });
        return v;
    }

    public static void bisection(double xi, double xs, double tolerance, int niter, String functionRead) {
        //System.out.print("Type the function to be evaluated: ");
        //Scanner function = new Scanner(System.in);
        //String functionRead = function.next();
        //PrettyTable table = new PrettyTable("i","Inferior","Superior","Medio","f(medio)","Absolute Error");
        Expression fx = new Expression(functionRead).setPrecision(16);
        BigDecimal fxid = fx.with("x", Double.toString(xi)).eval();
        double fxi = fxid.doubleValue();
        BigDecimal fxsd = fx.with("x", Double.toString(xs)).eval();
        double fxs = fxsd.doubleValue();
        if (fxi == 0) {
            System.out.println(xi + " is a root");
        } else if (fxs == 0) {
            System.out.println(xs + " is a root");
        } else if (fxi * fxs < 0) {
            double xm = (xi + xs)/2;
            BigDecimal fxmd = fx.with("x", Double.toString(xm)).eval();
            double fxm = fxmd.doubleValue();
            int count = 1;
            double error = tolerance + 1;
            //table.addRow(Double.toString(count), Double.toString(xi), Double.toString(xs), Double.toString(xm), Double.toString(fxm), "-");
            while (error > tolerance && count < niter && fxm != 0) {
                if (fxi * fxm < 0) {
                    xs = xm;
                    fxs = fxm;
                } else {
                    xi = xm;
                    fxi = fxm;
                }
                double xaux = xm;
                xm = (xi + xs)/2;
                fxmd = fx.with("x", Double.toString(xm)).eval();
                fxm = fxmd.doubleValue();
                error = Math.abs(xm - xaux);
                count++;
                //table.addRow(Double.toString(count), Double.toString(xi), Double.toString(xs), Double.toString(xm), Double.toString(fxm), Double.toString(error));
            }
            if (fxm == 0) {
                System.out.println(xm + " is root");
            } else if (error < tolerance) {
                System.out.println(count);
                System.out.println(xm + " is an aproximation of a root with a tolerance = " + tolerance);
            } else {
                System.out.println("failed at " + niter + " iterations");
            }
        } else {
            System.out.println("the interval is unsuitable");
        }
        /*System.out.println(table);
        System.out.print("Type the name of the file to be generated: ");
        Scanner fileName = new Scanner(System.in);
        String fileNameRead = fileName.next();
        FileWriter file = null;
        PrintWriter pw = null;
        try {
            file = new FileWriter(fileNameRead +".txt");
            //pw = new PrintWriter(file);
            //pw.println(table);
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

}