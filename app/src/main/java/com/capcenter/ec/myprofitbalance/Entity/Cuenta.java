package com.capcenter.ec.myprofitbalance.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.provider.BaseColumns;


@Entity(tableName  = Transaccion.TABLE_NAME)
public class Cuenta {

    /** The name of the Cheese table. */
    public static final String TABLE_NAME = "CUENTAS";

    public static final String COLUMN_NAME = "name";

    /** The name of the ID column. */

    public static final String COLUMN_ID = BaseColumns._ID;


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    private int id;

    @ColumnInfo(name = "DESCRIPCION")
    private String descripcion;

    @ColumnInfo(name = "TIPO_CUENTA")
    private String tipo_cta;

    @ColumnInfo(name = "NRO_CTA")
    private String  nro_cta;

    @ColumnInfo(name = "FECHA")
    private Double saldo;

    public Cuenta(int id, String descripcion, String tipo_cta, String nro_cta, Double saldo) {
        this.id = id;
        this.descripcion = descripcion;
        this.tipo_cta = tipo_cta;
        this.nro_cta = nro_cta;
        this.saldo = saldo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo_cta() {
        return tipo_cta;
    }

    public void setTipo_cta(String tipo_cta) {
        this.tipo_cta = tipo_cta;
    }

    public String getNro_cta() {
        return nro_cta;
    }

    public void setNro_cta(String nro_cta) {
        this.nro_cta = nro_cta;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }
}
