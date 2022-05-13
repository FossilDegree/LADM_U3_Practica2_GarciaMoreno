package mx.tecnm.tepic.ladm_u3_practica1_garciamoreno

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BD(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int) :
    SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(bd: SQLiteDatabase) {
        bd.execSQL("CREATE TABLE INVENTARIO(CODIGOBARRAS VARCHAR(50) PRIMARY KEY,TIPOEQUIPO VARCHAR(200),CARACTERISTICAS VARCHAR(500),FECHACOMPRA DATE);")
        bd.execSQL("CREATE TABLE ASIGNACION(IDASIGNACION INTEGER PRIMARY KEY AUTOINCREMENT,NOM_EMPLEADO VARCHAR(200),AREA_TRABAJO VARCHAR(50),FECHA DATE,CODIGOBARRAS VARCHAR(50),FOREIGN KEY(CODIGOBARRAS) REFERENCES INVENTARIO(CODIGOBARRAS) ON DELETE SET NULL);")

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

}