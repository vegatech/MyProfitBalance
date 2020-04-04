package com.capcenter.ec.myprofitbalance.ui.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.capcenter.ec.myprofitbalance.R;
import com.capcenter.ec.myprofitbalance.io.AdaptadorAvatar;
import com.capcenter.ec.myprofitbalance.io.AvatarVo;
import com.capcenter.ec.myprofitbalance.io.ConexionSQLiteHelper;
import com.capcenter.ec.myprofitbalance.io.Utilidades;
import com.capcenter.ec.myprofitbalance.model.Categoria;
import com.capcenter.ec.myprofitbalance.model.Cuenta;
import com.capcenter.ec.myprofitbalance.model.Transaccion;
import com.capcenter.ec.myprofitbalance.ui.MainActivity;

import com.capcenter.ec.myprofitbalance.ui.interfaces.IComunicaFragments;
import com.capcenter.ec.myprofitbalance.ui.transaccionesActivity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link transaccionesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class transaccionesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 ="TipoTran";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int mparam3;
    private OnFragmentInteractionListener mListener;

    Activity actividad;
    View vista;
    IComunicaFragments iComunicaFragments;

    private View vistaTransaccion;
    private Spinner spCategorias,SpinnerCuentas,SpinnerCuentaDestino;
    private EditText txtmonto, txtDescripcion;
    private TextView txtvmonto, txtvtituloTransaccion;
    private EditText txtfecha;
    private Button btn_date_hoy,btn_date_ayer, btn_date_otros;
    ArrayList<Transaccion> listaTransacciones;
    ArrayList<String> listainformacionTransacciones;
    ArrayList<Categoria> listaCategorias;
    ArrayList<String> listaCat;
    ArrayList<String> listaCatDraw;
    ArrayList<String> listaCatColor;
    ArrayList<String> listaMtoCat;
    ArrayList<String> lstInformacionCategoria;
    ArrayList<Cuenta> listaCuentas;
    ArrayList<String> listainfoCuentas;
    private  EditText DialogoTextCategoria;
    RadioButton radioIng, radioEgr, radioTransf;
    EditText iconoSeleccionarDialogo;
    Button btnCategorias ;
    ConexionSQLiteHelper conn;
    int tipotran;
    Integer numerotran;
    Integer posision;
    Integer posision2;
    Integer categoriaSeleccionada;
    String  sqlCuentas;

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
    Button btn_guardar_salir, btn_guardar;
    int  btn_billete_show=0;

    int y,d,m, fechaI;
    String fecha;
    ListView lstcategoriasTransacciones;
    String[] opcionnombre={"Ingresos","Egresos","Transferencias"};
    String[] desc={"Administrar Ingresos","Administrar Egresos","Administrar Transferencias"};
    int [] imgid1 ={R.drawable.ingresos,R.drawable.egresos,R.drawable.transacciones,R.drawable.egresos,R.drawable.transacciones};
    String[] detalleCategoriasNEw={"01","02","03","04","05","06","07"};
    int [] imgCategoriasNew ={R.drawable.ic_cat_01,R.drawable.ic_cat_02,R.drawable.ic_cat_03,R.drawable.ic_cat_04,R.drawable.ic_cat_05,R.drawable.ic_cat_06,R.drawable.ic_cat_07,R.drawable.ic_cat_generic};



    public transaccionesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment transacciones_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static transaccionesFragment newInstance(String param1, String param2,Integer param3) {
        transaccionesFragment fragment = new transaccionesFragment();

        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putInt(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tipotran = getArguments().getInt(ARG_PARAM3);
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //tipotran=getActivity().getIntent().getExtras().getInt("tipoper");
        tipotran=getArguments().getInt("tipoper");;
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
       // return inflater.inflate(R.layout.fragment_transacciones, container, false);
        vista =inflater.inflate(R.layout.fragment_transacciones, container, false);
        //vistaTransaccion =vista.findViewById(R.id.vistaTransacciones);
        txtvtituloTransaccion=vista.findViewById(R.id.TextTituloTransaccion1);
        btn_date_hoy =vista.findViewById(R.id.btn_set_date_today);
        btn_date_ayer=vista.findViewById(R.id.btn_set_date_yesterday);
        btn_date_otros=vista.findViewById(R.id.btn_set_date_others);
        btn_date_hoy.setBackgroundResource(R.drawable.button_date_pressed);
        btn_billete1=vista.findViewById(R.id.btnBillete1);
        btn_billete2=vista.findViewById(R.id.btnBillete2);
        btn_billete3=vista.findViewById(R.id.btnBillete3);
        btn_guardar_salir =vista.findViewById(R.id.ButtonSendTransaction);
        btn_guardar =vista.findViewById(R.id.ButtonSendTransactionNuevo);
        btn_billete1.setVisibility(View.GONE);
        btn_billete2.setVisibility(View.GONE);
        btn_billete3.setVisibility(View.GONE);

        radioIng =vista.findViewById(R.id.radioI);
        radioEgr =vista.findViewById(R.id.radioE);
        radioTransf =vista.findViewById(R.id.radioT);


        btnCategorias =vista.findViewById(R.id.btnCategoria);
        spCategorias =vista.findViewById(R.id.SpinnerCategorias);
        SpinnerCuentas=vista.findViewById(R.id.SpinnerCuentas);
        SpinnerCuentaDestino =vista.findViewById(R.id.SpinnerCuentaDestino);
        txtmonto =vista.findViewById(R.id.EditTextMonto);
        txtfecha =vista.findViewById(R.id.EditTextFecha);
        txtDescripcion =vista.findViewById(R.id.EditTextDescripcion);


        final RecyclerView recyclerAvatars;
        //recyclerAvatars =vista.findViewById(R.id.recyclerAvatarsId);
        recyclerAvatars = vista.findViewById(R.id.recyclerAvatarsId);
        //RelativeLayout item = (RelativeLayout)findViewById(R.id.item);
        recyclerAvatars.setLayoutManager(new GridLayoutManager(getActivity(),3));
        recyclerAvatars.setHasFixedSize(true);
        ArrayList<AvatarVo> listaAvatars;
        listaAvatars=new ArrayList<AvatarVo>();


        listaAvatars.add(new AvatarVo(1,R.drawable.ic_cat_01,"Avatar1"));
        listaAvatars.add(new AvatarVo(2,R.drawable.ic_cat_02,"Avatar2"));
        listaAvatars.add(new AvatarVo(3,R.drawable.ic_cat_03,"Avatar3"));
        listaAvatars.add(new AvatarVo(4,R.drawable.ic_cat_04,"Avatar4"));
        listaAvatars.add(new AvatarVo(5,R.drawable.ic_cat_05,"Avatar5"));
        listaAvatars.add(new AvatarVo(6,R.drawable.ic_cat_06,"Avatar6"));
        listaAvatars.add(new AvatarVo(7,R.drawable.ic_cat_07,"Avatar7"));
        listaAvatars.add(new AvatarVo(8,R.drawable.ic_cat_generic,"Avatar8"));
        listaAvatars.add(new AvatarVo(9,R.drawable.ic_cat_01,"Avatar9"));

        final AdaptadorAvatar miAdaptador=new AdaptadorAvatar(listaAvatars);

        recyclerAvatars.setAdapter(miAdaptador);



        btn_date_hoy.setTextColor(getResources().getColor(android.R.color.white));
        if (tipotran==1){
            txtvtituloTransaccion.setText("Nuevo Ingreso...");
            txtvtituloTransaccion.setTextColor(getResources().getColor(android.R.color.white));
            vista.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
            sqlCuentas ="Select * from " + Utilidades.TABLA_CUENTAS;
            SpinnerCuentaDestino.setVisibility(View.GONE);
        }
        if (tipotran==2){
            txtvtituloTransaccion.setText("Nuevo Egreso...");
            txtvtituloTransaccion.setTextColor(getResources().getColor(android.R.color.white));
            vista.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
            sqlCuentas ="Select * from " + Utilidades.TABLA_CUENTAS;
            SpinnerCuentaDestino.setVisibility(View.GONE);
        }
        if (tipotran==3){
            txtvtituloTransaccion.setText("Nueva Transferencia...");
            txtvtituloTransaccion.setTextColor(getResources().getColor(android.R.color.white));
            vista.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
            sqlCuentas ="Select * from " + Utilidades.TABLA_CUENTAS +" WHERE "+Utilidades.CTA_CAMPO_TIPO_CUENTA+ "<> 'EFECTIVO'";
            SpinnerCuentaDestino.setVisibility(View.VISIBLE);
        }

        String fechaOper = Utilidades.getCurrentDate();
        fechaI = Integer.parseInt(Utilidades.getAmericanDate(fechaOper).trim()) ;
        txtfecha.setText(fechaOper);
        // txtvmonto = (TextView) findViewById(R.id.TextViewMonto1);


        //numerotran=bundle.getInt("numoper");

        //Toast.makeText(getApplicationContext(), "Nro transacion:"+numerotran, Toast.LENGTH_SHORT).show();

        //consultarTransaccionById();
        consultarCategorias();
        ArrayAdapter<CharSequence> adapador=  new ArrayAdapter (getActivity(),android.R.layout.simple_spinner_item,listaCat);
//        spCategorias.setAdapter(adapador);
        //spCategorias.setSelection(posision);
        spCategorias.setSelection(1);

        consultarCuentas();
        ArrayAdapter<CharSequence> adapador2=  new ArrayAdapter (getActivity(),android.R.layout.simple_spinner_item,listainfoCuentas);
        SpinnerCuentas.setAdapter(adapador2);
        SpinnerCuentaDestino.setAdapter(adapador2);

        lstcategoriasTransacciones= vista.findViewById(R.id.listview_category);

        //SpinnerCuentas.setSelection(posision2);
        SpinnerCuentas.setSelection(1);
        SpinnerCuentaDestino.setSelection(2);
        SpinnerCuentaDestino.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(tipotran==3 && categoriaSeleccionada==11) {
                    if (SpinnerCuentas.getSelectedItem().toString().trim().equals(SpinnerCuentaDestino.getSelectedItem().toString().trim())) {
                        Toast.makeText(getActivity(), "Cuenta Origen y Destino no pueden ser la misma", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        SpinnerCuentas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(tipotran==3 && categoriaSeleccionada==11) {
                    if (SpinnerCuentas.getSelectedItem().toString().trim().equals(SpinnerCuentaDestino.getSelectedItem().toString().trim())) {
                        Toast.makeText(getActivity(), "Cuenta Origen y Destino no pueden ser la misma", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
                fechaI = Integer.parseInt(Utilidades.getAmericanDate(fechaOper).trim()) ;
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
                fechaI = Integer.parseInt(Utilidades.getAmericanDate(fechaOper).trim()) ;
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


                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        fecha= dayOfMonth+"/"+ (month+1)  +"/"+year;
                        try {
                            txtfecha.setText(Utilidades.getCurrentDatePicker(fecha));
                            fechaI = Integer.parseInt(Utilidades.getAmericanDate(txtfecha.getText().toString()).trim()) ;
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                },y,m,d);
                datePickerDialog.show();
            }
        });
        if(tipotran==1){
            categoriaSeleccionada=1;
            btnCategorias.setText("Sueldo");

            String imagenSel =Utilidades.RUTA_APP+"ic_cat_sueldo".toString();
            int img3 =getResources().getIdentifier(imagenSel,"Drawable",getActivity().getPackageName());
            Drawable img = getResources().getDrawable(img3);
            btnCategorias.setCompoundDrawables(img, null, null, null);
            btnCategorias.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
        }
        if (tipotran==2){
            categoriaSeleccionada=6;
            btnCategorias.setText("Transporte");
            String imagenSel =Utilidades.RUTA_APP+"ic_cat_transporte".toString();
            int img3 =getResources().getIdentifier(imagenSel,"Drawable",getActivity().getPackageName());
            Drawable img = getResources().getDrawable(img3);
            btnCategorias.setCompoundDrawables(img, null, null, null);
            btnCategorias.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
        }
        if (tipotran==3){
            categoriaSeleccionada=11;
            btnCategorias.setText("Transferencia");
            String imagenSel =Utilidades.RUTA_APP+"ic_transferencia_cat".toString();
            int img3 =getResources().getIdentifier(imagenSel,"Drawable",getActivity().getPackageName());
            Drawable img = getResources().getDrawable(img3);
            btnCategorias.setCompoundDrawables(img, null, null, null);
            btnCategorias.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
            if(categoriaSeleccionada==11){
                SpinnerCuentaDestino.setVisibility(View.VISIBLE);
            }else{
                SpinnerCuentaDestino.setVisibility(View.GONE);
            }
        }
        consultarMontoTransaccionByCat();
        if ( btn_billete_show !=0 && btn_billete_show <=3){
            btn_billete1.setVisibility(View.VISIBLE);
        }
        if ( btn_billete_show !=0 && btn_billete_show >=2 ){
            btn_billete2.setVisibility(View.VISIBLE);
        }
        if ( btn_billete_show !=0 && btn_billete_show >=3){
            btn_billete3.setVisibility(View.VISIBLE);
        }

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
                btn_billete1.setText(null);
                btn_billete2.setText(null);
                btn_billete3.setText(null);
            }
        });
        btn_billete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtmonto.setText(btn_billete2.getText());
                btn_billete1.setVisibility(View.GONE);
                btn_billete2.setVisibility(View.GONE);
                btn_billete3.setVisibility(View.GONE);
                btn_billete1.setText(null);
                btn_billete2.setText(null);
                btn_billete3.setText(null);
            }
        });
        btn_billete3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtmonto.setText(btn_billete3.getText());
                btn_billete1.setVisibility(View.GONE);
                btn_billete2.setVisibility(View.GONE);
                btn_billete3.setVisibility(View.GONE);
                btn_billete1.setText(null);
                btn_billete2.setText(null);
                btn_billete3.setText(null);
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
            img3 =getResources().getIdentifier(imagen,"Drawable",getActivity().getPackageName());
            //imgDraw = getResources().getDrawable( img3);
            imgid2[i]=img3;
        }
        btnCategorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder= new AlertDialog.Builder(getActivity());
                mBuilder.setIcon(R.drawable.ic_category_section);
                mBuilder.setTitle("Selecciona una categoria");
                final VivzAdapterT adapter1 = new VivzAdapterT(getActivity(),catlistitems, imgid2);
                //lstcategoriasTransacciones.setAdapter(adapter1);
                // View mView=  getLayoutInflater().inflate(R.layout.custom_category_dialog,null);
                mBuilder.setAdapter(adapter1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        btn_billete1.setVisibility(View.GONE);
                        btn_billete2.setVisibility(View.GONE);
                        btn_billete3.setVisibility(View.GONE);
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
                                int img3 =getResources().getIdentifier(imagenSel,"Drawable",getActivity().getPackageName());
                                Drawable img = getResources().getDrawable(img3);
                                btnCategorias.setCompoundDrawables(img, null, null, null);
                                btnCategorias.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                                String colorSel =Utilidades.RUTA_APP+listaCatColor.get(i);
                                int imgColor =getResources().getIdentifier(colorSel,"Drawable",getActivity().getPackageName());
                                //Drawable drawColor = getResources().getDrawable(imgColor);
                                btnCategorias.setBackgroundResource(imgColor);
                                categoriaSeleccionada=which+1;
                                consultarMontoTransaccionByCat();
                                if ( btn_billete_show !=0 && btn_billete_show <=3){
                                    btn_billete1.setVisibility(View.VISIBLE);
                                }
                                if ( btn_billete_show !=0 && btn_billete_show >=2 ){
                                    btn_billete2.setVisibility(View.VISIBLE);
                                }
                                if ( btn_billete_show !=0 && btn_billete_show >=3){
                                    btn_billete3.setVisibility(View.VISIBLE);
                                }
                                if(tipotran==3 && categoriaSeleccionada==1){
                                    SpinnerCuentaDestino.setVisibility(View.VISIBLE);
                                }else{
                                    SpinnerCuentaDestino.setVisibility(View.GONE);
                                }
                            }
                        }
                    }
                });
                mBuilder.setNeutralButton("Nueva Categoria", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(getActivity());
                        builderInner.setTitle(" Registro de Categoria...");
                        builderInner.setIcon(R.drawable.ic_category_section);
                        View view = LayoutInflater.from(getActivity()).inflate(R.layout.manage_category_dialog, null);
                        DialogoTextCategoria =view.findViewById(R.id.campoDescripcionCategoria1);
                        RecyclerView recyclerView =   view.findViewById(R.id.recyclerAvatarsId);
                        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(miAdaptador);

                        builderInner.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Integer tipoTrans=0;

                                if (radioIng.isChecked()==true){
                                    tipoTrans=1;
                                }else if(radioEgr.isChecked()==true){
                                    tipoTrans=2;
                                }else{
                                    tipoTrans=3;
                                }
                                if (tipoTrans.intValue()!=0 && DialogoTextCategoria.getText().toString()!=null
                                    //&& !campoNick.getText().toString().trim().equals("")
                                ) {

                                    String RecursoDraww = getResources().getResourceName(imgCategoriasNew[Utilidades.avatarIdSeleccion-1]);
                                    String substr = RecursoDraww.substring(42);
                                    String NombreCAtegoria=DialogoTextCategoria.getText().toString();

                                    conn = new ConexionSQLiteHelper(getActivity(), Utilidades.NOMBRE_BD, null, 1);
                                    SQLiteDatabase db = conn.getWritableDatabase();
                                    ContentValues values = new ContentValues();
                                    values.put(Utilidades.CAT_CAMPO_DESCRIPCION,NombreCAtegoria);
                                    values.put(Utilidades.CAT_CAMPO_COLOR, "custom_button_3");
                                    values.put(Utilidades.CAT_CAMPO_TIPO,tipoTrans );
                                    values.put(Utilidades.CAT_CAMPO_DRAWABLE,substr);
                                    Long idResultante = db.insert(Utilidades.TABLA_CATEGORIAS, Utilidades.CAT_CAMPO_ID, values);
                                    //Toast.makeText(getApplicationContext(), "idResultante" + idResultante, Toast.LENGTH_LONG).show();
                                    db.close();
                                    consultarCategorias();
                                    Toast.makeText(getActivity(), "Registro Guardado", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getActivity(), "Debe indicar todos los campos para continuar", Toast.LENGTH_SHORT).show();

                                }
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
        btn_guardar_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarTransaccionSalir1(v);
            }
        });
        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarTransaccionyNuevo1(v);
            }
        });

        return  vista;
    }// fin create View
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.actividad= (Activity) context;
            iComunicaFragments= (IComunicaFragments) this.actividad;
        }
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void registrarTransaccionSalir1(View view){
        guardarnuevoRegistro();
        iComunicaFragments.llamaHome();
    }
    public void registrarTransaccionyNuevo1(View view){
        guardarnuevoRegistro();
    }
    // Trae los datos paralaedicion de un registro seleccionado en la lista detransacciones
    private void consultarTransaccionById(){
        try {
            conn = new ConexionSQLiteHelper(getContext(), Utilidades.NOMBRE_BD, null, 1);
            SQLiteDatabase db = conn.getReadableDatabase();
            String SQL_QUERY = "Select * from " + Utilidades.TABLA_OPERACIONES + " WHERE " + Utilidades.CAMPO_TIPO_OPER + "=" + tipotran+ " AND "+Utilidades.CAMPO_ID+"="+numerotran;
            Log.d("STATE",SQL_QUERY);
            Cursor cursor = db.rawQuery(SQL_QUERY, null);

            Transaccion transaccion = null;
            listaTransacciones = new ArrayList<Transaccion>();

            while (cursor.moveToNext()) {
                transaccion = new Transaccion();
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
            conn = new ConexionSQLiteHelper(getContext(), Utilidades.NOMBRE_BD, null, 1);
            SQLiteDatabase db = conn.getReadableDatabase();
            String SQL_QUERY = "SELECT DISTINCT "+ Utilidades.CAMPO_ID+", "+Utilidades.CAMPO_MONTO+" FROM  " + Utilidades.TABLA_OPERACIONES + " WHERE " + Utilidades.CAMPO_TIPO_OPER + "=" + tipotran+ " AND "+Utilidades.CAMPO_TIPO_CAT+" = "+categoriaSeleccionada  +" ORDER BY "+Utilidades.CAMPO_ID+ " DESC "+" LIMIT 3 ";
            Log.d("STATE",SQL_QUERY);
            Cursor cursor = db.rawQuery(SQL_QUERY, null);

            Transaccion transaccion = null;
            listaTransacciones = new ArrayList<Transaccion>();

            while (cursor.moveToNext()) {
                transaccion = new Transaccion();
                transaccion.setId(cursor.getInt(0));
                // transaccion.setFecha(cursor.getString(1));
                transaccion.setMonto_operacion(cursor.getDouble(1));
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
                btn_billete_show=1;
            }
            if (i==1){
                btn_billete2.setText(listaTransacciones.get(i).getMonto_operacion().toString());
                btn_billete_show=2;
            }
            if (i==2){
                btn_billete3.setText(listaTransacciones.get(i).getMonto_operacion().toString());
                btn_billete_show=3;
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
            conn = new ConexionSQLiteHelper(getContext(), Utilidades.NOMBRE_BD, null, 1);
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
            conn = new ConexionSQLiteHelper(getContext(), Utilidades.NOMBRE_BD, null, 1);
            SQLiteDatabase db = conn.getReadableDatabase();
            Cursor cursorc = db.rawQuery(sqlCuentas, null);


            Cuenta regCuenta= null;
            listaCuentas= new ArrayList<Cuenta>();

            while (cursorc.moveToNext()) {
                regCuenta = new Cuenta();
                regCuenta.setId(cursorc.getInt(0));
                // transaccion.setFecha(cursor.getString(1));
                regCuenta.setDESCRIPCION(cursorc.getString(1));
                regCuenta.setSALDO(cursorc.getDouble(4));
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


    public void guardarnuevoRegistro(){
        String resultado=null;
        if  (btnCategorias.getText() != null ){
            resultado ="ok";
        }else{
            resultado =null;
            Toast.makeText(getContext(),"El campo Categoria debe ser indicado",Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(txtmonto.getText().toString())){
            resultado =null;
            Toast.makeText(getContext(),"El campo Monto debe ser indicado",Toast.LENGTH_LONG).show();
        }else{
            resultado ="ok";
        }
        if (resultado =="ok") {
            conn = new ConexionSQLiteHelper(getContext(), Utilidades.NOMBRE_BD, null, 1);
            SQLiteDatabase db = conn.getWritableDatabase();
            ContentValues values = new ContentValues();
            ContentValues valuesUpdate = new ContentValues();
            values.put(Utilidades.CAMPO_TIPO_OPER, tipotran);//SpinnerCuentas.getSelectedItem().toString());
            values.put(Utilidades.CAMPO_FECHA, txtfecha.getText().toString().trim());
            values.put(Utilidades.CAMPO_FECHA_INT,fechaI);
            values.put(Utilidades.CAMPO_TIPO_CAT, categoriaSeleccionada);//spCategorias.getSelectedItemPosition());
            values.put(Utilidades.CAMPO_ID_CTA, SpinnerCuentas.getSelectedItemPosition());
            values.put(Utilidades.CAMPO_MONTO, txtmonto.getText().toString());
            //values.put(Utilidades.CAMPO_ID,txtfecha.getText().toString());
            Long idResultante = db.insert(Utilidades.TABLA_OPERACIONES, Utilidades.CAMPO_ID, values);
            double montoAfectaSaldo=0;
            String spinn;
            String monto=txtmonto.getText().toString();
            int sel1 =SpinnerCuentas.getSelectedItemPosition();



            String Whereqry="";
            montoAfectaSaldo = Double.valueOf(monto);
            double Saldo =listaCuentas.get(SpinnerCuentas.getSelectedItemPosition()-1).getSALDO();
            if (tipotran==1){
                spinn=String.valueOf(sel1);
                Whereqry="id="+spinn ;
                Saldo = Saldo +montoAfectaSaldo;
                Log.d("Whereqry",Whereqry);
            }
            if (tipotran==2){
                spinn=String.valueOf(sel1);
                Whereqry="id="+spinn ;
                Saldo = Saldo -montoAfectaSaldo;
                Log.d("Whereqry",Whereqry);
            }
            if (tipotran==3){
                sel1= sel1+1;
                spinn=String.valueOf(sel1);
                Whereqry="id="+spinn ;
                if (categoriaSeleccionada==11 ||categoriaSeleccionada==13){
                    Saldo = Saldo -montoAfectaSaldo;
                }else{
                    Saldo = Saldo +montoAfectaSaldo;
                }

                Log.d("Whereqry",Whereqry);
            }

            valuesUpdate.put(Utilidades.CTA_CAMPO_SALDO,Saldo);
            int count= db.update("CUENTAS",valuesUpdate,Whereqry,null);

            // Toast.makeText(getApplicationContext(), "idResultante" + idResultante, Toast.LENGTH_LONG).show();
            Toast.makeText(getContext(), "Registro incluido Satisfactoriamente", Toast.LENGTH_LONG).show();
            db.close();

            if(categoriaSeleccionada==11){
                SQLiteDatabase db2 = conn.getWritableDatabase();
                ContentValues values2 = new ContentValues();
                values2.put(Utilidades.CAMPO_TIPO_OPER, tipotran);
                values2.put(Utilidades.CAMPO_FECHA, txtfecha.getText().toString());
                values.put(Utilidades.CAMPO_FECHA_INT,fechaI);
                values2.put(Utilidades.CAMPO_TIPO_CAT, categoriaSeleccionada);//spCategorias.getSelectedItemPosition());
                values2.put(Utilidades.CAMPO_ID_CTA, SpinnerCuentaDestino.getSelectedItemPosition());
                values2.put(Utilidades.CAMPO_MONTO, txtmonto.getText().toString());
                //values.put(Utilidades.CAMPO_ID,txtfecha.getText().toString());
                Long idResultante2 = db2.insert(Utilidades.TABLA_OPERACIONES, Utilidades.CAMPO_ID, values2);

                ContentValues valuesUpdate1 = new ContentValues();
                double montoAfectaSaldo1=0;
                String monto1=txtmonto.getText().toString();
                int sel =SpinnerCuentaDestino.getSelectedItemPosition();
                sel= sel+1;
                String spinn1=String.valueOf(sel);
                String Whereqry1="";
                montoAfectaSaldo1 = Double.valueOf(monto1);
                double Saldo1 =listaCuentas.get(SpinnerCuentaDestino.getSelectedItemPosition()-1).getSALDO();

                Whereqry1="id="+spinn1 ;
                Saldo1 = Saldo1 +montoAfectaSaldo1;
                Log.d("Whereqry",Whereqry1);
                valuesUpdate1.put(Utilidades.CTA_CAMPO_SALDO,Saldo1);
                int count2= db2.update("CUENTAS",valuesUpdate1,Whereqry1,null);

                //Toast.makeText(getApplicationContext(), "idResultante" + idResultante, Toast.LENGTH_LONG).show();
                Toast.makeText(getContext(), "Registro Incluido Satisfactoriamente", Toast.LENGTH_LONG).show();
                db2.close();
            }
            txtDescripcion.setText("");
            txtmonto.setText("");
        }
    }
}// Fin Clase Fragment

class VivzAdapterT extends ArrayAdapter<String> {
    Context context;
    int[] images;
    String[] tituloArray;

    VivzAdapterT(Context c, String[] titulos, int imagenes[]) {
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


