package id.co.woiapp.ui.createbook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import id.co.woiapp.R
import id.co.woiapp.databinding.FragmentCreatebookBinding
import id.co.woiapp.databinding.FragmentNewbookBinding
import id.co.woiapp.utils.autoCleared

class CreatebookFragment : Fragment() {

    private var binding: FragmentCreatebookBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreatebookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener{
            findNavController().navigateUp()
        }
        binding.btnBookBaru.setOnClickListener {
            findNavController().navigate(R.id.action_createbookFragment2_to_newbookFragment)
        }
        binding.btnGabungBook.setOnClickListener {
            findNavController().navigate(R.id.action_createbookFragment2_to_joinbookFragment2)
        }
    }
}