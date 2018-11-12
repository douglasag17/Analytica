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

public class FragQuadraticSpline extends Fragment {

    int num = 3;
    double x [];
    double fx [];
    private TableLayout vectorX;
    private TableLayout vectorFx;
    private TextView polinomio;
    private TextView t;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflaterView = inflater.inflate(R.layout.frag_quadratic_spline, container, false);
        final View helpView = inflater.inflate(R.layout.help_bisection,  (ViewGroup) getView(), false);
        vectorX = inflaterView.findViewById(R.id.vectorX);
        vectorFx = inflaterView.findViewById(R.id.vectorFx);
        polinomio = inflaterView.findViewById(R.id.polinomio);
        createTable(inflaterView);
        Button butCalculate = inflaterView.findViewById(R.id.butCalculate);
        butCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    x = getVectorX();
                    fx = getVectorFx();
                    linearSpline(x, fx, x.length-1);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    Toast toast = Toast.makeText(getContext(),"Complete the fields and verify that the fields are well written, see helps", Toast.LENGTH_LONG);
                    View view = toast.getView();
                    TextView text = view.findViewById(android.R.id.message);
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
                t.setText("Spline interpolation is a form of interpolation where the interpolant is a " +
                        "special type of piecewise polynomial called a spline.\n");
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

    public void linearSpline(double[] x, double[] y, int n){
        GaussianEliminationPartialPivoting pv = new GaussianEliminationPartialPivoting();

        double[][] A = new double[n*3][n*3];
        double[]   b = new double[n*3];
        String pol;
        int j,l;
        for(int i=0; i < n; i++){
            j = 2*i;
            l = 3*i;
            A[j][l]     = Math.pow(x[i],2);
            A[j][l+1]   = x[i];
            A[j][l+2]   = 1;
            b[j]        = y[i];
            A[j+1][l]   = Math.pow(x[i+1],2);
            A[j+1][l+1] = x[i+1];
            A[j+1][l+2] = 1;
            b[j+1]      = y[i+1];
        }
        j=0;
        l=1;
        for (int i = n*2; i < (n*3)-1; i++){
            A[i][j]   = 2*x[l];
            A[i][j+1] = 1;
            A[i][j+3] = -2*x[l];
            A[i][j+4] = -1;
            j+=3;
            l+=1;
        }
        A[(3*n)-1][0] = 1;
        double[] Ab = pv.gaussianElimination(A,b);
        int index = 0;
        for(int i = 0; i < Ab.length; i += 3){
            pol = String.format("%.1f", Ab[i]) + "x^2";
            if(Ab[i+1] >= 0) {
                pol = pol + "+";
            }
            pol += String.format("%.1f", Ab[i+1]) + "x";
            if(Ab[i+2] >= 0) {
                pol = pol + "+";
            }
            pol += String.format("%.1f", Ab[i+2]);
            polinomio.append(pol + "    "+ (int)x[index] + " < x < " + (int)x[index+1] +"\n");
            index += 1;
        }
    }
}