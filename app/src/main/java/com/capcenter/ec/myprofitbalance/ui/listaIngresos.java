package com.capcenter.ec.myprofitbalance.ui;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.capcenter.ec.myprofitbalance.io.ConexionSQLiteHelper;
import com.capcenter.ec.myprofitbalance.model.Ingreso;
import com.capcenter.ec.myprofitbalance.R;
import com.capcenter.ec.myprofitbalance.io.Utilidades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class listaIngresos extends AppCompatActivity {
    ConexionSQLiteHelper conn;
    ListView lvingresos;
    ArrayList<Ingreso> listaingresos;
    ArrayList<String> listainformacionIngreso;
    Integer tipotran;
    Map<Integer, Integer> mapLista = new HashMap<Integer, Integer>();
    Integer posisionLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_ingresos);
      //conn =new ConexionSQLiteHelper(getApplicationContext(),Utilidades.NOMBRE_BD,null,1);
           lvingresos =(ListView) findViewById(R.id.lvingresos);
        Bundle bundle = getIntent().getExtras();
        //tipotran=bundle.getInt("tipoper");
        tipotran=1;
        consultarListaIngresos();

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
            for (int i=0; i<listaingresos.size();i++){
                listainformacionIngreso.add(listaingresos.get(i).getId()+" - "
                        +listaingresos.get(i).getFecha()+"                                 "
                        +listaingresos.get(i).getMonto_operacion()+"$"
                );
            }
        }

}
