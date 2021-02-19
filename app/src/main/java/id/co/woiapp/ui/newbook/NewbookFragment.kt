package id.co.woiapp.ui.newbook

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.co.woiapp.R
import id.co.woiapp.data.entities.Book
import id.co.woiapp.databinding.FragmentNewbookBinding
import id.co.woiapp.ui.booktask.BooktaskFragment
import id.co.woiapp.utils.*

@AndroidEntryPoint
class NewbookFragment : Fragment() {

    private var binding : FragmentNewbookBinding by autoCleared()
    private val viewModel : NewbookViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewbookBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GenerateView.loading(binding.loading, requireContext())
        binding.btnTitleBar.setOnClickListener { newbook() }
        binding.btnBack.setOnClickListener{
            findNavController().navigateUp()
        }
        setupObservers()
    }



    private fun newbook() {
        val title = binding.tieTitle.text.toString()
        val desc = binding.tieDesc.text.toString()
        if (title.length > 40){
            SnackBin.show(binding.root, "Panjang maksimal Judul Book adalah 40")
        }else{
            val idUser = SharedPref.getIntVal(SharedPref.IDUSER,requireActivity())
            val body = Book(idUser = idUser, title = title,description = desc)
            viewModel.newbook(body)
        }

    }

    private fun setupObservers() {
        viewModel.response.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    it.data?.get(0)?.let { it1 -> goToBook(it1) }
                    binding.loading.visibility = View.INVISIBLE
                }
                Resource.Status.ERROR -> {
                    SnackBin.show(binding.root, it.message.toString())
                    binding.loading.visibility = View.INVISIBLE
                }

                Resource.Status.LOADING ->
                    binding.loading.visibility = View.VISIBLE
            }
        })
    }

    private fun goToBook(book : Book) {
        findNavController().navigate(R.id.action_newbookFragment_to_booktaskFragment,
            bundleOf(BooktaskFragment.BOOK to book)
        )
    }

}