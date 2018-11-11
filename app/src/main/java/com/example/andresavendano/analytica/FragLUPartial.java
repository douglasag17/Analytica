package com.example.andresavendano.analytica;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
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


public class FragLUPartial extends Fragment {
    int num = 3;
    double A [][];
    double b [];
    double [][] L;
    double [][] U;
    private TableLayout table;
    private TableLayout vectorBB;
    private TableLayout vectorX;
    private TableLayout matrixL;
    private TableLayout matrixU;
    private TextView txtL;
    private TextView txtU;
    private TextView t;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflaterView = inflater.inflate(R.layout.frag_lupartial, container, false);
        final View helpView = inflater.inflate(R.layout.help_bisection,  (ViewGroup) getView(), false);
        table = inflaterView.findViewById(R.id.tableGauss);
        vectorBB = inflaterView.findViewById(R.id.vectorB);
        vectorX = inflaterView.findViewById(R.id.vectorX);
        matrixL = inflaterView.findViewById(R.id.matrixL);
        matrixU = inflaterView.findViewById(R.id.matrixU);
        txtL = inflaterView.findViewById(R.id.L);
        txtU = inflaterView.findViewById(R.id.U);
        createTable(inflaterView);
        Button button = inflaterView.findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    A = getMatrixA();
                    b = getVectorB();
                    luFactorization(A, b);
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
                TableRow edit[]=new TableRow[num];
                TableRow edit2[]=new TableRow[num];
                TableRow edit3[]=new TableRow[num];
                for(int i = 0;i<num-1;i++){
                    TableRow row = (TableRow) table.getChildAt(i);
                    TableRow rowB = (TableRow) vectorBB.getChildAt(i);
                    TableRow rowX = (TableRow) vectorX.getChildAt(i);
                    edit[i]=row;
                    edit2[i]=rowB;
                    edit3[i]=rowX;
                }
                table.removeAllViews();
                vectorBB.removeAllViews();
                vectorX.removeAllViews();
                for(int j=0; j<num; j++){
                    if(j<num-1) {
                        TableRow row = edit[j];
                        TableRow rowB = edit2[j];
                        TableRow rowX = edit3[j];
                        EditText editText = new EditText(getContext());
                        //editText.setText("0");
                        editText.setHint("  0  ");
                        editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        editText.setGravity(Gravity.CENTER_HORIZONTAL);
                        //editText.setWidth(170);
                        row.addView(editText);
                        table.addView(row);
                        vectorBB.addView(rowB);
                        vectorX.addView(rowX);
                    } else {
                        TableRow row = new TableRow(getContext());
                        TableRow rowB = new TableRow(getContext());
                        TableRow rowX = new TableRow(getContext());
                        EditText editTextB = new EditText(getContext());
                        EditText editTextX = new EditText(getContext());
                        //editTextB.setText("0");
                        editTextB.setHint("  0  ");
                        editTextB.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        editTextB.setGravity(Gravity.CENTER_HORIZONTAL);
                        editTextX.setHint("  X"+num+"  ");
                        editTextX.setEnabled(false);
                        editTextX.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        editTextX.setGravity(Gravity.CENTER_HORIZONTAL);
                        //editTextB.setWidth(170);
                        rowB.addView(editTextB);
                        rowX.addView(editTextX);
                        for(int i=0;i<num;i++){
                            EditText editText = new EditText(getContext());
                            //editText.setText("0");
                            editText.setHint("  0  ");
                            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                            editText.setGravity(Gravity.CENTER_HORIZONTAL);
                            //editText.setWidth(170);
                            row.addView(editText);
                        }
                        vectorBB.addView(rowB);
                        vectorX.addView(rowX);
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
                TableRow editX[] = new TableRow[num+1];
                for(int i = 0;i<num+1;i++){
                    TableRow row = (TableRow) table.getChildAt(i);
                    TableRow rowB = (TableRow) vectorBB.getChildAt(i);
                    TableRow rowX = (TableRow) vectorX.getChildAt(i);
                    edit[i]=row;
                    editB[i]=rowB;
                    editX[i]=rowX;
                }
                table.removeAllViews();
                vectorBB.removeAllViews();
                vectorX.removeAllViews();
                for(int j =0;j<num;j++){
                    TableRow row = edit[j];
                    TableRow rowB = editB[j];
                    TableRow rowX = editX[j];
                    row.removeViewAt(num);
                    vectorBB.addView(rowB);
                    vectorX.addView(rowX);
                    table.addView(row);
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
                t.setText("In every stage k, this method find the biggest (in absolute value) of " +
                        "the k column that occupies the position akk.In other words, it looks for " +
                        "the biggest between |akk| with  k <= i <= n , and it swaps the rows in " +
                        "order to set the biggest chosen in the row k. These steps guarantee the " +
                        "next property.\n");
                t.setTextSize(25);
                builder.show();
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
                editText.setHint("  0  ");
                editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                editText.setGravity(Gravity.CENTER_HORIZONTAL);
                //editText.setWidth(170);
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
            editText.setHint("  0  ");
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            editText.setGravity(Gravity.CENTER_HORIZONTAL);
            //editText.setWidth(170);
            row.addView(editText);
            vectorB.addView(row);
        }

        TableLayout vectorX = inflaterView.findViewById(R.id.vectorX);
        int count = 1;
        for(int j=0; j<num; j++){
            TableRow row = new TableRow(getContext());
            row.setId(j);
            EditText editText = new EditText(getContext());
            editText.setId(j);
            //editText.setText("0");
            editText.setHint("  X"+count+"  ");
            editText.setEnabled(false);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            editText.setGravity(Gravity.CENTER_HORIZONTAL);
            //editText.setWidth(170);
            row.addView(editText);
            vectorX.addView(row);
            count++;
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

    public void luFactorization(double [][] A, double [] b) {
        luFactorizationWithPivoting(A,b);
        double [][] Lz = augmentMatrix(L, b);
        double [] z = progressiveSubstitution(L, b, L.length);
        double [][] Uz = augmentMatrix(U, z);
        double [] x = regressiveSubstitution(Uz, Uz.length);
        System.out.println("\n Lz");
        for (int v = 0; v < Lz.length; v++) {
            System.out.print("[");
            for (int m = 0; m < Lz[v].length; m++) {
                System.out.print (Lz[v][m]);
                if (m != Lz[v].length-1) System.out.print("   ");
            }
            System.out.println("]");
        }
        System.out.println("\n Uz");
        for (int v = 0; v < Uz.length; v++) {
            System.out.print("[");
            for (int m = 0; m < Uz[v].length; m++) {
                System.out.print (Uz[v][m]);
                if (m != Uz[v].length-1) System.out.print("   ");
            }
            System.out.println("]");
        }
        System.out.println("\n Progressive Substitution: ");
        for (int i = 0; i < z.length; i++) {
            System.out.print(z[i] + " ");
        }
        // Escribe en el vector X Regressive Substitution
        for(int i = 0; i < x.length; i++) {
            TableRow row = (TableRow) vectorX.getChildAt(i);
            EditText ed = (EditText) row.getChildAt(0);
            ed.setEnabled(false);
            ed.setTextColor(getResources().getColor(R.color.colorAccent));
            ed.setText(String.format("%.3f", x[i])+"");
        }
    }

    public void luFactorizationWithPivoting(double [][] A, double [] b) {
        int n = A.length;
        L = new double[n][n];
        U = new double[n][n];
        double [] marks = new double[n];
        for (int i = 1; i < n; i++) {
            marks[i] = i;
        }
        for (int k = 0; k < n; k++) {
            partialPivoting(A, L, n, k, b);
            for (int i = k + 1; i < n; i++) {
                double m = (float)(A[i][k])/A[k][k];
                L[i][k] = m;
                for(int j = k; j < n; j++){
                    A[i][j] = A[i][j]-(m*A[k][j]);
                }
            }
        }
        for (int x=0; x < L.length; x++) {
            for (int y=0; y < L[x].length; y++) {
                if (x == y) {
                    L[x][y] = 1;
                }
            }
        }
        U = A;
        // Escribe L en matrixL
        for (int v = 0; v < L.length; v++) {
            TableRow row = new TableRow(getContext());
            row.setId(v);
            for (int m = 0; m < L[v].length; m++) {
                System.out.print (L[v][m]);
                EditText ed = new EditText(getContext());
                ed.setEnabled(false);
                ed.setTextColor(getResources().getColor(R.color.colorAccent));
                ed.setText(String.format("%.3f", L[v][m])+"");
                row.addView(ed);
                txtL.setText("L");
                txtL.setTextSize(30);
                //ab.setTextColor(getResources().getColor(R.color.colorAccent));
            }
            matrixL.addView(row);
        }

        matrixU.removeAllViews();
        // Escribe U en matrixU
        for (int v = 0; v < U.length; v++) {
            TableRow row = new TableRow(getContext());
            row.setId(v);
            for (int m = 0; m < U[v].length; m++) {
                System.out.print (U[v][m]);
                EditText ed = new EditText(getContext());
                ed.setEnabled(false);
                ed.setTextColor(getResources().getColor(R.color.colorAccent));
                ed.setText(String.format("%.3f", U[v][m])+"");
                row.addView(ed);
                txtU.setText("U");
                txtU.setTextSize(30);
                //ab.setTextColor(getResources().getColor(R.color.colorAccent));
            }
            matrixU.addView(row);
        }
    }

    public void partialPivoting(double [][] A, double [][] L, int n, int k, double [] b) {
        double maxi = Math.abs(A[k][k]);
        int maxiRow = k;
        for(int s = k; s < n; s++) {
            if(Math.abs(A[s][k]) > maxi) {
                maxi = Math.abs(A[s][k]);
                maxiRow = s;
            }
        }
        if (maxi == 0) {
            System.out.println("The system has no solution");
        } else {
            if(maxiRow != k) {
                A = exchangeRows(A, maxiRow, k);
                L = exchangeRows(L, maxiRow, k);
                exchangeMarks(b, maxiRow, k);
            }
        }
    }

    public double[][] exchangeRows(double [][]A, int maxiRow, int k) {
        double aux;
        for(int i = 0; i < A[0].length; i++) {
            aux = A[k][i];
            A[k][i] = A[maxiRow][i];
            A[maxiRow][i] = aux;
        }
        return A;
    }

    public double[] exchangeMarks(double [] marks, int maxiCol, int k) {
        double aux = marks[k];
        marks[k] = marks[maxiCol];
        marks[maxiCol] = aux;
        return marks;
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

    public double[] progressiveSubstitution(double L[][], double b[], int n) {
        double[] x = new double[n];
        for(int i = 0; i < n; i++) {
            double sum = 0;
            for(int p = 0; p < n; p++) {
                sum += (L[i][p] * x[p]);
            }
            x[i] = (b[i] - sum) / (double)(L[i][i]);
        }
        return x;
    }
}
