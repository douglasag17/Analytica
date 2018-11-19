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

public class FragCubicSpline extends Fragment {

    int num = 3;
    double x [];
    double fx [];
    private TableLayout vectorX;
    private TableLayout vectorFx;
    private TextView polinomio;
    private TextView t;

    private boolean isError;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflaterView = inflater.inflate(R.layout.frag_cubic_spline, container, false);
        final View helpView = inflater.inflate(R.layout.help_bisection,  (ViewGroup) getView(), false);
        vectorX = inflaterView.findViewById(R.id.vectorX);
        vectorFx = inflaterView.findViewById(R.id.vectorFx);
        polinomio = inflaterView.findViewById(R.id.polinomio);
        createTable(inflaterView);
        Button butCalculate = inflaterView.findViewById(R.id.butCalculate);
        butCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isError = false;
                try {
                    x = getVectorX();
                    fx = getVectorFx();
                    cubic(x, fx, x.length);
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

    public void cubic(double[] xi, double[] yi, int n) {
        double[][] ecu = new double[(n-1)*4][(n-1)*4];
        double[] b = new double[4*(n-1)];
        ecu[0][0] = Math.pow(xi[0],3);
        ecu[0][1] = Math.pow(xi[0],2);
        ecu[0][2] = xi[0];
        ecu[0][3] = 1;
        b[0] = yi[0];
        int i = 1;
        int j = 0;
        for (int k = 1; k < n-1; k++) {
            ecu[i][j] = Math.pow(xi[k],3);
            ecu[i][j+1] = Math.pow(xi[k],2);
            ecu[i][j+2] = xi[k];
            ecu[i][j+3] = 1;
            b[i] = yi[k];
            ecu[i + 1][j + 4] = Math.pow(xi[k],3);
            ecu[i + 1][j + 5] = Math.pow(xi[k],2);
            ecu[i + 1][j + 6] = xi[k];
            ecu[i + 1][j + 7] = 1;
            b[i + 1] = yi[k];
            i += 2;
            j += 4;
        }
        ecu[i][j] = Math.pow(xi[xi.length-1],3);
        ecu[i][j+1] = Math.pow(xi[xi.length-1],2);
        ecu[i][j+2] = xi[xi.length-1];
        ecu[i][j+3] = 1;
        b[i] = yi[n-1];
        i++;
        j=0;
        for (int k = 1; k < n-1; k++) {
            ecu[i][j] = 3*Math.pow(xi[k], 2);
            ecu[i][j+1] = 2*xi[k];
            ecu[i][j+2] = 1;
            ecu[i][j+3] = 0;
            ecu[i][j+4] = -3*Math.pow(xi[k], 2);
            ecu[i][j+5] = -2*xi[k];
            ecu[i][j+6] = -1;
            ecu[i][j+7] = 0;
            i++;
            j += 4;
        }
        j = 0;
        for (int k = 1; k < n-1; k++) {
            ecu[i][j] = 6*xi[k];
            ecu[i][j+1] = 2;
            ecu[i][j+2] = 0;
            ecu[i][j+3] = 0;
            ecu[i][j+4] = -6*xi[k];
            ecu[i][j+5] = -2;
            ecu[i][j+6] = 0;
            ecu[i][j+7] = 0;
            i++;
            j += 4;
        }
        ecu[i][0]= 6*xi[0];
        ecu[i][1]= 2;
        ecu[i][2]= 0;
        ecu[i][3]= 0;
        i++;
        ecu[i][ecu.length-4]= 6*xi[n-1];
        ecu[i][ecu.length-3]= 2;
        ecu[i][ecu.length-2]= 0;
        ecu[i][ecu.length-1]= 0;

        GaussianEliminationPartialPivoting pv = new GaussianEliminationPartialPivoting();
        double[] Ab = pv.gaussianElimination(ecu,b);
        int index = 0;
        String pol = "";
        polinomio.setText("");
        for(int k = 0; k < Ab.length; k += 4){
            if(Ab[k] == Double.NaN || Ab[k] == Double.POSITIVE_INFINITY || Ab[k] == Double.NEGATIVE_INFINITY ) {
                isError = true;
            } else {
                pol = String.format("%.1f", Ab[k]) + "x^3";
                if (Ab[k + 1] >= 0) {
                    pol += "+";
                }
            }
            if(Ab[k+1] == Double.NaN || Ab[k+1] == Double.POSITIVE_INFINITY || Ab[k+1] == Double.NEGATIVE_INFINITY ) {
                isError = true;
            } else {
                pol += String.format("%.1f", Ab[k + 1]) + "x^2";
                if (Ab[k + 2] >= 0) {
                    pol += "+";
                }
            }
            if(Ab[k+2] == Double.NaN || Ab[k+2] == Double.POSITIVE_INFINITY || Ab[k+2] == Double.NEGATIVE_INFINITY ) {
                isError = true;
            } else {
                pol += String.format("%.1f", Ab[k + 2]) + "x";
                if (Ab[k + 3] >= 0) {
                    pol += "+";
                }
            }
            if(Ab[k+3] == Double.NaN || Ab[k+3] == Double.POSITIVE_INFINITY || Ab[k+3] == Double.NEGATIVE_INFINITY ) {
                isError = true;
            } else {
                pol += String.format("%.1f", Ab[k + 3]);
            }
            if(!isError) {
                polinomio.append(pol + "    " + (int) x[index] + " < x < " + (int) x[index + 1] + "\n");
                index += 1;
            } else {
                polinomio.setText("");
                Toast toast = Toast.makeText(getContext(),"Mathematical error", Toast.LENGTH_LONG);
                View view = toast.getView();
                TextView text = (TextView) view.findViewById(android.R.id.message);
                text.setTextColor(Color.BLACK);
                text.setGravity(1);
                view.setBackgroundColor(Color.parseColor("#B3E5FE"));
                toast.show();
                break;
            }
        }
    }
}