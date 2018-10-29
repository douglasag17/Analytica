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
    private Double tolerance;
    private int errorType;
    private Button calculate;
    private TableLayout table;
    Spinner tol;
    ArrayAdapter<String> adapter;
    ArrayList<String> values;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_bisection, container, false);
        final View newTol = inflater.inflate(R.layout.new_tolerance,  (ViewGroup) getView(), false);

        table = (TableLayout) v.findViewById(R.id.tableBisection);

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
                String fx = function.getText().toString().trim();
                Double xi = Double.parseDouble(xinf.getText().toString());
                Double xu = Double.parseDouble(xupp.getText().toString());
                int niter = Integer.parseInt(iterations.getText().toString());
                tolerance = Double.parseDouble(values.get(tol.getSelectedItemPosition()));
                bisection(xi, xu, tolerance, niter, fx);

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

            TableRow fila = new TableRow(getContext());
            TextView tv_col1 = new TextView(getContext());
            tv_col1.setId(R.id.iterationsTable);
            tv_col1.setText(count+"  ");
            TextView tv_col2 = new TextView(getContext());
            tv_col2.setId(R.id.xiTable);
            tv_col2.setText(xi+"  ");
            TextView tv_col3 = new TextView(getContext());
            tv_col3.setId(R.id.xuTable);
            tv_col3.setText(xs+"   ");
            TextView tv_col4 = new TextView(getContext());
            tv_col4.setId(R.id.xmTable);
            tv_col4.setText(xm+"   ");
            TextView tv_col5 = new TextView(getContext());
            tv_col5.setId(R.id.evalTable);
            tv_col5.setText(fxm+"  ");
            TextView tv_col6 = new TextView(getContext());
            tv_col6.setId(R.id.errorTable);
            tv_col6.setText("-");
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
                tv_col11.setId(R.id.iterationsTable);
                tv_col11.setText(count+"  ");
                TextView tv_col22 = new TextView(getContext());
                tv_col22.setId(R.id.xiTable);
                tv_col22.setText(xi+"  ");
                TextView tv_col33 = new TextView(getContext());
                tv_col33.setId(R.id.xuTable);
                tv_col33.setText(xs+"   ");
                TextView tv_col44 = new TextView(getContext());
                tv_col44.setId(R.id.xmTable);
                tv_col44.setText(xm+"   ");
                TextView tv_col55 = new TextView(getContext());
                tv_col55.setId(R.id.evalTable);
                tv_col55.setText(fxm+"  ");
                TextView tv_col66 = new TextView(getContext());
                tv_col66.setId(R.id.errorTable);
                tv_col66.setText(error+"");
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
            } else if (error < tolerance) {
                System.out.println(count);
                System.out.println(xm + " is an aproximation of a root with a tolerance = " + tolerance);
                Toast.makeText(getContext(),xm + " is an aproximation of a root with a tolerance = " + tolerance,Toast.LENGTH_LONG).show();
            } else {
                System.out.println("failed at " + niter + " iterations");
            }
        } else {
            System.out.println("the interval is unsuitable");
        }
    }

}