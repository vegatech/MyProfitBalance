package com.capcenter.ec.myprofitbalance.ui;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
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
import com.capcenter.ec.myprofitbalance.io.Graficas;
import com.capcenter.ec.myprofitbalance.io.Utilidades;
import com.capcenter.ec.myprofitbalance.model.Categoria;

import com.capcenter.ec.myprofitbalance.model.Transaccion;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import static com.capcenter.ec.myprofitbalance.io.Utilidades.qryTrnbyMesbyCat;

public class reportesActivity extends AppCompatActivity {
    ConexionSQLiteHelper conn;
    private String FechaRep, fechaDesde, fechaHasta;
    private String consultaSQL, transaccioneSQL, categoriasSQL, semestreIngresosISQL, semestreIngresosIISQL, semestreEgresosISQL, semestreEgresosIISQL;
    private int posicionSeleccionada, fechaI, fechaF;
    ListView lvSaldos;
   // ArrayList<Transaccion> listaingresos,listaIngresos,listaEgresos,listaTransferencias, listaTorta;
    ArrayList<String> listainformacionIngreso,listainformacionEgreso, ListainformacionTransferencia, listainformacionTorta;
    ArrayList<Double> listaMontoTransaccionesGrafica, listaMontoEgresoGrafica, listaMontoIngresoGrafica, ListaMontoTransferenciaGrafica, listaMontoTorta;
    ArrayList<String> listaRangoTransaccionesFechaGrafica,listarangotrnEgresos;
    ArrayList ListaColorGraficas, ListaColorPie, ListaColorEgreso;
    //ArrayList<Categoria> listaCategorias;
    ArrayList<String> listaCat, listaSaldoCuentas;
    ArrayList<Integer> listColores = new ArrayList<>();
    Integer tipotran;
    Map<Integer, Integer> mapLista = new HashMap<Integer, Integer>();
    Integer posisionLista;
    BottomNavigationView mbotomnavigationviewR;
    private PieChart piechart;
    private BarChart barchartI, barchartE;
    private HorizontalBarChart horizontalBarChart;


    private Button btn_date_hoy,btn_date_ayer, btn_date_mes, btn_date_otro, btn_ejecutar_qry;
    private Spinner spinner_categoria_rep;
    private Button btn_trn_todas, btn_trn_ingreso, btn_trn_egreso, btn_trn_transfer;

