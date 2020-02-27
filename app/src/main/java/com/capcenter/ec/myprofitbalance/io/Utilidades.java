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
    public static final int VERSION_DB=11;

    //tablas
    public static final String TABLA_OPERACIONES= "TRANSACCIONES";
    public static final String CAMPO_ID="id";
    public static final String CAMPO_FECHA="FECHA";
    public static final String CAMPO_TIPO_OPER="TIPO_OPERACION";
    public static final String CAMPO_TIPO_CAT="TIPO_CATEGORIA";
    public static final String CAMPO_ID_CTA="ID_CTA";
    public static final String CAMPO_MONTO="MONTO_OPERACION";

    public static final String CREAR_TABLA_OPERACIONES ="CREATE TABLE "+TABLA_OPERACIONES+"  ("+CAMPO_ID+" INTEGER PRIMARY KEY autoincrement, "+CAMPO_FECHA+" TEXT, "+CAMPO_TIPO_OPER+" INTEGER,"+CAMPO_TIPO_CAT+" INTEGER,"+CAMPO_ID_CTA+" INTEGER,"+CAMPO_MONTO+ " TEXT)";


    public static final String TABLA_CATEGORIAS="CATEGORIAS";
    public static final String CAT_CAMPO_ID="id";
    public static final String CAT_CAMPO_DESCRIPCION="DESCRIPCION";
    public static final String CAT_CAMPO_TIPO="TIPO_TRANSACCION";

    public static final String CREAR_TABLA_CATEGORIAS="CREATE TABLE "+TABLA_CATEGORIAS+"  ("+CAT_CAMPO_ID+" INTEGER PRIMARY KEY autoincrement, "+CAT_CAMPO_DESCRIPCION+" TEXT, "+ CAT_CAMPO_TIPO+" INTEGER)";

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
    public static final String INSERT_OPERACIONES0="INSERT INTO "+TABLA_OPERACIONES+"("+CAMPO_FECHA+","+  CAMPO_TIPO_OPER  +","+CAMPO_TIPO_CAT+","+CAMPO_ID_CTA+","+ CAMPO_MONTO+")"+" VALUES("+"'01/01/2020',"+1+","+1+","+1+","+700+")";
    public static final String INSERT_OPERACIONES1="INSERT INTO "+TABLA_OPERACIONES+"("+CAMPO_FECHA+","+  CAMPO_TIPO_OPER  +","+CAMPO_TIPO_CAT+","+CAMPO_ID_CTA+","+ CAMPO_MONTO+")"+" VALUES("+"'02/01/2020',"+1+","+2+","+1+","+180+")";
    public static final String INSERT_OPERACIONES2="INSERT INTO "+TABLA_OPERACIONES+"("+CAMPO_FECHA+","+  CAMPO_TIPO_OPER  +","+CAMPO_TIPO_CAT+","+CAMPO_ID_CTA+","+ CAMPO_MONTO+")"+" VALUES("+"'03/01/2020',"+1+","+3+","+1+","+20+")";
    public static final String INSERT_OPERACIONES3="INSERT INTO "+TABLA_OPERACIONES+"("+CAMPO_FECHA+","+  CAMPO_TIPO_OPER  +","+CAMPO_TIPO_CAT+","+CAMPO_ID_CTA+","+ CAMPO_MONTO+")"+" VALUES("+"'06/01/2020',"+1+","+1+","+1+","+3+")";
    public static final String INSERT_OPERACIONES4="INSERT INTO "+TABLA_OPERACIONES+"("+CAMPO_FECHA+","+  CAMPO_TIPO_OPER  +","+CAMPO_TIPO_CAT+","+CAMPO_ID_CTA+","+ CAMPO_MONTO+")"+" VALUES("+"'08/01/2020',"+1+","+4+","+1+","+1600+")";
    //Egresos
    public static final String INSERT_OPERACIONES10="INSERT INTO "+TABLA_OPERACIONES+"("+CAMPO_FECHA+","+  CAMPO_TIPO_OPER  +","+CAMPO_TIPO_CAT+","+CAMPO_ID_CTA+","+ CAMPO_MONTO+")"+" VALUES("+"'01/01/2020',"+2+","+1+","+1+","+20+")";
    public static final String INSERT_OPERACIONES11="INSERT INTO "+TABLA_OPERACIONES+"("+CAMPO_FECHA+","+  CAMPO_TIPO_OPER  +","+CAMPO_TIPO_CAT+","+CAMPO_ID_CTA+","+ CAMPO_MONTO+")"+" VALUES("+"'02/01/2020',"+2+","+2+","+1+","+200+")";
    public static final String INSERT_OPERACIONES12="INSERT INTO "+TABLA_OPERACIONES+"("+CAMPO_FECHA+","+  CAMPO_TIPO_OPER  +","+CAMPO_TIPO_CAT+","+CAMPO_ID_CTA+","+ CAMPO_MONTO+")"+" VALUES("+"'03/01/2020',"+2+","+3+","+1+","+19+")";
    public static final String INSERT_OPERACIONES13="INSERT INTO "+TABLA_OPERACIONES+"("+CAMPO_FECHA+","+  CAMPO_TIPO_OPER  +","+CAMPO_TIPO_CAT+","+CAMPO_ID_CTA+","+ CAMPO_MONTO+")"+" VALUES("+"'07/01/2020',"+2+","+4+","+1+","+3+")";
    public static final String INSERT_OPERACIONES14="INSERT INTO "+TABLA_OPERACIONES+"("+CAMPO_FECHA+","+  CAMPO_TIPO_OPER  +","+CAMPO_TIPO_CAT+","+CAMPO_ID_CTA+","+ CAMPO_MONTO+")"+" VALUES("+"'08/01/2020',"+2+","+5+","+1+","+100+")";
    //Insert de Categorias
    public static final String INSERT_CAT0="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO  +")"+" VALUES("+"'Sueldo',"+1+")";
    public static final String INSERT_CAT1="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO  +")"+" VALUES("+"'Regalos',"+1+")";
    public static final String INSERT_CAT2="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO  +")"+" VALUES("+"'Premios',"+1+")";
    public static final String INSERT_CAT3="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO  +")"+" VALUES("+"'Ventas',"+1+")";
    public static final String INSERT_CAT4="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO  +")"+" VALUES("+"'Inversiones',"+1+")";

    public static final String INSERT_CAT10="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO  +")"+" VALUES("+"'Transporte',"+2+")";
    public static final String INSERT_CAT11="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO  +")"+" VALUES("+"'Comida',"+2+")";
    public static final String INSERT_CAT12="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO  +")"+" VALUES("+"'Salud',"+2+")";
    public static final String INSERT_CAT13="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO  +")"+" VALUES("+"'Vivienda',"+2+")";
    public static final String INSERT_CAT14="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO  +")"+" VALUES("+"'Educacion',"+2+")";
    public static final String INSERT_CAT15="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO  +")"+" VALUES("+"'Ropa',"+2+")";
    public static final String INSERT_CAT16="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO  +")"+" VALUES("+"'Pagos',"+2+")";

    public static final String INSERT_CAT20="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO  +")"+" VALUES("+"'Transferencia',"+3+")";
    public static final String INSERT_CAT21="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO  +")"+" VALUES("+"'Deposito',"+3+")";
    public static final String INSERT_CAT23="INSERT INTO "+TABLA_CATEGORIAS+"("+CAT_CAMPO_DESCRIPCION+","+  CAT_CAMPO_TIPO  +")"+" VALUES("+"'Retiro',"+3+")";

}
