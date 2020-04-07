package com.capcenter.ec.myprofitbalance.io;


import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Environment;
import android.util.Log;

import com.capcenter.ec.myprofitbalance.R;
import com.capcenter.ec.myprofitbalance.model.Categoria;
import com.capcenter.ec.myprofitbalance.model.Cuenta;
import com.capcenter.ec.myprofitbalance.model.Transaccion;
import com.capcenter.ec.myprofitbalance.ui.reportesActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Utilidades {
    public static final String DATE_FORMAT_1 = "dd/MM/yyyy";

    public static final String RUTA_APK= Environment.getExternalStorageDirectory().toString();
    public static final String RUTA_APK_SD="/storage/sdcard0/";
    public static AvatarVo avatarSeleccion=null;
    public static int avatarIdSeleccion=0;
    public static String avatarIdSeleccionDialogo="";
    public static ArrayList<AvatarVo> listaAvatars=null;

    public static final String NOMBRE_BD="myprofitbd.db";///mnt/sdcard/
    public static final int VERSION_DB=25;
    public static final String RUTA_APP="com.capcenter.ec.myprofitbalance:drawable/";
    public static  ArrayList<Integer> listColores=null;
    public static  ConexionSQLiteHelper conn;
    public static ArrayList<Categoria> listaCategorias;
    public static ArrayList<Cuenta>  listaSaldos;
   public static ArrayList<Transaccion> listaingresos,listaIngresos,listaEgresos,listaTransferencias, listatransacciones;
    public static  String getAmericanDate(String fechaI){
        String fechaE;
        fechaE = fechaI.substring(6,10)+fechaI.substring(3,5)+fechaI.substring(0,2);
        return fechaE;
    }
    public static String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_1);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC-5"));
        Date today = Calendar.getInstance().getTime();
        return dateFormat.format(today);
    }
    public static String getCurrentDatePicker(String fecha) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_1);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC-5"));
        Date today = new SimpleDateFormat("dd/MM/yyyy").parse(fecha);//Calendar.getInstance().getTime();
        return dateFormat.format(today);
    }

    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    Date date = new Date();
    //System.out.println(formatter.format(date));
    public static String getYesterday(){
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_1);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC-5"));
        Date today = Calendar.getInstance().getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today); // Configuramos la fecha que se recibe
        calendar.add(Calendar.DAY_OF_YEAR, -1);  // numero de días a añadir, o restar en caso de días<0
        //return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos
        return dateFormat.format(calendar.getTime());
    }
    public int obtenerUltimoDiaMes (int anio, int mes) {

        Calendar calendario=Calendar.getInstance();
        calendario.set(anio, mes-1, 1);
        return calendario.getActualMaximum(Calendar.DAY_OF_MONTH);

    }
    public static int obtenerPrimerDiaMesActual() {
        int mes = Calendar.getInstance().get(Calendar.MONTH);
        int anio = Calendar.getInstance().get(Calendar.YEAR);
        int dia, fecha;
        String fechaStr, m, d, a;
        Calendar calendario = Calendar.getInstance();
        calendario.set(anio, mes, 1);
        dia = calendario.getActualMinimum(Calendar.DAY_OF_MONTH);

        mes = mes +1;
        a = String.valueOf(anio);
        if (dia <10){
            d  = String.valueOf(dia);
            d = "0"+d;
        }else{
            d  = String.valueOf(dia);
        }
        if (mes <10){
            m = String.valueOf(mes);
            m = "0"+m;
        }else{
            m = String.valueOf(mes);
        }
        fechaStr =a+m+d;
        fecha = Integer.parseInt(fechaStr);
        return fecha;
    }
    public static int obtenerUltimoDiaMesActual() {
        int mes = Calendar.getInstance().get(Calendar.MONTH);
        int anio = Calendar.getInstance().get(Calendar.YEAR);
        int dia,fecha;
        String fechaStr,m,d,a;
        Calendar calendario=Calendar.getInstance();
        calendario.set(anio, mes, 1);
        dia = calendario.getActualMaximum(Calendar.DAY_OF_MONTH);

        mes = mes +1;
        a = String.valueOf(anio);
        if (dia <10){
             d  = String.valueOf(dia);
            d = "0"+d;
        }else{
            d  = String.valueOf(dia);
        }
        if (mes <10){
             m = String.valueOf(mes);
            m = "0"+m;
        }else{
            m = String.valueOf(mes);
        }
        fechaStr =a+m+d;
        fecha = Integer.parseInt(fechaStr);
        return fecha;
    }
    public ArrayList<String> getMesesGrafica(){
        ArrayList<String> Meses = new ArrayList<>();
        int i = Calendar.getInstance().get(Calendar.MONTH)+1;

        if (i <=6){
            Meses.add("Enero");
            Meses.add("Febrero");
            Meses.add("Marzo");
            Meses.add("Abril");
            Meses.add("Mayo");
            Meses.add("Junio");
        }else{
            Meses.add("Julio");
            Meses.add("Agosto");
            Meses.add("Septiembre");
            Meses.add("Octubre");
            Meses.add("Noviembre");
            Meses.add("Diciembre");
        }

        return Meses;
    }
    public static ArrayList<String> getDialogMeses(){
        ArrayList<String> Meses = new ArrayList<>();

        Meses.add("Enero");
        Meses.add("Febrero");
        Meses.add("Marzo");
        Meses.add("Abril");
        Meses.add("Mayo");
        Meses.add("Junio");
        Meses.add("Julio");
        Meses.add("Agosto");
        Meses.add("Septiembre");
        Meses.add("Octubre");
        Meses.add("Noviembre");
        Meses.add("Diciembre");

        return Meses;
    }
    public static ArrayList<String> getDialogAnios(){
        ArrayList<String> Anios = new ArrayList<>();

        Anios.add("2019");
        Anios.add("2020");
        Anios.add("2021");
        Anios.add("2022");
        Anios.add("2023");
        Anios.add("2024");
       /* Anios.add("2025");
        Anios.add("2026");
        Anios.add("2027");
        Anios.add("2028");
        Anios.add("2029");
        Anios.add("2030");*/
        return Anios;
    }
    //tablas
    public static final String TABLA_OPERACIONES= "TRANSACCIONES";
    public static final String CAMPO_ID="id";
    public static final String CAMPO_FECHA="FECHA";
    public static final String CAMPO_FECHA_INT="FECHAINT";
    public static final String CAMPO_TIPO_OPER="TIPO_OPERACION";
    public static final String CAMPO_TIPO_CAT="TIPO_CATEGORIA";
    public static final String CAMPO_ID_CTA="ID_CTA";
    public static final String CAMPO_MONTO="MONTO_OPERACION";
    public static final String CAMPO_DESCRIPCION="DESCRIPCION";

    public static final String CREAR_TABLA_OPERACIONES ="CREATE TABLE "+TABLA_OPERACIONES+"  ("+CAMPO_ID+" INTEGER PRIMARY KEY autoincrement, "+CAMPO_FECHA+" TEXT, "+CAMPO_FECHA_INT+" INT, "+CAMPO_TIPO_OPER+" INTEGER,"+CAMPO_TIPO_CAT+" INTEGER,"+CAMPO_ID_CTA+" INTEGER,"+CAMPO_MONTO+ " DECIMAL(10,5), "+CAMPO_DESCRIPCION +" TEXT"+")";


    public static final String TABLA_CATEGORIAS="CATEGORIAS";
    public static final String CAT_CAMPO_ID="id";
    public static final String CAT_CAMPO_DESCRIPCION="DESCRIPCION";
    public static final String CAT_CAMPO_TIPO="TIPO_TRANSACCION";
    public static final String CAT_CAMPO_DRAWABLE="DRAWABLE";
    public static final String CAT_CAMPO_COLOR="COLOR";

    public static final String CREAR_TABLA_CATEGORIAS="CREATE TABLE "+TABLA_CATEGORIAS+"  ("+CAT_CAMPO_ID+" INTEGER PRIMARY KEY autoincrement, "+CAT_CAMPO_DESCRIPCION+" TEXT, "+ CAT_CAMPO_TIPO+" INTEGER, "+CAT_CAMPO_DRAWABLE+" TEXT, "+CAT_CAMPO_COLOR+" TEXT"+")";

    public static final String TABLA_CUENTAS="CUENTAS";
    public static final String CTA_CAMPO_ID="id";
    public static final String CTA_CAMPO_DESCRIPCION="DESCRIPCION";
    public static final String CTA_CAMPO_TIPO_CUENTA="TIPO_CUENTA";
    public static final String CTA_CAMPO_NRO_CTA="NRO_CTA";
    public static final String CTA_CAMPO_SALDO="SALDO";

    public static final String CREAR_TABLA_CUENTAS="CREATE TABLE "+TABLA_CUENTAS+"  ("+CTA_CAMPO_ID+" INTEGER PRIMARY KEY autoincrement, "+CTA_CAMPO_DESCRIPCION+" TEXT, "+CTA_CAMPO_TIPO_CUENTA+" TEXT, "+CTA_CAMPO_NRO_CTA+" TEXT,"+CTA_CAMPO_SALDO+" DECIMAL(10,5) )";

    //Insert de la tabla Cuenta por defecto
    public static final String INSERT_CUENTA="INSERT INTO "+TABLA_CUENTAS+"("+CTA_CAMPO_DESCRIPCION+","+CTA_CAMPO_TIPO_CUENTA+","+CTA_CAMPO_NRO_CTA+","+CTA_CAMPO_SALDO+")"+" VALUES("+" 'EFECTIVO', "+" 'EFECTIVO',"+" 'EFECTIVO' ,"+5000+")";
    public static final String INSERT_CUENTA1="INSERT INTO "+TABLA_CUENTAS+"("+CTA_CAMPO_DESCRIPCION+","+CTA_CAMPO_TIPO_CUENTA+","+CTA_CAMPO_NRO_CTA+","+CTA_CAMPO_SALDO+")"+" VALUES("+" 'BANCO PICHINCHA', "+" 'CORRIENTE',"+" '01236547' ,"+1000+")";
    public static final String INSERT_CUENTA2="INSERT INTO "+TABLA_CUENTAS+"("+CTA_CAMPO_DESCRIPCION+","+CTA_CAMPO_TIPO_CUENTA+","+CTA_CAMPO_NRO_CTA+","+CTA_CAMPO_SALDO+")"+" VALUES("+" 'BANCO PRODUBANCO', "+" 'CORRIENTE',"+" '05234237' ,"+2000+")";
    public static final String INSERT_CUENTA3="INSERT INTO "+TABLA_CUENTAS+"("+CTA_CAMPO_DESCRIPCION+","+CTA_CAMPO_TIPO_CUENTA+","+CTA_CAMPO_NRO_CTA+","+CTA_CAMPO_SALDO+")"+" VALUES("+" 'BANCO GUAYAQUIL', "+" 'CORRIENTE',"+" '01236547' ,"+3000+")";

    // insert de la tabla de operaciones
    //Ingresos
    public static final String INSERT_OPERACIONES0="INSERT INTO "+TABLA_OPERACIONES+"("+CAMPO_FECHA+","+CAMPO_FECHA_INT+","+  CAMPO_TIPO_OPER  +","+CAMPO_TIPO_CAT+","+CAMPO_ID_CTA+","+ CAMPO_MONTO+","+CAMPO_DESCRIPCION+")"+" VALUES("+"'01/01/2020'"+","+20200101+","+1+","+1+","+1+","+70+","+"'Descripcion'"+")";
    public static final String INSERT_OPERACIONES1="INSERT INTO "+TABLA_OPERACIONES+"("+CAMPO_FECHA+","+CAMPO_FECHA_INT+","+  CAMPO_TIPO_OPER  +","+CAMPO_TIPO_CAT+","+CAMPO_ID_CTA+","+ CAMPO_MONTO+","+CAMPO_DESCRIPCION+")"+" VALUES("+"'02/01/2020'"+","+20200102+","+1+","+2+","+1+","+18+","+"'Descripcion'"+")";
    public static final String INSERT_OPERACIONES2="INSERT INTO "+TABLA_OPERACIONES+"("+CAMPO_FECHA+","+CAMPO_FECHA_INT+","+  CAMPO_TIPO_OPER  +","+CAMPO_TIPO_CAT+","+CAMPO_ID_CTA+","+ CAMPO_MONTO+","+CAMPO_DESCRIPCION+")"+" VALUES("+"'03/01/2020'"+","+20200103+","+1+","+3+","+1+","+20+","+"'Descripcion'"+")";
    public static final String INSERT_OPERACIONES3="INSERT INTO "+TABLA_OPERACIONES+"("+CAMPO_FECHA+","+CAMPO_FECHA_INT+","+  CAMPO_TIPO_OPER  +","+CAMPO_TIPO_CAT+","+CAMPO_ID_CTA+","+ CAMPO_MONTO+","+CAMPO_DESCRIPCION+")"+" VALUES("+"'06/02/2020'"+","+20200204+","+1+","+1+","+1+","+300+","+"'Descripcion'"+")";
    public static final String INSERT_OPERACIONES4="INSERT INTO "+TABLA_OPERACIONES+"("+CAMPO_FECHA+","+CAMPO_FECHA_INT+","+  CAMPO_TIPO_OPER  +","+CAMPO_TIPO_CAT+","+CAMPO_ID_CTA+","+ CAMPO_MONTO+","+CAMPO_DESCRIPCION+")"+" VALUES("+"'08/03/2020'"+","+20200305+","+1+","+4+","+1+","+60+","+"'Descripcion'"+")";
    //Egresos
    public static final String INSERT_OPERACIONES10="INSERT INTO "+TABLA_OPERACIONES+"("+CAMPO_FECHA+","+CAMPO_FECHA_INT+","+  CAMPO_TIPO_OPER  +","+CAMPO_TIPO_CAT+","+CAMPO_ID_CTA+","+ CAMPO_MONTO+","+CAMPO_DESCRIPCION+")"+" VALUES("+"'01/01/2020'"+","+20200101+","+2+","+1+","+1+","+20+","+"'Descripcion'"+")";
    public static final String INSERT_OPERACIONES11="INSERT INTO "+TABLA_OPERACIONES+"("+CAMPO_FECHA+","+CAMPO_FECHA_INT+","+  CAMPO_TIPO_OPER  +","+CAMPO_TIPO_CAT+","+CAMPO_ID_CTA+","+ CAMPO_MONTO+","+CAMPO_DESCRIPCION+")"+" VALUES("+"'02/01/2020'"+","+20200102+","+2+","+2+","+1+","+450+","+"'Descripcion'"+")";
    public static final String INSERT_OPERACIONES12="INSERT INTO "+TABLA_OPERACIONES+"("+CAMPO_FECHA+","+CAMPO_FECHA_INT+","+  CAMPO_TIPO_OPER  +","+CAMPO_TIPO_CAT+","+CAMPO_ID_CTA+","+ CAMPO_MONTO+","+CAMPO_DESCRIPCION+")"+" VALUES("+"'03/02/2020'"+","+20200203+","+2+","+3+","+1+","+190+","+"'Descripcion'"+")";
    public static final String INSERT_OPERACIONES13="INSERT INTO "+TABLA_OPERACIONES+"("+CAMPO_FECHA+","+CAMPO_FECHA_INT+","+  CAMPO_TIPO_OPER  +","+CAMPO_TIPO_CAT+","+CAMPO_ID_CTA+","+ CAMPO_MONTO+","+CAMPO_DESCRIPCION+")"+" VALUES("+"'07/02/2020'"+","+20200204+","+2+","+4+","+1+","+300+","+"'Descripcion'"+")";
    public static final String INSERT_OPERACIONES14="INSERT INTO "+TABLA_OPERACIONES+"("+CAMPO_FECHA+","+CAMPO_FECHA_INT+","+  CAMPO_TIPO_OPER  +","+CAMPO_TIPO_CAT+","+CAMPO_ID_CTA+","+ CAMPO_MONTO+","+CAMPO_DESCRIPCION+")"+" VALUES("+"'08/03/2020'"+","+20200305+","+2+","+5+","+1+","+100+","+"'Descripcion'"+")";
    //Transferencias y retiro
    public static final String INSERT_OPERACIONES20="INSERT INTO "+TABLA_OPERACIONES+"("+CAMPO_FECHA+","+CAMPO_FECHA_INT+","+  CAMPO_TIPO_OPER  +","+CAMPO_TIPO_CAT+","+CAMPO_ID_CTA+","+ CAMPO_MONTO+","+CAMPO_DESCRIPCION+")"+" VALUES("+"'01/01/2020'"+","+20200101+","+3+","+1+","+1+","+20+","+"'Descripcion'"+")";
    public static final String INSERT_OPERACIONES21="INSERT INTO "+TABLA_OPERACIONES+"("+CAMPO_FECHA+","+CAMPO_FECHA_INT+","+  CAMPO_TIPO_OPER  +","+CAMPO_TIPO_CAT+","+CAMPO_ID_CTA+","+ CAMPO_MONTO+","+CAMPO_DESCRIPCION+")"+" VALUES("+"'02/01/2020'"+","+20200102+","+3+","+1+","+1+","+200+","+"'Descripcion'"+")";
    public static final String INSERT_OPERACIONES22="INSERT INTO "+TABLA_OPERACIONES+"("+CAMPO_FECHA+","+CAMPO_FECHA_INT+","+  CAMPO_TIPO_OPER  +","+CAMPO_TIPO_CAT+","+CAMPO_ID_CTA+","+ CAMPO_MONTO+","+CAMPO_DESCRIPCION+")"+" VALUES("+"'03/02/2020'"+","+20200203+","+3+","+1+","+1+","+190+","+"'Descripcion'"+")";
    public static final String INSERT_OPERACIONES23="INSERT INTO "+TABLA_OPERACIONES+"("+CAMPO_FECHA+","+CAMPO_FECHA_INT+","+  CAMPO_TIPO_OPER  +","+CAMPO_TIPO_CAT+","+CAMPO_ID_CTA+","+ CAMPO_MONTO+","+CAMPO_DESCRIPCION+")"+" VALUES("+"'07/02/2020'"+","+20200204+","+3+","+2+","+1+","+300+","+"'Descripcion'"+")";
    public static final String INSERT_OPERACIONES24="INSERT INTO "+TABLA_OPERACIONES+"("+CAMPO_FECHA+","+CAMPO_FECHA_INT+","+  CAMPO_TIPO_OPER  +","+CAMPO_TIPO_CAT+","+CAMPO_ID_CTA+","+ CAMPO_MONTO+","+CAMPO_DESCRIPCION+")"+" VALUES("+"'08/03/2020'"+","+20200305+","+3+","+3+","+1+","+100+","+"'Descripcion'"+")";

    //Insert de Categorias
    public static final String INSERT_CAT0="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO  +","+CAT_CAMPO_DRAWABLE+","+CAT_CAMPO_COLOR +")"+" VALUES("+"'Sueldo',"+1+", 'ic_cat_sueldo', 'custom_button_1')";
  // public static final String INSERT_CAT1="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO  +","+CAT_CAMPO_DRAWABLE+","+CAT_CAMPO_COLOR +")"+" VALUES("+"'Regalos',"+1+", 'ic_cat_regalos', 'custom_button_2')";
   // public static final String INSERT_CAT2="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO  +","+CAT_CAMPO_DRAWABLE+","+CAT_CAMPO_COLOR +")"+" VALUES("+"'Premios',"+1+", 'ic_cat_premios', 'custom_button_3')";
    public static final String INSERT_CAT3="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO  +","+CAT_CAMPO_DRAWABLE+","+CAT_CAMPO_COLOR +")"+" VALUES("+"'Varios',"+1+", 'ic_cat_ventas', 'custom_button_4')";
    public static final String INSERT_CAT4="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO  +","+CAT_CAMPO_DRAWABLE+","+CAT_CAMPO_COLOR +")"+" VALUES("+"'Negocios',"+1+", 'ic_cat_inversiones', 'custom_button_5')";

    public static final String INSERT_CAT10="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO +","+CAT_CAMPO_DRAWABLE+","+CAT_CAMPO_COLOR  +")"+" VALUES("+"'Transporte',"+2+", 'ic_cat_transporte', 'custom_button_6')";
    public static final String INSERT_CAT11="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO  +","+CAT_CAMPO_DRAWABLE+","+CAT_CAMPO_COLOR +")"+" VALUES("+"'Comida',"+2+", 'ic_cat_comida', 'custom_button_7')";
    public static final String INSERT_CAT12="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO +","+CAT_CAMPO_DRAWABLE+","+CAT_CAMPO_COLOR  +")"+" VALUES("+"'Salud',"+2+", 'ic_cat_salud', 'custom_button_8')";
    public static final String INSERT_CAT13="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO +","+CAT_CAMPO_DRAWABLE+","+CAT_CAMPO_COLOR  +")"+" VALUES("+"'Vivienda',"+2+", 'ic_cat_vivienda', 'custom_button_9')";
    public static final String INSERT_CAT14="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO  +","+CAT_CAMPO_DRAWABLE+","+CAT_CAMPO_COLOR +")"+" VALUES("+"'Educacion',"+2+", 'ic_cat_educacion', 'custom_button_5')";
    public static final String INSERT_CAT15="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO  +","+CAT_CAMPO_DRAWABLE+","+CAT_CAMPO_COLOR +")"+" VALUES("+"'Deudas',"+2+", 'ic_cat_educacion', 'custom_button_5')";
    public static final String INSERT_CAT16="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO  +","+CAT_CAMPO_DRAWABLE+","+CAT_CAMPO_COLOR +")"+" VALUES("+"'Servicios',"+2+", 'ic_cat_educacion', 'custom_button_5')";
    public static final String INSERT_CAT17="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO  +","+CAT_CAMPO_DRAWABLE+","+CAT_CAMPO_COLOR +")"+" VALUES("+"'Ahorro',"+2+", 'ic_cat_educacion', 'custom_button_5')";
    public static final String INSERT_CAT18="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO  +","+CAT_CAMPO_DRAWABLE+","+CAT_CAMPO_COLOR +")"+" VALUES("+"'Otros',"+2+", 'ic_cat_educacion', 'custom_button_5')";
    //public static final String INSERT_CAT15="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO  +")"+" VALUES("+"'Ropa',"+2+" 'ic_cat_ropa', 'custom_button_6')";
   // public static final String INSERT_CAT16="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO  +")"+" VALUES("+"'Deudas',"+2+" 'ic_cat_pagos', 'custom_button_7')";

    public static final String INSERT_CAT20="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO  +","+CAT_CAMPO_DRAWABLE+","+CAT_CAMPO_COLOR +")"+" VALUES("+"'Transferencia',"+3+", 'ic_transferencia_cat', 'custom_button_1')";
    public static final String INSERT_CAT21="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO  +","+CAT_CAMPO_DRAWABLE+","+CAT_CAMPO_COLOR +")"+" VALUES("+"'Deposito',"+3+", 'ic_deposito_cat', 'custom_button_2')";
    public static final String INSERT_CAT22="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO  +","+CAT_CAMPO_DRAWABLE+","+CAT_CAMPO_COLOR +")"+" VALUES("+"'Retiro',"+3+", 'ic_retiro_cat', 'custom_button_3')";



    public static void obtenerListaAvatars() {

        //se instancian los avatars y se llena la lista
        listaAvatars=new ArrayList<AvatarVo>();

        listaAvatars.add(new AvatarVo(1,R.drawable.avatar1,"Avatar1"));
        listaAvatars.add(new AvatarVo(2, R.drawable.avatar2,"Avatar2"));
        listaAvatars.add(new AvatarVo(3,R.drawable.avatar3,"Avatar3"));
        listaAvatars.add(new AvatarVo(4,R.drawable.avatar4,"Avatar4"));
        listaAvatars.add(new AvatarVo(5,R.drawable.avatar5,"Avatar5"));
        listaAvatars.add(new AvatarVo(6,R.drawable.avatar6,"Avatar6"));
        listaAvatars.add(new AvatarVo(7,R.drawable.avatar7,"Avatar7"));
        listaAvatars.add(new AvatarVo(8,R.drawable.avatar8,"Avatar8"));
        listaAvatars.add(new AvatarVo(9,R.drawable.avatar9,"Avatar9"));
        listaAvatars.add(new AvatarVo(10,R.drawable.avatar10,"Avatar10"));
        listaAvatars.add(new AvatarVo(11,R.drawable.avatar11,"Avatar11"));
        listaAvatars.add(new AvatarVo(12,R.drawable.avatar12,"Avatar12"));
        listaAvatars.add(new AvatarVo(13,R.drawable.avatar13,"Avatar13"));
        listaAvatars.add(new AvatarVo(14,R.drawable.avatar14,"Avatar14"));
        listaAvatars.add(new AvatarVo(15,R.drawable.avatar15,"Avatar15"));
        listaAvatars.add(new AvatarVo(16,R.drawable.avatar16,"Avatar16"));
        listaAvatars.add(new AvatarVo(17,R.drawable.avatar17,"Avatar17"));
        listaAvatars.add(new AvatarVo(18,R.drawable.avatar18,"Avatar18"));
        listaAvatars.add(new AvatarVo(19,R.drawable.avatar19,"Avatar19"));
        listaAvatars.add(new AvatarVo(20,R.drawable.avatar20,"Avatar20"));
        listaAvatars.add(new AvatarVo(21,R.drawable.avatar21,"Avatar21"));
        listaAvatars.add(new AvatarVo(22,R.drawable.avatar22,"Avatar22"));
        listaAvatars.add(new AvatarVo(23,R.drawable.avatar23,"Avatar23"));
        listaAvatars.add(new AvatarVo(24,R.drawable.avatar24,"Avatar24"));
        listaAvatars.add(new AvatarVo(25,R.drawable.avatar25,"Avatar25"));
        listaAvatars.add(new AvatarVo(26,R.drawable.avatar26,"Avatar26"));
        listaAvatars.add(new AvatarVo(27,R.drawable.avatar27,"Avatar27"));

    }
    public static void setColors(){


    listColores = new ArrayList<Integer>();
        listColores.add(0xFF0000FF);
        listColores.add(0xFFFF00FF);
        listColores.add(0xFF00FF00);
        listColores.add(0xFFCCCCCC);
        listColores.add(0xFF00FFFF);
        listColores.add(0xFFFF0000);
        listColores.add(0xFF00FF00);
        listColores.add(0xFF0000FF);
        listColores.add(0xFFFFFF00);
        listColores.add(0xFF00FFFF);
        listColores.add(0xFFFF00FF);
        listColores.add(0xFF0000FF);
        listColores.add(0xFFFF00FF);
        listColores.add(0xFF00FF00);
        listColores.add(0xFFCCCCCC);
        listColores.add(0xFF00FFFF);
        listColores.add(0xFFFF0000);
        listColores.add(0xFF00FF00);
        listColores.add(0xFF0000FF);
        listColores.add(0xFFFFFF00);
        listColores.add(0xFF00FFFF);
        listColores.add(0xFFFF00FF);
        listColores.add(0xFF0000FF);
        listColores.add(0xFFFF00FF);
        listColores.add(0xFF00FF00);
        listColores.add(0xFFCCCCCC);
        listColores.add(0xFF00FFFF);
        listColores.add(0xFFFF0000);
        listColores.add(0xFF00FF00);
        listColores.add(0xFF0000FF);
        listColores.add(0xFFFFFF00);
        listColores.add(0xFF00FFFF);
        listColores.add(0xFFFF00FF);
    }
    public static int getColorsbyid(int i){

        return listColores.get(i);
    }
    public static void consultarCategorias(Context actividad, String consultaSQL){
        try {
            conn = new ConexionSQLiteHelper(actividad, Utilidades.NOMBRE_BD, null, 1);
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
           // obtenerListaCat();

        } catch (SQLiteException | IllegalStateException | NullPointerException e) {
            e.printStackTrace();
        }
    }
    public static void consultarListaIngresos(Context actividad, String transaccioneSQL){
        try {
            conn = new ConexionSQLiteHelper(actividad, Utilidades.NOMBRE_BD, null, 1);
            SQLiteDatabase db = conn.getReadableDatabase();
            transaccioneSQL= Utilidades.qryTransaccionesbyMes(1);
            Transaccion ingreso = null;
            listaingresos = new ArrayList<Transaccion>();
            Cursor cursor = db.rawQuery(transaccioneSQL, null);
            int i = 0;
            while (cursor.moveToNext()) {
                i =i+1;
                ingreso = new Transaccion();
                ingreso.setId(i);
                ingreso.setDescripcion(cursor.getString(1));

                ingreso.setMonto_operacion(cursor.getDouble(0));
                listaingresos.add(ingreso);
                //mapLista.put(cursor.getPosition(),cursor.getInt(0));

            }

            //obtenerListaIngresos();
        }catch (SQLiteException | IllegalStateException | NullPointerException e) {
            e.printStackTrace();
        }
    }
    public static void consultarTransaccionesbycategoria(Context actividad, String SQL){
        try {
            conn = new ConexionSQLiteHelper(actividad, Utilidades.NOMBRE_BD, null, 1);
            SQLiteDatabase db = conn.getReadableDatabase();
            String queryCAt =Utilidades.qryTrnbyMesbyCat();
            Transaccion transaccion = null;
            listatransacciones = new ArrayList<Transaccion>();
            Cursor cursorCat = db.rawQuery(queryCAt, null);
            int i = 0;
            while (cursorCat.moveToNext()) {
                i = i+1;
                transaccion = new Transaccion();
                transaccion.setId(i);
                transaccion.setDescripcion(cursorCat.getString(1));
                transaccion.setMonto_operacion(cursorCat.getDouble(0));
                transaccion.setTipo_cat(cursorCat.getString(2));
                listatransacciones.add(transaccion);

            }

            //obtenerListaIngresos();
        }catch (SQLiteException | IllegalStateException | NullPointerException e) {
            e.printStackTrace();
        }
    }
    public static void consultarListaEgresos(Context actividad, String semestreEgresosISQL){
        try {
            conn = new ConexionSQLiteHelper(actividad, Utilidades.NOMBRE_BD, null, 1);
            SQLiteDatabase db = conn.getReadableDatabase();
            semestreEgresosISQL= Utilidades.qryTransaccionesbyMes(2);
            Transaccion Egreso = null;
            listaEgresos = new ArrayList<Transaccion>();
            Cursor cursor = db.rawQuery(semestreEgresosISQL, null);
            int i = 0;
            while (cursor.moveToNext()) {
                i = i++;
                Egreso = new Transaccion();
                Egreso.setId(i);
                //Egreso.setFecha(cursor.getString(1));
                //Egreso.setFecha_int(cursor.getInt(2));
                Egreso.setDescripcion(cursor.getString(1));
                Egreso.setMonto_operacion(cursor.getDouble(0));
                listaEgresos.add(Egreso);
                // mapLista.put(cursor.getPosition(),cursor.getInt(0));

            }

            //obtenerListaEgresos();
        }catch (SQLiteException | IllegalStateException | NullPointerException e) {
            e.printStackTrace();
        }
    }
    public static void consultarListaSaldosCuentas(Context actividad, String SQL){
        try {
            conn = new ConexionSQLiteHelper(actividad, Utilidades.NOMBRE_BD, null, 1);
            SQLiteDatabase db = conn.getReadableDatabase();
            SQL= Utilidades.qrySaldoCuentas();
            Cuenta Saldo = null;
            listaSaldos = new ArrayList<Cuenta>();
            Cursor cursor = db.rawQuery(SQL, null);
            int i = 0;
            while (cursor.moveToNext()) {
                i = i+1;
                Saldo = new Cuenta();
                Saldo.setId(i);
                Saldo.setDESCRIPCION(cursor.getString(1));
                Saldo.setSALDO(cursor.getDouble(4));
                listaSaldos.add(Saldo);

            }

            //obtenerListaEgresos();
        }catch (SQLiteException | IllegalStateException | NullPointerException e) {
            e.printStackTrace();
        }
    }
    public static String qryTransaccionesbyMes(int tipoper){
       String SQL=" ";
        String[] mes={"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
       for(int i=1;i <= 6; i++){
        if (i !=1){
            SQL = SQL+" UNION ALL \n";
        }
            SQL = SQL+ " SELECT SUM(T"+i+".MONTO_OPERACION), '"+mes[i-1]+"' \n";
            SQL = SQL +" FROM TRANSACCIONES T"+i+" \n" ;
            SQL = SQL +       " WHERE 1 =1\n";
           SQL = SQL+" AND substr(T"+i+".FECHA,4,2)='0"+i+"'\n";
           SQL = SQL+" AND substr(T"+i+".FECHA,7,4)='2020'\n";
           SQL = SQL+" AND T"+i+".TIPO_OPERACION ="+tipoper+ "\n";

       }
        Log.d("SQL",SQL);
        return SQL;
    }
    public static String qryTrnbyMesbyCat(){
        int mes = Calendar.getInstance().get(Calendar.MONTH);
        int anio = Calendar.getInstance().get(Calendar.YEAR);
        mes = mes +1;
        String SQL="";
        SQL = SQL+ "SELECT SUM("+Utilidades.CAMPO_MONTO+"), CATEGORIAS.DESCRIPCION, CATEGORIAS.DRAWABLE  \n";
        SQL = SQL +" FROM "+Utilidades.TABLA_OPERACIONES+" \n" ;
        SQL = SQL +" INNER JOIN CATEGORIAS ON CATEGORIAS.id= TRANSACCIONES.TIPO_CATEGORIA  \n" ;
        SQL = SQL +" WHERE 1 =1 \n";
        SQL = SQL+" AND substr(FECHA,4,2)='0"+mes+"'\n";
        SQL = SQL+" AND substr(FECHA,7,4)='"+anio+"'\n";
        SQL = SQL+" GROUP BY "+Utilidades.CAMPO_TIPO_CAT+ " \n";
        SQL = SQL+" ORDER BY "+Utilidades.CAMPO_TIPO_CAT+" ASC"+ " ";


        Log.d("SQL",SQL);
        return SQL;

    }
    public static String qrySaldoCuentas(){
        String SQL="SELECT * FROM CUENTAS";

        return SQL;
    }

}
