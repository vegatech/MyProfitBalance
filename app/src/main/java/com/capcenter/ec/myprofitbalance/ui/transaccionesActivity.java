package com.capcenter.ec.myprofitbalance.ui;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.capcenter.ec.myprofitbalance.io.AdaptadorAvatar;
import com.capcenter.ec.myprofitbalance.model.Categoria;
import com.capcenter.ec.myprofitbalance.io.ConexionSQLiteHelper;
import com.capcenter.ec.myprofitbalance.model.Cuenta;
import com.capcenter.ec.myprofitbalance.model.Ingreso;
import com.capcenter.ec.myprofitbalance.R;
import com.capcenter.ec.myprofitbalance.io.Utilidades;
import com.capcenter.ec.myprofitbalance.io.AvatarVo;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class transaccionesActivity extends AppCompatActivity {
    private View vistaTransaccion;
    private Spinner spCategorias,SpinnerCuentas;
    private EditText txtmonto;
    private TextView txtvmonto, txtvtituloTransaccion;
    private EditText txtfecha;
    private Button btn_date_hoy,btn_date_ayer, btn_date_otros;
    ArrayList<Ingreso> listaTransacciones;
    ArrayList<String> listainformacionTransacciones;
    ArrayList<Categoria> listaCategorias;
    ArrayList<String> listaCat;
    ArrayList<String> listaCatDraw;
    ArrayList<String> listaCatColor;
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
    Integer categoriaSeleccionada;
    //String[] catlistitems;//=new String[] ;
    String[] mtolistitems =new String[5] ;//={"Administrar Ingresos","Administrar Egresos","Administrar Transacciones"};
    boolean basic = true;
    boolean[] chequedItems;
    Integer lencadena;
    int [] imgid ={R.drawable.ingresos,R.drawable.egresos,R.drawable.transacciones};
    ArrayList<Integer> mCategoryItems= new ArrayList<>();
    ArrayList<Integer> mDiagMtoItems= new ArrayList<>();
    Map<Integer, Integer> map = new HashMap<Integer, Integer>();
    Map<Integer, String> mapDrawable = new HashMap<Integer, String>();
    Button btn_billete1;
    Button btn_billete2;
    Button btn_billete3;

    int y,d,m;
    String fecha;
    ListView lstcategoriasTransacciones;
    String[] opcionnombre={"Ingresos","Egresos","Transferencias"};
    String[] desc={"Administrar Ingresos","Administrar Egresos","Administrar Transferencias"};
    int [] imgid1 ={R.drawable.ingresos,R.drawable.egresos,R.drawable.transacciones,R.drawable.egresos,R.drawable.transacciones};
    String[] detalleCategoriasNEw={"01","02","03","04","05","06","07"};
    int [] imgCategoriasNew ={R.drawable.ic_cat_01,R.drawable.ic_cat_02,R.drawable.ic_cat_03,R.drawable.ic_cat_04,R.drawable.ic_cat_05,R.drawable.ic_cat_06,R.drawable.ic_cat_07};


    View vista;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transacciones);

        final String DBO;


        vistaTransaccion =(View) findViewById(R.id.vistaTransacciones);
        txtvtituloTransaccion=(TextView) findViewById(R.id.TextTituloTransaccion1);
        btn_date_hoy =(Button) findViewById(R.id.btn_set_date_today);
        btn_date_ayer=(Button) findViewById(R.id.btn_set_date_yesterday);
        btn_date_otros=(Button) findViewById(R.id.btn_set_date_others);
        btn_date_hoy.setBackgroundResource(R.drawable.button_date_pressed);
        btn_billete1=(Button)  findViewById(R.id.btnBillete1);
        btn_billete2=(Button)  findViewById(R.id.btnBillete2);
        btn_billete3=(Button)  findViewById(R.id.btnBillete3);
        btn_billete1.setVisibility(View.GONE);
        btn_billete2.setVisibility(View.GONE);
        btn_billete3.setVisibility(View.GONE);

        //btn_date_hoy.setBackgroundColor(Color.rgb(70, 80, 90));
        btnCategorias =(Button) findViewById(R.id.btnCategoria);
        spCategorias =(Spinner) findViewById(R.id.SpinnerCategorias);
        SpinnerCuentas=(Spinner) findViewById(R.id.SpinnerCuentas);
        txtmonto =(EditText) findViewById(R.id.EditTextMonto);
        txtfecha = (EditText) findViewById(R.id.EditTextFecha);
        //final RecyclerView recyclerAvatars;
        //recyclerAvatars = vistaTransaccion.findViewById(R.id.recyclerAvatarsId2);
        //RelativeLayout item = (RelativeLayout)findViewById(R.id.item);

        Bundle bundle = getIntent().getExtras();
        tipotran=bundle.getInt("tipoper");
        btn_date_hoy.setTextColor(getResources().getColor(android.R.color.white));
        if (tipotran==1){
            txtvtituloTransaccion.setText("Nuevo Ingreso...");
            txtvtituloTransaccion.setTextColor(getResources().getColor(android.R.color.white));
            vistaTransaccion.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        }
        if (tipotran==2){
            txtvtituloTransaccion.setText("Nuevo Egreso...");
            txtvtituloTransaccion.setTextColor(getResources().getColor(android.R.color.white));
            vistaTransaccion.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
        }
        if (tipotran==3){
            txtvtituloTransaccion.setText("Nueva Transferencia...");
            txtvtituloTransaccion.setTextColor(getResources().getColor(android.R.color.white));
            vistaTransaccion.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
        }

        String fechaOper = Utilidades.getCurrentDate();
        txtfecha.setText(fechaOper);
        // txtvmonto = (TextView) findViewById(R.id.TextViewMonto1);


        //numerotran=bundle.getInt("numoper");

        //Toast.makeText(getApplicationContext(), "Nro transacion:"+numerotran, Toast.LENGTH_SHORT).show();

        //consultarTransaccionById();
        consultarCategorias();
        ArrayAdapter<CharSequence> adapador=  new ArrayAdapter (this,android.R.layout.simple_spinner_item,listaCat);
