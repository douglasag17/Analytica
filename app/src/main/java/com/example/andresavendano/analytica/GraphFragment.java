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

import java.math.BigDecimal;
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
                    double x, y;
                    x = -100.0;
                    series = new LineGraphSeries<DataPoint>();
                    Expression expression = new Expression(function);
                    for (int i = 0; i < 5000; i++) {
                        x = x + 0.1;
                            y = expression.setVariable("x", String.valueOf(x)).eval().doubleValue();

                            series.appendData(new DataPoint(x, y), true, 5000);
                            // set manual X bounds

                    }
                    graph.getViewport().setYAxisBoundsManual(true);
                    graph.getViewport().setMinY(-30);
                    graph.getViewport().setMaxY(30);

                    graph.getViewport().setXAxisBoundsManual(true);
                    graph.getViewport().setMinX(-30);
                    graph.getViewport().setMaxX(30);

                    // enable scaling and scrolling
                    graph.getViewport().setScrollable(true); // enables horizontal scrolling
                    graph.getViewport().setScrollableY(true); // enables vertical scrolling
                    graph.getViewport().setScalable(true); // enables horizontal zooming and scrolling
                    graph.getViewport().setScalableY(true); // enables vertical zooming and scrolling

                    graph.addSeries(series);
                }

            }
        });

        return v;
    }
    private LineGraphSeries graphit(String function){
        LineGraphSeries series= new LineGraphSeries();
        BigDecimal result = null;

        Expression expression = new Expression(function);
        for(int i=0;i<100;i++){
            result = expression.with("x",String.valueOf(i)).eval();
            series.appendData(new DataPoint(i, result.intValue()), true, 10000000);
        }
        return series;
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
