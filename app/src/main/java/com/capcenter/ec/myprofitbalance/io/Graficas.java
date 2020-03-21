package com.capcenter.ec.myprofitbalance.io;

import android.graphics.Color;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;

public class Graficas {

    private PieChart piechart;
    private BarChart barchart;
    //private String []months= new String[]{"Enero","Febrero","Marzo","Abril","Mayo"};
    // private int[]sale = new int[]{25,20,38,40,70};
    public String [] months;
    public Double [] sale;
    public int[] colors;

    private Chart getSameChart(Chart chart, String descripcion, int textColor, int background, int AnimateY){
        chart.getDescription().setText("Ventas");
        chart.getDescription().setEnabled(false);
        chart.getDescription().setTextSize(15);
        chart.setBackgroundColor(background);
        chart.animateY(AnimateY);
        legend(chart);
        return chart;
    }
    private void legend(Chart chart){
        Legend legend = chart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

        ArrayList<LegendEntry> entries =new ArrayList<>();
        for (int i=0; i < months.length;i++){
            LegendEntry entry= new LegendEntry();
            entry.formColor=colors[i];
            entry.label=months[i];
            entries.add(entry);
        }
        legend.setCustom(entries);
    }
    private ArrayList<BarEntry>getBarEntries(){
        ArrayList<BarEntry> entries =new ArrayList<>();

        for (int i=0; i < sale.length;i++) {
            entries.add(new BarEntry(i,sale[i].intValue()));
        }
        return entries;
    }
    private ArrayList<PieEntry>getPieEntries(){
        ArrayList<PieEntry> entries =new ArrayList<>();

        for (int i=0; i < sale.length;i++) {
            entries.add(new PieEntry( sale[i].intValue()));
        }
        return entries;
    }
    private void axisX(XAxis xaxis){
        xaxis.setGranularityEnabled(true);
        xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxis.setValueFormatter(new IndexAxisValueFormatter(months));

    }
    private void axisLeft(YAxis axis){
        axis.setSpaceTop(30);
        axis.setAxisMinimum(0);

    }
    private void axisRight(YAxis axis){
        axis.setEnabled(false);

    }
    public void createCharts(){
        barchart =(BarChart) getSameChart(barchart,"Series", Color.RED,Color.CYAN,3000);
        barchart.setDrawGridBackground(true);
        barchart.setDrawBarShadow(true);
        barchart.setData(getBarData());
        barchart.invalidate();
        axisX(barchart.getXAxis());
        axisLeft(barchart.getAxisLeft());
        axisRight(barchart.getAxisRight());

        piechart =(PieChart) getSameChart(piechart,"Ventas",Color.GRAY,Color.WHITE,3000);
        piechart.setHoleRadius(60);
        piechart.setTransparentCircleRadius(12);
        piechart.setData(getPieData());
        piechart.invalidate();
    }
    private DataSet getData(DataSet dataset){
        dataset.setColors(colors);
        dataset.setValueTextSize(Color.WHITE);
        dataset.setValueTextSize(10);
        return dataset;
    }
    private BarData getBarData(){
        BarDataSet barDataSet=(BarDataSet) getData(new BarDataSet(getBarEntries(),""));

        barDataSet.setBarShadowColor(Color.GRAY);
        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.45f);
        return barData;
    }
    private PieData getPieData(){
        PieDataSet pieDataSet=(PieDataSet) getData(new PieDataSet(getPieEntries(),""));

        pieDataSet.setSliceSpace(2);// getSliceSpace(2);
        pieDataSet.setValueFormatter(new PercentFormatter());

        return new PieData(pieDataSet);
    }

}
