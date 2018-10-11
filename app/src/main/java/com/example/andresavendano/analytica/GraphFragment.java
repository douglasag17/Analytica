package com.example.andresavendano.analytica;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.udojava.evalex.*;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class GraphFragment extends Fragment{
    LineGraphSeries<DataPoint> series;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_graph,null);
        final TextInputEditText text=v.findViewById(R.id.lol);
        final TextView tv= v.findViewById(R.id.tv);
        Button button= (Button) v.findViewById(R.id.button);
        final GraphView graph= (GraphView)v.findViewById(R.id.graph);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String function= text.getText().toString();

                if(checkSyntax(function)==true){
                    tv.setText(function);

                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                            new DataPoint(0, 1),
                            new DataPoint(1, 5),
                            new DataPoint(2, 3)
                    });
                    graph.addSeries(series);
                }

            }
        });

        return v;
    }

    private boolean checkSyntax(String function) {
        try {
            new Expression(function).with("x", "0").eval();

        } catch (Expression.ExpressionException e) {
            return false;
        } catch (Exception e) {
            return true;
        }
        return true;
    }
}
