package id.co.woiapp.ui.newtask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.co.woiapp.data.entities.BodyTask
import id.co.woiapp.databinding.FragmentNewtaskBinding
import id.co.woiapp.utils.*
import id.co.woiapp.utils.ConstVal.FRAGMENTRESULT
import java.util.*

@AndroidEntryPoint
class NewtaskFragment : Fragment() {

    private var binding: FragmentNewtaskBinding by autoCleared()
    private val viewModel: NewtaskViewModel by viewModels()
    private var idBook = 0
    private lateinit var date : Date

    companion object {
        const val IDBOOK = "IDBOOK"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewtaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GenerateView.loading(binding.loadingBlocker, requireContext())
        idBook = arguments?.getInt(IDBOOK) ?: 0
        binding.btnTitleBar.setOnClickListener { newtask(idBook) }
        binding.btnBack.setOnClickListener { findNavController().navigateUp() }
        binding.btnDate.setOnClickListener { pickDate() }
        binding.btnOk.setOnClickListener { okDate() }
        setupObservers()
    }

    private fun newtask(idBook: Int) {
        val title = binding.tieTitle.text.toString()
        val desc = binding.tieDesc.text.toString()
        if(binding.tieDueDate.text.isNullOrBlank()){
            SnackBin.show(binding.root, "Lengkapi form")
        }else
        if (title.length > 40) {
            SnackBin.show(binding.root, "Panjang maksimal Judul Task adalah 40")
        } else {
            val body = BodyTask(titleTask = title, idBook = idBook, descriptionTask = desc,dueDate = date)
            viewModel.newtask(body)
        }


    }

    private fun pickDate() {
        binding.llDatePicker.visibility = View.VISIBLE
        binding.btnBack.isEnabled = false
        binding.btnTitleBar.isEnabled = false
        binding.tieTitle.isEnabled = false
        binding.tieDesc.isEnabled = false
        binding.btnDate.isEnabled = false
    }
    private fun okDate(){
        binding.llDatePicker.visibility = View.GONE
        binding.btnBack.isEnabled = true
        binding.btnTitleBar.isEnabled = true
        binding.tieTitle.isEnabled = true
        binding.tieDesc.isEnabled = true
        binding.btnDate.isEnabled = true
        date = binding.datePicker.date
        binding.tieDueDate.setText(DateUtils.dateToStr(date))
//        Log.d("dasdsadad21321",binding.datePicker.date.toString())
//        "Sun Feb 14 14:56:00 GMT+07:00 2021"


//        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
//        val parser2 = SimpleDateFormat("EEE MMM dd HH:mm:ss GMT yyyy")
//        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm")
//        val output: String = formatter.format(parser.parse("2018-12-14T09:55:00"))
//
//        val simpleDateFormat = SimpleDateFormat(pattern)
//        val date = simpleDateFormat.format(Date())

    }

    private fun setupObservers() {
        viewModel.response.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.loadingBlocker.visibility = View.INVISIBLE
                    binding.blocker.visibility = View.INVISIBLE
                    requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    setNavigationResult(FRAGMENTRESULT, "success")

                }
                Resource.Status.ERROR -> {
                    SnackBin.show(binding.root, it.message.toString())
                    binding.loadingBlocker.visibility = View.INVISIBLE
                    binding.blocker.visibility = View.INVISIBLE
                    requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                }

                Resource.Status.LOADING -> {
                    binding.loadingBlocker.visibility = View.VISIBLE
                    binding.blocker.visibility = View.VISIBLE
                    requireActivity().window.setFlags(
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                    )
                }
            }
        })
    }

    private fun setNavigationResult(key: String, result: String) {
        findNavController().navigateUp()
    }

}