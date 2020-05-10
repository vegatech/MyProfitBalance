package com.capcenter.ec.myprofitbalance.io;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.capcenter.ec.myprofitbalance.Entity.Categoria;
import com.capcenter.ec.myprofitbalance.Entity.Cuenta;
import com.capcenter.ec.myprofitbalance.Entity.Transaccion;
import com.capcenter.ec.myprofitbalance.ui.interfaces.CategoriaDao;
import com.capcenter.ec.myprofitbalance.ui.interfaces.CuentaDao;
import com.capcenter.ec.myprofitbalance.ui.interfaces.TransaccionDao;

@Database(entities = {Transaccion.class, Categoria.class, Cuenta.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    @SuppressWarnings("WeakerAccess")
    public abstract TransaccionDao transaccionDao(); //que permisos va tener listar, eliminar, update, insertar

    public abstract CuentaDao cuentaDao(); //
    public abstract CategoriaDao categoriaDao(); //
    /**
     * The only instance
     */
    private static AppDatabase sInstance; //variable.

}
