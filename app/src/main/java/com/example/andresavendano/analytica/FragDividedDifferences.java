package com.example.andresavendano.analytica;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import katex.hourglass.in.mathlib.MathView;


public class FragDividedDifferences extends Fragment {
    int num = 3;
    double x [];
    double fx [];
    private TableLayout vectorX;
    private TableLayout vectorFx;
    private EditText edtEval;
    private TextView polinomio;
    private TextView answer;
    private TextView t;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflaterView = inflater.inflate(R.layout.frag_divided_differences, container, false);
        final View helpView = inflater.inflate(R.layout.help_bisection,  (ViewGroup) getView(), false);
        vectorX = inflaterView.findViewById(R.id.vectorX);
        vectorFx = inflaterView.findViewById(R.id.vectorFx);
        polinomio = inflaterView.findViewById(R.id.polinomio);
        answer = inflaterView.findViewById(R.id.answer);
        edtEval = inflaterView.findViewById(R.id.edtEval);
        edtEval.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL| InputType.TYPE_NUMBER_FLAG_SIGNED);
        createTable(inflaterView);

        Button butCalculate = inflaterView.findViewById(R.id.butCalculate);
        butCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    x = getVectorX();
                    fx = getVectorFx();
                    int nPoints = x.length;
                    double value = Double.valueOf(edtEval.getText().toString());
                    newton(nPoints, value, x, fx);
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

        Button button3 = inflaterView.findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num++;
                TableRow edit2[]=new TableRow[num];
                TableRow edit3[]=new TableRow[num];
                for(int i = 0;i<num-1;i++){
                    TableRow rowB = (TableRow) vectorFx.getChildAt(i);
                    TableRow rowX = (TableRow) vectorX.getChildAt(i);
                    edit2[i]=rowB;
                    edit3[i]=rowX;
                }
                vectorFx.removeAllViews();
                vectorX.removeAllViews();
                for(int j=0; j<num; j++){
                    if(j<num-1) {
                        TableRow rowB = edit2[j];
                        TableRow rowX = edit3[j];
                        EditText editText = new EditText(getContext());
                        //editText.setText("0");
                        editText.setHint("  0  ");
                        editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL| InputType.TYPE_NUMBER_FLAG_SIGNED);
                        editText.setGravity(Gravity.CENTER_HORIZONTAL);
                        //editText.setWidth(170);
                        vectorFx.addView(rowB);
                        vectorX.addView(rowX);
                    } else {
                        TableRow row = new TableRow(getContext());
                        TableRow rowB = new TableRow(getContext());
                        TableRow rowX = new TableRow(getContext());
                        EditText editTextB = new EditText(getContext());
                        EditText editTextX = new EditText(getContext());
                        //editTextB.setText("0");
                        editTextB.setHint("  0  ");
                        editTextB.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL| InputType.TYPE_NUMBER_FLAG_SIGNED);
                        editTextB.setGravity(Gravity.CENTER_HORIZONTAL);
                        editTextX.setHint("  0  ");
                        editTextX.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL| InputType.TYPE_NUMBER_FLAG_SIGNED);
                        editTextX.setGravity(Gravity.CENTER_HORIZONTAL);
                        //editTextB.setWidth(170);
                        rowB.addView(editTextB);
                        rowX.addView(editTextX);
                        for(int i=0;i<num;i++){
                            EditText editText = new EditText(getContext());
                            //editText.setText("0");
                            editText.setHint("  0  ");
                            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL| InputType.TYPE_NUMBER_FLAG_SIGNED);
                            editText.setGravity(Gravity.CENTER_HORIZONTAL);
                            //editText.setWidth(170);
                            row.addView(editText);
                        }
                        vectorFx.addView(rowB);
                        vectorX.addView(rowX);
                    }
                }
            }
        });

        Button button4 = inflaterView.findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num--;
                TableRow editB[] = new TableRow[num+1];
                TableRow editX[] = new TableRow[num+1];
                for(int i = 0;i<num+1;i++){
                    TableRow rowB = (TableRow) vectorFx.getChildAt(i);
                    TableRow rowX = (TableRow) vectorX.getChildAt(i);
                    editB[i]=rowB;
                    editX[i]=rowX;
                }
                vectorFx.removeAllViews();
                vectorX.removeAllViews();
                for(int j =0;j<num;j++){
                    TableRow rowB = editB[j];
                    TableRow rowX = editX[j];
                    vectorFx.addView(rowB);
                    vectorX.addView(rowX);
                }
            }
        });
        final Button help = inflaterView.findViewById(R.id.butHelp);
        t = (TextView) helpView.findViewById(R.id.helpText);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                if (helpView.getParent() != null)
                    ((ViewGroup) helpView.getParent()).removeView(helpView);
                builder.setView(helpView);
                t.setText("Given a sequence\n" +
                        "of (n +1) data points and a function f, the aim is to determine an n-th degree polynomial which interpolates\n" +
                        "f at these points.\n\n-Be careful to don't repeat any x, else it won't be a function\n");
                t.setTextSize(25);
                builder.show();
            }
        });
        return inflaterView;
    }

    public void createTable(View inflaterView){
        TableLayout vectorX = inflaterView.findViewById(R.id.vectorX);
        for(int j=0; j<num; j++){
            TableRow row = new TableRow(getContext());
            row.setId(j);
            EditText editText = new EditText(getContext());
            editText.setId(j);
            //editText.setText("0");
            editText.setHint("  0  ");
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL| InputType.TYPE_NUMBER_FLAG_SIGNED);
            editText.setGravity(Gravity.CENTER_HORIZONTAL);
            //editText.setWidth(170);
            row.addView(editText);
            vectorX.addView(row);
        }

        TableLayout vectorFx = inflaterView.findViewById(R.id.vectorFx);
        for(int j=0; j<num; j++){
            TableRow row = new TableRow(getContext());
            row.setId(j);
            EditText editText = new EditText(getContext());
            editText.setId(j);
            //editText.setText("0");
            editText.setHint("  0  ");
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL| InputType.TYPE_NUMBER_FLAG_SIGNED);
            editText.setGravity(Gravity.CENTER_HORIZONTAL);
            //editText.setWidth(170);
            row.addView(editText);
            vectorFx.addView(row);
        }
    }

    public double[] getVectorX(){
        int n = vectorX.getChildCount();
        x = new double [n];
        for(int i=0; i<n; i++) {
            TableRow row = (TableRow) vectorX.getChildAt(i);
            EditText f = (EditText) row.getChildAt(0);
            x[i] = Double.valueOf(f.getText().toString());
        }
        return x;
    }

    public double[] getVectorFx(){
        int n = vectorFx.getChildCount();
        fx = new double [n];
        for(int i=0; i<n; i++) {
            TableRow row = (TableRow) vectorFx.getChildAt(i);
            EditText f = (EditText) row.getChildAt(0);
            fx[i] = Double.valueOf(f.getText().toString());
        }
        return fx;
    }

    public void newton(int nPoints, double value, double x[], double y[]){
        double [][] table = new double[nPoints][nPoints];
        double [] b = new  double[nPoints];
        for (int i = 0; i < nPoints; i++) {
            table[i][0] = y[i];
        }
        for (int i = 0; i < nPoints; i++) {
            for (int j = 1; j < i+1; j++) {
                table[i][j] = (table[i][j-1] - table[i-1][j-1])/(x[i] - x[i-j]);
            }
        }
        for (int i = 0; i < nPoints; i++) {
            System.out.print(x[i] + " ");
            for (int j = 0; j < nPoints; j++) {
                System.out.print(table[i][j] + " ");
            }
            System.out.println();
        }

        for (int i = 0; i < nPoints; i++) {
            b[i] = table[i][i];
            System.out.println(b[i]);
        }
        // Polinomio interpolante
        String pol = "P(x) = " + table[0][0];
        String temp = "";
        double result = table[0][0];
        double aux = 1;
        for (int i = 1; i < nPoints; i++) {
            temp += "(x " + "- " + x[i-1] + ")";
            pol += "+" + table[i][i] + "*" + temp;
            aux *= (value - x[i-1]);
            result += table[i][i]*aux;
        }
        polinomio.setClickable(false);
        polinomio.setText(pol + "");
        answer.setText("P(" + value + ") = " + result);
    }
}
