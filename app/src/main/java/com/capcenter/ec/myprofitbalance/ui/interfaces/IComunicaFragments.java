package com.capcenter.ec.myprofitbalance.ui.interfaces;

import android.support.v4.app.Fragment;

public interface IComunicaFragments {

    public void showSelectedFragment(Fragment fragment);
    public void llamaTransacciones(int i);
    public void llamaHome();
    public void llamaReportes();
    public void llamaAjustes();

}
