package id.co.woiapp.ui.detailtask

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
import id.co.woiapp.data.entities.Book
import id.co.woiapp.data.entities.DetailTask
import id.co.woiapp.databinding.FragmentDetailtaskBinding
import id.co.woiapp.ui.booktask.BooktaskFragment
import id.co.woiapp.ui.edittask.EdittaskFragment
import id.co.woiapp.utils.*
import java.util.*

@AndroidEntryPoint
class DetailtaskFragment : Fragment() {

    private var binding : FragmentDetailtaskBinding by autoCleared()
    private val viewModel : DetailtaskViewModel by viewModels()
    private var idTask = 0
    private lateinit var task : DetailTask

    companion object{
        const val IDTASK = "IDTASK"
        const val STATUS = "STATUS"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailtaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GenerateView.loading(binding.loadingBlocker, requireContext())
        idTask = arguments?.getInt(IDTASK)?:0
        val status = arguments?.getString(STATUS,"")
        Log.d("dasdasda434sd",status)
        if (status != "owner") {
            binding.btnTitleBar.visibility = View.GONE
            binding.btnDelete.visibility = View.GONE
        }
        viewModel.getTask(idTask)
        binding.btnBack.setOnClickListener { findNavController().navigateUp() }
        binding.btnTitleBar.setOnClickListener { edit() }
        binding.btnDelete.setOnClickListener {
            deleteTask()
        }
        setupObservers()
    }

    private fun edit(){
        findNavController().navigate(R.id.action_detailtaskFragment_to_edittaskFragment,
            bundleOf(EdittaskFragment.TASK to task )
        )
    }

    private fun deleteTask(){
        viewModel.deletetask(idTask)
    }


    private fun setupObservers() {
        viewModel.tasks.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    if (it.data != null) {
                        task = it.data[0]
                        binding.tvTitle.text = task.titleTask
                        binding.tvDesc.text = task.descriptionTask
                        binding.tvDueDate.text = DateUtils.dateToStr(DateUtils.apiToDate(task.dueDate))
                    }
                    binding.loadingBlocker.visibility = View.INVISIBLE
                    binding.blocker.visibility = View.INVISIBLE
                    requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

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

        viewModel.responseDelete.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.loadingBlocker.visibility = View.INVISIBLE
                    binding.blocker.visibility = View.INVISIBLE
                    requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    backAfterDelete()
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

    private fun backAfterDelete() {
        findNavController().navigateUp()
//        findNavController().navigate(R.id.action_edittaskFragment_to_detailtaskFragment,
//        bundleOf(TASK to task))
    }
}