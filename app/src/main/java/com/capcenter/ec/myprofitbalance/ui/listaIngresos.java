package com.capcenter.ec.myprofitbalance.ui;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.capcenter.ec.myprofitbalance.R;
import com.capcenter.ec.myprofitbalance.io.ConexionSQLiteHelper;
import com.capcenter.ec.myprofitbalance.io.Utilidades;
import com.capcenter.ec.myprofitbalance.model.Categoria;
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
    private String FechaRep, fechaDesde, fechaHasta;
    private String consultaSQL, transaccioneSQL;
    private int posicionSeleccionada;
    ListView lvingresos;
    ArrayList<Ingreso> listaingresos;
    ArrayList<String> listainformacionIngreso;
    ArrayList<Double> listaMontoTransaccionesGrafica;
    ArrayList<String> listaRangoTransaccionesFechaGrafica;
    ArrayList ListaColorGraficas;
    ArrayList<Categoria> listaCategorias;
    ArrayList<String> listaCat;
    Integer tipotran;
    Map<Integer, Integer> mapLista = new HashMap<Integer, Integer>();
    Integer posisionLista;
    BottomNavigationView mbotomnavigationviewR;
    private PieChart piechart;
    private BarChart barchart;
    //private String []months= new String[]{"Enero","Febrero","Marzo","Abril","Mayo"};
   // private int[]sale = new int[]{25,20,38,40,70};
    public String [] months;
    public Double [] sale;
    public int[] colors; // = new int[]{Color.RED, Color.BLACK,Color.BLUE,Color.GREEN,Color.LTGRAY,Color.YELLOW};

    private Button btn_date_hoy,btn_date_ayer, btn_date_mes, btn_date_otro, btn_ejecutar_qry;
    private Spinner spinner_categoria_rep;
    private Button btn_trn_todas, btn_trn_ingreso, btn_trn_egreso, btn_trn_transfer;

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

        mbotomnavigationviewR =(BottomNavigationView) findViewById(R.id.navigationbariewR);
        mbotomnavigationviewR.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if (menuItem.getItemId()== R.id.menu_home){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    //showSelectedFragment(new homeFragment());
                }
                if (menuItem.getItemId()== R.id.menu_Reportes){
                    Intent intent = new Intent(getApplicationContext(), listaIngresos.class);
                    startActivity(intent);
                    //showSelectedFragment(new reportsFragment());
                }
                if (menuItem.getItemId()== R.id.menu_ajustes){
                    //showSelectedFragment(new settingsFragment());
                }
                return true;
            }
        });

        btn_date_hoy =(Button) findViewById(R.id.btn_set_date_today_rep);
        btn_date_ayer =(Button) findViewById(R.id.btn_set_date_yesterday_rep);
        btn_date_mes =(Button) findViewById(R.id.btn_set_date_this_month_rep);
        btn_date_otro =(Button) findViewById(R.id.btn_set_date_others_rep);
        btn_trn_todas =(Button) findViewById(R.id.btn_set_trn_todo_rep);
        btn_trn_ingreso =(Button) findViewById(R.id.btn_set_trn_ingreso_rep);
        btn_trn_egreso =(Button) findViewById(R.id.btn_set_trn_egreso_rep);
        btn_trn_transfer =(Button) findViewById(R.id.btn_set_trn_transferencias_rep);
        spinner_categoria_rep =(Spinner)findViewById(R.id.SpinnerCategoriasRep);
        btn_ejecutar_qry =(Button) findViewById(R.id.btn_set_execute_rep);

        // Set Default botones
        String fechaOper = Utilidades.getCurrentDate();
        FechaRep=fechaOper;
        btn_date_hoy.setBackgroundResource(R.drawable.button_date_pressed);
        btn_date_hoy.setTextColor(getResources().getColor(android.R.color.white));
        btn_trn_todas.setBackgroundResource(R.drawable.button_date_pressed);
        btn_trn_todas.setTextColor(getResources().getColor(android.R.color.white));
        btn_ejecutar_qry.setBackgroundResource(R.drawable.custom_date_button);
        consultaSQL ="Select * from " + Utilidades.TABLA_CATEGORIAS ;
        consultarCategorias();
        ArrayAdapter<CharSequence> adapador=  new ArrayAdapter (this,android.R.layout.simple_spinner_item,listaCat);
        spinner_categoria_rep.setAdapter(adapador);
        spinner_categoria_rep.setSelection(0);





        btn_date_hoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fechaOper = Utilidades.getCurrentDate();
                 FechaRep=fechaOper;
                btn_date_hoy.setBackgroundResource(R.drawable.button_date_pressed);
                btn_date_hoy.setTextColor(getResources().getColor(android.R.color.white));
                btn_date_ayer.setBackgroundResource(R.drawable.custom_date_button);
                btn_date_mes.setBackgroundResource(R.drawable.custom_date_button);
                btn_date_otro.setBackgroundResource(R.drawable.custom_date_button);
                btn_date_ayer.setTextColor(getResources().getColor(android.R.color.black));
                btn_date_mes.setTextColor(getResources().getColor(android.R.color.black));
                btn_date_otro.setTextColor(getResources().getColor(android.R.color.black));
            }
        });
        btn_date_ayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_date_ayer.setBackgroundResource(R.drawable.button_date_pressed);
                btn_date_ayer.setTextColor(getResources().getColor(android.R.color.white));
                btn_date_hoy.setBackgroundResource(R.drawable.custom_date_button);
                btn_date_mes.setBackgroundResource(R.drawable.custom_date_button);
                btn_date_otro.setBackgroundResource(R.drawable.custom_date_button);
                btn_date_hoy.setTextColor(getResources().getColor(android.R.color.black));
                btn_date_mes.setTextColor(getResources().getColor(android.R.color.black));
                btn_date_otro.setTextColor(getResources().getColor(android.R.color.black));
                String fechaOper =Utilidades.getYesterday().toString();
                FechaRep=fechaOper;
            }
        });
        btn_date_mes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_date_mes.setBackgroundResource(R.drawable.button_date_pressed);
                btn_date_mes.setTextColor(getResources().getColor(android.R.color.white));
                btn_date_hoy.setBackgroundResource(R.drawable.custom_date_button);
                btn_date_ayer.setBackgroundResource(R.drawable.custom_date_button);
                btn_date_otro.setBackgroundResource(R.drawable.custom_date_button);
                btn_date_hoy.setTextColor(getResources().getColor(android.R.color.black));
                btn_date_ayer.setTextColor(getResources().getColor(android.R.color.black));
                btn_date_otro.setTextColor(getResources().getColor(android.R.color.black));
                String fechaOper = Utilidades.getCurrentDate();
                FechaRep=fechaOper;

            }
        });
        btn_date_otro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_date_otro.setBackgroundResource(R.drawable.button_date_pressed);
                btn_date_otro.setTextColor(getResources().getColor(android.R.color.white));
                btn_date_hoy.setBackgroundResource(R.drawable.custom_date_button);
                btn_date_ayer.setBackgroundResource(R.drawable.custom_date_button);
                btn_date_mes.setBackgroundResource(R.drawable.custom_date_button);
                btn_date_hoy.setTextColor(getResources().getColor(android.R.color.black));
                btn_date_ayer.setTextColor(getResources().getColor(android.R.color.black));
                btn_date_mes.setTextColor(getResources().getColor(android.R.color.black));
                String fechaOper = Utilidades.getCurrentDate();
                FechaRep=fechaOper;

            }
        });

        btn_trn_todas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_trn_todas.setBackgroundResource(R.drawable.button_date_pressed);
                btn_trn_todas.setTextColor(getResources().getColor(android.R.color.white));
                btn_trn_ingreso.setBackgroundResource(R.drawable.custom_date_button);
                btn_trn_egreso.setBackgroundResource(R.drawable.custom_date_button);
                btn_trn_transfer.setBackgroundResource(R.drawable.custom_date_button);
                btn_trn_ingreso.setTextColor(getResources().getColor(android.R.color.black));
                btn_trn_egreso.setTextColor(getResources().getColor(android.R.color.black));
                btn_trn_transfer.setTextColor(getResources().getColor(android.R.color.black));
                consultaSQL ="Select * from " + Utilidades.TABLA_CATEGORIAS ;

                consultarCategorias();
                ArrayAdapter<CharSequence> adapador=  new ArrayAdapter (listaIngresos.this,android.R.layout.simple_spinner_item,listaCat);
                spinner_categoria_rep.setAdapter(adapador);
                spinner_categoria_rep.setSelection(0);
            }
        });
        btn_trn_ingreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_trn_ingreso.setBackgroundResource(R.drawable.button_date_pressed);
                btn_trn_ingreso.setTextColor(getResources().getColor(android.R.color.white));
                btn_trn_todas.setBackgroundResource(R.drawable.custom_date_button);
                btn_trn_egreso.setBackgroundResource(R.drawable.custom_date_button);
                btn_trn_transfer.setBackgroundResource(R.drawable.custom_date_button);
                btn_trn_todas.setTextColor(getResources().getColor(android.R.color.black));
                btn_trn_egreso.setTextColor(getResources().getColor(android.R.color.black));
                btn_trn_transfer.setTextColor(getResources().getColor(android.R.color.black));
                tipotran=1;
                consultaSQL ="Select * from " + Utilidades.TABLA_CATEGORIAS + " WHERE "+Utilidades.CAT_CAMPO_TIPO+"="+tipotran;
                transaccioneSQL ="";
                consultarCategorias();
                ArrayAdapter<CharSequence> adapador=  new ArrayAdapter (listaIngresos.this,android.R.layout.simple_spinner_item,listaCat);
                spinner_categoria_rep.setAdapter(adapador);
                spinner_categoria_rep.setSelection(0);
            }
        });
        btn_trn_egreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_trn_egreso.setBackgroundResource(R.drawable.button_date_pressed);
                btn_trn_egreso.setTextColor(getResources().getColor(android.R.color.white));
                btn_trn_ingreso.setBackgroundResource(R.drawable.custom_date_button);
                btn_trn_todas.setBackgroundResource(R.drawable.custom_date_button);
                btn_trn_transfer.setBackgroundResource(R.drawable.custom_date_button);
                btn_trn_ingreso.setTextColor(getResources().getColor(android.R.color.black));
                btn_trn_todas.setTextColor(getResources().getColor(android.R.color.black));
                btn_trn_transfer.setTextColor(getResources().getColor(android.R.color.black));
                tipotran=2;
                consultaSQL ="Select * from " + Utilidades.TABLA_CATEGORIAS + " WHERE "+Utilidades.CAT_CAMPO_TIPO+"="+tipotran;
                consultarCategorias();
                ArrayAdapter<CharSequence> adapador=  new ArrayAdapter (listaIngresos.this,android.R.layout.simple_spinner_item,listaCat);
                spinner_categoria_rep.setAdapter(adapador);
                spinner_categoria_rep.setSelection(0);
            }
        });
        btn_trn_transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_trn_transfer.setBackgroundResource(R.drawable.button_date_pressed);
                btn_trn_transfer.setTextColor(getResources().getColor(android.R.color.white));
                btn_trn_ingreso.setBackgroundResource(R.drawable.custom_date_button);
                btn_trn_egreso.setBackgroundResource(R.drawable.custom_date_button);
                btn_trn_todas.setBackgroundResource(R.drawable.custom_date_button);
                btn_trn_ingreso.setTextColor(getResources().getColor(android.R.color.black));
                btn_trn_egreso.setTextColor(getResources().getColor(android.R.color.black));
                btn_trn_todas.setTextColor(getResources().getColor(android.R.color.black));
                tipotran=3;
                consultaSQL ="Select * from " + Utilidades.TABLA_CATEGORIAS + " WHERE "+Utilidades.CAT_CAMPO_TIPO+"="+tipotran;
                consultarCategorias();
                ArrayAdapter<CharSequence> adapador=  new ArrayAdapter (listaIngresos.this,android.R.layout.simple_spinner_item,listaCat);
                spinner_categoria_rep.setAdapter(adapador);
                spinner_categoria_rep.setSelection(0);
            }
        });
        spinner_categoria_rep.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posicionSeleccionada = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn_ejecutar_qry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaccioneSQL="SELECT * FROM " + Utilidades.TABLA_OPERACIONES+" ";
                if (tipotran!=0) {
                    transaccioneSQL=transaccioneSQL+ " WHERE " + Utilidades.CAMPO_TIPO_OPER + "="+tipotran+" ";
                }
                if (posicionSeleccionada!=0){
                    transaccioneSQL=transaccioneSQL+" AND "+Utilidades.CAMPO_TIPO_CAT+" = " +posicionSeleccionada+" ";
                }
                if (FechaRep.length()!=0){
                   // transaccioneSQL=transaccioneSQL+"  AND DATE("+Utilidades.CAMPO_FECHA +") BETWEEN  DATE('11/03/2020') and Date('11/03/2020')";
                //+FechaRep.trim().toString()+"' AND '"+FechaRep.trim().toString()+"'";
                    Log.d("SQL",transaccioneSQL);
                }

                        //" AND "+ Utilidades.CAMPO_FECHA +" = "+FechaRep+" " ;
                consultarListaIngresos();
                ArrayAdapter adaptador = new ArrayAdapter(listaIngresos.this, android.R.layout.simple_list_item_1,listainformacionIngreso);
                lvingresos.setAdapter(adaptador);
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

            }
        });
        //conn =new ConexionSQLiteHelper(getApplicationContext(),Utilidades.NOMBRE_BD,null,1);
        lvingresos =(ListView) findViewById(R.id.lvreportes);
        Bundle bundle = getIntent().getExtras();
        //tipotran=bundle.getInt("tipoper");
        tipotran=1;
        transaccioneSQL="SELECT * FROM " + Utilidades.TABLA_OPERACIONES ;
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
            Cursor cursor = db.rawQuery(transaccioneSQL, null);



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

        for (int i=0; i<listaingresos.size();i++) {
            String fechaVal=listaingresos.get(i).getFecha().trim().toString();
            if (FechaRep.length() != 0 && fechaVal == FechaRep.trim().toString()) {
                listainformacionIngreso.add(
                        listaingresos.get(i).getFecha() + "                                 "
                                + listaingresos.get(i).getMonto_operacion() + "$"

                        );
                listaRangoTransaccionesFechaGrafica.add(listaingresos.get(i).getFecha());
                listaMontoTransaccionesGrafica.add(listaingresos.get(i).getMonto_operacion());
                Random rnd = new Random();
                int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                ListaColorGraficas.add(color);

            }else{
                listainformacionIngreso.add(
                        listaingresos.get(i).getFecha() + "                                 "
                                + listaingresos.get(i).getMonto_operacion() + "$"
                );
                listaRangoTransaccionesFechaGrafica.add(listaingresos.get(i).getFecha());
                listaMontoTransaccionesGrafica.add(listaingresos.get(i).getMonto_operacion());
                Random rnd = new Random();
                int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                ListaColorGraficas.add(color);
            }


        }
    }

    private void consultarCategorias(){
        try {
            conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.NOMBRE_BD, null, 1);
            SQLiteDatabase db = conn.getReadableDatabase();
            Cursor cursorc = db.rawQuery(consultaSQL, null);


            Categoria regCategoria = null;
            listaCategorias= new ArrayList<Categoria>();

            while (cursorc.moveToNext()) {
                regCategoria = new Categoria();
                regCategoria.setId(cursorc.getInt(0));
                regCategoria.setDescripcat(cursorc.getString(1));
                //regCategoria.setTipoCat(cursorc.getString(2));
                regCategoria.setDrawable(cursorc.getString(3));
                regCategoria.setColor(cursorc.getString(4));
                listaCategorias.add(regCategoria);


            }
            obtenerListaCat();

        } catch (SQLiteException | IllegalStateException | NullPointerException e) {
            e.printStackTrace();
        }
    }
    private void obtenerListaCat(){
        listaCat= new ArrayList<String>();
        listaCat.add("Seleccione...");
        for (int i=0; i<listaCategorias.size();i++){
            listaCat.add(
                    listaCategorias.get(i).getDescripcat()
            );
        }
    }


}
