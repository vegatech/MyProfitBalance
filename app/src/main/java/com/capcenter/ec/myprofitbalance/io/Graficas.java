package com.capcenter.ec.myprofitbalance.io;

import android.graphics.Color;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
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

    private static PieChart piechart;
    private static BarChart barchart;
    private static HorizontalBarChart horizontalBarChart;
    //private String []months= new String[]{"Enero","Febrero","Marzo","Abril","Mayo"};
    // private int[]sale = new int[]{25,20,38,40,70};
    //Ingresos
    public static String [] months;
    public static Double [] sale;
    public static int[] colors;

    //Pie
    public static String [] leyendaPie;
    public static Double [] montoPie;
    public static int[] colorPie;
    //Egresos
    public static String [] monthsE;
    public static Double [] saleE;
    public static int[] colorsE;

    public static Chart getSameChartI(Chart chart, String descripcion, int textColor, int background, int AnimateY){

      /*  chart.getDescription().setText("Ventas");
         chart.getDescription().setEnabled(false);
        chart.getDescription().setTextSize(15);*/
        chart.setBackgroundColor(background);
        chart.animateY(AnimateY);
        legendI(chart);
        return chart;
    }
    public static Chart getSameChartE(Chart chart, String descripcion, int textColor, int background, int AnimateY){

      /*  chart.getDescription().setText("Ventas");
         chart.getDescription().setEnabled(false);
        chart.getDescription().setTextSize(15);*/
        chart.setBackgroundColor(background);
        chart.animateY(AnimateY);
        legendE(chart);
        return chart;
    }
    public static Chart getSameChartPie(Chart chart, String descripcion, int textColor, int background, int AnimateY){

      /*  chart.getDescription().setText("Ventas");
         chart.getDescription().setEnabled(false);
        chart.getDescription().setTextSize(15);*/
        chart.getDescription().setTextColor(Color.WHITE);
        chart.setBackgroundColor(background);
        chart.animateY(AnimateY);
        legendPie(chart);
        return chart;
    }
    public static  void legendI(Chart chart){
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
    public static  void legendE(Chart chart){
        Legend legend = chart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

        ArrayList<LegendEntry> entries =new ArrayList<>();
        for (int i=0; i < monthsE.length;i++){
            LegendEntry entry= new LegendEntry();
            entry.formColor=colorsE[i];
            entry.label=monthsE[i];
            entries.add(entry);
        }
        legend.setCustom(entries);
    }
    public static  void legendPie(Chart chart){
        Legend legend = chart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

        ArrayList<LegendEntry> entries =new ArrayList<>();
        for (int i=0; i < leyendaPie.length;i++){
            LegendEntry entry= new LegendEntry();
            entry.formColor=colorPie[i];
            entry.label=leyendaPie[i];
            entries.add(entry);
        }
        legend.setCustom(entries);
    }
    private static ArrayList<BarEntry>getBarEntriesI(){
        ArrayList<BarEntry> entries =new ArrayList<>();

        for (int i=0; i < sale.length;i++) {
            entries.add(new BarEntry(i,sale[i].intValue()));
        }
        return entries;
    }
    private static ArrayList<BarEntry>getBarEntriesE(){
        ArrayList<BarEntry> entries =new ArrayList<>();

        for (int i=0; i < saleE.length;i++) {
            entries.add(new BarEntry(i,saleE[i].intValue()));
        }
        return entries;
    }
    private static ArrayList<PieEntry>getPieEntries(){
        ArrayList<PieEntry> entries =new ArrayList<>();

        for (int i=0; i < montoPie.length;i++) {
            entries.add(new PieEntry( montoPie[i].intValue()));
        }
        return entries;
    }
    private static void axisX(XAxis xaxis){
        xaxis.setGranularityEnabled(true);
        xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxis.setValueFormatter(new IndexAxisValueFormatter(months));
        xaxis.setEnabled(false);
    }
    private static void axisLeft(YAxis axis){
        axis.setSpaceTop(30);
        axis.setAxisMinimum(0);

    }
    private static void axisRight(YAxis axis){
        axis.setEnabled(false);

    }
    public static void createCharts(BarChart barchartI,BarChart barchartE,PieChart piechart,HorizontalBarChart horizontalBarChart){
        Description desc=  new Description();
        desc.setEnabled(false);
        //barchart.setDescription("asd");

        barchartI =(BarChart) getSameChartI(barchartI,"Ingresos", Color.RED,Color.WHITE,3000);
        barchartI.setDrawGridBackground(true);
        barchartI.setDrawBarShadow(true);
        barchartI.setData(getBarDataI());
        barchartI.invalidate();
        axisX(barchartI.getXAxis());
        axisLeft(barchartI.getAxisLeft());
        axisRight(barchartI.getAxisRight());

        barchartE =(BarChart) getSameChartE(barchartE,"Egresos", Color.RED,Color.WHITE,3000);
        barchartE.setDrawGridBackground(true);
        barchartE.setDrawBarShadow(true);
        barchartE.setData(getBarDataE());
        barchartE.invalidate();
        axisX(barchartE.getXAxis());
        axisLeft(barchartE.getAxisLeft());
        axisRight(barchartE.getAxisRight());


        piechart.setDescription(desc);
        piechart =(PieChart) getSameChartPie(piechart,"Categorias",Color.GRAY,Color.WHITE,3000);
        piechart.setHoleRadius(60);
        piechart.setTransparentCircleRadius(12);
        piechart.setData(getPieData());
        piechart.invalidate();

        horizontalBarChart.setDescription(desc);
        horizontalBarChart = (HorizontalBarChart) getSameChartI(horizontalBarChart, "Series", Color.RED, Color.WHITE, 3000);
        horizontalBarChart.setDrawGridBackground(true);
        horizontalBarChart.setDrawBarShadow(true);
        horizontalBarChart.setData(getBarDataI());
        horizontalBarChart.invalidate();
        axisX(horizontalBarChart.getXAxis());
        axisLeft(horizontalBarChart.getAxisLeft());
        axisRight(horizontalBarChart.getAxisRight());
    }
    private static DataSet getData(DataSet dataset){
        dataset.setColors(colors);
        dataset.setValueTextSize(Color.WHITE);
        dataset.setValueTextSize(10);
        return dataset;
    }
    private static BarData getBarDataI(){
        BarDataSet barDataSet=(BarDataSet) getData(new BarDataSet(getBarEntriesI(),""));

        barDataSet.setBarShadowColor(Color.GRAY);
        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.45f);
        return barData;
    }
    private static BarData getBarDataE(){
        BarDataSet barDataSet=(BarDataSet) getData(new BarDataSet(getBarEntriesE(),""));

        barDataSet.setBarShadowColor(Color.GRAY);
        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.45f);
        return barData;
    }
    private static PieData getPieData(){
        PieDataSet pieDataSet=(PieDataSet) getData(new PieDataSet(getPieEntries(),""));

        pieDataSet.setSliceSpace(2);// getSliceSpace(2);
        pieDataSet.setValueFormatter(new PercentFormatter());

        return new PieData(pieDataSet);
    }

}
