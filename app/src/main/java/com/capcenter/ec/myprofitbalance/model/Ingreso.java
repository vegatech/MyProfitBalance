package com.capcenter.ec.myprofitbalance.model;

public class Ingreso {

    private int id;
    private String fecha;
    private String tipo_operacion;
    private String tipo_cat;
    private Double monto_operacion;
    private String Descripcion;

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getTipo_cat() {
        return tipo_cat;
    }

    public void setTipo_cat(String tipo_cat) {
        this.tipo_cat = tipo_cat;
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

    public String getTipo_operacion() {
        return tipo_operacion;
    }

    public void setTipo_operacion(String tipo_operacion) {
        this.tipo_operacion = tipo_operacion;
    }

    public Double getMonto_operacion() {
        return monto_operacion;
    }

    public void setMonto_operacion(Double monto_operacion) {
        this.monto_operacion = monto_operacion;
    }




}
