package com.example.andresavendano.analytica;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class FragFalsePosition extends Fragment {
    private TableLayout table;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_falseposition,container,false);

        //table
        table = v.findViewById(R.id.tableFR);

        for (int i = 0; i < 5; i++) {
            TableRow fila = new TableRow(getContext());
            TextView tv_col1 = new TextView(getContext());
            tv_col1.setId(R.id.iterationsTable);
            tv_col1.setText("1   ");
            TextView tv_col2 = new TextView(getContext());
            tv_col2.setId(R.id.xnTable);
            tv_col2.setText("0.2222   ");
            TextView tv_col3 = new TextView(getContext());
            tv_col3.setId(R.id.evalTable);
            tv_col3.setText("3.277   ");
            TextView tv_col4 = new TextView(getContext());
            tv_col4.setId(R.id.errorTable);
            tv_col4.setText("0.000003   ");

            fila.addView(tv_col1);
            fila.addView(tv_col2);
            fila.addView(tv_col3);
            fila.addView(tv_col4);
            table.addView(fila);
        }

        return v;
    }
}