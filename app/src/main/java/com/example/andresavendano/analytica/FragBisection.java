package com.example.andresavendano.analytica;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.udojava.evalex.Expression;
import java.math.BigDecimal;
import java.util.ArrayList;

public class FragBisection extends Fragment {

    private EditText function;
    private EditText xinf;
    private EditText xupp;
    private EditText iterations;
    private TextView tittleIterations;
    private TextView titleXi;
    private TextView titleXu;
    private TextView titleXm;
    private TextView titleEval;
    private TextView titleError;
    private Double tolerance;
    private int errorType;
    private Button calculate;
    private Button graph;
    private TableLayout table;
    Spinner tol;
    ArrayAdapter<String> adapter;
    ArrayList<String> values;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_bisection, container, false);
        final View newTol = inflater.inflate(R.layout.new_tolerance,  (ViewGroup) getView(), false);
        //Table
        table = v.findViewById(R.id.tableBisection);
        tittleIterations = v.findViewById(R.id.titleIterations);
        titleXi = v.findViewById(R.id.titleXi);
        titleXu = v.findViewById(R.id.titleXu);
        titleXm = v.findViewById(R.id.titleXm);
        titleEval = v.findViewById(R.id.titleEval);
        titleError = v.findViewById(R.id.titleError);
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
        RadioGroup radioGroup = v.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                switch(checkedId) {
                    case R.id.absoluteError:
                        errorType = 1;
                        break;
                    case R.id.relativeError:
                        errorType = 0;
                        break;
                }
            }
        });
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
                try {
                    String fx = function.getText().toString().trim();
                    Double xi = Double.parseDouble(xinf.getText().toString());
                    Double xu = Double.parseDouble(xupp.getText().toString());
                    int niter = Integer.parseInt(iterations.getText().toString());
                    tolerance = Double.parseDouble(values.get(tol.getSelectedItemPosition()));
                    bisection(xi, xu, tolerance, niter, fx);
                } catch (Exception e) {
                    Toast.makeText(getContext(),"Complete the blank spaces", Toast.LENGTH_LONG).show();
                }
            }
        });
        graph = v.findViewById(R.id.graph);
        graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), GraphActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        return v;
    }

    public void bisection(double xi, double xs, double tolerance, int niter, String functionRead) {
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
            //adding rows
            tittleIterations.setText("i");
            titleXi.setText("Xi");
            titleXu.setText("Xu");
            titleXm.setText("Xm");
            titleEval.setText("f(xm)");
            titleError.setText("Error");
            tittleIterations.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            titleXi.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            titleXu.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            titleXm.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            titleEval.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            titleError.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            tittleIterations.setTextSize(16);
            titleXi.setTextSize(16);
            titleXu.setTextSize(16);
            titleXm.setTextSize(16);
            titleEval.setTextSize(16);
            titleError.setTextSize(16);
            TableRow fila = new TableRow(getContext());
            TextView tv_col1 = new TextView(getContext());
            tv_col1.setId(R.id.titleIterations);
            tv_col1.setText(count+"   ");
            tv_col1.setTextColor(getResources().getColor(R.color.colorAccent));
            tv_col1.setTextSize(16);
            TextView tv_col2 = new TextView(getContext());
            tv_col2.setId(R.id.titleXi);
            tv_col2.setText(String.format("%.3f", xi)+"   ");
            tv_col2.setTextColor(getResources().getColor(R.color.colorAccent));
            tv_col2.setTextSize(16);
            TextView tv_col3 = new TextView(getContext());
            tv_col3.setId(R.id.titleXu);
            tv_col3.setText(String.format("%.3f", xs)+"   ");
            tv_col3.setTextColor(getResources().getColor(R.color.colorAccent));
            tv_col3.setTextSize(16);
            TextView tv_col4 = new TextView(getContext());
            tv_col4.setId(R.id.titleXm);
            tv_col4.setText(String.format("%.6f", xm)+"   ");
            tv_col4.setTextColor(getResources().getColor(R.color.colorAccent));
            tv_col4.setTextSize(16);
            TextView tv_col5 = new TextView(getContext());
            tv_col5.setId(R.id.titleEval);
            tv_col5.setText(String.format("%.3f", fxm)+"   ");
            tv_col5.setTextColor(getResources().getColor(R.color.colorAccent));
            tv_col5.setTextSize(16);
            TextView tv_col6 = new TextView(getContext());
            tv_col6.setId(R.id.titleError);
            tv_col6.setText("--------");
            tv_col6.setTextColor(getResources().getColor(R.color.colorAccent));
            tv_col6.setTextSize(16);
            fila.addView(tv_col1);
            fila.addView(tv_col2);
            fila.addView(tv_col3);
            fila.addView(tv_col4);
            fila.addView(tv_col5);
            fila.addView(tv_col6);
            table.addView(fila);

            while (error > tolerance && count < niter && fxm != 0) {
                if (fxi * fxm < 0) {
                    xs = xm;
                    fxs = fxm;
                } else {
                    xi = xm;
                    fxi = fxm;
                }
                double xaux = xm;
                xm = (xi + xs) / 2;
                fxmd = fx.with("x", Double.toString(xm)).eval();
                fxm = fxmd.doubleValue();
                if(errorType == 1) error = Math.abs(xm - xaux);//absolute
                else error = Math.abs((xm-xaux)/xm);//relative
                count++;
                TableRow fila1 = new TableRow(getContext());
                TextView tv_col11 = new TextView(getContext());
                tv_col11.setId(R.id.titleIterations);
                tv_col11.setText(count+"   ");
                tv_col11.setTextColor(getResources().getColor(R.color.colorAccent));
                tv_col11.setTextSize(16);
                TextView tv_col22 = new TextView(getContext());
                tv_col22.setId(R.id.titleXi);
                tv_col22.setText(String.format("%.3f", xi)+"   ");
                tv_col22.setTextColor(getResources().getColor(R.color.colorAccent));
                tv_col22.setTextSize(16);
                TextView tv_col33 = new TextView(getContext());
                tv_col33.setId(R.id.titleXu);
                tv_col33.setText(String.format("%.3f", xs)+"   ");
                tv_col33.setTextColor(getResources().getColor(R.color.colorAccent));
                tv_col33.setTextSize(16);
                TextView tv_col44 = new TextView(getContext());
                tv_col44.setId(R.id.titleXm);
                tv_col44.setText(String.format("%.6f", xm)+"   ");
                tv_col44.setTextColor(getResources().getColor(R.color.colorAccent));
                tv_col44.setTextSize(16);
                TextView tv_col55 = new TextView(getContext());
                tv_col55.setId(R.id.titleEval);
                tv_col55.setText(String.format("%.3f", fxm)+"   ");
                tv_col55.setTextColor(getResources().getColor(R.color.colorAccent));
                tv_col55.setTextSize(16);
                TextView tv_col66 = new TextView(getContext());
                tv_col66.setId(R.id.titleError);
                tv_col66.setText(String.format("%.3f", error));
                tv_col66.setTextColor(getResources().getColor(R.color.colorAccent));
                tv_col66.setTextSize(16);
                fila1.addView(tv_col11);
                fila1.addView(tv_col22);
                fila1.addView(tv_col33);
                fila1.addView(tv_col44);
                fila1.addView(tv_col55);
                fila1.addView(tv_col66);
                table.addView(fila1);
            }
            if (fxm == 0) {
                System.out.println(xm + " is root");
                Toast.makeText(getContext(),xm + " is root", Toast.LENGTH_LONG).show();
            } else if (error < tolerance) {
                System.out.println(count);
                System.out.println(xm + " is an aproximation of a root with a tolerance = " + tolerance);
                Toast.makeText(getContext(),xm + " is an aproximation of a root with a tolerance = " + tolerance, Toast.LENGTH_LONG).show();
            } else {
                System.out.println("failed at " + niter + " iterations");
                Toast.makeText(getContext(),"failed at " + niter + " iterations", Toast.LENGTH_LONG).show();
            }
        } else {
            System.out.println("the interval is unsuitable");
            Toast.makeText(getContext(),"the interval is unsuitable", Toast.LENGTH_LONG).show();
        }
    }

}