package com.example.andresavendano.analytica;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.udojava.evalex.Expression;

public class GraphActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        GraphView graph = findViewById(R.id.graph);

        String[] functions = getIntent().getExtras().getStringArray("function");
        for(int j =0;j<2;j++) {
            double x=-50;
            LineGraphSeries<DataPoint> series= new LineGraphSeries<DataPoint>();
            String function=functions[j];
            Expression expression = new Expression(function);

            for (int i = 0; i < 1000; i++) {
                x = x + 0.1;
                try {
                    double y = expression.setVariable("x", String.valueOf(x)).eval().doubleValue();
                    series.appendData(new DataPoint(x, y), true, 500000);
                } catch (Exception e) {
                    System.out.print(e);
                }
            }
            if(j==0) {
                series.setColor(Color.BLUE);
            }
            else if(j==1){
                series.setColor(Color.RED);
            }
            else{
                series.setColor(Color.GREEN);
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
            graph.getViewport().setScalableY(true);
            graph.addSeries(series);
        }
    }
}
