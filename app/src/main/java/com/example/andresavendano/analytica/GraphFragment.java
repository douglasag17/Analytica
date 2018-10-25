package com.example.andresavendano.analytica;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.udojava.evalex.*;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class GraphFragment extends Fragment{
    LineGraphSeries<DataPoint> series;
    int numCores = Runtime.getRuntime().availableProcessors();
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View inflatedView =inflater.inflate(R.layout.fragment_graph,null);
        
        final TextInputEditText text=inflatedView.findViewById(R.id.lol);
        final TextView tv= inflatedView.findViewById(R.id.tv);
        Button button= (Button) inflatedView.findViewById(R.id.button);
        final GraphView graph= (GraphView)inflatedView.findViewById(R.id.graph);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                String function= text.getText().toString();
                Thread[] cores = new Thread[numCores];
                HiloChart[] values = new HiloChart[numCores];
                tv.setText(Integer.toString(numCores));
                
                if(checkSyntax(function)==true){
                    int inicio =-200;
                    int cuanto = 400 / numCores;
                    List<LineGraphSeries<DataPoint>> listSeries = new LinkedList<>();
                    for(int num=0;num<numCores;num++) {
                        if (values[num]==null) {
                            //crear eeedd para guardar los hilos y lo otro
                            values[num] = new HiloChart( function, inicio, inicio + cuanto);
                            inicio = inicio + cuanto;
                            cores[num] = new Thread(values[num]);
                            cores[num].start();
                        } else {
                            try {
                                cores[num].join();
                                listSeries.add(values[num].getSeries());
                                //do another serie
                                values[num] = new HiloChart(function,inicio, inicio+cuanto);
                                inicio = inicio+cuanto;
                                cores[num] = new Thread(values[num]);
                                cores[num].start();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    for(int i = 0; i<numCores;i++){
                        if(cores[i]!=null){
                            try{
                                cores[i].join();
                                listSeries.add(values[i].getSeries());
                            }
                            catch (InterruptedException e){
                                e.printStackTrace();
                            }
                        }
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
                    for(int num=0;num<numCores;num++){
                        //GraphHiloChart gafi= new GraphHiloChart(listSeries.get(num),inflatedView);
                        //new Thread(gafi).start();
                        graph.addSeries(listSeries.get(num));
                    }
                }
                
            }
        });
        
        
        return inflatedView;
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
    
    private class HiloChart implements Runnable{
        private String function;
        private double x;
        private double end;
        private LineGraphSeries<DataPoint> series;
        
        
        public HiloChart(String function,double x, double end) {
            this.function=function;
            this.x=x;
            this.end=end;
            
        }
        
        @Override
        public void run() {
            //do something
            series = new LineGraphSeries<DataPoint>();
            Expression expression = new Expression(function);
            while(this.x<this.end) {
                x = x + 0.1;
                try{
                    double y = expression.setVariable("x", String.valueOf(x)).eval().doubleValue();
                    series.appendData(new DataPoint(x, y), true, 500000);
                }
                catch (Exception e){
                    System.out.print(e);
                }
                
            }
        }
        LineGraphSeries<DataPoint> getSeries() {
            return series;
        }
    }
    private class GraphHiloChart implements Runnable{
        
        private LineGraphSeries serie;
        private View v;
        
        
        public GraphHiloChart(LineGraphSeries serie, View vie) {
            this.serie=serie;
            this.v=vie;
        }
        
        @Override
        public void run() {
            //do something
            GraphView graph= (GraphView)v.findViewById(R.id.graph);
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
            graph.addSeries(serie);
        }
    }
}

