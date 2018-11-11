package com.example.andresavendano.analytica;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class StepsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        ArrayList<Double[][]> myList = (ArrayList<Double[][]>) getIntent().getSerializableExtra("stepsMatrix");
        //System.out.println(myList.size());
        LinearLayout linear = findViewById(R.id.linearLay);
        for(int i =0;i<myList.size();i++) {
            TableLayout table = new TableLayout(this);
            TableLayout iteration = new TableLayout(this);
            Double matrix[][] = myList.get(i);
            for(int j = 0;j<matrix.length;j++){
                TableRow row = new TableRow(this);
                System.out.println();
                for(int k =0;k<matrix[0].length;k++) {
                    EditText et= new EditText(this);
                    DecimalFormat f = new DecimalFormat("####.####");
                    et.setText(f.format(matrix[j][k])+" ");
                    et.setGravity(1);
                    et.setEnabled(false);
                    et.setTextColor(Color.BLACK);
                    row.addView(et);
                    System.out.print(matrix[j][k]);
                }
                table.addView(row);
            }
            TableRow rowIteration= new TableRow(this);
            TextView number=new TextView(this);
            number.setText("Step: "+(i+1));
            rowIteration.addView(number);
            iteration.addView(rowIteration);
            linear.addView(iteration);
            linear.addView(table);
        }


    }

}