    @RequiresApi(api = Build.VERSION_CODES.O)
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
                    Intent intent = new Intent(getApplicationContext(), reportesActivity.class);
                    startActivity(intent);
                    //showSelectedFragment(new reportsFragment());
                }
                if (menuItem.getItemId()== R.id.menu_ajustes){
                    //showSelectedFragment(new settingsFragment());
                }
                return true;
            }
        });
        // lista de colores para graficos
        Utilidades.setColors();

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
        tipotran=0;
        // Set Default botones
        String fechaOper = Utilidades.getCurrentDate();
        FechaRep=fechaOper;
        fechaI = Utilidades.obtenerPrimerDiaMesActual();
        fechaF = Utilidades.obtenerUltimoDiaMesActual();
        btn_date_hoy.setBackgroundResource(R.drawable.button_date_pressed);
        btn_date_hoy.setTextColor(getResources().getColor(android.R.color.white));
        btn_trn_todas.setBackgroundResource(R.drawable.button_date_pressed);
        btn_trn_todas.setTextColor(getResources().getColor(android.R.color.white));
        btn_ejecutar_qry.setBackgroundResource(R.drawable.custom_date_button);
        consultaSQL ="Select * from " + Utilidades.TABLA_CATEGORIAS ;
        Utilidades.consultarCategorias(getApplicationContext(),consultaSQL);
        obtenerListaCat();
        ArrayAdapter<CharSequence> adapador=  new ArrayAdapter (this,android.R.layout.simple_spinner_item,listaCat);
        spinner_categoria_rep.setAdapter(adapador);
        spinner_categoria_rep.setSelection(0);





        btn_date_hoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fechaOper = Utilidades.getCurrentDate();
                 FechaRep=fechaOper;
                fechaI = Integer.parseInt(Utilidades.getAmericanDate(fechaOper).trim()) ;
                fechaF=Integer.parseInt(Utilidades.getAmericanDate(fechaOper).trim()) ;
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
                fechaI = Integer.parseInt(Utilidades.getAmericanDate(fechaOper).trim()) ;
                fechaF= Integer.parseInt(Utilidades.getAmericanDate(fechaOper).trim()) ;
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
                fechaI = Integer.parseInt(Utilidades.getAmericanDate(fechaOper).trim()) ;
                fechaF =  Utilidades.obtenerUltimoDiaMesActual();
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
                fechaI = Integer.parseInt(Utilidades.getAmericanDate(fechaOper).trim()) ;

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
                //posicionSeleccionada=0;
                tipotran=0;
                Utilidades.consultarCategorias(getApplicationContext(),consultaSQL);
                obtenerListaCat();
                ArrayAdapter<CharSequence> adapador=  new ArrayAdapter (reportesActivity.this,android.R.layout.simple_spinner_item,listaCat);
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
                Utilidades.consultarCategorias(getApplicationContext(),consultaSQL);
                obtenerListaCat();
                ArrayAdapter<CharSequence> adapador=  new ArrayAdapter (reportesActivity.this,android.R.layout.simple_spinner_item,listaCat);
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
                Utilidades.consultarCategorias(getApplicationContext(),consultaSQL);
                obtenerListaCat();
                ArrayAdapter<CharSequence> adapador=  new ArrayAdapter (reportesActivity.this,android.R.layout.simple_spinner_item,listaCat);
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
                Utilidades.consultarCategorias(getApplicationContext(),consultaSQL);
                obtenerListaCat();
                ArrayAdapter<CharSequence> adapador=  new ArrayAdapter (reportesActivity.this,android.R.layout.simple_spinner_item,listaCat);
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
                transaccioneSQL="SELECT * FROM " + Utilidades.TABLA_OPERACIONES+" WHERE 1 =1 ";
                categoriasSQL="SELECT SUM("+Utilidades.CAMPO_MONTO+"),"+Utilidades.CAMPO_TIPO_CAT+" FROM "+ Utilidades.TABLA_OPERACIONES+" WHERE 1 =1 ";
                if (tipotran!=0) {
                    transaccioneSQL=transaccioneSQL+ " AND " + Utilidades.CAMPO_TIPO_OPER + "="+tipotran+" ";

                }
                if (posicionSeleccionada!=0){
                    transaccioneSQL=transaccioneSQL+" AND "+Utilidades.CAMPO_TIPO_CAT+" = " +posicionSeleccionada+" ";
                }
                if (fechaF !=0 && fechaI!=0){
                    transaccioneSQL=transaccioneSQL+"  AND "+Utilidades.CAMPO_FECHA_INT +" BETWEEN "+ fechaI +" and "+ fechaF ;
                    Log.d("SQL",transaccioneSQL);
                    semestreEgresosISQL ="SELECT SUM("+Utilidades.CAMPO_MONTO+") FROM "+Utilidades.TABLA_OPERACIONES+" WHERE 1=1 AND ";
                    categoriasSQL=categoriasSQL+"  AND "+Utilidades.CAMPO_FECHA_INT +" BETWEEN "+ fechaI +" and "+ fechaF+" GROUP BY "+Utilidades.CAMPO_TIPO_CAT+ " ORDER BY "+Utilidades.CAMPO_TIPO_CAT+" ASC";
                    Log.d("SQL",categoriasSQL);
                }

                        //" AND "+ Utilidades.CAMPO_FECHA +" = "+FechaRep+" " ;

                Utilidades.consultarTransaccionesbycategoria(getApplicationContext(),categoriasSQL);
                getListaTransaciones();
                Utilidades.consultarListaIngresos(getApplicationContext(),transaccioneSQL);
                obtenerListaIngresos();
                Utilidades.consultarListaEgresos(getApplicationContext(),transaccioneSQL);
                obtenerListaEgresos();
                Utilidades.consultarListaSaldosCuentas(getApplicationContext(),"");
                obtenerListaSaldoCuentas();
                ArrayAdapter adaptador = new ArrayAdapter(reportesActivity.this, android.R.layout.simple_list_item_1,listaSaldoCuentas);
                lvSaldos.setAdapter(adaptador);
                Graficas.months = listaRangoTransaccionesFechaGrafica.toArray(new String[listaRangoTransaccionesFechaGrafica.size()]);
                Graficas.sale =  listaMontoTransaccionesGrafica.toArray(new Double[listaMontoTransaccionesGrafica.size()]);
                Graficas.colors = new int[ListaColorGraficas.size()];

                //Pie
                Graficas.leyendaPie = listainformacionTorta.toArray(new String[listainformacionTorta.size()]);
                Graficas.montoPie = listaMontoTorta.toArray(new Double[listaMontoTorta.size()]);
                Graficas.colorPie = new int[ListaColorPie.size()];
                //Egresos
                Graficas.monthsE= listarangotrnEgresos.toArray(new String[listarangotrnEgresos.size()]);
                Graficas.saleE= listaMontoEgresoGrafica.toArray(new Double[listaMontoEgresoGrafica.size()]);
                Graficas.colorsE=new int[ListaColorEgreso.size()];

                for (int i = 0;i < ListaColorGraficas.size(); i++) {
                    Graficas.colors[i] = Utilidades.getColorsbyid(i);
                }

                for (int i = 0;i < ListaColorPie.size(); i++){
                    Graficas.colorPie[i] = Utilidades.getColorsbyid(i);
                }
                for (int i = 0;i < ListaColorEgreso.size(); i++){
                    Graficas.colorsE[i] = Utilidades.getColorsbyid(i);
                }
                // Carga Graficas
                barchartI =(BarChart) findViewById(R.id.BarchartI);
                barchartE =(BarChart) findViewById(R.id.BarchartE);
                piechart  =(PieChart) findViewById(R.id.Piechart);
                horizontalBarChart =(HorizontalBarChart) findViewById(R.id.BarchartHorizontalI);
                Graficas.createCharts(barchartI,barchartE,piechart,horizontalBarChart);

            }
        });
            conn =new ConexionSQLiteHelper(getApplicationContext(),Utilidades.NOMBRE_BD,null,1);
        lvSaldos =(ListView) findViewById(R.id.lvrepSaldos);
        Bundle bundle = getIntent().getExtras();
        //tipotran=bundle.getInt("tipoper");
        //tipotran=1;
        transaccioneSQL="SELECT * FROM " + Utilidades.TABLA_OPERACIONES ;//+"  WHERE 1 =1 AND " +Utilidades.CAMPO_FECHA_INT + " BETWEEN "+ fechaI +" and "+ fechaF;
        //categoriasSQL="SELECT SUM("+Utilidades.CAMPO_MONTO+"),CATEGORIAS.DESCRIPCION"+" FROM "+ Utilidades.TABLA_OPERACIONES+" INNER JOIN CATEGORIAS ON CATEGORIAS.id= TRANSACCIONES.TIPO_CATEGORIA WHERE 1 =1 ";
        //categoriasSQL=categoriasSQL+"  AND "+Utilidades.CAMPO_FECHA_INT +" BETWEEN "+ fechaI +" and "+ fechaF +" GROUP BY "+Utilidades.CAMPO_TIPO_CAT+ " ORDER BY "+Utilidades.CAMPO_TIPO_CAT+" ASC";
        categoriasSQL=Utilidades.qryTrnbyMesbyCat();
        Log.d("SQL",categoriasSQL);

        Utilidades.consultarTransaccionesbycategoria(getApplicationContext(),categoriasSQL);
        getListaTransaciones();
        Utilidades.consultarListaIngresos(getApplicationContext(),transaccioneSQL);
        obtenerListaIngresos();
        Utilidades.consultarListaEgresos(getApplicationContext(),transaccioneSQL);
        obtenerListaEgresos();

        Graficas.months = listaRangoTransaccionesFechaGrafica.toArray(new String[listaRangoTransaccionesFechaGrafica.size()]);
        Graficas.sale =  listaMontoTransaccionesGrafica.toArray(new Double[listaMontoTransaccionesGrafica.size()]);
        Graficas.colors = new int[ListaColorGraficas.size()];

        //Pie
        Graficas.leyendaPie = listainformacionTorta.toArray(new String[listainformacionTorta.size()]);
        Graficas.montoPie = listaMontoTorta.toArray(new Double[listaMontoTorta.size()]);
        Graficas.colorPie = new int[ListaColorPie.size()];
        //Egresos
        Graficas.monthsE= listarangotrnEgresos.toArray(new String[listarangotrnEgresos.size()]);
        Graficas.saleE= listaMontoEgresoGrafica.toArray(new Double[listaMontoEgresoGrafica.size()]);
        Graficas.colorsE=new int[ListaColorEgreso.size()];

         for (int i = 0;i < ListaColorGraficas.size(); i++) {
            Graficas.colors[i] = Utilidades.getColorsbyid(i);
         }
         for (int i = 0;i < ListaColorPie.size(); i++){
             Graficas.colorPie[i] = Utilidades.getColorsbyid(i);
         }
        for (int i = 0;i < ListaColorEgreso.size(); i++){
            Graficas.colorsE[i] = Utilidades.getColorsbyid(i);
        }

        // Carga Graficas
        barchartI =(BarChart) findViewById(R.id.BarchartI);
        barchartE =(BarChart) findViewById(R.id.BarchartE);
        piechart =(PieChart) findViewById(R.id.Piechart);
        horizontalBarChart =(HorizontalBarChart)  findViewById(R.id.BarchartHorizontalI);

        Graficas.createCharts(barchartI,barchartE,piechart,horizontalBarChart);
        Utilidades.consultarListaSaldosCuentas(getApplicationContext(),"");
        obtenerListaSaldoCuentas();
        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1,listaSaldoCuentas);
        lvSaldos.setAdapter(adaptador);




        /*lvingresos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        });*/
    }
    private void getListaTransaciones(){
        listaMontoTorta = new ArrayList<Double>();
        listainformacionTorta = new ArrayList<String>();
        ListaColorPie = new ArrayList<>();
        if (Utilidades.listatransacciones.size()==0){
            listaMontoTorta.add(0.0);
            listainformacionTorta.add("Datos No Encontrados");
            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            ListaColorPie.add(color);
        }else{
           for (int i=0; i< Utilidades.listatransacciones.size();i++){
               listainformacionTorta.add(Utilidades.listatransacciones.get(i).getDescripcion());
               listaMontoTorta.add(Utilidades.listatransacciones.get(i).getMonto_operacion());
               Random rnd = new Random();
               int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
               ListaColorPie.add(color);
           }
        }
    }
    private void obtenerListaIngresos(){
        listainformacionIngreso =new ArrayList<String>();
        listaMontoTransaccionesGrafica =new ArrayList<Double>();
        listaRangoTransaccionesFechaGrafica = new ArrayList<String>();
        ListaColorGraficas = new ArrayList<Integer>();
        if (Utilidades.listaingresos.size()==0){
            listainformacionIngreso.add("Datos no Encontrados");
            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            ListaColorGraficas.add(color);
        }else{
        for (int i=0; i<Utilidades.listaingresos.size();i++) {
/*            String fechaVal = Utilidades.listaingresos.get(i).getDescripcion().trim().toString());
            listainformacionIngreso.add(
                    Utilidades.listaingresos.get(i).getFecha() +"-"+ Utilidades.listaingresos.get(i).getFecha_int()//"                                 "
                            +"-"+ Utilidades.listaingresos.get(i).getMonto_operacion() + "$"
            );*/
            listaRangoTransaccionesFechaGrafica.add(Utilidades.listaingresos.get(i).getDescripcion());
            listaMontoTransaccionesGrafica.add(Utilidades.listaingresos.get(i).getMonto_operacion());
            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            ListaColorGraficas.add(color);
//            }

        }
        }
    }

    private void obtenerListaEgresos(){
        listainformacionEgreso =new ArrayList<String>();
        listaMontoEgresoGrafica =new ArrayList<Double>();
        listarangotrnEgresos = new ArrayList<String>();
        ListaColorEgreso = new ArrayList<Integer>();
        if (Utilidades.listaEgresos.size()==0){
            listainformacionEgreso.add("Sin Datos Encontrados");
            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            ListaColorEgreso.add(color);
        }else{
            for (int i=0; i<Utilidades.listaEgresos.size();i++) {
                listarangotrnEgresos.add(Utilidades.listaEgresos.get(i).getDescripcion());
                listaMontoEgresoGrafica.add(Utilidades.listaEgresos.get(i).getMonto_operacion());
                Random rnd = new Random();
                int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                ListaColorEgreso.add(color);

            }
        }
    }

    private void obtenerListaCat(){
        listaCat= new ArrayList<String>();
        listaCat.add("Seleccione...");
        for (int i=0; i<Utilidades.listaCategorias.size();i++){
            listaCat.add(
                    Utilidades.listaCategorias.get(i).getDescripcat()
            );
        }
    }
    private void obtenerListaSaldoCuentas(){
        Double saldoCuentas=0.0;
        listaSaldoCuentas= new ArrayList<String>();
        listaSaldoCuentas.add("                  Saldo en cuentas...");

        for (int i=0; i<Utilidades.listaSaldos.size();i++){
            listaSaldoCuentas.add(
                    Utilidades.listaSaldos.get(i).getDESCRIPCION()+"   =   "+Utilidades.listaSaldos.get(i).getSALDO().toString()
            );
            saldoCuentas=saldoCuentas+Utilidades.listaSaldos.get(i).getSALDO();
        }
        listaSaldoCuentas.add("                             Total  :"+saldoCuentas.toString());
    }

}
