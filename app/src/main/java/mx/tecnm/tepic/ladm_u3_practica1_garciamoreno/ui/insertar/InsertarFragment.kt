package mx.tecnm.tepic.ladm_u3_practica1_garciamoreno.ui.insertar

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
//import mx.tecnm.tepic.ladm_u3_practica1_garciamoreno.databinding.FragmentGalleryBinding
import mx.tecnm.tepic.ladm_u3_practica1_garciamoreno.databinding.FragmentInsertarBinding

class InsertarFragment : Fragment() {

    private var _binding: FragmentInsertarBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(InsertarViewModel::class.java)

        _binding = FragmentInsertarBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.insInv.setOnClickListener {
            val inv = Intent(requireContext(),InsertarInventario::class.java)
            startActivity(inv)
        }
        binding.insAsig.setOnClickListener {
            val inv = Intent(requireContext(),InsertarAsignacion::class.java)
            startActivity(inv)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}