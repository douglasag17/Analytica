package com.example.andresavendano.analytica;

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
    private Button calculate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_incremental, container, false);
        txtfunction = v.findViewById(R.id.function);
        txtvalue = v.findViewById(R.id.value);
        txtdelta = v.findViewById(R.id.delta);
        txtiters = v.findViewById(R.id.iters);
        resultado = v.findViewById(R.id.textView);
        calculate = v.findViewById(R.id.bttCalculate);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String functionRead = txtfunction.getText().toString();
                double x0 = Double.valueOf(txtvalue.getText().toString());
                double delta = Double.valueOf(txtdelta.getText().toString());
                int niter = Integer.valueOf(txtiters.getText().toString());
                Expression fx = new Expression(functionRead);
                BigDecimal fx0d = fx.with("x", Double.toString(x0)).eval();
                double fx0 = fx0d.doubleValue();
                if (fx0 == 0) {
                    resultado.setText(x0 + " is a root");
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
                    } else if (fx0 * fx1 < 0) {
                        resultado.setText("There is a root between " + x0 + " and " + x1);
                        Toast.makeText(getContext(),"there is a root between " + x0 + " and " + x1,Toast.LENGTH_SHORT).show();
                    } else {
                        resultado.setText("failed at " + niter + " iterations");
                    }
                }
            }
        });
        return v;
    }
}
