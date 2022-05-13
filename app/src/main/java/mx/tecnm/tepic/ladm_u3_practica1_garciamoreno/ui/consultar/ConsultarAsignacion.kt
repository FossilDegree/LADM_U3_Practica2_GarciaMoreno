package mx.tecnm.tepic.ladm_u3_practica1_garciamoreno.ui.consultar

import android.R
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import mx.tecnm.tepic.ladm_u3_practica1_garciamoreno.databinding.ActivityConsultarAsignacionBinding

class ConsultarAsignacion : AppCompatActivity() {
    lateinit var binding:ActivityConsultarAsignacionBinding
    var codigo = ""
    var tipoequipo = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConsultarAsignacionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.equipo.setOnClickListener {
            var bd = FirebaseFirestore.getInstance()
                .collection("INVENTARIO")
                .addSnapshotListener{ query,error->
                    if(error!=null){
                        //ERROR
                        AlertDialog.Builder(this)
                            .setMessage(error.message)
                            .show()
                        return@addSnapshotListener
                    }
                    var arr = ArrayList<String>()
                    var arrCodigos = ArrayList<String>()
                    var arrInfo = ArrayList<Array<String>>()
                    for(documento in query!!){
                        var cadena = "CODIGO: ${documento.getString("CODIGO")}\n"+
                                "EQUIPO: ${documento.getString("EQUIPO")}\n" +
                                "CARACTERISTICAS: ${documento.getString("CARACTERISTICAS")}\n" +
                                "FECHA: ${documento.getDate("FECHA").toString()}\n"
                        arr.add(cadena)
                        arrInfo.add(arrayOf(documento.getString("CODIGO")!!,documento.getString("EQUIPO")!!))
                        arrCodigos.add(documento.getString("CODIGO")!!)
                    }
                    binding.lista.adapter = ArrayAdapter(this, R.layout.simple_list_item_1,arr)
                    binding.lista.setOnItemClickListener{adapterView, view, i, l ->
                        binding.equipo.setText(arrInfo.get(i).get(0))
                        codigo = arrInfo.get(i).get(0)
                        tipoequipo = arrInfo.get(i).get(1)
                        arr.clear()
                        binding.lista.adapter = ArrayAdapter(this, R.layout.simple_list_item_1,arr)
                    }
                }
        }
        binding.consultar.setOnClickListener {
            var listaIDs = ArrayList<String>();
            var bd = FirebaseFirestore.getInstance()
                .collection("ASIGNACION")

                .addSnapshotListener{ query,error->
                    if(error!=null){
                        //ERROR
                        AlertDialog.Builder(this)
                            .setMessage(error.message)
                            .show()
                        return@addSnapshotListener
                    }
                    var arr = ArrayList<String>()
                    listaIDs.clear()
                    for(documento in query!!){
                        var cadena = "IDASIGNACION: ${documento.getLong("IDASIGNACION").toString()}\n"+
                                "NOM_EMPLEADO: ${documento.getString("NOM_EMPLEADO")}\n" +
                                "AREA_TRABAJO: ${documento.getString("AREA_TRABAJO")}\n" +
                                "FECHA: ${documento.getString("FECHA")}\n" +
                                "EQUIPO: ${documento.getString("EQUIPO")}\n" +
                                "TIPO: ${documento.getString("TIPO")}"
                        arr.add(cadena)
                        listaIDs.add(documento.id)
                    }
                    val inv = Intent(this,MostrarConsultaAsignacion::class.java)
                    inv.putExtra("arr",arr)
                    inv.putExtra("ids",listaIDs)
                    startActivity(inv)
                }

        }
        binding.consulta.setOnClickListener {
            var listaIDs = ArrayList<String>();
            var arr = ArrayList<String>()


            val idasig = binding.idasig.text.toString()
            val empleado = binding.empleado.text.toString()
            val area = binding.area.text.toString()
            val fecha = binding.fecha.text.toString()
            val equipo = binding.equipo.text.toString()

            if(idasig.equals("")&&empleado.equals("")&&area.equals("")&&fecha.equals("")&&equipo.equals("")){
                return@setOnClickListener
            }
            var x :Query
            var query = FirebaseFirestore.getInstance().collection("ASIGNACION")
            x = query.whereNotEqualTo("IDASIGNACION",-1)
            if(!idasig.equals("")){
                x = x.whereEqualTo("IDASIGNACION",idasig.toInt())
            }
            if(!empleado.equals("")){
                x = x.whereEqualTo("NOM_EMPLEADO",empleado)
            }
            if(!area.equals("")){
                x = x.whereEqualTo("AREA_TRABAJO",area)
            }
            if(!fecha.equals("")){
                x = x.whereEqualTo("FECHA",fecha)
            }
            if(!equipo.equals("")){
                x = x.whereEqualTo("EQUIPO",equipo)
            }
            x.addSnapshotListener{query_,error->
                if(error!=null){
                    //ERROR
                    AlertDialog.Builder(this)
                        .setMessage(error.message)
                        .show()
                    return@addSnapshotListener
                }
                listaIDs.clear()
                for(documento in query_!!){
                    var cadena = "IDASIGNACION: ${documento.getLong("IDASIGNACION").toString()}\n"+
                            "NOM_EMPLEADO: ${documento.getString("NOM_EMPLEADO")}\n" +
                            "AREA_TRABAJO: ${documento.getString("AREA_TRABAJO")}\n" +
                            "FECHA: ${documento.getString("FECHA")}\n" +
                            "EQUIPO: ${documento.getString("EQUIPO")}\n" +
                            "TIPO: ${documento.getString("TIPO")}"
                    arr.add(cadena)
                    listaIDs.add(documento.id)
                }
                val inv = Intent(this,MostrarConsultaAsignacion::class.java)
                inv.putExtra("arr",arr)
                inv.putExtra("ids",listaIDs)
                startActivity(inv)
            }

        }
    }

}