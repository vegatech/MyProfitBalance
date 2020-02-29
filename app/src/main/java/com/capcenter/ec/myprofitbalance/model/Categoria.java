package com.capcenter.ec.myprofitbalance.model;

public class Categoria {

    private int id;
    private String descripcat;
    private int tipoCat;
    private String Drawable;
    private String Color;



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
        return Drawable;
    }

    public void setDrawable(String drawable) {
        Drawable = drawable;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }
}
