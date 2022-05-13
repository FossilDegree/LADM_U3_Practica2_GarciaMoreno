package mx.tecnm.tepic.ladm_u3_practica1_garciamoreno.ui.consultar

import android.R
import android.app.AlertDialog
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import mx.tecnm.tepic.ladm_u3_practica1_garciamoreno.databinding.ActivityActualizarAsignacionBinding
import mx.tecnm.tepic.ladm_u3_practica1_garciamoreno.ui.dialog.DatePickerFragment

class ActualizarAsignacion : AppCompatActivity() {
    lateinit var binding: ActivityActualizarAsignacionBinding
    var codigo = ""
    var tipoequipo = ""
    var id = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActualizarAsignacionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        id = intent.extras!!.getString("id")!!

        val bd = FirebaseFirestore.getInstance()
        bd.collection("ASIGNACION")
            .document(id)
            .get()
            .addOnSuccessListener {
                binding.empleado.setText(it.getString("NOM_EMPLEADO"))
                binding.area.setText(it.getString("AREA_TRABAJO"))
                binding.fecha.setText(it.getString("FECHA"))
                binding.equipo.setText(it.getString("EQUIPO"))
            }
            .addOnFailureListener {
                AlertDialog.Builder(this)
                    .setMessage(it.message)
                    .show()
            }

        binding.actualizar.setOnClickListener {
            val bd = FirebaseFirestore.getInstance()
            bd.collection("ASIGNACION")
                .document(id)
                .update("NOM_EMPLEADO",binding.empleado.text.toString(),
                "AREA_TRABAJO",binding.area.text.toString(),
                "FECHA",binding.fecha.text.toString(),
                "EQUIPO",codigo,
                "TIPO",tipoequipo)
                .addOnSuccessListener {
                    Toast.makeText(this, "ÉXITO AL ACTUALIZAR", Toast.LENGTH_LONG)
                        .show()
                    binding.empleado.setText("")
                    binding.area.setText("")
                    binding.fecha.setText("")
                }
        }
        binding.equipo.setOnClickListener {
            var bd = FirebaseFirestore.getInstance()
                .collection("INVENTARIO")
                .addSnapshotListener { query, error ->
                    if (error != null) {
                        //ERROR
                        AlertDialog.Builder(this)
                            .setMessage(error.message)
                            .show()
                        return@addSnapshotListener
                    }
                    var arr = ArrayList<String>()
                    var arrCodigos = ArrayList<String>()
                    var arrInfo = ArrayList<Array<String>>()
                    for (documento in query!!) {
                        var cadena = "CODIGO: ${documento.getString("CODIGO")}\n" +
                                "EQUIPO: ${documento.getString("EQUIPO")}\n" +
                                "CARACTERISTICAS: ${documento.getString("CARACTERISTICAS")}\n" +
                                "FECHA: ${documento.getDate("FECHA").toString()}\n"
                        arr.add(cadena)
                        arrInfo.add(
                            arrayOf(
                                documento.getString("CODIGO")!!,
                                documento.getString("EQUIPO")!!
                            )
                        )
                        arrCodigos.add(documento.getString("CODIGO")!!)
                    }
                    binding.lista.adapter = ArrayAdapter(this, R.layout.simple_list_item_1, arr)
                    binding.lista.setOnItemClickListener { adapterView, view, i, l ->
                        binding.equipo.setText(arrInfo.get(i).get(0))
                        codigo = arrInfo.get(i).get(0)
                        tipoequipo = arrInfo.get(i).get(1)
                        arr.clear()
                        binding.lista.adapter = ArrayAdapter(this, R.layout.simple_list_item_1, arr)
                    }
                }

        }
        binding.fecha.setOnClickListener {
            showDatePickerDialog()
        }

    }
    private fun showDatePickerDialog() {
        val newFragment =
            DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
                // +1 because January is zero
                //val selectedDate = day.toString() + " / " + (month + 1) + " / " + year
                val selectedDate = year.toString() + "-" + (month + 1) + "-" + day
                binding.fecha.setText(selectedDate)
            })

        newFragment.show(supportFragmentManager, "datePicker")
    }
}