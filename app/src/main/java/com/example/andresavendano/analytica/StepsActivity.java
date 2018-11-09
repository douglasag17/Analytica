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

public class StepsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        double[] objeto = (double[]) getIntent().getExtras().getDoubleArray("parametro");
        LinearLayout linear = findViewById(R.id.linearLay);
        for(int i =0;i<objeto.length;i++) {
            TableLayout table = new TableLayout(this);
            for(int j = 0;j<1;j++){
                TableRow row = new TableRow(this);
                for(int k =0;k<3;k++) {
                    TextView tv = new TextView(this);
                    tv.setText(objeto[k]+"  ");
                    row.addView(tv);
                }
                table.addView(row);
            }
            linear.addView(table);
        }


    }

}
