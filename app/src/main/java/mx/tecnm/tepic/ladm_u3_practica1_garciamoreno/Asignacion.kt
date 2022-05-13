package mx.tecnm.tepic.ladm_u3_practica1_garciamoreno

import android.content.ContentValues
import android.database.sqlite.SQLiteException
import androidx.fragment.app.FragmentActivity

class Asignacion(activity: FragmentActivity) {
    private val activity = activity
    var idasignacion = ""
    var nom_empleado = ""
    var area_trabajo = ""
    var fecha = System.currentTimeMillis()
    var codigobarras = ""
    private var err = ""

    fun insertar():Boolean{
        val bd = BD(activity, "EMPRESA", null, 1)
        err = ""
        try{
            val tabla = bd.writableDatabase
            var datos = ContentValues()

            datos.put("IDASIGNACION",idasignacion)
            datos.put("NOM_EMPLEADO",nom_empleado)
            datos.put("AREA_TRABAJO",area_trabajo)
            datos.put("FECHA",fecha)
            datos.put("CODIGOBARRAS",codigobarras)

            val res = tabla.insert("ASIGNACION",null,datos)
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
    fun mostrarTodos():ArrayList<Asignacion>{
        val bd = BD(activity, "EMPRESA", null, 1)
        err = ""
        var arr = ArrayList<Asignacion>()
        try{
            val tabla = bd.readableDatabase
            val SQLSELECT = "SELECT * FROM ASIGNACION"
            var cursor = tabla.rawQuery(SQLSELECT,null)
            val asig = Asignacion(activity)
            if(cursor.moveToFirst()){
                do{
                    asig.idasignacion=cursor.getString(0)
                    asig.nom_empleado=cursor.getString(1)
                    asig.area_trabajo=cursor.getString(2)
                    asig.fecha=cursor.getLong(3)
                    asig.codigobarras=cursor.getString(4)
                    arr.add(asig)
                }while (cursor.moveToNext())
            }
        }catch (err: SQLiteException){
            this.err = err.message!!

        }finally {
            bd.close()
        }
        return arr

    }
    fun mostrarParam(idasignacion_:String,nom_empleado:String,area_trabajo_:String,fecha_:String,codigobarras_:String):ArrayList<Asignacion>{
        val bd = BD(activity, "EMPRESA", null, 1)
        err = ""
        var arr = ArrayList<Asignacion>()
        var params = ArrayList<String>()
        val asig = Asignacion(activity)
        var query = ""
        if(idasignacion_!=""){
            if(!params.isEmpty()){
                query+=" AND "
            }
            params.add(idasignacion_)
            query+=" IDASIGNACION = ? "
        }
        if(nom_empleado!=""){
            if(!params.isEmpty()){
                query+=" AND "
            }
            params.add(nom_empleado)
            query+=" NOM_EMPLEADO = ? "
        }
        if(area_trabajo_!=""){
            if(!params.isEmpty()){
                query+=" AND "
            }
            params.add(area_trabajo_)
            query+=" AREA_TRABAJO = ? "
        }
        if(fecha_!=""){
            if(!params.isEmpty()){
                query+=" AND "
            }
            params.add(fecha_)
            query+=" FECHA = ? "
        }
        if(codigobarras_!=""){
            if(!params.isEmpty()){
                query+=" AND "
            }
            params.add(codigobarras_)
            query+=" CODIGOBARRAS = ? "
        }
        var params2 = arrayOfNulls<String>(params.size)
        params.toArray(params2)
        try{
            val tabla = bd.readableDatabase
            var SQLSELECT = "SELECT * FROM ASIGNACION WHERE "+query
            var cursor = tabla.rawQuery(SQLSELECT,params2)
            if(cursor.moveToFirst()){
                do{
                    asig.idasignacion=cursor.getString(0)
                    asig.nom_empleado=cursor.getString(1)
                    asig.area_trabajo=cursor.getString(2)
                    asig.fecha=cursor.getLong(3)
                    asig.codigobarras=cursor.getString(4)
                    arr.add(asig)
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
            datos.put("NOM_EMPLEADO",nom_empleado)
            datos.put("AREA_TRABAJO",area_trabajo)
            datos.put("FECHA",fecha)
            datos.put("CODIGOBARRAS",codigobarras)

            val res = tabla.update("ASIGNACION",datos,"IDASIGNACION = ?",arrayOf(idasignacion))
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
            val res = tabla.delete("ASIGNACION","IDASIGNACION = ?",arrayOf(idasignacion))
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
    fun eliminar(idasignacion_: String):Boolean{
        val bd = BD(activity, "EMPRESA", null, 1)
        err = ""
        try{
            val tabla = bd.writableDatabase
            val res = tabla.delete("ASIGNACION","IDASIGNACION = ?",arrayOf(idasignacion_))
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