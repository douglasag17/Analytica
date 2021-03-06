package com.example.andresavendano.analytica;

import android.app.AlertDialog;
import android.content.Intent;
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

import java.util.ArrayList;

public class FragSimpleGaussian extends Fragment {
    int num = 3;
    double A [][];
    double b [];
    private TableLayout table;
    private TableLayout vectorBB;
    private TableLayout vectorX;
    private TableLayout matrixAb;
    private TextView ab;
    private TextView t;
    private ArrayList<Double[][]> stepsMatrix = new ArrayList<Double[][]>();

    private boolean isError;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflaterView=inflater.inflate(R.layout.frag_simple_gaussian, container, false);
        final View helpView = inflater.inflate(R.layout.help_bisection,  (ViewGroup) getView(), false);
        table = inflaterView.findViewById(R.id.tableGauss);
        vectorBB = inflaterView.findViewById(R.id.vectorB);
        vectorX = inflaterView.findViewById(R.id.vectorX);
        matrixAb = inflaterView.findViewById(R.id.matrixAb);
        ab = inflaterView.findViewById(R.id.Ab);
        createTable(inflaterView);
        Button button = inflaterView.findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isError = false;
                try {
                    A = getMatrixA();
                    b = getVectorB();
                    simpleGaussianElimination(A, b,helpView);
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
        final Button steps = inflaterView.findViewById(R.id.butEtapas);
        steps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(v.getContext(), StepsActivity.class);
                    intent.putExtra("stepsMatrix", stepsMatrix);
                    startActivityForResult(intent, 0);
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
                        editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
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
                        editTextB.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                        editTextB.setGravity(Gravity.CENTER_HORIZONTAL);
                        editTextX.setHint("  X"+num+"  ");
                        editTextX.setEnabled(false);
                        editTextX.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                        editTextX.setGravity(Gravity.CENTER_HORIZONTAL);
                        //editTextB.setWidth(170);
                        rowB.addView(editTextB);
                        rowX.addView(editTextX);
                        for(int i=0;i<num;i++){
                            EditText editText = new EditText(getContext());
                            //editText.setText("0");
                            editText.setHint("  0  ");
                            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
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
                t.setText("The method of gaussian elimination is used to solve systems of linear " +
                        "equations. This method has to steps. First it converts the original matrix " +
                        "to another equivalent trought a serie of transformations, this matrix is" +
                        " called the scalonated matrix,  this matrix is also an lower triangular " +
                        "matrix. And the final step is to replace variables from the last row to " +
                        "the first one.\n" +
                        "If the system has 0's in the diagonal and the elements under the diagonal are 0's" +
                        "in the same column, then it has infinity solutions.\n");
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
                editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
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
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
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

    public void simpleGaussianElimination(double [][] A, double [] b, View helpView) {
        Double [][] U = scallingMatrix(A, b, A.length);
        int B = U.length;
        double x[] = regressiveSubstitution(U, B);
        // Escribe en el vector X Regressive Substitution
        for(int i = 0; i < x.length; i++) {
            TableRow row = (TableRow) vectorX.getChildAt(i);
            EditText ed = (EditText) row.getChildAt(0);
            ed.setEnabled(false);
            ed.setTextColor(getResources().getColor(R.color.colorAccent));
            if (isError) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                if (helpView.getParent() != null)
                    ((ViewGroup) helpView.getParent()).removeView(helpView);
                builder.setView(helpView);
                double[][] matrix = diagonalDomiante(A);
                String text="The matrix is not diagonally dominant, try with this one.\n";
                    for(int j = 0;j<matrix.length;j++){
                        for(int k =0;k<matrix.length;k++){
                            text=text+matrix[j][k] + "  ";
                        }
                        text=text+"\n";
                    }
                t.setText(text);

                t.setTextSize(25);
                builder.show();
                break;
            } else {
                ed.setText(String.format("%.3f", x[i]) + "");
            }
        }
    }
    public double[][] diagonalDomiante(double m[][]){
        for(int i = 0; i < m.length;i++){
            double sum = getSum(i, m);
            int index = extractGreater(i, m);
            if(m[i][index] > (sum-m[i][index])){
                double aux = m[i][i];
                m[i][i] = m[i][index];
                m[i][index] = aux;
            }else{
                Toast toast = Toast.makeText(getContext(),"This matrix can't be convert to diagonally dominant", Toast.LENGTH_LONG);
                View view = toast.getView();
                TextView text = (TextView) view.findViewById(android.R.id.message);
                text.setTextColor(Color.BLACK);
                text.setGravity(1);
                view.setBackgroundColor(Color.parseColor("#B3E5FE"));
                toast.show();
            }
        }
        return m;
    }

    public int extractGreater(int i, double m[][]){
        double greater = 0;
        int index = 0;
        for(int j = 0; j < m[0].length; j++){
            if(m[i][j] > greater){
                greater = m[i][j];
                index = j;
            }
        }
        return index;
    }

    public double getSum(int i, double m[][]){
        double sum = 0;
        for(int j = 0; j < m[0].length; j++){
            sum+=Math.abs(m[i][j]);
        }
        return sum;
    }
    public Double[][] scallingMatrix(double A[][], double b[], int n){
        stepsMatrix.clear();
        double det = det(A);
        if(det == 0) {
            isError = true;
            Toast toast = Toast.makeText(getContext(),"The matrix you entered can not be invertible", Toast.LENGTH_LONG);
            View view = toast.getView();
            TextView text = (TextView) view.findViewById(android.R.id.message);
            text.setTextColor(Color.BLACK);
            text.setGravity(1);
            view.setBackgroundColor(Color.parseColor("#B3E5FE"));
            toast.show();
        }
        Double [][] Ab = augmentMatrix(A, b);
        for (int i = 0; i < A.length; i++) {
            if(A[i][i] == 0){
                double a[] = new double[A[0].length];
                System.arraycopy(A[i], 0, a, 0, A[0].length); // a = Ab[i]
                for (int j = 1; j < A.length; j++) {
                    if(A[j][i] != 0){
                        System.arraycopy(A[j], 0, A[i], 0, A[0].length); //Ab[i] = Ab[j]
                        System.arraycopy(a, 0, A[j], 0, A[0].length); //Ab[j] = a
                        break;
                    }

                }
            }
        }
        for(int k = 0; k < n-1; k++){
            for(int i = k+1; i < n; i++){
                double m = (Ab[i][k])/Ab[k][k];
                if(!isError) {
                    if (Ab[k][k] == 0) {
                        isError = true;
                        Toast toast = Toast.makeText(getContext(), "Division by 0, check the matrix", Toast.LENGTH_LONG);
                        View view = toast.getView();
                        TextView text = (TextView) view.findViewById(android.R.id.message);
                        text.setTextColor(Color.BLACK);
                        text.setGravity(1);
                        view.setBackgroundColor(Color.parseColor("#B3E5FE"));
                        toast.show();
                    }
                }
                for(int j = 0; j < n+1; j++){
                    Ab[i][j] = Ab[i][j]-(m*Ab[k][j]);
                }
            }
            Double[][] matrixCopy = copy(Ab);
            stepsMatrix.add(matrixCopy);
        }
        matrixAb.removeAllViews();
        // Escribe Ab es matrixAb
        for (int v = 0; v < Ab.length; v++) {
            TableRow row = new TableRow(getContext());
            row.setId(v);
            for (int m = 0; m < Ab[v].length; m++) {
                System.out.print (Ab[v][m]);
                EditText ed = new EditText(getContext());
                ed.setEnabled(false);
                ed.setTextColor(getResources().getColor(R.color.colorAccent));
                ed.setText(String.format("%.3f", Ab[v][m])+"");
                row.addView(ed);
                ab.setText("Ab");
                ab.setTextSize(30);
                //ab.setTextColor(getResources().getColor(R.color.colorAccent));
            }
            if(!isError) {
                matrixAb.addView(row);
            } else {
                matrixAb.removeView(row);
            }
        }
        return Ab;
    }

    public static double det(double[][] A) {
        double det;
        if(A.length == 2) {
            det = (A[0][0]*A[1][1])-(A[1][0]*A[0][1]);
            return det;
        }
        double suma = 0;
        for(int i = 0; i < A.length; i++){
            double[][] nm = new double[A.length-1][A.length-1];
            for(int j = 0; j< A.length; j++){
                if(j != i){
                    for(int k = 1; k < A.length; k++){
                        int indice = -1;
                        if (j < i) {
                            indice = j;
                        } else if(j > i) {
                            indice = j - 1;
                        }
                        nm[indice][k-1]=A[j][k];
                    }
                }
            }
            if(i%2==0)
                suma+=A[i][0] * det(nm);
            else
                suma-=A[i][0] * det(nm);
        }
        System.out.println(suma);
        return suma;
    }

    public Double[][] augmentMatrix(double A[][], double b[]){
        Double result[][] = new Double[A.length][A.length+1];
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

    public double[] regressiveSubstitution(Double Ab[][], int n){
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

    public Double[][] copy(Double[][] Ab){
        Double[][] matrix = new Double[Ab.length][Ab[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = Ab[i][j];
            }
        }
        return matrix;
    }
}