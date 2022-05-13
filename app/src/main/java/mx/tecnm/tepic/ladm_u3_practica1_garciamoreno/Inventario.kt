package mx.tecnm.tepic.ladm_u3_practica1_garciamoreno

import android.app.Activity
import android.content.ContentValues
import android.database.sqlite.SQLiteException
import androidx.fragment.app.FragmentActivity



class Inventario(activity: Activity) {
    private val activity = activity
    var codigobarras=""
    var tipoequipo = ""
    var caracteristicas = ""
    var fechacompra=0L
    private var err = ""

    fun insertar():Boolean{
        val bd = BD(activity, "EMPRESA", null, 1)
        err = ""
        try {
            val tabla = bd.writableDatabase
            var datos = ContentValues()
            datos.put("CODIGOBARRAS",codigobarras)
            datos.put("TIPOEQUIPO",tipoequipo)
            datos.put("CARACTERISTICAS",caracteristicas)
            datos.put("FECHACOMPRA",fechacompra)
            val res = tabla.insert("INVENTARIO",null,datos)
            if(res==-1L){
                return false
            }
        }catch (err: SQLiteException){
            this.err = err.message!!
            return false
        }finally {
            bd.close()
        }
        return true
    }

    fun mostrarTodos(): ArrayList<Inventario> {
        val bd = BD(activity, "EMPRESA", null, 1)
        err = ""
        var arr = ArrayList<Inventario>()
        try{
            val tabla = bd.readableDatabase
            val SQLSELECT = "SELECT * FROM INVENTARIO"
            var cursor = tabla.rawQuery(SQLSELECT,null)
            val inv = Inventario(activity)
            if(cursor.moveToFirst()){
                do{
                    inv.codigobarras=cursor.getString(0)
                    inv.tipoequipo=cursor.getString(1)
                    inv.caracteristicas=cursor.getString(2)
                    inv.fechacompra=cursor.getLong(3)
                    arr.add(inv)
                }while (cursor.moveToNext())
            }
        }catch (err: SQLiteException){
            this.err = err.message!!

        }finally {
            bd.close()
        }
        return arr
    }
    fun mostrarParam(codigobarras_:String,tipoequipo_:String,caracteristicas_:String,fecha1:String,fecha2:String): ArrayList<Inventario> {
        val bd = BD(activity, "EMPRESA", null, 1)
        err = ""
        val inv = Inventario(activity)
        var arr = ArrayList<Inventario>()
        var params = ArrayList<String>()
        var query = ""

        if(codigobarras_!=""){
            if(!params.isEmpty()){
                query+=" AND "
            }
            params.add(codigobarras_)
            query+=" CODIGOBARRAS = ? "
        }
        if(tipoequipo_!=""){
            if(!params.isEmpty()){
                query+=" AND "
            }
            params.add(tipoequipo_)
            query+=" TIPOEQUIPO = ? "
        }
        if(caracteristicas_!=""){
            if(!params.isEmpty()){
                query+=" AND "
            }
            params.add(caracteristicas_)
            query+=" CARACTERISTICAS = ? "
        }
        if(fecha1!="" && fecha2!=""){
            if(!params.isEmpty()){
                query+=" AND "
            }

            params.add(fecha1)
            params.add(fecha2)
            query+=" FECHACOMPRA BETWEEN ? AND ? "
        }
        //var params2 = Array<String>()
        var params2 = arrayOfNulls<String>(params.size)
        params.toArray(params2)


        try{
            val tabla = bd.readableDatabase
            var SQLSELECT = "SELECT * FROM INVENTARIO WHERE "+query
            var cursor = tabla.rawQuery(SQLSELECT,params2)
            if(cursor.moveToFirst()){
                do{
                    inv.codigobarras=cursor.getString(0)
                    inv.tipoequipo=cursor.getString(1)
                    inv.caracteristicas=cursor.getString(2)
                    inv.fechacompra=cursor.getLong(3)
                    arr.add(inv)
                }while (cursor.moveToNext())
            }
        }catch (err: SQLiteException){
            this.err = err.message!!

        }finally {
            bd.close()
        }
        return arr
    }
    fun actualizar():Boolean{
        val bd = BD(activity, "EMPRESA", null, 1)
        err = ""
        try{
            val tabla = bd.writableDatabase
            val datos = ContentValues()
            datos.put("TIPOEQUIPO",tipoequipo)
            datos.put("CARACTERISTICAS",caracteristicas)
            datos.put("FECHACOMPRA",fechacompra)

            val res = tabla.update("INVENTARIO",datos,"CODIGOBARRAS = ?", arrayOf(codigobarras))
            if(res==0){
                return false
            }

        }catch (err: SQLiteException){
            this.err = err.message!!
            return false

        }finally {
            bd.close()
        }
        return true
    }
    fun eliminar(codigobarras_: String):Boolean{
        val bd = BD(activity, "EMPRESA", null, 1)
        err = ""
        try{
            val tabla = bd.writableDatabase
            val res = tabla.delete("INVENTARIO","CODIGOBARRAS = ?", arrayOf(codigobarras_))
            if(res==0){
                return false
            }
        }catch (err: SQLiteException){
            this.err = err.message!!
            return false
        }finally {
            bd.close()
        }
        return true
    }
    fun eliminar():Boolean{
        val bd = BD(activity, "EMPRESA", null, 1)
        err = ""
        try{
            val tabla = bd.writableDatabase
            val res = tabla.delete("INVENTARIO","CODIGOBARRAS = ?", arrayOf(codigobarras))
            if(res==0){
                return false
            }
        }catch (err: SQLiteException){
            this.err = err.message!!
            return false
        }finally {
            bd.close()
        }
        return true
    }



}