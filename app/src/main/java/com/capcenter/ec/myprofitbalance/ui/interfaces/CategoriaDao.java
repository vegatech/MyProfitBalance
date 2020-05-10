package com.capcenter.ec.myprofitbalance.ui.interfaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.capcenter.ec.myprofitbalance.Entity.Categoria;
import com.capcenter.ec.myprofitbalance.Entity.Transaccion;

import java.util.List;

@Dao
public interface CategoriaDao {



    //aqui declaramos los metodos o las acciones para la bd
    //seleccionar cantidades
    @Query("SELECT COUNT(*) FROM " + Categoria.TABLE_NAME)
    int count(); //metodo



    //seleccionar todo
    @Query("SELECT * FROM "+Categoria.TABLE_NAME)
    List<Transaccion> getAllCategorias();


    //insertar
    @Insert
    void insertarAll(Categoria... Categorias);

    //eliminar
    @Query("DELETE FROM " + Categoria.TABLE_NAME + " WHERE " + Categoria.COLUMN_ID + " = :id")
    int deleteById(long id);

    //actualizar
    @Update
    int updateEntidad(Categoria obj);


    //insertar 2
    @Insert
    long insert(Categoria Categorias);





}
