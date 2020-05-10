package com.capcenter.ec.myprofitbalance.Entity;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.provider.BaseColumns;
import android.widget.TableLayout;

@Entity (tableName  = Transaccion.TABLE_NAME)
public class Transaccion {
    /** The name of the Cheese table. */
    public static final String TABLE_NAME = "TRANSACCIONES";

    public static final String COLUMN_NAME = "name";

    /** The name of the ID column. */

    public static final String COLUMN_ID = BaseColumns._ID;


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    private int id;

    @ColumnInfo(name = "FECHA")
    private String fecha;

    @ColumnInfo(name = "FECHAINT")
    private int fecha_int;

    @ColumnInfo(name = "TIPO_OPERACION")
    private String tipo_operacion;

    @ColumnInfo(name = "TIPO_CATEGORIA")
    private String tipo_cat;

    @ColumnInfo(name = "MONTO_OPERACION")
    private Double monto_operacion;

    @ColumnInfo(name = "ID_CTA")
    private int id_cta;

    @ColumnInfo(name = "DESCRIPCION")
    private String descripcion;

    public Transaccion(int id, String fecha, int fecha_int, String tipo_operacion, String tipo_cat, Double monto_operacion, int id_cta, String descripcion) {
        this.id = id;
        this.fecha = fecha;
        this.fecha_int = fecha_int;
        this.tipo_operacion = tipo_operacion;
        this.tipo_cat = tipo_cat;
        this.monto_operacion = monto_operacion;
        this.id_cta = id_cta;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getFecha_int() {
        return fecha_int;
    }

    public void setFecha_int(int fecha_int) {
        this.fecha_int = fecha_int;
    }

    public String getTipo_operacion() {
        return tipo_operacion;
    }

    public void setTipo_operacion(String tipo_operacion) {
        this.tipo_operacion = tipo_operacion;
    }

    public String getTipo_cat() {
        return tipo_cat;
    }

    public void setTipo_cat(String tipo_cat) {
        this.tipo_cat = tipo_cat;
    }

    public Double getMonto_operacion() {
        return monto_operacion;
    }

    public void setMonto_operacion(Double monto_operacion) {
        this.monto_operacion = monto_operacion;
    }

    public int getId_cta() {
        return id_cta;
    }

    public void setId_cta(int id_cta) {
        this.id_cta = id_cta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
