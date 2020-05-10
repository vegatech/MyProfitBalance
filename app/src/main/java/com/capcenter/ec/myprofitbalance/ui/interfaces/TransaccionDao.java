package com.capcenter.ec.myprofitbalance.ui.interfaces;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.capcenter.ec.myprofitbalance.Entity.Transaccion;

import java.util.List;

@Dao
public interface TransaccionDao {


    //aqui declaramos los metodos o las acciones para la bd
    //seleccionar cantidades
    @Query("SELECT COUNT(*) FROM " + Transaccion.TABLE_NAME)
    int count(); //metodo



    //seleccionar todo
    @Query("SELECT * FROM "+Transaccion.TABLE_NAME)
    List<Transaccion> getAllTransacciones();


    //insertar
    @Insert
    void insertarAll(Transaccion... Transacciones);

    //eliminar
    @Query("DELETE FROM " + Transaccion.TABLE_NAME + " WHERE " + Transaccion.COLUMN_ID + " = :id")
    int deleteById(long id);

    //actualizar
    @Update
    int updateEntidad(Transaccion obj);


    //insertar 2
    @Insert
    long insert(Transaccion usuarios);




}
