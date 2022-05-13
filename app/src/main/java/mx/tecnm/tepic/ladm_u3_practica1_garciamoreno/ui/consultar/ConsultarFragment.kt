package mx.tecnm.tepic.ladm_u3_practica1_garciamoreno.ui.consultar

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import mx.tecnm.tepic.ladm_u3_practica1_garciamoreno.databinding.FragmentConsultarBinding
//import mx.tecnm.tepic.ladm_u3_practica1_garciamoreno.databinding.FragmentHomeBinding

class ConsultarFragment : Fragment() {

    private var _binding: FragmentConsultarBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(ConsultarViewModel::class.java)

        _binding = FragmentConsultarBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.consInv.setOnClickListener {
            val inv = Intent(requireContext(),ConsultarInventario::class.java)
            startActivity(inv)
        }
        binding.consAsig.setOnClickListener {
            val inv = Intent(requireContext(),ConsultarAsignacion::class.java)
            startActivity(inv)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}