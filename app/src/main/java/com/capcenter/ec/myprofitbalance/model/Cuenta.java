package com.capcenter.ec.myprofitbalance.model;

public class Cuenta {

    private int id;
    private String DESCRIPCION;
    private String TIPO_CUENTA;
    private String NRO_CTA;
    private String SALDO;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDESCRIPCION() {
        return DESCRIPCION;
    }

    public void setDESCRIPCION(String DESCRIPCION) {
        this.DESCRIPCION = DESCRIPCION;
    }

    public String getTIPO_CUENTA() {
        return TIPO_CUENTA;
    }

    public void setTIPO_CUENTA(String TIPO_CUENTA) {
        this.TIPO_CUENTA = TIPO_CUENTA;
    }

    public String getNRO_CTA() {
        return NRO_CTA;
    }

    public void setNRO_CTA(String NRO_CTA) {
        this.NRO_CTA = NRO_CTA;
    }

    public String getSALDO() {
        return SALDO;
    }

    public void setSALDO(String SALDO) {
        this.SALDO = SALDO;
    }
}
