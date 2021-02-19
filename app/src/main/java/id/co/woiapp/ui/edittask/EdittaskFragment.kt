package id.co.woiapp.ui.edittask

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.co.woiapp.R
import id.co.woiapp.data.entities.BodyTask
import id.co.woiapp.data.entities.DetailTask
import id.co.woiapp.databinding.FragmentEdittaskBinding
import id.co.woiapp.utils.*
import java.util.*

@AndroidEntryPoint
class EdittaskFragment : Fragment() {

    private var binding: FragmentEdittaskBinding by autoCleared()
    private val viewModel: EdittaskViewModel by viewModels()
    private lateinit var task : DetailTask
    private lateinit var date : Date


    companion object{
        const val TASK = "TASK"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEdittaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GenerateView.loading(binding.loadingBlocker, requireContext())
        task = requireArguments().getParcelable(TASK)?:DetailTask()
        binding.tieTitle.setText(task.titleTask)
        binding.tieDesc.setText(task.descriptionTask)
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnTitleBar.setOnClickListener {
            editTask()
        }
        date = DateUtils.apiToDate(task.dueDate)
        binding.tieDueDate.setText(DateUtils.dateToStr(date))
        binding.tieDueDate.setText(DateUtils.dateToStr(date))
        binding.btnDate.setOnClickListener { pickDate() }
        binding.btnOk.setOnClickListener { okDate() }
        setupObservers()
    }

    private fun editTask() {
        val title = binding.tieTitle.text.toString()
        val desc = binding.tieDesc.text.toString()
        if(binding.tieDueDate.text.isNullOrBlank()){
            SnackBin.show(binding.root, "Lengkapi form")
        }else
        if(title.length > 40){
            SnackBin.show(binding.root, "Panjang maksimal Judul Task adalah 40")
        }else{

            val body = DetailTask(idTask = task.idTask, titleTask = title, idBook = task.idBook, descriptionTask = desc,dueDate = DateUtils.dateToStrApi(date))

            viewModel.edittask(body)
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

    }


    private fun setupObservers() {
        viewModel.response.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.loadingBlocker.visibility = View.INVISIBLE
                    binding.blocker.visibility = View.INVISIBLE
                    requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    back()
                }
                Resource.Status.ERROR -> {
                    SnackBin.show(binding.root, it.message.toString())
                    binding.loadingBlocker.visibility = View.INVISIBLE
                    binding.blocker.visibility = View.INVISIBLE
                    requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                }

                Resource.Status.LOADING ->{
                    binding.loadingBlocker.visibility = View.VISIBLE
                    binding.blocker.visibility = View.VISIBLE
                    requireActivity().window.setFlags(
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                    );
                }
            }
        })

    }

    private fun back() {
        findNavController().navigateUp()
//        findNavController().navigate(R.id.action_edittaskFragment_to_detailtaskFragment,
//        bundleOf(TASK to task))
    }

}