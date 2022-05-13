package mx.tecnm.tepic.ladm_u3_practica1_garciamoreno.ui.consultar

import android.R
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import mx.tecnm.tepic.ladm_u3_practica1_garciamoreno.databinding.ActivityMostrarConsultaAsignacionBinding

class MostrarConsultaAsignacion : AppCompatActivity() {
    lateinit var binding:ActivityMostrarConsultaAsignacionBinding
    var listaIDs = ArrayList<String>()
    var arr = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMostrarConsultaAsignacionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listaIDs = intent.extras!!.getStringArrayList("ids")!!
        arr = intent.extras!!.getStringArrayList("arr")!!

        binding.lista.adapter = ArrayAdapter(this, R.layout.simple_list_item_1,arr)
        binding.lista.setOnItemClickListener{adapterView, view, i, l ->
            val idSeleccionado = listaIDs.get(i)
            AlertDialog.Builder(this)
                .setTitle("ATENCIÓN")
                .setMessage("¿Qué deseas hacer con el ID:${idSeleccionado}?")
                .setNeutralButton("Eliminar",{d,i->
                    eliminar(idSeleccionado);
                })
                .setPositiveButton("Actualizar",{d,i->
                    actualizar(idSeleccionado)
                })
                .setNegativeButton("Aceptar",{d,i->})
                .show()
        }
    }
    private fun eliminar(id:String){
        val bd = FirebaseFirestore.getInstance()
        bd.collection("ASIGNACION")
            .document(id)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(this,"ÉXITO AL BORRAR", Toast.LENGTH_LONG)
                    .show()
            }
            .addOnFailureListener {
                AlertDialog.Builder(this)
                    .setMessage(it.message)
                    .show()
            }

    }
    private fun actualizar(id_:String){
        var ventana = Intent(this,ActualizarAsignacion::class.java)
        ventana.putExtra("id",id_)
        startActivity(ventana)
    }
}