package com.capcenter.ec.myprofitbalance.io;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Utilidades {
    public static final String DATE_FORMAT_1 = "dd/MM/yyyy";

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
    public static final String NOMBRE_BD="myprofitbd.db";
    public static final int VERSION_DB=14;
    public static final String RUTA_APP="com.capcenter.ec.myprofitbalance:drawable/";

    //tablas
    public static final String TABLA_OPERACIONES= "TRANSACCIONES";
    public static final String CAMPO_ID="id";
    public static final String CAMPO_FECHA="FECHA";
    public static final String CAMPO_TIPO_OPER="TIPO_OPERACION";
    public static final String CAMPO_TIPO_CAT="TIPO_CATEGORIA";
    public static final String CAMPO_ID_CTA="ID_CTA";
    public static final String CAMPO_MONTO="MONTO_OPERACION";
    public static final String CAMPO_DESCRIPCION="DESCRIPCION";

    public static final String CREAR_TABLA_OPERACIONES ="CREATE TABLE "+TABLA_OPERACIONES+"  ("+CAMPO_ID+" INTEGER PRIMARY KEY autoincrement, "+CAMPO_FECHA+" TEXT, "+CAMPO_TIPO_OPER+" INTEGER,"+CAMPO_TIPO_CAT+" INTEGER,"+CAMPO_ID_CTA+" INTEGER,"+CAMPO_MONTO+ " DECIMAL(10,5), "+CAMPO_DESCRIPCION +" TEXT"+")";


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

    //Insert de la tablla Cuenta por defecto
    public static final String INSERT_CUENTA="INSERT INTO "+TABLA_CUENTAS+"("+CTA_CAMPO_DESCRIPCION+","+CTA_CAMPO_TIPO_CUENTA+","+CTA_CAMPO_NRO_CTA+","+CTA_CAMPO_SALDO+")"+" VALUES("+" 'EFECTIVO', "+" 'EFECTIVO',"+" 'EFECTIVO' ,"+0+")";


    // insert de la tabla de operaciones
    //Ingresos
    public static final String INSERT_OPERACIONES0="INSERT INTO "+TABLA_OPERACIONES+"("+CAMPO_FECHA+","+  CAMPO_TIPO_OPER  +","+CAMPO_TIPO_CAT+","+CAMPO_ID_CTA+","+ CAMPO_MONTO+","+CAMPO_DESCRIPCION+")"+" VALUES("+"'01/01/2020',"+1+","+1+","+1+","+700+","+"'Descripcion'"+")";
    public static final String INSERT_OPERACIONES1="INSERT INTO "+TABLA_OPERACIONES+"("+CAMPO_FECHA+","+  CAMPO_TIPO_OPER  +","+CAMPO_TIPO_CAT+","+CAMPO_ID_CTA+","+ CAMPO_MONTO+","+CAMPO_DESCRIPCION+")"+" VALUES("+"'02/01/2020',"+1+","+2+","+1+","+180+","+"'Descripcion'"+")";
    public static final String INSERT_OPERACIONES2="INSERT INTO "+TABLA_OPERACIONES+"("+CAMPO_FECHA+","+  CAMPO_TIPO_OPER  +","+CAMPO_TIPO_CAT+","+CAMPO_ID_CTA+","+ CAMPO_MONTO+","+CAMPO_DESCRIPCION+")"+" VALUES("+"'03/01/2020',"+1+","+3+","+1+","+20+","+"'Descripcion'"+")";
    public static final String INSERT_OPERACIONES3="INSERT INTO "+TABLA_OPERACIONES+"("+CAMPO_FECHA+","+  CAMPO_TIPO_OPER  +","+CAMPO_TIPO_CAT+","+CAMPO_ID_CTA+","+ CAMPO_MONTO+","+CAMPO_DESCRIPCION+")"+" VALUES("+"'06/01/2020',"+1+","+1+","+1+","+3+","+"'Descripcion'"+")";
    public static final String INSERT_OPERACIONES4="INSERT INTO "+TABLA_OPERACIONES+"("+CAMPO_FECHA+","+  CAMPO_TIPO_OPER  +","+CAMPO_TIPO_CAT+","+CAMPO_ID_CTA+","+ CAMPO_MONTO+","+CAMPO_DESCRIPCION+")"+" VALUES("+"'08/01/2020',"+1+","+4+","+1+","+600+","+"'Descripcion'"+")";
    //Egresos
    public static final String INSERT_OPERACIONES10="INSERT INTO "+TABLA_OPERACIONES+"("+CAMPO_FECHA+","+  CAMPO_TIPO_OPER  +","+CAMPO_TIPO_CAT+","+CAMPO_ID_CTA+","+ CAMPO_MONTO+","+CAMPO_DESCRIPCION+")"+" VALUES("+"'01/01/2020',"+2+","+1+","+1+","+20+","+"'Descripcion'"+")";
    public static final String INSERT_OPERACIONES11="INSERT INTO "+TABLA_OPERACIONES+"("+CAMPO_FECHA+","+  CAMPO_TIPO_OPER  +","+CAMPO_TIPO_CAT+","+CAMPO_ID_CTA+","+ CAMPO_MONTO+","+CAMPO_DESCRIPCION+")"+" VALUES("+"'02/01/2020',"+2+","+2+","+1+","+200+","+"'Descripcion'"+")";
    public static final String INSERT_OPERACIONES12="INSERT INTO "+TABLA_OPERACIONES+"("+CAMPO_FECHA+","+  CAMPO_TIPO_OPER  +","+CAMPO_TIPO_CAT+","+CAMPO_ID_CTA+","+ CAMPO_MONTO+","+CAMPO_DESCRIPCION+")"+" VALUES("+"'03/01/2020',"+2+","+3+","+1+","+19+","+"'Descripcion'"+")";
    public static final String INSERT_OPERACIONES13="INSERT INTO "+TABLA_OPERACIONES+"("+CAMPO_FECHA+","+  CAMPO_TIPO_OPER  +","+CAMPO_TIPO_CAT+","+CAMPO_ID_CTA+","+ CAMPO_MONTO+","+CAMPO_DESCRIPCION+")"+" VALUES("+"'07/01/2020',"+2+","+4+","+1+","+3+","+"'Descripcion'"+")";
    public static final String INSERT_OPERACIONES14="INSERT INTO "+TABLA_OPERACIONES+"("+CAMPO_FECHA+","+  CAMPO_TIPO_OPER  +","+CAMPO_TIPO_CAT+","+CAMPO_ID_CTA+","+ CAMPO_MONTO+","+CAMPO_DESCRIPCION+")"+" VALUES("+"'08/01/2020',"+2+","+5+","+1+","+100+","+"'Descripcion'"+")";
    //Insert de Categorias
    public static final String INSERT_CAT0="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO  +","+CAT_CAMPO_DRAWABLE+","+CAT_CAMPO_COLOR +")"+" VALUES("+"'Sueldo',"+1+", 'ic_cat_sueldo', 'custom_button_1')";
    public static final String INSERT_CAT1="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO  +","+CAT_CAMPO_DRAWABLE+","+CAT_CAMPO_COLOR +")"+" VALUES("+"'Regalos',"+1+", 'ic_cat_regalos', 'custom_button_2')";
    public static final String INSERT_CAT2="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO  +","+CAT_CAMPO_DRAWABLE+","+CAT_CAMPO_COLOR +")"+" VALUES("+"'Premios',"+1+", 'ic_cat_premios', 'custom_button_3')";
    public static final String INSERT_CAT3="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO  +","+CAT_CAMPO_DRAWABLE+","+CAT_CAMPO_COLOR +")"+" VALUES("+"'Ventas',"+1+", 'ic_cat_ventas', 'custom_button_4')";
    public static final String INSERT_CAT4="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO  +","+CAT_CAMPO_DRAWABLE+","+CAT_CAMPO_COLOR +")"+" VALUES("+"'Inversiones',"+1+", 'ic_cat_inversiones', 'custom_button_5')";

    public static final String INSERT_CAT10="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO +","+CAT_CAMPO_DRAWABLE+","+CAT_CAMPO_COLOR  +")"+" VALUES("+"'Transporte',"+2+", 'ic_cat_transporte', 'custom_button_6')";
    public static final String INSERT_CAT11="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO  +","+CAT_CAMPO_DRAWABLE+","+CAT_CAMPO_COLOR +")"+" VALUES("+"'Comida',"+2+", 'ic_cat_comida', 'custom_button_7')";
    public static final String INSERT_CAT12="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO +","+CAT_CAMPO_DRAWABLE+","+CAT_CAMPO_COLOR  +")"+" VALUES("+"'Salud',"+2+", 'ic_cat_salud', 'custom_button_8')";
    public static final String INSERT_CAT13="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO +","+CAT_CAMPO_DRAWABLE+","+CAT_CAMPO_COLOR  +")"+" VALUES("+"'Vivienda',"+2+", 'ic_cat_vivienda', 'custom_button_9')";
    public static final String INSERT_CAT14="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO  +","+CAT_CAMPO_DRAWABLE+","+CAT_CAMPO_COLOR +")"+" VALUES("+"'Educacion',"+2+", 'ic_cat_educacion', 'custom_button_5')";
    //public static final String INSERT_CAT15="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO  +")"+" VALUES("+"'Ropa',"+2+" 'ic_cat_ropa', 'custom_button_6')";
   // public static final String INSERT_CAT16="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO  +")"+" VALUES("+"'Pagos',"+2+" 'ic_cat_pagos', 'custom_button_7')";

    public static final String INSERT_CAT20="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO  +","+CAT_CAMPO_DRAWABLE+","+CAT_CAMPO_COLOR +")"+" VALUES("+"'Transferencia',"+3+", 'ic_cat_transferencia', 'custom_button_1')";
    public static final String INSERT_CAT21="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO  +","+CAT_CAMPO_DRAWABLE+","+CAT_CAMPO_COLOR +")"+" VALUES("+"'Deposito',"+3+", 'ic_cat_deposito', 'custom_button_2')";
    public static final String INSERT_CAT22="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO  +","+CAT_CAMPO_DRAWABLE+","+CAT_CAMPO_COLOR +")"+" VALUES("+"'Retiro',"+3+", 'ic_cat_retiro', 'custom_button_3')";

}
