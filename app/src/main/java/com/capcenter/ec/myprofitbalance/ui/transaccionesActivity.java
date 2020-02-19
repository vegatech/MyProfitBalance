package com.capcenter.ec.myprofitbalance.ui;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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

    private EditText txtfecha;
    ArrayList<Ingreso> listaTransacciones;
    ArrayList<String> listainformacionTransacciones;
    ArrayList<Categoria> listaCategorias;
    ArrayList<String> listaCat;
    ArrayList<String> lstInformacionCategoria;
    ArrayList<Cuenta> listaCuentas;
    ArrayList<String> listainfoCuentas;
    ConexionSQLiteHelper conn;
    Integer tipotran;
    Integer numerotran;
    Integer posision;
    Integer posision2;
    Map<Integer, Integer> map = new HashMap<Integer, Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transacciones);

        spCategorias =(Spinner) findViewById(R.id.SpinnerCategorias);
        SpinnerCuentas=(Spinner) findViewById(R.id.SpinnerCuentas);
        txtmonto =(EditText) findViewById(R.id.EditTextMonto);
        txtfecha = (EditText) findViewById(R.id.EditTextFecha);
        String fechaOper = Utilidades.getCurrentDate();
        txtfecha.setText(fechaOper);

                Bundle bundle = getIntent().getExtras();
        tipotran=bundle.getInt("tipoper");
        //numerotran=bundle.getInt("numoper");

        Toast.makeText(getApplicationContext(), "Nro transacion:"+numerotran, Toast.LENGTH_SHORT).show();

        //consultarTransaccionById();
        consultarCategorias();
        ArrayAdapter<CharSequence> adapador=  new ArrayAdapter (this,android.R.layout.simple_spinner_item,listaCat);
        spCategorias.setAdapter(adapador);
        //spCategorias.setSelection(posision);
        spCategorias.setSelection(0);

        consultarCuentas();
        ArrayAdapter<CharSequence> adapador2=  new ArrayAdapter (this,android.R.layout.simple_spinner_item,listainfoCuentas);
        SpinnerCuentas.setAdapter(adapador2);
        //SpinnerCuentas.setSelection(posision2);
        SpinnerCuentas.setSelection(1);
    }
        public void registrarUsuario(View view){

            conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.NOMBRE_BD, null, 1);
            SQLiteDatabase db = conn.getWritableDatabase();
            ContentValues values= new ContentValues();
            values.put(Utilidades.CAMPO_TIPO_OPER,SpinnerCuentas.getSelectedItem().toString());
            values.put(Utilidades.CAMPO_FECHA,txtfecha.getText().toString());
            values.put(Utilidades.CAMPO_TIPO_CAT, spCategorias.getSelectedItem().toString());
            values.put(Utilidades.CAMPO_ID_CTA,1);
            values.put(Utilidades.CAMPO_MONTO,txtmonto.getText().toString());
            //values.put(Utilidades.CAMPO_ID,txtfecha.getText().toString());
            Long idResultante= db.insert(Utilidades.TABLA_OPERACIONES,Utilidades.CAMPO_ID,values);

            Toast.makeText(getApplicationContext(),"idResultante"+idResultante,Toast.LENGTH_LONG).show();
            db.close();
        }
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
