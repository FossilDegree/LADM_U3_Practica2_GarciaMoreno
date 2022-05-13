package mx.tecnm.tepic.ladm_u3_practica1_garciamoreno.ui.consultar

import android.app.AlertDialog
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import mx.tecnm.tepic.ladm_u3_practica1_garciamoreno.databinding.ActivityActualizarInventarioBinding
import mx.tecnm.tepic.ladm_u3_practica1_garciamoreno.ui.dialog.DatePickerFragment
import java.text.SimpleDateFormat

class ActualizarInventario : AppCompatActivity() {
    lateinit var binding: ActivityActualizarInventarioBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActualizarInventarioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var id = intent.extras!!.getString("id")!!
        val bd = FirebaseFirestore.getInstance()
        bd.collection("INVENTARIO")
            .document(id)
            .get()
            .addOnSuccessListener {
                binding.codigobarras.setText(it.getString("CODIGO"))
                binding.tipoequipo.setText(it.getString("EQUIPO"))
                binding.caracteristicas.setText(it.getString("CARACTERISTICAS"))
                binding.fecha.setText(it.getString("FECHA"))
            }
            .addOnFailureListener {
                AlertDialog.Builder(this)
                    .setMessage(it.message)
                    .show()
            }
        binding.regresar.setOnClickListener {
            finish()
        }
        binding.actualizar.setOnClickListener {
            val bd = FirebaseFirestore.getInstance()
            val fecha = SimpleDateFormat("yyyy-MM-dd").parse(binding.fecha.text.toString())
            bd.collection("INVENTARIO")
                .document(id)
                .update("CODIGO",binding.codigobarras.text.toString(),
                    "EQUIPO", binding.tipoequipo.text.toString(),
                "CARACTERISTICAS",binding.caracteristicas.text.toString(),
                "FECHA", binding.fecha.text.toString())
                .addOnSuccessListener {
                    Toast.makeText(this, "ÉXITO AL ACTUALIZAR", Toast.LENGTH_LONG)
                        .show()

                    binding.codigobarras.text.clear()
                    binding.tipoequipo.text.clear()
                    binding.caracteristicas.text.clear()
                    binding.fecha.text.clear()

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