package mx.tecnm.tepic.ladm_u3_practica1_garciamoreno.ui.insertar

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.FirebaseFirestore
import mx.tecnm.tepic.ladm_u3_practica1_garciamoreno.Inventario
import mx.tecnm.tepic.ladm_u3_practica1_garciamoreno.databinding.ActivityInsertarInventarioBinding
import mx.tecnm.tepic.ladm_u3_practica1_garciamoreno.ui.dialog.DatePickerFragment
import java.text.SimpleDateFormat
import java.time.LocalDateTime

class InsertarInventario : AppCompatActivity() {
    lateinit var binding: ActivityInsertarInventarioBinding
    var tipo = ""
    val tipos = arrayOf("PC","Laptop","Tableta","Impresora")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertarInventarioBinding.inflate(layoutInflater)
        setContentView(binding.root)
       // var adapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,tipos)
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //binding.tipoequipo.adapter=adapter


        binding.insertar.setOnClickListener {


            //¡val fecha = SimpleDateFormat("yyyy-MM-dd").parse(binding.fecha.text.toString())
            val bd = FirebaseFirestore.getInstance()
            val datos = hashMapOf(
                "CODIGO" to binding.codigobarras.text.toString(),
                "EQUIPO" to binding.tipoequipo.text.toString(),
                "CARACTERISTICAS" to binding.caracteristicas.text.toString(),
                "FECHA" to binding.fecha.text.toString()
            )
            bd.collection("INVENTARIO")
                .add(datos)
                .addOnSuccessListener {
                    Toast.makeText(this,"ÉXITO AL INSERTAR",Toast.LENGTH_LONG)
                        .show()
                    binding.codigobarras.setText("")
                    binding.tipoequipo.setText("")
                    binding.caracteristicas.setText("")

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