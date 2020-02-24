package com.capcenter.ec.myprofitbalance.ui;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.capcenter.ec.myprofitbalance.model.Categoria;
import com.capcenter.ec.myprofitbalance.io.ConexionSQLiteHelper;
import com.capcenter.ec.myprofitbalance.model.Cuenta;
import com.capcenter.ec.myprofitbalance.model.Ingreso;
import com.capcenter.ec.myprofitbalance.R;
import com.capcenter.ec.myprofitbalance.io.Utilidades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class transaccionesActivity extends AppCompatActivity {
    private Spinner spCategorias,SpinnerCuentas;
    private EditText txtmonto;
    private TextView txtvmonto;
    private EditText txtfecha;
    private Button btn_date_hoy,btn_date_ayer, btn_date_otros;
    ArrayList<Ingreso> listaTransacciones;
    ArrayList<String> listainformacionTransacciones;
    ArrayList<Categoria> listaCategorias;
    ArrayList<String> listaCat;
    ArrayList<String> listaMtoCat;
    ArrayList<String> lstInformacionCategoria;
    ArrayList<Cuenta> listaCuentas;
    ArrayList<String> listainfoCuentas;
    Button btnCategorias ;
    ConexionSQLiteHelper conn;
    Integer tipotran;
    Integer numerotran;
    Integer posision;
    Integer posision2;
    String[] catlistitems =new String[5] ;
    String[] mtolistitems =new String[5] ;//={"Administrar Ingresos","Administrar Egresos","Administrar Transacciones"};
    boolean basic = true;
    boolean[] chequedItems;
    ArrayList<Integer> mCategoryItems= new ArrayList<>();
    ArrayList<Integer> mDiagMtoItems= new ArrayList<>();
    Map<Integer, Integer> map = new HashMap<Integer, Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transacciones);

        btn_date_hoy =(Button) findViewById(R.id.btn_set_date_today);
        btn_date_hoy.setBackgroundResource(R.drawable.button_date_pressed);
        //btn_date_hoy.setBackgroundColor(Color.rgb(70, 80, 90));
        btnCategorias =(Button) findViewById(R.id.btnCategoria);
        spCategorias =(Spinner) findViewById(R.id.SpinnerCategorias);
        SpinnerCuentas=(Spinner) findViewById(R.id.SpinnerCuentas);
        txtmonto =(EditText) findViewById(R.id.EditTextMonto);
        txtfecha = (EditText) findViewById(R.id.EditTextFecha);
        String fechaOper = Utilidades.getCurrentDate();
        txtfecha.setText(fechaOper);
       // txtvmonto = (TextView) findViewById(R.id.TextViewMonto1);
                Bundle bundle = getIntent().getExtras();
        tipotran=bundle.getInt("tipoper");
        //numerotran=bundle.getInt("numoper");

        Toast.makeText(getApplicationContext(), "Nro transacion:"+numerotran, Toast.LENGTH_SHORT).show();

        //consultarTransaccionById();
        consultarCategorias();
        ArrayAdapter<CharSequence> adapador=  new ArrayAdapter (this,android.R.layout.simple_spinner_item,listaCat);
        spCategorias.setAdapter(adapador);
        //spCategorias.setSelection(posision);
        spCategorias.setSelection(1);

        consultarCuentas();
        ArrayAdapter<CharSequence> adapador2=  new ArrayAdapter (this,android.R.layout.simple_spinner_item,listainfoCuentas);
        SpinnerCuentas.setAdapter(adapador2);
        //SpinnerCuentas.setSelection(posision2);
        SpinnerCuentas.setSelection(1);
        consultarMontoTransaccionByCat();
        //DialogoSeleccion de Monto consultado de la BD
        chequedItems = new boolean[mtolistitems.length];
        /*txtvmonto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder =new AlertDialog.Builder(transaccionesActivity.this);
                mBuilder.setTitle("Ultimos montos   ");
                mBuilder.setSingleChoiceItems(mtolistitems, -1 , new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        for (int i = 0; i < chequedItems.length; i++) {
                           // chequedItems [i] = false;
                            mDiagMtoItems.clear();
                            txtvmonto.setText("");
                        }
                       // if(isChecked){
                            mDiagMtoItems.add(position);
                        //}else{
                         //   mDiagMtoItems.remove((Integer.valueOf(position)));
                        //}
                    }
                });
                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("Seleccionar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int wich) {
                        String item="";
                        for(int i = 0 ;i < mDiagMtoItems.size();i++ ){
                            txtvmonto.setText(mtolistitems[mDiagMtoItems.get(i)]);
                            item = item +mtolistitems[mDiagMtoItems.get(i)];
                            if(i !=  mDiagMtoItems.size()-1){
                                item =item + ", ";

                            }
                        }
                    }
                });
                mBuilder.setNegativeButton("Olvidar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                mBuilder.setNeutralButton("Anular", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < chequedItems.length; i++) {
                            chequedItems [i] = false;
                            mDiagMtoItems.clear();
                            txtvmonto.setText("");
                        }
                    }
                });
                AlertDialog mDialog =mBuilder.create();
                mDialog.show();
            }
        });// Fin boton monto
*/

        // Dialogo Seleccion de Categoria
        chequedItems = new boolean[catlistitems.length];
            btnCategorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder =new AlertDialog.Builder(transaccionesActivity.this);
                mBuilder.setTitle("Categorias disponibles");
                //mBuilder.setMultiChoiceItems(catlistitems, chequedItems, new DialogInterface.OnMultiChoiceClickListener() {
                mBuilder.setSingleChoiceItems(catlistitems, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int Position) {


                        for (int i = 0; i < chequedItems.length; i++) {
                            //chequedItems [i] = false;
                            mCategoryItems.clear();
                            btnCategorias.setText("");
                            mCategoryItems.add(Position);
                        }
                    }

                    });
                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("Seleccionar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int wich) {
                        String item="";
                        for(int i = 0 ;i < mCategoryItems.size();i++ ){
                            btnCategorias.setText(catlistitems[ mCategoryItems.get(i)]);
                           /* item = item +catlistitems[mCategoryItems.get(i)];
                            if(i !=  mCategoryItems.size()-1){
                                item =item + ", ";

                            }*/
                        }
                    }
                });
                AlertDialog mDialog =mBuilder.create();
                mDialog.show();
            }
        });
    }

        public void registrarUsuario(View view){
        String resultado=null;
            if  (btnCategorias.getText() != null ){
                resultado ="ok";
            }else{
                resultado =null;
                Toast.makeText(getApplicationContext(),"El campo Categoria debe ser indicado",Toast.LENGTH_LONG).show();
            }
            if (TextUtils.isEmpty(txtmonto.getText().toString())){
                resultado =null;
                Toast.makeText(getApplicationContext(),"El campo Monto debe ser indicado",Toast.LENGTH_LONG).show();
            }else{
                resultado ="ok";
            }
            if (resultado =="ok") {
                conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.NOMBRE_BD, null, 1);
                SQLiteDatabase db = conn.getWritableDatabase();
                ContentValues values = new ContentValues();

                values.put(Utilidades.CAMPO_TIPO_OPER, tipotran);//SpinnerCuentas.getSelectedItem().toString());
                values.put(Utilidades.CAMPO_FECHA, txtfecha.getText().toString());
                values.put(Utilidades.CAMPO_TIPO_CAT, spCategorias.getSelectedItemPosition());
                values.put(Utilidades.CAMPO_ID_CTA, SpinnerCuentas.getSelectedItemPosition());
                values.put(Utilidades.CAMPO_MONTO, txtmonto.getText().toString());
                //values.put(Utilidades.CAMPO_ID,txtfecha.getText().toString());
                Long idResultante = db.insert(Utilidades.TABLA_OPERACIONES, Utilidades.CAMPO_ID, values);

                Toast.makeText(getApplicationContext(), "idResultante" + idResultante, Toast.LENGTH_LONG).show();
                db.close();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }

        }
        // Trae los datos paralaedicion de un registro seleccionado en la lista detransacciones
        private void consultarTransaccionById(){
        try {
            conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.NOMBRE_BD, null, 1);
            SQLiteDatabase db = conn.getReadableDatabase();
            String SQL_QUERY = "Select * from " + Utilidades.TABLA_OPERACIONES + " WHERE " + Utilidades.CAMPO_TIPO_OPER + "=" + tipotran+ " AND "+Utilidades.CAMPO_ID+"="+numerotran;
            Log.d("STATE",SQL_QUERY);
            Cursor cursor = db.rawQuery(SQL_QUERY, null);

            Ingreso transaccion = null;
            listaTransacciones = new ArrayList<Ingreso>();

            while (cursor.moveToNext()) {
                transaccion = new Ingreso();
                transaccion.setId(cursor.getInt(0));
                transaccion.setFecha(cursor.getString(1));
                transaccion.setMonto_operacion(cursor.getString(4));
                txtfecha.setText(cursor.getString(1));
                txtmonto.setText(cursor.getString(5));
                map.put(cursor.getPosition(),cursor.getInt(0));
                posision=cursor.getInt(3);
                posision2=cursor.getInt(4);
                listaTransacciones.add(transaccion);

            }
            //obtenerListaTransaccion();

        } catch (SQLiteException | IllegalStateException | NullPointerException e) {
            e.printStackTrace();
        }
    }
    private void obtenerListaTransaccion(){


        for (int i=0; i<listaTransacciones.size();i++){

           // txtmonto.setText( listaTransacciones.get(i).getMonto_operacion()) ;
                         //   +listaCategorias.get(i).getDescripcat()
                    // +listaCategorias.get(i).getTipoCat()

        }
    }
    private void consultarMontoTransaccionByCat(){
        try {
            conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.NOMBRE_BD, null, 1);
            SQLiteDatabase db = conn.getReadableDatabase();
            String SQL_QUERY = "SELECT DISTINCT "+ Utilidades.CAMPO_ID+", "+Utilidades.CAMPO_MONTO+" FROM  " + Utilidades.TABLA_OPERACIONES + " WHERE " + Utilidades.CAMPO_TIPO_OPER + "=" + tipotran+ " AND "+Utilidades.CAMPO_TIPO_CAT+" = "+1 +" LIMIT 5";
            Log.d("STATE",SQL_QUERY);
            Cursor cursor = db.rawQuery(SQL_QUERY, null);

            Ingreso transaccion = null;
            listaTransacciones = new ArrayList<Ingreso>();

            while (cursor.moveToNext()) {
                transaccion = new Ingreso();
                transaccion.setId(cursor.getInt(0));
               // transaccion.setFecha(cursor.getString(1));
                transaccion.setMonto_operacion(cursor.getString(1));
                //txtfecha.setText(cursor.getString(1));
                //txtmonto.setText(cursor.getString(1));
                //map.put(cursor.getPosition(),cursor.getInt(0));
               // posision=cursor.getInt(3);
               // posision2=cursor.getInt(4);
                listaTransacciones.add(transaccion);

            }
            obtenerListaMtoTransaccion();

        } catch (SQLiteException | IllegalStateException | NullPointerException e) {
            e.printStackTrace();
        }
    }
    private void obtenerListaMtoTransaccion(){
        listaMtoCat= new ArrayList<String>();

        for (int i=0; i<listaTransacciones.size();i++){
            mtolistitems[i] =listaTransacciones.get(i).getMonto_operacion();
            listaMtoCat.add(
                    listaTransacciones.get(i).getId()+" - "
                            +listaTransacciones.get(i).getMonto_operacion()
                    // +listaCategorias.get(i).getTipoCat()
            );

        }
    }
    private void consultarCategorias(){
        try {
            conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.NOMBRE_BD, null, 1);
            SQLiteDatabase db = conn.getReadableDatabase();
            Cursor cursorc = db.rawQuery("Select * from " + Utilidades.TABLA_CATEGORIAS + " WHERE "+Utilidades.CAT_CAMPO_TIPO+"="+tipotran, null);


            Categoria regCategoria = null;
            listaCategorias= new ArrayList<Categoria>();

            while (cursorc.moveToNext()) {
                regCategoria = new Categoria();
                regCategoria.setId(cursorc.getInt(0));
               // transaccion.setFecha(cursor.getString(1));
                regCategoria.setDescripcat(cursorc.getString(1));
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
            catlistitems[i] =listaCategorias.get(i).getDescripcat().toString();
            listaCat.add(
                    listaCategorias.get(i).getId()+" - "
                    +listaCategorias.get(i).getDescripcat()
                   // +listaCategorias.get(i).getTipoCat()
            );
        }
    }

    private void consultarCuentas(){
        try {
            conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.NOMBRE_BD, null, 1);
            SQLiteDatabase db = conn.getReadableDatabase();
            Cursor cursorc = db.rawQuery("Select * from " + Utilidades.TABLA_CUENTAS, null);


            Cuenta regCuenta= null;
            listaCuentas= new ArrayList<Cuenta>();

            while (cursorc.moveToNext()) {
                regCuenta = new Cuenta();
                regCuenta.setId(cursorc.getInt(0));
                // transaccion.setFecha(cursor.getString(1));
                regCuenta.setDESCRIPCION(cursorc.getString(1));
                listaCuentas.add(regCuenta);

            }
            obtenerListaCta();

        } catch (SQLiteException | IllegalStateException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void obtenerListaCta(){
        listainfoCuentas= new ArrayList<String>();
        listainfoCuentas.add("Seleccione...");

        for (int i=0; i<listaCuentas.size();i++){
            listainfoCuentas.add(
                    listaCuentas.get(i).getId()+" - "
                            +listaCuentas.get(i).getDESCRIPCION()
                    // +listaCategorias.get(i).getTipoCat()
            );
        }
    }

}
