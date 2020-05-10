package com.capcenter.ec.myprofitbalance.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.provider.BaseColumns;


@Entity(tableName  = Transaccion.TABLE_NAME)
public class Categoria {

    /** The name of the Cheese table. */
    public static final String TABLE_NAME = "CATEGORIAS";

    public static final String COLUMN_NAME = "name";

    /** The name of the ID column. */

    public static final String COLUMN_ID = BaseColumns._ID;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    private int id;

    @ColumnInfo(name = "DESCRIPCION")
    private String descripcat;

    @ColumnInfo(name = "TIPO_TRANSACCION")
    private int tipoCat;

    @ColumnInfo(name = "DRAWABLE")
    private String drawable;

    @ColumnInfo(name = "COLOR")
    private String color;

    public Categoria(int id, String descripcat, int tipoCat, String drawable, String color) {
        this.id = id;
        this.descripcat = descripcat;
        this.tipoCat = tipoCat;
        this.drawable = drawable;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcat() {
        return descripcat;
    }

    public void setDescripcat(String descripcat) {
        this.descripcat = descripcat;
    }

    public int getTipoCat() {
        return tipoCat;
    }

    public void setTipoCat(int tipoCat) {
        this.tipoCat = tipoCat;
    }

    public String getDrawable() {
        return drawable;
    }

    public void setDrawable(String drawable) {
        this.drawable = drawable;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
