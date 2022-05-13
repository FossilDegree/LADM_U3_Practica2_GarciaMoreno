package mx.tecnm.tepic.ladm_u3_practica1_garciamoreno.ui.consultar

import android.R
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import mx.tecnm.tepic.ladm_u3_practica1_garciamoreno.databinding.ActivityConsultarInventarioBinding
import mx.tecnm.tepic.ladm_u3_practica1_garciamoreno.databinding.FragmentConsultarBinding
import mx.tecnm.tepic.ladm_u3_practica1_garciamoreno.ui.dialog.DatePickerFragment
import java.text.SimpleDateFormat

class ConsultarInventario : AppCompatActivity() {
    lateinit var binding:ActivityConsultarInventarioBinding
    var listaIDs = ArrayList<String>();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConsultarInventarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mostrartodo.setOnClickListener {

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
                    listaIDs.clear()
                    for(documento in query!!){
                        var cadena = "CODIGO: ${documento.getString("CODIGO")}\n"+
                                "EQUIPO: ${documento.getString("EQUIPO")}\n" +
                                "CARACTERISTICAS: ${documento.getString("CARACTERISTICAS")}\n" +
                                "FECHA: ${documento.getDate("FECHA").toString()}\n"
                        arr.add(cadena)
                        listaIDs.add(documento.id)
                    }
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



        }
        binding.consultar.setOnClickListener {
            val cod = binding.codigobarras.text.toString()
            val tipo = binding.tipoequipo.text.toString()
            val car = binding.caracteristicas.text.toString()
           // val fecha1 = binding.fecha1.text.toString()
           // val fecha2 = binding.fecha2.text.toString()

           // val fecha1 = SimpleDateFormat("yyyy-MM-dd").parse(binding.fecha1.text.toString())
           // val fecha2 = SimpleDateFormat("yyyy-MM-dd").parse(binding.fecha2.text.toString())

            if(cod.equals("")&&tipo.equals("")&&car.equals("")){
                return@setOnClickListener
            }
            var x :Query
            var query = FirebaseFirestore.getInstance().collection("INVENTARIO")
            x = query.whereNotEqualTo("CODIGO","")
            if(!cod.equals("")){
                x = x.whereEqualTo("CODIGO",cod)
            }
            if(!tipo.equals("")){

                x=x.whereEqualTo("EQUIPO",tipo)
            }
            if(!car.equals("")){
                x=x.whereEqualTo("CARACTERISTICAS",car)
            }
            /*
            if(!(fecha1.equals("")&&fecha2.equals(""))){
                val fecha1_f = SimpleDateFormat("yyyy-MM-dd").parse(fecha1).time
                val fecha2_f = SimpleDateFormat("yyyy-MM-dd").parse(fecha2).time
                x=x.whereGreaterThanOrEqualTo("FECHA",fecha1_f)
                    .whereLessThanOrEqualTo("FECHA",fecha2_f)
            }*/
            x.addSnapshotListener{query_,error->
                if(error!=null){
                    //ERROR
                    AlertDialog.Builder(this)
                        .setMessage(error.message)
                        .show()
                    return@addSnapshotListener
                }
                    var arr = ArrayList<String>()
                    listaIDs.clear()
                    for(documentos in query_!!){
                        var cadena = "CODIGO: ${documentos.getString("CODIGO")}\n"+
                                "EQUIPO: ${documentos.getString("EQUIPO")}\n" +
                                "CARACTERISTICAS: ${documentos.getString("CARACTERISTICAS")}\n" +
                                "FECHA: ${documentos.getDate("FECHA").toString()}\n"
                        arr.add(cadena)
                        listaIDs.add(documentos.id)
                    }
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

        }
        /*
        binding.fecha1.setOnClickListener {
            showDatePickerDialog1()
        }
        binding.fecha2.setOnClickListener {
            showDatePickerDialog2()
        }*/

    }
    private fun eliminar(id:String){
        val bd = FirebaseFirestore.getInstance()
        bd.collection("INVENTARIO")
            .document(id)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(this,"ÉXITO AL BORRAR",Toast.LENGTH_LONG)
                    .show()
            }
            .addOnFailureListener {
                AlertDialog.Builder(this)
                    .setMessage(it.message)
                    .show()
            }

    }
    private fun actualizar(id:String){
        var ventana = Intent(this,ActualizarInventario::class.java)
        ventana.putExtra("id",id)
        startActivity(ventana)
    }
    /*
    private fun showDatePickerDialog1() {
        val newFragment = DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
            // +1 because January is zero
            //val selectedDate = day.toString() + " / " + (month + 1) + " / " + year
            val selectedDate = year.toString()+"-"+ (month + 1) +"-"+day
            binding.fecha1.setText(selectedDate)
        })

        newFragment.show(supportFragmentManager, "datePicker")
    }
    private fun showDatePickerDialog2() {
        val newFragment = DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
            // +1 because January is zero
            //val selectedDate = day.toString() + " / " + (month + 1) + " / " + year
            val selectedDate = year.toString()+"-"+ (month + 1) +"-"+day
            binding.fecha2.setText(selectedDate)
        })

        newFragment.show(supportFragmentManager, "datePicker")
    }
     */

}