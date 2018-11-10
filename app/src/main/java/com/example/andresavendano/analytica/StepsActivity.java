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

import java.util.ArrayList;

public class StepsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        ArrayList<Double[][]> myList = (ArrayList<Double[][]>) getIntent().getSerializableExtra("mylist");

        LinearLayout linear = findViewById(R.id.linearLay);
        for(int i =0;i<myList.size();i++) {
            TableLayout table = new TableLayout(this);
            Double matrix[][] = myList.get(i);
            for(int j = 0;j<matrix.length;j++){
                TableRow row = new TableRow(this);
                System.out.println();
                for(int k =0;k<matrix[0].length;k++) {
                    TextView tv = new TextView(this);
                    tv.setText((" "+matrix[j][k]+" ").toString());
                    tv.setGravity(1);
                    row.addView(tv);
                    System.out.print(matrix[j][k]);
                }
                table.addView(row);
            }
            linear.addView(table);
        }


    }

}