//        spCategorias.setAdapter(adapador);
        //spCategorias.setSelection(posision);
        spCategorias.setSelection(1);

        consultarCuentas();
        ArrayAdapter<CharSequence> adapador2=  new ArrayAdapter (this,android.R.layout.simple_spinner_item,listainfoCuentas);
        SpinnerCuentas.setAdapter(adapador2);

        lstcategoriasTransacciones= (ListView) findViewById(R.id.listview_category);




        //SpinnerCuentas.setSelection(posision2);
        SpinnerCuentas.setSelection(1);
        consultarMontoTransaccionByCat();
        //set de botones de fecha
        btn_date_hoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_date_hoy.setBackgroundResource(R.drawable.button_date_pressed);

                btn_date_ayer.setBackgroundResource(R.drawable.custom_date_button);
                btn_date_otros.setBackgroundResource(R.drawable.custom_date_button);
                btn_date_hoy.setTextColor(getResources().getColor(android.R.color.white));
                btn_date_ayer.setTextColor(getResources().getColor(android.R.color.black));
                btn_date_otros.setTextColor(getResources().getColor(android.R.color.black));

                String fechaOper = Utilidades.getCurrentDate();
                txtfecha.setText(fechaOper);
            }
        });
        btn_date_ayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_date_hoy.setBackgroundResource(R.drawable.custom_date_button);
                btn_date_ayer.setBackgroundResource(R.drawable.button_date_pressed);
                btn_date_hoy.setTextColor(getResources().getColor(android.R.color.black));
                btn_date_ayer.setTextColor(getResources().getColor(android.R.color.white));
                btn_date_otros.setTextColor(getResources().getColor(android.R.color.black));
                btn_date_otros.setBackgroundResource(R.drawable.custom_date_button);
                String fechaOper =Utilidades.getYesterday().toString();
                txtfecha.setText(fechaOper);
            }
        });
        btn_date_otros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_date_hoy.setBackgroundResource(R.drawable.custom_date_button);
                btn_date_ayer.setBackgroundResource(R.drawable.custom_date_button);
                btn_date_otros.setBackgroundResource(R.drawable.button_date_pressed);
                btn_date_hoy.setTextColor(getResources().getColor(android.R.color.black));
                btn_date_ayer.setTextColor(getResources().getColor(android.R.color.black));
                btn_date_otros.setTextColor(getResources().getColor(android.R.color.white));

                final Calendar calendar = Calendar.getInstance();
                y = calendar.get(Calendar.YEAR);
                d = calendar.get(Calendar.DAY_OF_MONTH);
                m = calendar.get(Calendar.MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(transaccionesActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        fecha= dayOfMonth+"/"+month  +"/"+year;

                        try {
                            txtfecha.setText(Utilidades.getCurrentDatePicker(fecha));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                },y,m,d);
                datePickerDialog.show();
            }
        });
        //DialogoSeleccion de Monto consultado de la BD
        chequedItems = new boolean[mtolistitems.length];

        //Seleccion de Billetes
        btn_billete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtmonto.setText(btn_billete1.getText());
                btn_billete1.setVisibility(View.GONE);
                btn_billete2.setVisibility(View.GONE);
                btn_billete3.setVisibility(View.GONE);
            }
        });
        btn_billete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtmonto.setText(btn_billete2.getText());
                btn_billete1.setVisibility(View.GONE);
                btn_billete2.setVisibility(View.GONE);
                btn_billete3.setVisibility(View.GONE);
            }
        });
        btn_billete3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtmonto.setText(btn_billete3.getText());
                btn_billete1.setVisibility(View.GONE);
                btn_billete2.setVisibility(View.GONE);
                btn_billete3.setVisibility(View.GONE);
            }
        });


        // Dialogo Seleccion de Categoria
        final String [] catlistitems = listaCat.toArray(new String[listaCat.size()]);
        //int img2 =getResources().getIdentifier("ic_cat_ventas","Drawable",getPackageName());
        //Drawable img = getResources().getDrawable( img2);

        final int [] imgid2 = new int[listaCat.size()];
        int img3;
        Drawable imgDraw;
        String imagen;
        for (int i = 0; i < listaCatDraw.size();i++){
            imagen =Utilidades.RUTA_APP+listaCatDraw.get(i).toString();
            Log.d("imagen: ",imagen);
            img3 =getResources().getIdentifier(imagen,"Drawable",getPackageName());
            //imgDraw = getResources().getDrawable( img3);
            imgid2[i]=img3;
        }
        btnCategorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder= new AlertDialog.Builder(transaccionesActivity.this);
                mBuilder.setIcon(R.drawable.ic_category_section);
                mBuilder.setTitle("Selecciona una categoria");
                final VivzAdapter1 adapter1 = new VivzAdapter1(transaccionesActivity.this,catlistitems, imgid2);
                //lstcategoriasTransacciones.setAdapter(adapter1);
                // View mView=  getLayoutInflater().inflate(R.layout.custom_category_dialog,null);
                mBuilder.setAdapter(adapter1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = adapter1.getItem(which);

                       /* AlertDialog.Builder builderInner = new AlertDialog.Builder(transaccionesActivity.this);
                        builderInner.setMessage(strName);
                        builderInner.setTitle("Usted Selecciono");
                        builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,int which) {
                                dialog.dismiss();
                            }
                        });
                        builderInner.show();*/
                        //mCategoryItems.add(which);

                        for (int i = 0; i < listaCatDraw.size();i++) {
                            if (which == i) {
                                btnCategorias.setText(strName);
                                String imagenSel =Utilidades.RUTA_APP+listaCatDraw.get(i).toString();
                                int img3 =getResources().getIdentifier(imagenSel,"Drawable",getPackageName());
                                Drawable img = getResources().getDrawable(img3);
                                btnCategorias.setCompoundDrawables(img, null, null, null);
                                btnCategorias.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                                String colorSel =Utilidades.RUTA_APP+listaCatColor.get(i);
                                int imgColor =getResources().getIdentifier(colorSel,"Drawable",getPackageName());
                                //Drawable drawColor = getResources().getDrawable(imgColor);
                                btnCategorias.setBackgroundResource(imgColor);
                                categoriaSeleccionada=which+1;
                                consultarMontoTransaccionByCat();
                                btn_billete1.setVisibility(View.VISIBLE);
                                btn_billete2.setVisibility(View.VISIBLE);
                                btn_billete3.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
                mBuilder.setNeutralButton("Nueva Categoria", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(transaccionesActivity.this);
                        builderInner.setTitle(" Registro de Categoria...");
                        builderInner.setIcon(R.drawable.ic_category_section);

                        View view = LayoutInflater.from(transaccionesActivity.this).inflate(R.layout.manage_category_dialog, null);
                        builderInner.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(transaccionesActivity.this,"Registro Guardado",Toast.LENGTH_SHORT).show();
                            }
                        });
                        final VivzAdapter1 adapter2 = new VivzAdapter1(transaccionesActivity.this,detalleCategoriasNEw, imgCategoriasNew);
                        builderInner.setAdapter(adapter2, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builderInner.setView(view);
                        builderInner.show();
                    }
                });
                AlertDialog dialog = mBuilder.create();
                // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });




    }// On Create

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
                transaccion.setMonto_operacion(cursor.getDouble(4));
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
            String SQL_QUERY = "SELECT DISTINCT "+ Utilidades.CAMPO_ID+", "+Utilidades.CAMPO_MONTO+" FROM  " + Utilidades.TABLA_OPERACIONES + " WHERE " + Utilidades.CAMPO_TIPO_OPER + "=" + tipotran+ " AND "+Utilidades.CAMPO_TIPO_CAT+" = "+categoriaSeleccionada  +" ORDER BY "+Utilidades.CAMPO_ID+ " DESC "+" LIMIT 3 ";
            Log.d("STATE",SQL_QUERY);
            Cursor cursor = db.rawQuery(SQL_QUERY, null);

            Ingreso transaccion = null;
            listaTransacciones = new ArrayList<Ingreso>();

            while (cursor.moveToNext()) {
                transaccion = new Ingreso();
                transaccion.setId(cursor.getInt(0));
                // transaccion.setFecha(cursor.getString(1));
                transaccion.setMonto_operacion(cursor.getDouble(1));
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
            mtolistitems[i] =listaTransacciones.get(i).getMonto_operacion().toString();
            if (i==0){
                btn_billete1.setText(listaTransacciones.get(i).getMonto_operacion().toString());
            }
            if (i==1){
                btn_billete2.setText(listaTransacciones.get(i).getMonto_operacion().toString());
            }
            if (i==2){
                btn_billete3.setText(listaTransacciones.get(i).getMonto_operacion().toString());
            }
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
        listaCatDraw = new ArrayList<String>();
        listaCatColor = new ArrayList<String>();
        // listaCat.add("Seleccione...");
        //String[] catlistitems =new String[listaCategorias.size()] ;

        lencadena=   listaCategorias.size();
        for (int i=0; i<listaCategorias.size();i++){
            //catlistitems[i] =listaCategorias.get(i).getDescripcat().toString();
            listaCat.add(

                    listaCategorias.get(i).getDescripcat()
                    // +listaCategorias.get(i).getTipoCat()
            );
            listaCatDraw.add(listaCategorias.get(i).getDrawable());
            listaCatColor.add(listaCategorias.get(i).getColor());
        }
        // String [] stringArray = listaCat.toArray(new String[listaCat.size()]);
        // catlistitems =stringArray;
        lencadena=  listaCategorias.size();
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
                    //  listaCuentas.get(i).getId()+" - "
                    listaCuentas.get(i).getDESCRIPCION()
                    // +listaCategorias.get(i).getTipoCat()
            );
        }
    }



}// Fin clase transacciones


class VivzAdapter1 extends ArrayAdapter<String> {
    Context context;
    int[] images;
    String[] tituloArray;

    VivzAdapter1(Context c, String[] titulos, int imagenes[]) {
        super(c, R.layout.single_row1, titulos);
        this.context = c;
        this.images = imagenes;
        this.tituloArray = titulos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.single_row1, parent, false);
        ImageView myImage = (ImageView) row.findViewById(R.id.imageView1);
        TextView myTitle = (TextView) row.findViewById(R.id.textView1);

        //myImage.setImageDrawable(images[position]);
        myImage.setImageResource(images[position]);
        myTitle.setText(tituloArray[position]);

        //return super.getView(position, convertView, parent);
        return row;
    }
}

