package com.example.andresavendano.analytica;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class FragSimpleGaussian extends Fragment {
    int num = 3;
    double A [][];
    double b [];
    private TableLayout table;
    private TableLayout vectorBB;
    private TextView resultado;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflaterView=inflater.inflate(R.layout.frag_simple_gaussian, container, false);
        table = inflaterView.findViewById(R.id.tableGauss);
        vectorBB = inflaterView.findViewById(R.id.vectorB);
        resultado = inflaterView.findViewById(R.id.textView2);
        createTable(inflaterView);
        Button button = inflaterView.findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                A = getMatrixA();
                b = getVectorB();
                simpleGaussianElimination(A, b);
            }
        });
        Button button3 = inflaterView.findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num++;
                TableRow edit[]=new TableRow[num];
                TableRow edit2[]=new TableRow[num];
                for(int i = 0;i<num-1;i++){
                    TableRow row = (TableRow) table.getChildAt(i);
                    TableRow rowB = (TableRow) vectorBB.getChildAt(i);
                    edit[i]=row;
                    edit2[i]=rowB;
                }
                table.removeAllViews();
                vectorBB.removeAllViews();
                for(int j=0; j<num; j++){
                    if(j<num-1) {
                        TableRow row = edit[j];
                        TableRow rowB = edit2[j];
                        EditText editText = new EditText(getContext());
                        //editText.setText("0");
                        editText.setHint("0");
                        row.addView(editText);
                        table.addView(row);
                        vectorBB.addView(rowB);
                    }
                    else{
                        TableRow row = new TableRow(getContext());
                        TableRow rowB = new TableRow(getContext());
                        EditText editTextB = new EditText(getContext());
                        editTextB.setText("0");
                        rowB.addView(editTextB);
                        for(int i=0;i<num;i++){
                            EditText editText = new EditText(getContext());
                            //editText.setText("0");
                            editText.setHint("0");
                            row.addView(editText);
                        }
                        vectorBB.addView(rowB);
                        table.addView(row);
                    }
                }
            }
        });
        Button button4 = inflaterView.findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num--;
                TableRow edit[] = new TableRow[num+1];
                TableRow editB[] = new TableRow[num+1];
                for(int i = 0;i<num+1;i++){
                    TableRow row = (TableRow) table.getChildAt(i);
                    TableRow rowB = (TableRow) vectorBB.getChildAt(i);
                    edit[i]=row;
                    editB[i]=rowB;
                }
                table.removeAllViews();
                vectorBB.removeAllViews();
                for(int j =0;j<num;j++){
                    TableRow row = edit[j];
                    TableRow rowB = editB[j];
                    row.removeViewAt(num);
                    vectorBB.addView(rowB);
                    table.addView(row);
                }
            }
        });
        return inflaterView;
    }

    public void createTable(View inflaterView){
        TableLayout table = inflaterView.findViewById(R.id.tableGauss);
        for(int j =0; j<num; j++){
            TableRow row = new TableRow(getContext());
            row.setId(j);
            for(int i = 0;i<num;i++) {
                EditText editText = new EditText(getContext());
                editText.setId(i+j);
                //editText.setText("0");
                editText.setHint("0");
                row.addView(editText);
            }
            table.addView(row);
        }

        TableLayout vectorB = inflaterView.findViewById(R.id.vectorB);
        for(int j=0; j<num; j++){
            TableRow row = new TableRow(getContext());
            row.setId(j);
            EditText editText = new EditText(getContext());
            editText.setId(j);
            //editText.setText("0");
            editText.setHint("0");
            row.addView(editText);
            vectorB.addView(row);
        }
    }

    public double[][] getMatrixA(){
        int n = table.getChildCount();
        A = new double [n][n];
        for(int i=0; i<n; i++){
            TableRow row = (TableRow) table.getChildAt(i);
            for(int x = 0;x<row.getChildCount();++x){
                EditText f = (EditText) row.getChildAt(x);
                A[i][x] = Double.valueOf(f.getText().toString());
            }
        }
        return A;
    }

    public double[] getVectorB(){
        int n = vectorBB.getChildCount();
        b = new double [n];
        for(int i=0; i<n; i++) {
            TableRow row = (TableRow) vectorBB.getChildAt(i);
            EditText f = (EditText) row.getChildAt(0);
            b[i] = Double.valueOf(f.getText().toString());
        }
        return b;
    }

    public void simpleGaussianElimination(double [][] A, double [] b) {
        double [][] U = scallingMatrix(A, b, A.length);
        int B = U.length;
        double x[] = regressiveSubstitution(U, B);

        System.out.println("Regressive Substitution");
        for(int i = 0; i < x.length; i++) {
            System.out.print(x[i] + "  ");
            resultado.append(String.format("%.f", x[i])+"  ");
        }
    }

    public double[][] scallingMatrix(double A[][], double b[], int n){
        double [][] Ab = augmentMatrix(A, b);
        if(Ab[0][0] == 0){
            double a[] = new double[Ab[0].length];
            System.arraycopy(Ab[0], 0, a, 0, Ab[0].length);
            for(int i = 1; i < n; i++){
                if(Ab[i][0] != 0){
                    System.arraycopy(Ab[i], 0, Ab[0], 0, Ab[0].length); //Ab[0] = Ab[i]
                    System.arraycopy(a, 0, Ab[i], 0, Ab[0].length);
                    break;
                }
            }
        }

        for(int k = 0; k < n; k++){
            for(int i = k+1; i < n; i++){
                double m = (float)(Ab[i][k])/Ab[k][k];
                for(int j = 0; j < n+1; j++){
                    Ab[i][j] = Ab[i][j]-(m*Ab[k][j]);
                }
            }
        }

        System.out.println("\n Ab");
        for (int v = 0; v < Ab.length; v++) {
            System.out.print("[");
            //resultado.append("[");
            for (int m = 0; m < Ab[v].length; m++) {
                System.out.print (Ab[v][m]);
                //resultado.append(Ab[v][m]+"  ");
                if (m != Ab[v].length-1) System.out.print("   ");
            }
            System.out.println("]");
            //resultado.append("]");
        }
        return Ab;
    }

    public double[][] augmentMatrix(double A[][], double b[]){
        double result[][] = new double[A.length][A.length+1];
        for(int i = 0; i < result.length; i++){
            for(int j = 0; j < result[0].length; j++){
                if(j < A.length){
                    result[i][j] = A[i][j];
                }else{
                    result[i][j] = b[i];
                }
            }
        }
        return result;
    }

    public double[] regressiveSubstitution(double Ab[][], int n){
        double x[] = new double[n];
        x[n-1] = Ab[n-1][n]/(double)Ab[n-1][n-1];
        for(int i = n-1; i > 0; i--){
            double sum = 0;
            for(int p = i + 1; p < n + 1; p++){
                sum += Ab[i-1][p-1]*x[p-1];
            }
            x[i-1] = (Ab[i-1][n]-sum)/(double)(Ab[i-1][i-1]);
        }
        return x;
    }
}