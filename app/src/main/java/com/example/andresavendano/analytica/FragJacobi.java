package com.example.andresavendano.analytica;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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


public class FragJacobi extends Fragment {
    int num = 3;
    double A [][];
    double b [];
    double x0 [];
    private TableLayout table;
    private TableLayout vectorBB;
    private TableLayout vectorX;
    private TableLayout vectorX0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflaterView = inflater.inflate(R.layout.frag_jacobi, container, false);
        table = inflaterView.findViewById(R.id.tableGauss);
        vectorBB = inflaterView.findViewById(R.id.vectorB);
        vectorX = inflaterView.findViewById(R.id.vectorX);
        vectorX0 = inflaterView.findViewById(R.id.vectorX0);
        createTable(inflaterView);
        Button button = inflaterView.findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    A = getMatrixA();
                    b = getVectorB();
                    x0 = getVectorX0();
                    //simpleGaussianElimination(A, b);
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
                TableRow edit4[]=new TableRow[num];
                for(int i = 0;i<num-1;i++){
                    TableRow row = (TableRow) table.getChildAt(i);
                    TableRow rowB = (TableRow) vectorBB.getChildAt(i);
                    TableRow rowX = (TableRow) vectorX.getChildAt(i);
                    TableRow rowX0 = (TableRow) vectorX0.getChildAt(i);
                    edit[i]=row;
                    edit2[i]=rowB;
                    edit3[i]=rowX;
                    edit4[i]=rowX0;
                }
                table.removeAllViews();
                vectorBB.removeAllViews();
                vectorX.removeAllViews();
                vectorX0.removeAllViews();
                for(int j=0; j<num; j++){
                    if(j<num-1) {
                        TableRow row = edit[j];
                        TableRow rowB = edit2[j];
                        TableRow rowX = edit3[j];
                        TableRow rowX0 = edit4[j];
                        EditText editText = new EditText(getContext());
                        //editText.setText("0");
                        editText.setHint("  0  ");
                        editText.setGravity(Gravity.CENTER_HORIZONTAL);
                        //editText.setWidth(170);
                        row.addView(editText);
                        table.addView(row);
                        vectorBB.addView(rowB);
                        vectorX.addView(rowX);
                        vectorX0.addView(rowX0);
                    } else {
                        TableRow row = new TableRow(getContext());
                        TableRow rowB = new TableRow(getContext());
                        TableRow rowX = new TableRow(getContext());
                        TableRow rowX0 = new TableRow(getContext());
                        EditText editTextB = new EditText(getContext());
                        EditText editTextX = new EditText(getContext());
                        EditText editTextX0 = new EditText(getContext());
                        //editTextB.setText("0");
                        editTextB.setHint("  0  ");
                        editTextB.setGravity(Gravity.CENTER_HORIZONTAL);
                        editTextX0.setHint("  0  ");
                        editTextX0.setGravity(Gravity.CENTER_HORIZONTAL);
                        editTextX.setHint("  X"+num+"  ");
                        editTextX.setGravity(Gravity.CENTER_HORIZONTAL);
                        //editTextB.setWidth(170);
                        rowB.addView(editTextB);
                        rowX.addView(editTextX);
                        rowX0.addView(editTextX0);
                        for(int i=0;i<num;i++){
                            EditText editText = new EditText(getContext());
                            //editText.setText("0");
                            editText.setHint("  0  ");
                            editText.setGravity(Gravity.CENTER_HORIZONTAL);
                            //editText.setWidth(170);
                            row.addView(editText);
                        }
                        vectorBB.addView(rowB);
                        vectorX.addView(rowX);
                        vectorX0.addView(rowX0);
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
                TableRow editX0[] = new TableRow[num+1];
                for(int i = 0;i<num+1;i++){
                    TableRow row = (TableRow) table.getChildAt(i);
                    TableRow rowB = (TableRow) vectorBB.getChildAt(i);
                    TableRow rowX = (TableRow) vectorX.getChildAt(i);
                    TableRow rowX0 = (TableRow) vectorX0.getChildAt(i);
                    edit[i]=row;
                    editB[i]=rowB;
                    editX[i]=rowX;
                    editX0[i]=rowX0;
                }
                table.removeAllViews();
                vectorBB.removeAllViews();
                vectorX.removeAllViews();
                vectorX0.removeAllViews();
                for(int j =0;j<num;j++){
                    TableRow row = edit[j];
                    TableRow rowB = editB[j];
                    TableRow rowX = editX[j];
                    TableRow rowX0 = editX0[j];
                    row.removeViewAt(num);
                    vectorBB.addView(rowB);
                    vectorX.addView(rowX);
                    table.addView(row);
                    vectorX0.addView(rowX0);
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
                editText.setHint("  0  ");
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
            editText.setGravity(Gravity.CENTER_HORIZONTAL);
            //editText.setWidth(170);
            row.addView(editText);
            vectorB.addView(row);
        }

        TableLayout vectorX0 = inflaterView.findViewById(R.id.vectorX0);
        for(int j=0; j<num; j++){
            TableRow row = new TableRow(getContext());
            row.setId(j);
            EditText editText = new EditText(getContext());
            editText.setId(j);
            //editText.setText("0");
            editText.setHint("  0  ");
            editText.setGravity(Gravity.CENTER_HORIZONTAL);
            //editText.setWidth(170);
            row.addView(editText);
            vectorX0.addView(row);
        }

        TableLayout vectorX = inflaterView.findViewById(R.id.vectorX);
        int count = 1;
        for(int j=0; j<num; j++){
            TableRow row = new TableRow(getContext());
            row.setId(j);
            EditText editText = new EditText(getContext());
            editText.setEnabled(false);
            editText.setId(j);
            //editText.setText("0");
            editText.setHint("  X"+count+"  ");
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

    public double[] getVectorX0(){
        int n = vectorX0.getChildCount();
        x0 = new double [n];
        for(int i=0; i<n; i++) {
            TableRow row = (TableRow) vectorX0.getChildAt(i);
            EditText f = (EditText) row.getChildAt(0);
            x0[i] = Double.valueOf(f.getText().toString());
        }
        return x0;
    }
}
