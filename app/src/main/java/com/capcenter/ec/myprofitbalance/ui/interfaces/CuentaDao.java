package com.capcenter.ec.myprofitbalance.ui.interfaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.capcenter.ec.myprofitbalance.Entity.Cuenta;
import com.capcenter.ec.myprofitbalance.Entity.Transaccion;

import java.util.List;

@Dao
public interface CuentaDao {

    //aqui declaramos los metodos o las acciones para la bd
    //seleccionar cantidades
    @Query("SELECT COUNT(*) FROM " + Cuenta.TABLE_NAME)
    int count(); //metodo



    //seleccionar todo
    @Query("SELECT * FROM "+Cuenta.TABLE_NAME)
    List<Cuenta> getAllCuentas();


    //insertar
    @Insert
    void insertarAll(Cuenta... Cuentas);

    //eliminar
    @Query("DELETE FROM " + Cuenta.TABLE_NAME + " WHERE " + Cuenta.COLUMN_ID + " = :id")
    int deleteById(long id);

    //actualizar
    @Update
    int updateEntidad(Cuenta obj);


    //insertar 2
    @Insert
    long insert(Cuenta usuarios);



}
