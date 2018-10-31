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
import android.widget.Toast;


public class FragSimpleGaussian extends Fragment {
    int num=3;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflaterView=inflater.inflate(R.layout.frag_simple_gaussian, container, false);
        final TableLayout table = inflaterView.findViewById(R.id.tableGauss);
        createTable(inflaterView);
        Button button = inflaterView.findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text= "";
                for(int j = 0;j<num;j++) {
                    TableRow row = (TableRow) table.getChildAt(j);
                    for (int i = 0; i < num; i++) {

                        EditText txt = (EditText) row.getChildAt(i);
                        txt.getText().toString();

                        text = text + txt.getText().toString();
                    }
                }
                Toast.makeText(getContext(),text, Toast.LENGTH_LONG).show();
            }
        });
        Button button3 = inflaterView.findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num++;
                TableRow edit[]=new TableRow[num];
                for(int i = 0;i<num-1;i++){
                    TableRow row = (TableRow) table.getChildAt(i);
                    edit[i]=row;
                }
                table.removeAllViews();

                for(int j =0;j<num;j++){
                    if(j<num-1) {
                        TableRow row = edit[j];
                        EditText editText = new EditText(getContext());
                        editText.setText("0");
                        row.addView(editText);
                        table.addView(row);
                    }
                    else{
                        TableRow row = new TableRow(getContext());
                        for(int i=0;i<num;i++){
                            EditText editText = new EditText(getContext());
                            editText.setText("0");
                            row.addView(editText);
                        }
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
                TableRow edit[]=new TableRow[num+1];
                for(int i = 0;i<num+1;i++){
                    TableRow row = (TableRow) table.getChildAt(i);
                    edit[i]=row;
                }
                table.removeAllViews();
                for(int j =0;j<num;j++){
                    TableRow row = edit[j];
                    row.removeViewAt(num);
                    table.addView(row);
                }
            }
        });
        return inflaterView;
    }
    public void createTable(View inflaterView){
        TableLayout table = inflaterView.findViewById(R.id.tableGauss);
        for(int j =0;j<num;j++){
            TableRow row = new TableRow(getContext());
            row.setId(j);
            for(int i = 0;i<num;i++) {
                EditText editText = new EditText(getContext());
                editText.setId(i+j);
                editText.setText("0");
                row.addView(editText);
            }
            table.addView(row);
        }
    }

}