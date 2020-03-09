package com.capcenter.ec.myprofitbalance.ui;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.capcenter.ec.myprofitbalance.R;
import com.capcenter.ec.myprofitbalance.io.ConexionSQLiteHelper;
import com.capcenter.ec.myprofitbalance.io.Utilidades;
import com.capcenter.ec.myprofitbalance.model.Ingreso;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class listaIngresos extends AppCompatActivity {
    ConexionSQLiteHelper conn;
    ListView lvingresos;
    ArrayList<Ingreso> listaingresos;
    ArrayList<String> listainformacionIngreso;
    ArrayList<Double> listaMontoTransaccionesGrafica;
    ArrayList<String> listaRangoTransaccionesFechaGrafica;
    ArrayList ListaColorGraficas;
    Integer tipotran;
    Map<Integer, Integer> mapLista = new HashMap<Integer, Integer>();
    Integer posisionLista;
    private PieChart piechart;
    private BarChart barchart;
    //private String []months= new String[]{"Enero","Febrero","Marzo","Abril","Mayo"};
   // private int[]sale = new int[]{25,20,38,40,70};
    public String [] months;
    public Double [] sale;
    public int[] colors; // = new int[]{Color.RED, Color.BLACK,Color.BLUE,Color.GREEN,Color.LTGRAY,Color.YELLOW};

    private Chart getSameChart(Chart chart,String descripcion,int textColor,int background,int AnimateY){
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
        barchart =(BarChart) getSameChart(barchart,"Series",Color.RED,Color.CYAN,3000);
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_ingresos);




        //conn =new ConexionSQLiteHelper(getApplicationContext(),Utilidades.NOMBRE_BD,null,1);
        lvingresos =(ListView) findViewById(R.id.lvreportes);
        Bundle bundle = getIntent().getExtras();
        //tipotran=bundle.getInt("tipoper");
        tipotran=1;
        consultarListaIngresos();
        months = listaRangoTransaccionesFechaGrafica.toArray(new String[listaRangoTransaccionesFechaGrafica.size()]);
        sale =  listaMontoTransaccionesGrafica.toArray(new Double[listaMontoTransaccionesGrafica.size()]);
        colors = new int[ListaColorGraficas.size()];

         for (int i = 0;i < ListaColorGraficas.size(); i++) {
             //Random rnd = new Random();
             //int colori = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            colors[i] = Color.RED;

         }


        // Carga Graficas
        barchart =(BarChart) findViewById(R.id.Barchart);
        piechart =(PieChart) findViewById(R.id.Piechart);
        createCharts();

        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1,listainformacionIngreso);
        lvingresos.setAdapter(adaptador);




        lvingresos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "ee transacion:"+tipotran, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), transaccionesActivity.class);
                long v =lvingresos.getSelectedItemId();//getSelectedItemPosition();
                //int  numoper= i+1;
                int  numoper=0;

                Iterator it = mapLista.keySet().iterator();
                while(it.hasNext()){
                    Integer key = (Integer) it.next();
                    if (key ==i){
                        numoper = mapLista.get(key);
                    }
                    Log.d("valor del arreglo",key.toString());
                    // System.out.println("Clave: " + key + " -> Valor: " + mapL.get(key));
                }
                intent.putExtra("tipoper",tipotran);
                intent.putExtra("numoper",numoper);
                startActivity(intent);


            }
        });
    }
    private void consultarListaIngresos(){
        try {
            conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.NOMBRE_BD, null, 1);
            SQLiteDatabase db = conn.getReadableDatabase();

            Ingreso ingreso = null;
            listaingresos = new ArrayList<Ingreso>();

            //Cursor cursor = db.rawQuery("Select * from " + Utilidades.TABLA_OPERACIONES + " WHERE " + Utilidades.CAMPO_TIPO_OPER + "='INGRESOS'", null);
            Cursor cursor = db.rawQuery("Select * from " + Utilidades.TABLA_OPERACIONES + " WHERE " + Utilidades.CAMPO_TIPO_OPER + "="+tipotran+" ", null);



            while (cursor.moveToNext()) {
                ingreso = new Ingreso();
                ingreso.setId(cursor.getInt(0));
                ingreso.setFecha(cursor.getString(1));
                ingreso.setMonto_operacion(cursor.getDouble(5));
                listaingresos.add(ingreso);
                mapLista.put(cursor.getPosition(),cursor.getInt(0));

            }

            obtenerLista();
        }catch (SQLiteException | IllegalStateException | NullPointerException e) {
            e.printStackTrace();
        }
    }
    private void obtenerLista(){
        listainformacionIngreso =new ArrayList<String>();
        listaMontoTransaccionesGrafica =new ArrayList<Double>();
        listaRangoTransaccionesFechaGrafica = new ArrayList<String>();
        ListaColorGraficas = new ArrayList<Integer>();
        for (int i=0; i<listaingresos.size();i++){
            listainformacionIngreso.add(listaingresos.get(i).getId()+" - "
                    +listaingresos.get(i).getFecha()+"                                 "
                    +listaingresos.get(i).getMonto_operacion()+"$"
            );
            listaRangoTransaccionesFechaGrafica.add(listaingresos.get(i).getFecha());
            listaMontoTransaccionesGrafica.add(listaingresos.get(i).getMonto_operacion());
            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            ListaColorGraficas.add(color);

        }
    }

}
