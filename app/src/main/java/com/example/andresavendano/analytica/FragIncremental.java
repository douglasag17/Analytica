package com.example.andresavendano.analytica;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.math.BigDecimal;
import com.udojava.evalex.Expression;

public class FragIncremental extends Fragment {

    private EditText txtfunction;
    private EditText txtvalue;
    private EditText txtdelta;
    private EditText txtiters;
    private TextView resultado;
    private TextView t;
    private Button calculate;
    private Button graph;
    private Button help;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_incremental, container, false);
        final View helpView = inflater.inflate(R.layout.help_bisection,  (ViewGroup) getView(), false);
        txtfunction = v.findViewById(R.id.function);
        txtvalue = v.findViewById(R.id.value);
        txtdelta = v.findViewById(R.id.delta);
        txtiters = v.findViewById(R.id.iters);
        resultado = v.findViewById(R.id.textView);
        calculate = v.findViewById(R.id.calculate);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String functionRead = txtfunction.getText().toString();
                    double x0 = Double.valueOf(txtvalue.getText().toString());
                    double delta = Double.valueOf(txtdelta.getText().toString());
                    int niter = Integer.valueOf(txtiters.getText().toString());
                    Expression fx = new Expression(functionRead);
                    BigDecimal fx0d = fx.with("x", Double.toString(x0)).eval();
                    double fx0 = fx0d.doubleValue();
                    if (fx0 == 0) {
                        resultado.setText(x0 + " is a root");
                        resultado.setTextSize(20);
                        resultado.setTextColor(getResources().getColor(R.color.colorAccent));
                    } else {
                        double x1 = x0 + delta;
                        int count = 1;
                        BigDecimal fx1d = fx.with("x", Double.toString(x1)).eval();
                        double fx1 = fx1d.doubleValue();
                        while ((fx0 * fx1 > 0) && (count < niter)) {
                            x0 = x1;
                            fx0 = fx1;
                            x1 = x0 + delta;
                            fx1d = fx.with("x", Double.toString(x1)).eval();
                            fx1 = fx1d.doubleValue();
                            count++;
                        }
                        if (fx1 == 0) {
                            resultado.setText(x1 + " is a root");
                            resultado.setTextSize(20);
                            resultado.setTextColor(getResources().getColor(R.color.colorAccent));
                        } else if (fx0 * fx1 < 0) {
                            resultado.setText("There is a root in [" + x0 + " , " + x1 + "]");
                            resultado.setTextSize(20);
                            resultado.setTextColor(getResources().getColor(R.color.colorAccent));
                        } else {
                            resultado.setText("failed at " + niter + " iterations");
                            resultado.setTextSize(20);
                            resultado.setTextColor(getResources().getColor(R.color.colorAccent));
                        }
                    }
                } catch (Exception e) {
                    Toast toast = Toast.makeText(getContext(),"Complete the fields and verify that the fields are well written, see helps", Toast.LENGTH_LONG);
                    View view = toast.getView();
                    TextView text = (TextView) view.findViewById(android.R.id.message);
                    text.setTextColor(Color.BLACK);
                    text.setGravity(1);
                    view.setBackgroundColor(Color.parseColor("#B3E5FE"));
                    toast.show();
                }
            }
        });
        /**
         * Help button
         */
        final Button help = v.findViewById(R.id.help);
        t = (TextView) helpView.findViewById(R.id.helpText);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                if (helpView.getParent() != null)
                    ((ViewGroup) helpView.getParent()).removeView(helpView);
                builder.setView(helpView);
                t.setText("\nTo Guarantee the existence of a root the function must fulfill 2 conditions:\n" +
                        "\t\t* \tThe function must be continuous in the interval [a, b]\n" +
                        "\t\t* \tThe function evaluated at the extremes of the interval must have a sign change f(a)*f(b) < 0\n\n" +
                        "The tolerance must be positive \n");
                t.setTextSize(25);
                builder.show();
            }
        });
        graph = v.findViewById(R.id.graph);
        graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), GraphActivity.class);
                String[] funciones={txtfunction.getText().toString()};
                intent.putExtra("function",funciones);
                startActivityForResult(intent, 0);
            }
        });
        return v;
    }
}
