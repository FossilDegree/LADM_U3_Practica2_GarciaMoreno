package mx.tecnm.tepic.ladm_u3_practica1_garciamoreno.ui.insertar

import android.R
import android.app.AlertDialog
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import mx.tecnm.tepic.ladm_u3_practica1_garciamoreno.databinding.ActivityInsertarAsignacionBinding
import mx.tecnm.tepic.ladm_u3_practica1_garciamoreno.ui.dialog.DatePickerFragment
import java.text.SimpleDateFormat

class InsertarAsignacion : AppCompatActivity() {
    lateinit var binding : ActivityInsertarAsignacionBinding
    var codigo = ""
    var tipoequipo = ""
    var id = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertarAsignacionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseFirestore.getInstance()
            .collection("ASIGNACION")
            .orderBy("IDASIGNACION",Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .addOnSuccessListener {
                for(i in it){
                    id = i.getLong("IDASIGNACION")!!
                }
            }
            .addOnFailureListener {

            }






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

        binding.insertar.setOnClickListener {
            val fecha = SimpleDateFormat("yyyy-MM-dd").parse(binding.fecha.text.toString())

            var bd = FirebaseFirestore.getInstance()

            val datos = hashMapOf(
                "IDASIGNACION" to ++id,
                "NOM_EMPLEADO" to binding.empleado.text.toString(),
                "AREA_TRABAJO" to binding.area.text.toString(),
                "FECHA" to binding.fecha.text.toString(),
                "EQUIPO" to codigo,
                "TIPO" to tipoequipo
            )
            bd.collection("ASIGNACION")
                .add(datos)
                .addOnSuccessListener {
                    binding.empleado.setText("")
                    binding.area.setText("")
                    binding.fecha.setText("")
                    binding.equipo.setText("")
                    Toast.makeText(this, "ÉXITO AL INSERTAR", Toast.LENGTH_LONG)
                        .show()
                }
                .addOnFailureListener {
                    AlertDialog.Builder(this)
                        .setMessage(it.message)
                        .show()
                }

        }
        binding.fecha.setOnClickListener {
            showDatePickerDialog()
        }

    }
    private fun showDatePickerDialog() {
        val newFragment = DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
            // +1 because January is zero
            //val selectedDate = day.toString() + " / " + (month + 1) + " / " + year
            val selectedDate = year.toString()+"-"+ (month + 1) +"-"+day
            binding.fecha.setText(selectedDate)
        })

        newFragment.show(supportFragmentManager, "datePicker")
    }
}